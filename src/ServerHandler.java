package chat;
import java.net.*;
import java.io.*;
public class ServerHandler implements Runnable {
    private ServerSocket serverSocket;
    public String ip;
    private boolean shutDown = false;
    private boolean started = false;
    
    /**
	 * This method starts a new server and creates a socket.
	 *
	 */
    public ServerHandler(String ip,int port) {
    	try {
			serverSocket = new ServerSocket(port);
			this.ip = ip;
			System.out.println("server started");
			started = true;
		} catch (IOException e) {
			System.out.print("unable to start the server");
			started = false;
		}
    	
    }
    
    /**
	 * This method checks if server has started successfully or not.
	 *
	 */
    
    public boolean isStarted() {
		return started;
	}
    
    /**
	 * This method stops the server.
	 *
	 */
 
    public void stop() {
        try {
			serverSocket.close();
			shutDown = true;
		} catch (IOException e) {
			System.out.print("unable to stop the server");
		}
    }
    
    /**
	 * This method takes care of accepting new connection.
	 *
	 */

	@Override
	public void run() {
		while (!shutDown ) {
			try {
				Socket socket = serverSocket.accept();
				System.out.println("The connection to peer "+ ip+ "is successfully established");
				Client cl = new Client(socket, ip);
				chat.clientList.addToList(cl);
				new Thread (new ClientHandler(cl)).start();	
			} catch (IOException e) {
				System.out.println("server stoped"); 
			}
	}
	}
}