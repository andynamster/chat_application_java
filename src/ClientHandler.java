package chat;

import java.io.IOException;
/**
 * This class takes care of open connections.
 *
 */

public class ClientHandler implements Runnable {
	private Client client;
    public ClientHandler(Client client) {
    	this.client =  client;
    }

    public void run() {
    	String inputLine;
    	String terminate = "Peer "+client.getIp()+" terminates the connection";
        try {
			while ((inputLine = client.getIn().readLine()) != null) {
			    if (".End".equals(inputLine)) {
			    	terminate = "Peer "+client.getIp()+" terminates the connection";
			    	client.getOut().println("..End");
			        break;
			    }
			    else if("..End".equals(inputLine)) {
			    	terminate = "connection to Peer"+ client.getIp()+" terminated ";
			    	break;
			    }
			    System.out.println("ip: "+client.getIp()+" port: "+ client.getPort() +" says: "+ inputLine);
			}
		} catch (IOException e) {
			System.out.println("could not read from the input");
		}
        client.terminate();
        System.out.println(terminate);
    }
}
