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
		super.start();
		ds = new DatagramSocket(8494);
	}

	public void run(){
		try {
			while(true){
				byte[] data = new byte[1024];
				DatagramPacket pk = new DatagramPacket(data, data.length);
				ds.receive(pk);
				g.chatmsg = new String(pk.getData()).trim();
				g.repaint();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
