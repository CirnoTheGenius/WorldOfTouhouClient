package Client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
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

	public Game(Server s, ClientVar c){
		Data = new RecieveDataThread(this);
		main = new JFrame("Client");
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
	}

	public void paint(Graphics g) {
		g.drawString(chatmsg, 100, 100);
	}
}
