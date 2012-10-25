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
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
	private final boolean debug = false;
	
	private boolean isRunning;

	public Main() throws IOException{
		main = new JFrame("ClientLauncher");
		main.setLayout(new FlowLayout());
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setSize(700, 386);
		main.setVisible(true);
		main.setLocationRelativeTo(null);
		main.setResizable(false);
		picture = new JLabel();
		
		host = new JTextField("Host");
		host.setSize(310, 50);
		host.setLocation(360, 100);
		
		username = new JTextField("username");
		username.setSize(310, 50);
		username.setLocation(360, 160);
		
		
		confirm = new JButton("Connect!");
		confirm.addActionListener(this);
		confirm.setSize(155, 50);
		confirm.setLocation(440, 220);
		
		picture.setIcon(randomSideImage());
		picture.setSize(340, 357);
		picture.setLocation(0, 0);
		
		main.getContentPane().add(picture);
		main.getContentPane().add(host);
		main.getContentPane().add(username);
		main.getContentPane().add(confirm);
		main.repaint();
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException{
		new Main();
	}
	
	public ImageIcon randomSideImage() throws IOException{
		int num = new Random().nextInt(6);
		if(num == 6){
			num = 0;
		}
		InputStream is = new BufferedInputStream(getClass().getResourceAsStream("/SideImages/img" + num + ".png"));
		return new ImageIcon(ImageIO.read(is));
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			Client.setHost(host.getText());
			Client.setUser(username.getText());
			if(debug){
				Client.setSocket(new Socket(Client.getHost(), 9999));
			}
			s = Client.getServer();
			s.sendData("user/" + Client.getUser());
			if(!isRunning){
				Client.setGame(new Game(s, Client));
				isRunning = true;
				main.setVisible(false);
			}
		} catch (Exception e){
			JOptionPane.showMessageDialog(main, "Could not connect to " + host.getText() + "!", "Failed to Connect!", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

}