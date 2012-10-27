package ClientLauncher;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

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
	
	private final static boolean debug = true;
	
	private boolean isRunning;

	private final int xOffset = 0, yOffset = 0;
	
	private final int version = 1;
	
	//If you really went through the trouble of downloading the source and modifying it JUST to remove this, congrats. You win nothing :3
	private final String[] requiredImages = {
		//Images used in the launcher.
		 /* 0 */ "http://dl.dropbox.com/u/35094909/WorldOfTouhou/img0.png",
		 /* 1 */ "http://dl.dropbox.com/u/35094909/WorldOfTouhou/img1.png",
		 /* 2 */ "http://dl.dropbox.com/u/35094909/WorldOfTouhou/img2.png",
		 /* 3 */ "http://dl.dropbox.com/u/35094909/WorldOfTouhou/img3.png",
		 /* 4 */ "http://dl.dropbox.com/u/35094909/WorldOfTouhou/img4.png",
		 /* 5 */ "http://dl.dropbox.com/u/35094909/WorldOfTouhou/credits.png",
	};
	
	public Main() {
		try {
			
			checkVersion();
			
			//public void callMe(){}
			//Hey, I just met you. I made a function. So here's callMe(). So call it maybe?
			
			InputStream sideimg = randomSideImage();
			InputStream watermarkimg = new BufferedInputStream(new File(System.getProperty("user.home") + "/WoTData/SideImages/credits.png").toURI().toURL().openStream());
			
			main = new JFrame("ClientLauncher");
			main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			main.setLayout(null);
			main.setSize(800, 384);
			main.setLocationRelativeTo(null);
			main.setResizable(false);
			
			host = new JTextField();
			host.setBounds(510 + xOffset, 25 + yOffset, 260, 50);
			hostL = new JLabel("Host: ");
			hostL.setBounds(430 + xOffset, 35 + yOffset, 70, 20);
			hostL.setFont(new Font(hostL.getFont().getFontName(), Font.BOLD, 25));

			username = new JTextField();
			username.setBounds(510 + xOffset, 85 + yOffset, 260, 50);
			usernameL = new JLabel("Username: ");
			usernameL.setBounds(365 + xOffset, 95 + yOffset, 200, 20);
			usernameL.setFont(new Font(usernameL.getFont().getFontName(), Font.BOLD, 25));
			
			confirm = new JButton("Connect!");
			confirm.addActionListener(this);
			confirm.setBounds(510 + xOffset, 155 + yOffset, 260, 50);

			picture = new JLabel();
			picture.setIcon(new ImageIcon(ImageIO.read(sideimg)));
			picture.setBounds(0, 0, 340, 357);

			watermark = new JLabel();
			watermark.setIcon(new ImageIcon(ImageIO.read(watermarkimg)));
			watermark.setBounds(400, 230, 350, 132);
			
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
			System.out.println("Failed to read files! Attempting to download images...");
			try {
				checkImages();
				restartApplication();
			} catch (IOException a){
				System.out.println("Failed to download images!");
				a.printStackTrace();
			} catch (URISyntaxException b) {
				System.out.println("Failed to restart application!");
				b.printStackTrace();
			}
		}
	}

	public void checkVersion() throws IOException{
		InputStream v = new URL("http://dl.dropbox.com/u/35094909/WorldOfTouhou/version.txt").openStream();
		Scanner s = new Scanner(v);
		int wv = Integer.valueOf(s.nextLine());
		if(wv > version){
			//JOptionPane.showMessageDialog(main, "New version avaliable! Contact Tenko!", "New version!", JOptionPane.WARNING_MESSAGE);
		}
		v.close();
	}
	
	public void checkImages() throws MalformedURLException, IOException{
		File folder = new File(System.getProperty("user.home") + "/WoTData");
		File sideImageFolder = new File(System.getProperty("user.home") + "/WoTData/SideImages");
		
		if(!folder.exists()){
			folder.mkdir();
			new File(folder.getAbsolutePath() + "/SideImages").mkdir();
		}
		
		if(!sideImageFolder.exists()){
			sideImageFolder.mkdir();
		}
		
		for(int i=0; i < 6; i++){
			if(!new File(System.getProperty("user.home") + (i == 5 ? ("/WoTData/SideImages/mark.png") : ("/WoTData/SideImages/img" + i + ".png"))).exists()){
				  ReadableByteChannel rbc = Channels.newChannel(new URL(requiredImages[i]).openStream());
				  FileOutputStream fos = new FileOutputStream(System.getProperty("user.home") + (i == 5 ? ("/WoTData/SideImages/mark.png") : ("/WoTData/SideImages/img" + i + ".png")));
				  fos.getChannel().transferFrom(rbc, 0, 1 << 24);
				  fos.close();
			}
		}
		
	}
	
	/**
	 * Restart a java application! Made by Veger.
	 * @author Veger
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public void restartApplication() throws URISyntaxException, IOException {
	  final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
	  final File currentJar = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());

	  if(!currentJar.getName().endsWith(".jar"))
	    return;

	  final ArrayList<String> command = new ArrayList<String>();
	  command.add(javaBin);
	  command.add("-jar");
	  command.add(currentJar.getPath());

	  new ProcessBuilder(command).start();
	  System.exit(0);
	}
	
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable() {
            public void run(){
                new Main();
            }
        });
	}
	
	public InputStream randomSideImage() throws MalformedURLException, IOException{
		int num = new Random().nextInt(5);
		if(num == 5){
			num = 0;
		}
		
		return new BufferedInputStream(new File(System.getProperty("user.home") + "/WoTData/SideImages/img" + num + ".png").toURI().toURL().openStream());
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			if(debug){
				Client.setHost("localhost");
				Client.setUser("[Debug]");
				Client.setSocket(new Socket("localhost", 9999));
			} else if(!host.getText().isEmpty() && !username.getText().isEmpty()){
				Client.setHost(host.getText());
				Client.setUser(username.getText());
				Client.setSocket(new Socket(Client.getHost(), 9999));
			} else {
				JOptionPane.showMessageDialog(main, "Please enter a host or username!", "Failed to Connect!", JOptionPane.ERROR_MESSAGE);
			}

			Client.getServer().sendData("user/" + Client.getUser());
			s = Client.getServer();	
			if(!isRunning){
				Client.setGame(new Game(s, Client));
				isRunning = true;
				main.setVisible(false);
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