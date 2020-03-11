package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


/**
 * This class maintains some data about a open connection.
 */

public class Client {
	private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private String ip;
    private int port;

    /**
     * This method is the class constructor.
     */

    public Client(Socket socket, String ip) {
        this.clientSocket = socket;
        this.port = socket.getPort();
        this.ip = ip;
        try {
			out = new PrintWriter(clientSocket.getOutputStream(), true);
		} catch (IOException e) {
			System.out.println("could not open the output stream");
		}
        try {
			in = new BufferedReader(
			  new InputStreamReader(clientSocket.getInputStream()));
		} catch (IOException e) {
			System.out.println("could not open the input stream");
		}
    }
    
    /**
     * This method sends a message to the other end system.
     */
    public void sendMessage(String msg) {
        out.println(msg);
    }
public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

public Socket getClientSocket() {
		return clientSocket;
	}

	public PrintWriter getOut() {
		return out;
	}

	public BufferedReader getIn() {
		return in;
	}

	/**
     * This method terminates the connection.
     */
    public void terminate() {
    	try {
			in.close();
		} catch (IOException e) {
			System.out.println("could not close the input stream");
		}
        out.close();
        try {
			clientSocket.close();
		} catch (IOException e) {
			System.out.println("could not close the socket");
		}
        chat.clientList.deleteCl(this);
    }
}
