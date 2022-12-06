package common;

import java.io.Serializable;

import ocsf.server.ConnectionToClient;

public class Player implements Serializable {
	// Private data fields for the username and password.
	private String username;
	private String password;
	private PieceColor color;
	private int gameNumber;
	private ConnectionToClient conn;

	// Getters for the username and password.
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	// Setters for the username and password.
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public PieceColor getColor() {
		return color;
	}

	public void setColor(PieceColor color) {
		this.color = color;
	}
	
	public void setGameNumber(int n) {
		this.gameNumber = n;
	}
	public int getGameNumber() {
		return gameNumber;
	}
	

	// Constructor that initializes the username and password.
	public Player(String username, String password) {
		setUsername(username);
		setPassword(password);
	}
	//getting connection
	public ConnectionToClient getConnectionToClient() {
		return conn;
	}
	//setting connection object
	public void setConnectionToClient(ConnectionToClient conn) {
		this.conn = conn;
	}
}
