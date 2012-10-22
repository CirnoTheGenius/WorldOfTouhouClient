package Client;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import ClientLauncher.ClientVar;
import Server.Server;

public class Chat extends JFrame {
	
	private JTextArea history;
	private JTextField chat;
	private JFrame main;
	private Server s;
	private ClientVar Client;
	
	public Chat(Server s, ClientVar c){
		main = new JFrame("World of Touhou: Chat");
		history = new JTextArea();
		chat = new JTextField();
		main.getContentPane().add(history);
		main.getContentPane().add(chat);
	}
	
	public void x(){
		while(true){
			s.sendData("chat/" + Client.getUser() + ": " + chat.getText());
		}
	}
	
}
