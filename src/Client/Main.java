package Client;

/**
 * @author Cirno the Genius/Tenko.
 */
import java.net.Socket;
import Server.Server;

public class Main {
	
	public static ClientVar Client = new ClientVar();
	public static Server s;
	
	public static void main(String[] args){
		try {
			Client.setHost(Client.getConsole().readLine("Server IP/Host: "));
			Client.setUser(Client.getConsole().readLine("Username: "));
			Client.setSocket(new Socket(Client.getHost(), 9999));
			s = Client.getServer();
			s.sendData("user/" + Client.getUser());
			
			while(true){
				s.sendData("chat/" + Client.getUser() + ": " + Client.getConsole().readLine("Chat: "));
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}