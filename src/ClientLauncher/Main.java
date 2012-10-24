package ClientLauncher;

/**
 * @author Cirno the Genius/Tenko.
 */
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import Client.Chat;
import Client.Game;
import Server.Server;


public class Main extends JFrame implements ActionListener {

	public final ClientVar Client = new ClientVar();
	private Server s;
	private JFrame main;
	private JTextField host, username;
	private JButton confirm;
	private JLabel picture;

	private boolean isRunning;

	public Main() throws IOException{
		main = new JFrame("ClientLauncher");
		main.setLayout(new FlowLayout());
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.getContentPane().setLayout(new GridLayout(4, 2));
		main.setSize(700, 400);
		main.setVisible(true);
		main.setLocationRelativeTo(null);
		main.setResizable(false);
		picture = new JLabel();
		
		host = new JTextField("Host");

		username = new JTextField("username");

		confirm = new JButton("Connect!");
		confirm.addActionListener(this);

		InputStream is = new BufferedInputStream(getClass().getResourceAsStream("/something.jpg"));

		picture.setIcon(new ImageIcon(ImageIO.read(is)));
		
		main.getContentPane().add(picture);
		main.getContentPane().add(host);
		main.getContentPane().add(username);
		main.getContentPane().add(confirm);
		
		main.repaint();
		main.validate();
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException{
		new Main();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			Client.setHost(host.getText());
			Client.setUser(username.getText());
			Client.setSocket(new Socket(Client.getHost(), 9999));
			s = Client.getServer();
			s.sendData("user/" + Client.getUser());
			if(!isRunning){
				Client.setChat(new Chat(s, Client));
				Client.setGame(new Game());
				isRunning = true;
				main.setVisible(false);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}