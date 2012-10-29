package Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import Client.Game;

public class RecieveDataThread extends Thread {

	private DatagramSocket ds;
	private Game g;

	public RecieveDataThread(Game g) throws SocketException{
		this.g = g;
		ds = new DatagramSocket(8494);
		super.start();
	}

	public void run(){
		try {
			while(true){
				byte[] data = new byte[100];
				DatagramPacket pk = new DatagramPacket(data, data.length);
				ds.receive(pk);
				g.addChatMessage(new String(pk.getData()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
