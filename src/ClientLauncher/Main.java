package ClientLauncher;

/**
 * @author Cirno the Genius/Tenko.
 */
import java.awt.FlowLayout;
import java.awt.Font;
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

import Client.Game;
import Server.Server;

public class Main extends JFrame implements ActionListener {

	public final ClientVar Client = new ClientVar();
	private Server s;
	
	private JFrame main;
	private JTextField host, username;
	private JLabel hostL, usernameL, picture, watermark;
	private JButton confirm;
	
	private final boolean debug = false;
	
	private boolean isRunning;

	public Main() throws IOException{
		
		//public void callMe(){}
		//Hey, I just met you. I made a function. So here's callMe(). So call it maybe?
		
		InputStream sideimg = randomSideImage();
		InputStream watermarkimg = new BufferedInputStream(getClass().getResourceAsStream("/SideImages/mark.png"));
		
		main = new JFrame("ClientLauncher");
		main.setLayout(new FlowLayout());
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setSize(800, 386);
		main.setVisible(true);
		main.setLocationRelativeTo(null);
		main.setResizable(false);
		
		host = new JTextField();
		host.setSize(260, 50);
		host.setLocation(510, 100);
		
		hostL = new JLabel("Host: ");
		hostL.setSize(70, 20);
		hostL.setLocation(430, 115);
		hostL.setFont(new Font(hostL.getFont().getFontName(), Font.BOLD, 25));

		username = new JTextField();
		username.setSize(260, 50);
		username.setLocation(510, 160);
		
		usernameL = new JLabel("Username: ");
		usernameL.setSize(200, 20);
		usernameL.setLocation(365, 170);
		usernameL.setFont(new Font(usernameL.getFont().getFontName(), Font.BOLD, 25));
		
		confirm = new JButton("Connect!");
		confirm.addActionListener(this);
		confirm.setSize(155, 50);
		confirm.setLocation(560, 220);

		picture = new JLabel();
		picture.setIcon(new ImageIcon(ImageIO.read(sideimg)));
		picture.setSize(340, 357);
		picture.setLocation(0, 0);
		
		watermark = new JLabel();
		watermark.setIcon(new ImageIcon(ImageIO.read(watermarkimg)));
		watermark.setSize(100, 89);
		watermark.setLocation(700, 269);

		main.add(picture);
		main.add(host);
		main.add(username);
		main.add(confirm);
		main.add(watermark);
		main.add(hostL);
		main.add(usernameL);
		main.repaint();
		
		sideimg.close();
		watermarkimg.close();
	}

	public static void main(String[] args) throws IOException{
		new Main();
	}
	
	public InputStream randomSideImage(){
		int num = new Random().nextInt(5);
		if(num == 5){
			num = 0;
		}
		
		return new BufferedInputStream(getClass().getResourceAsStream("/SideImages/img" + num + ".png"));
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