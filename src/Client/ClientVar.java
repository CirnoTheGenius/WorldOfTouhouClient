package Client;

import java.io.Console;
import java.net.Socket;

import Server.Server;

public class ClientVar {
	
	private Server s;
	private String username, host;
	private Socket connect;
	private ClientVar vars;
	
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
	
	public void setHost(String h){
		host = h;
	}
	
	public void setUser(String u){
		username = u;
	}
	
	public void setSocket(Socket s){
		connect = s;
	}
}
