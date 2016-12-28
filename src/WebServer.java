import java.io.*;
import java.net.*;
import java.util.*;

public final class WebServer {
	public static void main(String argv[]) throws Exception {
		// Set the port number
		int port = 4444;

		// Establish the listen socket.
		ServerSocket welcomeSocket = new ServerSocket(port);
		
		// Process HTTP service requests in an infinite loop.
		while (true) {
			// Listen to a TCP connection request.
			Socket connectionSocket = welcomeSocket.accept();
			
			// Construct an object to process the HTTP request message.
			HttpRequest request = new HttpRequest(connectionSocket);
			
			// Create a thread to process the request.
			Thread thread = new Thread(request);
			
			// Start the thread
			thread.start();
		}
	}
}

 final class HttpRequest implements Runnable {
	 final static String CRLF = "\r\n";
	 Socket connectionSocket;
	 
	 // Constructor
	 public HttpRequest(Socket connectionSocket) throws Exception{
		 this.connectionSocket = connectionSocket;
	 }
	 
	 // Implement the run() method of the Runnable interface
	 public void run() {
		 try {
			 processRequest();
		 } catch (Exception e) {
			 System.out.println(e);
		 }
	 }
	 
	 private void processRequest() throws Exception {
		 // Get a reference to the socket's input and output streams.
		 InputStream inputStream = connectionSocket.getInputStream();
		 DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
		 
		 // Set up input stream filters
		 BufferedReader inFromClient = new BufferedReader(new InputStreamReader(inputStream));
		 
		 // Get the request line of the HTTP request message
		 String requestLine = inFromClient.readLine();
		 
		 // Display the request line
		 System.out.println();
		 System.out.println("This is the request line: " + requestLine);
		 
		 // Get and display the header lines.
		 String headerLine = null;
		 while ((headerLine = inFromClient.readLine()).length() != 0) {
			 System.out.println(headerLine);
		 }
		 
		 // Extract the filename from the request line.
		 StringTokenizer tokens = new StringTokenizer(requestLine);
		 tokens.nextToken(); // Skip over the method, which should be "GET"
		 String filename = tokens.nextToken();
		 // Prepend a "." so that file request is within the current directory.
		 filename = "./webpage" + filename;
		 
		 String version = tokens.nextToken();
		 
		 // Open the requested file.
		 FileInputStream fileInputStream = null;
		 boolean fileExists = true;
		 try {
			 fileInputStream = new FileInputStream(filename);
		 } catch(FileNotFoundException e) {
			 fileExists = false;
		 }
		 System.out.println("********************************************");
		 System.out.println("RESPONSE MESSAGE");
		 // Construct the response message.
		 String statusLine = null;
		 String contentTypeLine = null;
		 String entityBody = null;
		 if (fileExists) {
			 statusLine = version + " " + 200 + " OK" + CRLF;
			 System.out.println(statusLine);
			 contentTypeLine = "Content-type: " + 
					 	contentType( filename ) + CRLF;
		 }else {
			 statusLine = version + " " + 404 + "Not Found" + CRLF;
			 contentTypeLine = "Content-type: " +
					 	contentType( filename ) + CRLF;
			 entityBody = "<HTML>" +
					 	"<HEAD><TITLE>Not Found</TITLE></HEAD>" +
					 	"<BODY><h1>Not Found</h1></BODY></HTML>";
		 }
		 
		 // Send the status line.
		 System.out.println(statusLine);
		 outToClient.writeBytes(statusLine);
		 
		 // Send the content type line.
		 System.out.println(contentTypeLine);
		 outToClient.writeBytes(contentTypeLine);
		 
		 // Send a blank line to indicate the end of the header lines.
		 System.out.println(CRLF);
		 outToClient.writeBytes(CRLF);
		 
		 // Send the entity body.
		 if (fileExists) {
			 sendBytes(fileInputStream, outToClient);
			 fileInputStream.close();
		 } else {
			 outToClient.writeBytes(entityBody);
		 }
		 
		 
		 // Close streams and socket.
		 outToClient.close();
		 inFromClient.close();
		 connectionSocket.close();
	 }
	 
	 // Private method that writes file to the socket output stream.
	 private static void sendBytes(FileInputStream fileInputStream, OutputStream outToClient) 
	 throws Exception {
		 // Construct a 1K buffer to hold bytes on their way to the socket.
		 byte[] buffer = new byte[1024];
		 int bytes = 0;
		 
		 // Copy requested file into the socket's output stream.
		 while ((bytes = fileInputStream.read(buffer)) != -1) {
			 outToClient.write(buffer, 0, bytes);
		 }
	 }
	 
	 // Private method that constructs the content-type header line.
	 private static String contentType(String filename) {
		 if (filename.endsWith(".html") || filename.endsWith(".htm")) {
			 return "text/html";
		 } if (filename.endsWith(".gif")) {
			 return "image/gif";
		 } if (filename.endsWith(".jpg")) {
			 return "image/jpeg";
		 }
		 return "application/octet-stream";
	 }
 }
 
 
 
 
 
 
 
 
 
 
 
 
 