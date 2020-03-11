package dfa;

import java.net.Socket;
import java.util.ArrayList;

public class ClientList {
	private ArrayList<Client> clients = new ArrayList<Client>();
	
	public boolean addToList(Client cl) {
		return clients.add(cl);
	}
	
	public boolean removeFromList(Client cl) {
		return clients.remove(cl);
	}
	
	public void print() {
		int i = 0;
		for (Client cl : clients) { 		      
	           System.out.println(i + ") ip= " + cl.getIp() + "  port: " + cl.getPort());
	           i++;
	      }
	}
	public Client search(String ip ,int port) {
		Client s = null;
		for (Client temp : clients) {
  			if(temp.getIp().equals(ip)) {
  				if(temp.getPort() == port) {
  					s = temp;
  				}
  			}
	}
		return s;
		}
	public Client getByIndex(int index) {
		return clients.get(index);
	}
	public void delete(int index) {
		clients.remove(index);
	}
	public void deleteCl(Client cl) {
		clients.remove(cl);
	}

	public ArrayList<Client> getClients() {
		return clients;
	}
	public int getSize() {
		return clients.size();
	}

}
