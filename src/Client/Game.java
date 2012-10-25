package Client;

import java.awt.Color;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import ClientLauncher.ClientVar;
import Server.Server;

public class Game extends JFrame {

	private JFrame main;
	private final KeyboardFocusManager Keyboard = KeyboardFocusManager.getCurrentKeyboardFocusManager();
	private ClientVar Client;
	public Game(Server s, ClientVar c){
		main = new JFrame("Client");
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setSize(700, 700);
		main.setVisible(true);
		main.setLocationRelativeTo(null);
		main.getContentPane().setBackground(Color.DARK_GRAY);
		
		Client = c;
		
		Keyboard.addKeyEventDispatcher(new KeyEventDispatcher(){
			@Override
			public boolean dispatchKeyEvent(KeyEvent key){
				if(key.getKeyCode() == KeyEvent.VK_T && !Client.isChatting){
					new Chat(Client);
					Client.isChatting = true;
				}
				return false;
			}
		});

		Client.setGame(this);
	}
	
}
