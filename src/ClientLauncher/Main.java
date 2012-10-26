package ClientLauncher;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import Client.Game;
import Server.Server;

public class Main extends JFrame implements ActionListener {

	public final ClientVar Client = new ClientVar();
	private Server s;
	
	private JFrame main;
	private JTextField host, username;
	private JLabel hostL, usernameL, picture, watermark;
	private JButton confirm;
	
	private final boolean debug = true;
	
	private boolean isRunning;

	private final int xOffset = 0, yOffset = 50;
	
	public Main(){
		try {
			//public void callMe(){}
			//Hey, I just met you. I made a function. So here's callMe(). So call it maybe?
			
			InputStream sideimg = randomSideImage();
			InputStream watermarkimg = new BufferedInputStream(getClass().getResourceAsStream("/SideImages/mark.png"));
			
			main = new JFrame("ClientLauncher");
			main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			main.setLayout(null);
			main.setSize(800, 384);
			main.setLocationRelativeTo(null);
			main.setResizable(false);
			
			host = new JTextField(debug ? "localhost" : "");
			host.setBounds(510 + xOffset, 25 + yOffset, 260, 50);
			hostL = new JLabel("Host: ");
			hostL.setBounds(430 + xOffset, 35 + yOffset, 70, 20);
			hostL.setFont(new Font(hostL.getFont().getFontName(), Font.BOLD, 25));

			username = new JTextField(debug ? "[DEBUG] Tenshi" : "");
			username.setBounds(510 + xOffset, 85 + yOffset, 260, 50);
			usernameL = new JLabel("Username: ");
			usernameL.setBounds(365 + xOffset, 95 + yOffset, 200, 20);
			usernameL.setFont(new Font(usernameL.getFont().getFontName(), Font.BOLD, 25));
			
			confirm = new JButton("Connect!");
			confirm.addActionListener(this);
			confirm.setBounds(560, 220, 155, 50);

			picture = new JLabel();
			picture.setIcon(new ImageIcon(ImageIO.read(sideimg)));
			picture.setBounds(0, 0, 340, 357);

			watermark = new JLabel();
			watermark.setIcon(new ImageIcon(ImageIO.read(watermarkimg)));
			watermark.setBounds(700, 270, 100, 89);
			
			watermarkimg.close();
			sideimg.close();	
			
			main.add(host);
			main.add(username);
			
			main.add(hostL);
			main.add(usernameL);
			
			main.add(confirm);
			
			main.add(picture);
			main.add(watermark);
			
			//Always do this last.
			main.setVisible(true);
		} catch (IOException e){
			System.out.println("Failed to read images!");
		}
	}

	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
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
			if(!host.getText().isEmpty() && !username.getText().isEmpty()){
				Client.setHost(host.getText());
				Client.setUser(username.getText());
				if(debug){
					Client.setSocket(new Socket(Client.getHost(), 9999));
				}
				s = Client.getServer();
				s.sendData("user/" + Client.getUser());
				s.sendData("servername/" + Client.getUser());
				if(!isRunning){
					Client.setGame(new Game(s, Client));
					isRunning = true;
					main.setVisible(false);
				}
			} else {
				JOptionPane.showMessageDialog(main, "Please enter a host or username!", "Failed to Connect!", JOptionPane.ERROR_MESSAGE);
			}
		} catch (UnknownHostException e){
			JOptionPane.showMessageDialog(main, "Could not connect to " + host.getText() + "! Unknown host!", "Failed to Connect!", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (IOException e){
			JOptionPane.showMessageDialog(main, "Could not connect to " + host.getText() + "! Host either rejected our connection or is down!", "Failed to Connect!", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
}