package Client;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.io.IOException;

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
	private KeyboardFocusManager Keyboard = KeyboardFocusManager.getCurrentKeyboardFocusManager();

	private String getClipboard() throws UnsupportedFlavorException, IOException {
		Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);

		if (t != null && t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
			String text = (String)t.getTransferData(DataFlavor.stringFlavor);
			while(chat.getText().length() + text.length() >= 100){
				text = text.substring(0, text.length() - 1);
			}
			return text;
		}

		return null;
	}

	public Chat(Server s, ClientVar c){
		Server = s;
		Client = c;
		main = new JFrame("World of Touhou: Chat");
		history = new JTextArea();
		chat = new JTextField();
		main.getContentPane().add(history);
		main.getContentPane().add(chat);
		main.setVisible(true);
		main.setResizable(false);
		main.setSize(300, 75);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setLocationRelativeTo(null);
		main.setLocation(main.getLocation().x, main.getLocation().y + 250);
		Keyboard.addKeyEventDispatcher(new KeyEventDispatcher(){
			@Override
			public boolean dispatchKeyEvent(KeyEvent key){
				
				if(chat.getText().length() >= 100){
					System.out.println("Before: " + chat.getText());
					chat.setText(chat.getText().substring(0, chat.getText().length() - 1));
					System.out.println("After: " + chat.getText());
					return false;
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
