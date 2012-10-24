package ClientLauncher;

import java.io.Console;
import java.net.Socket;

import Client.Chat;
import Client.Game;
import Server.Server;

public class ClientVar {

	private Server s;
	private String username, host;
	private Socket connect;
	private Chat chat;
	private Game game;
	private boolean isRunning = false;
	
	public ClientVar(){
		s = new Server(this);
	}

	public Console getConsole(){
		return System.console();
	}

	public Server getServer(){
		return s;
	}

	public String getUser(){
		return username;
	}

	public String getHost(){
		return host;
	}

	public Socket getSocket(){
		return connect;
	}

	public Chat getChat(){
		return chat;
	}
	
	public Game getGame(){
		return game;
	}

	public void setHost(String h){
		host = h;
	}

	public void setUser(String u){
		username = u;
	}

	public void setSocket(Socket s){
		connect = s;
	}

	public void setChat(Chat c){
		if(!isRunning){
			chat = c;
		} else {
			
		}
	}
	
	public void setGame(Game g){
		game = g;
	}
}
