package Client;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.SocketException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;

import ClientLauncher.ClientVar;
import Server.RecieveDataThread;
import Server.Server;

public class Game extends JFrame {

	private JFrame main;
	private final KeyboardFocusManager Keyboard = KeyboardFocusManager.getCurrentKeyboardFocusManager();
	private ClientVar Client;
	private RecieveDataThread Data;
	private volatile Graphics tmp;
	private int msgnum;
	public String chatmsg;
	private Server server;
	public ArrayList<JLabel> history;
	
	
	public Game(Server s, ClientVar c) throws SocketException{
		Data = new RecieveDataThread(this);
		main = new JFrame("Client");
		main.setLayout(null);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent winEvt) {
				server.sendData("disconnect/" + Client.getUser());
				System.exit(0);
			}
		});
		main.setSize(1000, 700);
		main.setVisible(true);
		main.setLocationRelativeTo(null);
		main.getContentPane().setBackground(Color.DARK_GRAY);

		Client = c;
		server = s;

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
		history = new ArrayList<JLabel>();
		
		main.repaint();
	}

	public void paint(Graphics g) {
		//g.drawString(chatmsg, 100, 100);
		
		for(JLabel l : history){
			if(l.getLocation().y <= 0){
				main.getContentPane().remove(l);
				history.remove(l);
			} else l.setLocation(l.getLocation().x, l.getLocation().y - 20);
		}
		
		JLabel chat = new JLabel(chatmsg);
		chat.setBounds(10, 600, 100, 20);
		chat.setFont(new Font(chat.getFont().getFontName(), Font.BOLD, 25));
		main.getContentPane().add(chat);
	}
}
