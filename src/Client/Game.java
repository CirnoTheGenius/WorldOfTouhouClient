package Client;

import java.awt.Component;
import java.awt.Font;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.SocketException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import ClientLauncher.ClientVar;
import Server.RecieveDataThread;
import Server.Server;

public class Game extends JFrame {

	private JFrame main;
	private ClientVar Client;
	private Server server;
	private JTextField chat;
	private ArrayList<JLabel> history = new ArrayList<JLabel>();

	private final KeyboardFocusManager Keyboard = KeyboardFocusManager.getCurrentKeyboardFocusManager();

	public Game(Server s, ClientVar c) throws SocketException{
		c.setGame(this);
		new RecieveDataThread(this);
		main = new JFrame("Client");
		main.setLayout(null);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setSize(1000, 700);
		main.setVisible(true);
		main.setLocationRelativeTo(null);

		chat = new JTextField();
		chat.setBounds(15, 625, 690, 25);

		Client = c;
		server = s;

		main.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent winEvt) {
				server.sendData("disconnect/" + Client.getUser());
				System.exit(0);
			}
		});

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
					if(!chat.getText().trim().isEmpty()){
						Client.getServer().sendData("chat/" + Client.getUser() + ": " + chat.getText());
					}
					chat.setText("");
				}
				return false;
			}
		});

		main.add(chat);
		main.repaint();
	}

	public void addChatMessage(String s) {
		for(Component l : main.getContentPane().getComponents()){
			if(l instanceof JLabel){
				l.setLocation(l.getLocation().x, l.getLocation().y - 20);
				if(l.getLocation().y <= 400){
					main.getContentPane().remove(l);
					history.remove(l);
				}		
			}
		}

		if(!s.trim().isEmpty()){
			JLabel chat = new JLabel(s.trim());
			chat.setBounds(15, 600, 690, 22);
			chat.setFont(new Font(chat.getFont().getFontName(), Font.PLAIN, 15));
			history.add(chat);
			main.add(chat);
		}

		main.repaint();
	}
}
