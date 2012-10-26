package Client;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JTextField;

import ClientLauncher.ClientVar;
import Server.Server;

public class Chat extends JFrame {

	private JTextField chat;
	private JFrame main;
	private Server Server;
	private ClientVar Client;
	private final KeyboardFocusManager Keyboard = KeyboardFocusManager.getCurrentKeyboardFocusManager();

	public Chat(ClientVar c){
		Server = c.getServer();
		Client = c;
		
		chat = new JTextField("");
		chat.setSize(690, 3434);
		chat.setLocation(0, 0);
		
		main = new JFrame("Chat");
		main.setResizable(false);
		main.setSize(690, 75);
		main.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		main.setLocationRelativeTo(null);
		main.setLocation(main.getLocation().x, main.getLocation().y + 400);
		
		Keyboard.addKeyEventDispatcher(new KeyEventDispatcher(){
			@Override
			public boolean dispatchKeyEvent(KeyEvent key){
				if(chat.getText().length() >= 100){
					String msg = null;
					while(chat.getText().length() >= 101){
						msg = chat.getText().substring(0, chat.getText().length() - 1);
					}
					chat.setText(msg);
				}
				if(key.getKeyCode() == KeyEvent.VK_ENTER && chat.hasFocus()){
					Server.sendData("chat/" + Client.getUser() + ": " + chat.getText());
					chat.setText("");
					main.removeAll();
					main.setVisible(false);
					Client.isChatting = false;
				}
				return false;
			}
		});
		
		main.add(chat);
		main.setVisible(true);
		main.repaint();
	}
}
