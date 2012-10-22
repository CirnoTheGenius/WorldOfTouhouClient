package Server;

import java.io.BufferedReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import Client.ClientVar;

public class Server {
	
	private BufferedReader in;
	private ServerSocket serverSocket;
	private Socket Server;
	private DatagramSocket playerSocket;
	private InetAddress Host;
	
	public Server(ClientVar cv){
		try {
			playerSocket = new DatagramSocket();
			Host = InetAddress.getByName(cv.getHost());
		} catch(Exception e){
			e.printStackTrace();
			System.out.println("Failed to connect to server!");
		}
	}

	public void sendData(String s){
		try {
			if(!s.isEmpty()){
				byte[] mb = s.getBytes();
				DatagramPacket pk = new DatagramPacket(mb, mb.length, Host, 9999);
				playerSocket.send(pk);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
