package Client;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import ClientLauncher.ClientVar;
import Server.Server;

public class Chat extends JFrame {

	private JTextArea history;
	private JTextField chat;
	private JFrame main;
	private Server Server;
	private ClientVar Client;
	private final KeyboardFocusManager Keyboard = KeyboardFocusManager.getCurrentKeyboardFocusManager();

	public Chat(Server s, ClientVar c){
		Server = s;
		Client = c;
		main = new JFrame("Chat");
		history = new JTextArea();
		chat = new JTextField();
		main.getContentPane().add(history);
		main.getContentPane().add(chat);
		main.setResizable(false);
		main.setSize(690, 75);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setLocationRelativeTo(null);
		main.setLocation(main.getLocation().x, main.getLocation().y + 400);
		Keyboard.addKeyEventDispatcher(new KeyEventDispatcher(){
			@Override
			public boolean dispatchKeyEvent(KeyEvent key){
				if(chat.getText().length() >= 100){
					while(chat.getText().length() >= 101){
						chat.setText(chat.getText().substring(0, chat.getText().length() - 1));
					}
				}
				if(key.getKeyCode() == KeyEvent.VK_ENTER){
					Server.sendData("chat/" + Client.getUser() + ": " + chat.getText());
					chat.setText("");
				}
				return false;
			}
		});
	}
}
