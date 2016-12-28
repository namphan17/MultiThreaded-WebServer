/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.simplewifidirectchat;

import android.bluetooth.BluetoothAdapter;

/**
 * Defines several constants used between {@link WiFiDirectService} and the UI.
 */
public interface Constants {

    public static final BluetoothAdapter localAdapter = BluetoothAdapter.getDefaultAdapter();
    public static final String localName = localAdapter.getName();


    // Message types sent from the WiFiDirectService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    public static final int MESSAGE_DISCONNECTED = 6;
    public static final int TIMESTAMP = 7;


    // Key names received from the WiFiDirectService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String LOCAL_NAME = localName;
    public static final String TOAST = "toast";
    public static final String FRAG_TAG = "tag";

}
