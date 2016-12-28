package com.example.simplewifidirectchat;

import android.app.ActionBar;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements WifiP2pManager.ConnectionInfoListener {

    private WifiManager mWiFi;
    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
    private BroadcastReceiver mReceiver;
    private final IntentFilter mIntentFilter = new IntentFilter();

    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;
    private ConnectedServerThread mConnectedServerThread;
    private InputStream mInStream;
    private OutputStream mOutStream;
    private int mState;
    private boolean isConnected;

    private InputStream mInputStream;
    private OutputStream mOutputStream;


    private String mConnectedDeviceName;

    // Constants that indicate the current connection state
    public static final int STATE_LISTEN = 1;     // now listening for incoming connections
    public static final int STATE_CONNECTING = 2; // now initiating an outgoing connection
    public static final int STATE_CONNECTED = 3;  // now connected to a remote device
    public static final int STATE_CONNECTED_SERVER = 4;


    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        mWiFi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);
        mReceiver = new WiFiDirectBroadcastReceiver(mManager, mChannel, this);

        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter
                .addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter
                .addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        if (mConnectThread != null) {
            mConnectThread = null;
        }

        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        mState = STATE_LISTEN;

    }


    /**
     * Return the current connection state.
     */
    public synchronized int getState() {
        return mState;
    }

    public synchronized void connect(WifiP2pManager.Channel channel, WifiP2pConfig config, String deviceAddress) {

        // Cancel any thread attempting to make a connection
        if (mState == STATE_CONNECTING) {
            if (mConnectThread != null) {
                mConnectThread = null;
            }
        }

        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        // Start the thread to connect with the given device
        mConnectThread = new ConnectThread(channel, config, deviceAddress);
        mConnectThread.start();
        mState = STATE_CONNECTING;
    }

    public synchronized void connected(InetAddress address) {

        // Cancel the thread that completed the connection
        if (mConnectThread != null) {
            mConnectThread = null;
        }

        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        mManager.stopPeerDiscovery(mChannel, null);

        // Start the thread to manage the connection and perform transmissions
        mConnectedThread = new ConnectedThread(address);
        mConnectedThread.start();

        mState = STATE_CONNECTED;
    }

    public synchronized void connectedServer(String deviceAddress) {

        // Cancel the thread that completed the connection
        if (mConnectThread != null) {
            mConnectThread = null;
        }

        // Send the name of the connected device back to the UI Activity
        mConnectedDeviceName = deviceAddress;

        // Start the thread to manage the connection and perform transmissions
        mConnectServerStreams();
        mState = STATE_CONNECTED_SERVER;

    }

    public void write(byte[] buffer) {
        try {
            mOutputStream.flush();
            mOutputStream.write(buffer);

        } catch (IOException e) {
        }
    }

    /**
     * This thread runs while attempting to make an outgoing connection
     * with a device. It runs straight through; the connection either
     * succeeds or fails.
     */
    private class ConnectThread extends Thread {
        private WifiP2pManager.Channel mmChannel;
        private WifiP2pConfig mmConfig;
        private String mmAddress;

        public ConnectThread(WifiP2pManager.Channel channel, WifiP2pConfig config, String deviceAddress) {
            mmChannel = channel;
            mmConfig = config;
            mmAddress = deviceAddress;
        }

        public void run() {
            setName("ConnectThread");

            // Make a connection to the BluetoothSocket
            mManager.connect(mmChannel, mmConfig, new WifiP2pManager.ActionListener() {
                @Override
                public void onSuccess() {
                }

                @Override
                public void onFailure(int reason) {
                    connectionFailed();
                }
            });

            // Reset the ConnectThread because we're done
            synchronized (this) {
                mConnectThread = null;
            }
        }

        public void cancel() {

        }



    }


    /**
     * This thread runs during a connection with a remote device.
     * It handles all incoming and outgoing transmissions.
     */
    private class ConnectedThread extends Thread {
        private final Socket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        private InetAddress mAddress;

        public ConnectedThread(InetAddress serverAddress) {
            mAddress = serverAddress;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            Socket socket = new Socket();

            // Get the BluetoothSocket input and output streams
            try {
                socket.bind(null);
                socket.connect(new InetSocketAddress(mAddress.getHostAddress(),
                        ), 5000);
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();

            } catch (IOException e) {
            }

            mmSocket = socket;
            mmInStream = tmpIn;
            mmOutStream = tmpOut;
            mInputStream = mmInStream;
            mOutputStream = mmOutStream;
        }

        public void run() {

            byte[] buffer = new byte[1024];
            int bytes;
            if (mState != STATE_CONNECTED) {
                mState = STATE_CONNECTED;
            }

            // Keep listening to the InputStream while connected
            while (mState == STATE_CONNECTED) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);

                } catch (IOException e) {
                    connectionLost();
                    break;
                }
            }
        }
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
            }
        }
    }

        private void mConnectServerStream() {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            ServerSocket socket;
            Socket client;

            // Get the BluetoothSocket input and output streams
            try {
                socket = new ServerSocket();
                client = socket.accept();
                tmpIn = client.getInputStream();
                tmpOut = client.getOutputStream();
            } catch (IOException e) {
            }

            mInStream = tmpIn;
            mOutStream = tmpOut;
            mConnectedServerThread = new ConnectedServerThread(mInStream,
                    mOutStream);

        }

        private class ConnectedServerThread extends Thread {
            private final InputStream mmInStream;
            private final OutputStream mmOutStream;

            public ConnectedServerThread(InputStream input, OutputStream output) {
                mmInStream = input;
                mmOutStream = output;

            }

            public void run() {

                byte[] buffer = new byte[];
                int bytes;

                // Keep listening to the InputStream while connected
                while (mState == STATE_CONNECTED_SERVER || mState == STATE_LISTEN) {
                    try {
                        // Read from the InputStream
                        bytes = mmInStream.read(buffer);

                    } catch (IOException e) {
                        connectionLost();
                        break;
                    }
                }

            }

            /**
             * Write to the connected OutStream.
             *
             * @param buffer The bytes to write
             */
            public void write(byte[] buffer) {

                try {
                    mmOutStream.write(buffer);

                } catch (IOException e) {
                }



            }


        }
        private void connectionFailed() {

        }

        /**
         * Indicate that the connection was lost and notify the UI Activity.
         */
        public void connectionLost() {
        }

    public void setIsWifiP2pEnabled(Boolean isEnabled){
        if (!isEnabled){
            Toast.makeText(this, "Please enable WiFi Direct through your options menu",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public  void disconnect() {
        mWiFi.disconnect();
        mWiFi.reconnect();
    }

    @Override
    public void onConnectionInfoAvailable(final WifiP2pInfo p2pInfo) {
        /*
         * The group owner accepts connections using a server socket and then spawns a
         * client socket for every client. This is handled by {@code
         * GroupOwnerSocketHandler}
         */
        if (p2pInfo.isGroupOwner && p2pInfo.groupFormed) {
            connectedServer(p2pInfo.groupOwnerAddress.getHostAddress());
            isConnected = true;
        } else if (p2pInfo.groupFormed) {
            connected(p2pInfo.groupOwnerAddress);
            isConnected = true;
        }
        else if (isConnected){
            connectionLost();
        }
    }

    private void setStatus(CharSequence subTitle) {

        final ActionBar actionBar = this.getActionBar();
        if (null == actionBar) {
            return;
        }
        actionBar.setSubtitle(subTitle);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data);
                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                } else {
                    // User did not enable Bluetooth or an error occurred
                    Toast.makeText(this, R.string.bt_not_enabled_leaving,
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    private void connectDevice(Intent data) {
        // Get the device MAC address
        String address = data.getExtras()
                .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        // Get the BluetoothDevice object
        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = address;
        config.wps.setup = WpsInfo.PBC;
        // Attempt to connect to the device
        connect(mChannel, config, config.deviceAddress);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.secure_connect_scan: {
                // Launch the DeviceListActivity to see devices and do scan
                Intent serverIntent = new Intent(this, DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
                return true;
            }

        }
        return false;
    }

}
