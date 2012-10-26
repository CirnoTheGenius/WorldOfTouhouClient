package Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import Client.Game;

public class RecieveDataThread extends Thread {

	DatagramSocket ds;
	DatagramPacket pk;
	byte[] data;
	Game g;
	
	public RecieveDataThread(Game g){
		this.g = g;
		super.start();
	}

	public void run(){
		try {
			ds = new DatagramSocket(8494);
			data = new byte[1024];
			pk = new DatagramPacket(data, data.length);
		} catch (SocketException e1) {
			e1.printStackTrace();
		}

		while(true){
			try {
				ds.receive(pk);
				g.chatmsg = new String(pk.getData()).trim();
				g.repaint();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
