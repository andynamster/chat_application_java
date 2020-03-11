package chat;
import java.io.*;
import java.net.*;
import java.util.ArrayList;


/**
 * This is the main class.
 */
public class chat {
	public static ClientList clientList = new ClientList();
	public static void main(String[] args) {
		int myport;
		if(args.length == 1 ) { //checks the arguments
			try {
				myport = Integer.parseInt(args[0]);
				InetAddress inetAddress = null;
			    String myip = null;
			    try {
					inetAddress = InetAddress.getLocalHost();
					myip = inetAddress.getHostAddress();
				} catch (UnknownHostException e) {
					System.out.println("could not get your ip");
					e.printStackTrace();
				}
			    BufferedReader stdIn =new BufferedReader(new InputStreamReader(System.in));
				ServerHandler server = new ServerHandler(myip, myport);
				if(server.isStarted()) {//checks if server started successfully or not
					Thread t = new Thread(server);//start a new thread to take care of incoming connections
				    t.start();
					String input = null;
					boolean ext = false;
					while(!ext) {//keeps the program running until user enters the word exit
							try {
								input = stdIn.readLine();
							} catch (IOException e) {
								System.out.println("could not read from the input");
							}
						String[] command = input.split("\\s+");
						//checks which option user selected
						if (command.length == 1) {
							switch (command[0]) {//prints program instructions
							case "help":
								System.out.println("This program operates the following functions:\n");
								System.out.println("1- help ");
								System.out.println("2- myip");
								System.out.println("3- myport");
								System.out.println("4- connect <destination> <port no>");
								System.out.println("5- list");
								System.out.println("6- terminate <connection id.>");
								System.out.println("7- send <connection id.> <message>");
								System.out.println("8- exit\n");
								break;
							case "myip"://prints program IP 
								try {
									inetAddress = InetAddress.getLocalHost();
									 System.out.println("IP Address: " + inetAddress.getHostAddress());
								} catch (UnknownHostException e) {
									System.out.println("could not get your ip");
									e.printStackTrace();
								}
								break;
							case "myport"://prints program port number
								System.out.println("Listening Port: " + myport);
								break;
							case "list"://prints all open connections
								clientList.print();
								break;
							case "exit"://close all open connections and stops the program
								ArrayList<Client> clients = clientList.getClients();
								for (Client temp : clients) {
						  			temp.sendMessage(".End");
						  			}
								server.stop();
								ext = true;
								break;
							default:
								System.out.println("worng command entered\n");
							}
							input = null;
							
						}
						else if(command.length == 3 && command[0].equals("connect")) {//connects to a new server
								String desIP = command[1];
								int desPort = 0;
								try
							    {
									desPort = Integer.parseInt(command[2]);
							    }
							    catch (NumberFormatException nfe)
							    {
							      System.out.println("the port should be a number " );
							    }
								
								if(!(myip.equals(desIP) && myport == desPort)) {
									Client s= clientList.search(desIP, desPort);
							    	 if(s==null) {
							    		  Socket clientSocket;
							    		  try {
							    				clientSocket = new Socket(desIP, desPort);
							    				s = new Client(clientSocket, desIP);
							    				clientList.addToList(s);
							    				new Thread (new ClientHandler(s)).start();
							    				System.out.println("The connection to peer"+ desIP +" is successfully established;");
							    			} catch (UnknownHostException e) {
							    				System.out.println("You entered an invalid destination address to connect to");
							    			} catch (IOException e) {
							    				System.out.println("You entered an invalid destination address to connect to");
							    			}
							    		  
							    	 }
							    	 else {
							    		 System.out.println("You are already connected to this server");
							    	 }
								}
								else {
									System.out.println("You are trying to initiate a self-connection");
								}
							input = null;
						}
						else if(command.length == 2 && command[0].equals("terminate")) {//terminates a specific connection
							int conId = 0;
							try
						    {
								conId = Integer.parseInt(command[1]);
								if (conId >= 0 && conId < clientList.getSize()) {
									Client cl = clientList.getByIndex(conId);
									cl.sendMessage(".End");
								}
								else {
									System.out.println("invalid id " );
								}
						    }
						    catch (NumberFormatException nfe)
						    {
						      System.out.println("the id should be a number " );
						    }
							input = null;
						}
						else if(command.length >= 3 && command[0].equals("send")) {//sends a message to a specific client
							int conIndex = 0;
							try
						    {
								conIndex = Integer.parseInt(command[1]);
								if (conIndex >= 0 && conIndex < clientList.getSize()) {
									Client cl1 = clientList.getByIndex(conIndex);
									int count = 2 ; 
									for (int i = 0; i < 2 ; i++) {
										count += command[i].length();
									}
									String msg = input.substring(count);
									if(msg.length() <= 100) {
											cl1.sendMessage(msg);
											System.out.println("Message sent to " + conIndex);
									}
									else {
										System.out.println("Your message is bigger than 100 characters");
									}
								}
								else {
									System.out.println("invalid id " );
								}
						    }
						    catch (NumberFormatException nfe)
						    {
						      System.out.println("the id should be a number " );
						    }
							input = null;
						}
						else {
							System.out.println("worng command entered\n");
							input = null;
						}
				}
				}
				
				
			} catch (NumberFormatException e1) {
				System.out.println("port should be a number");
			} 
		}
		else {
			System.out.println("wrong number of arguments");
		}
	}
}
