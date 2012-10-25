package Client;

import java.awt.Color;
import javax.swing.JFrame;

import ClientLauncher.ClientVar;
import Server.Server;

public class Game extends JFrame {

	private JFrame main;
	
	public Game(Server s, ClientVar Client){
		main = new JFrame("Client");
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setSize(700, 700);
		main.setVisible(true);
		main.setLocationRelativeTo(null);
		main.getContentPane().setBackground(Color.DARK_GRAY);
		
		Chat c = new Chat(s, Client);
		Client.setChat(c);
		main.add(c.getContentPane());
	}
	
}
