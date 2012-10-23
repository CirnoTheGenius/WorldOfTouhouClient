package ClientLauncher;

/**
 * @author Cirno the Genius/Tenko.
 */
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import Client.Chat;
import Server.Server;

public class Main extends JFrame implements ActionListener {

	public static ClientVar Client = new ClientVar();
	public static Server s;
	private JFrame main;
	private JTextField host, username;
	private JButton confirm;
	private boolean isRunning;

	public Main(){
		main = new JFrame("World of Touhou: ClientLauncher");
		host = new JTextField("Host");
		username = new JTextField("username");
		confirm = new JButton("Connect!");
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.getContentPane().setLayout(new GridLayout(3, 4));
		main.getContentPane().add(host);
		main.getContentPane().add(username);
		main.getContentPane().add(confirm);
		confirm.addActionListener(this);
		main.setSize(500, 300);
		main.setVisible(true);
		main.setLocationRelativeTo(null);
	}

	public static void main(String[] args){
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
				isRunning = true;
				main.setVisible(false);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}