package Client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.SocketException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import ClientLauncher.ClientVar;
import Server.RecieveDataThread;
import Server.Server;

public class Game extends JFrame {
	
	private ClientVar Client;
	private JTextField chat;
	
	private Server server;
	private ArrayList<JLabel> history = new ArrayList<JLabel>();
	private final KeyboardFocusManager Keyboard = KeyboardFocusManager.getCurrentKeyboardFocusManager();
	
	public Game(Server s, final ClientVar c) throws SocketException{
		c.setGame(this);
		new RecieveDataThread(this);
		new JFrame("Client");
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000, 700);
		this.setVisible(true);
		this.setLocationRelativeTo(null);

		Client = c;
		server = s;

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent winEvt) {
				server.sendData("disconnect/" + Client.getUser());
				System.exit(0);
			}
		});

		Keyboard.addKeyEventDispatcher(new KeyEventDispatcher(){
			@Override
			public boolean dispatchKeyEvent(KeyEvent key){
				if(key.getKeyCode() == KeyEvent.VK_T && !chat.isVisible()){
					chat.setVisible(true);
					chat.requestFocus();
					c.getGame().repaint();
				}
				if(key.getKeyCode() == KeyEvent.VK_ENTER && chat.hasFocus() && chat.isVisible()){
					if(!chat.getText().trim().isEmpty()){
						Client.getServer().sendData("chat/" + Client.getUser() + ": " + chat.getText());
						chat.setText("");
					}
					chat.setVisible(false);
				}
				return false;
			}
		});

		chat = new JTextField(){
			public void paint(Graphics g) {
                g.clearRect(0,0,getWidth(), getHeight());
                g.setColor(new Color(0,0,0,100));
                super.paint(g);
            }
		};
		chat.setVisible(false);
		chat.setBackground(null);
		chat.setBounds(15, 625, 690, 25);
	    chat.setBorder(BorderFactory.createLineBorder(Color.white, 0));  
		
	    this.add(chat);
		this.repaint();
	}
	
	public void addChatMessage(String s) {
		for(Component l : this.getContentPane().getComponents()){
			if(l instanceof JLabel){
				l.setLocation(l.getLocation().x, l.getLocation().y - 20);
				if(l.getLocation().y <= 400){
					this.getContentPane().remove(l);
					history.remove(l);
				}		
			}
		}

		if(!s.trim().isEmpty()){
			JLabel chat = new JLabel(s.trim());
			chat.setBounds(15, 600, 690, 22);
			chat.setFont(new Font(chat.getFont().getFontName(), Font.PLAIN, 15));
			history.add(chat);
			this.add(chat);
		}

		this.repaint();
	}
}
