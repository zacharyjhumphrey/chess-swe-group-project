package backend;

import ocsf.*;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import javax.swing.*;
import database.Database;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.List;
import common.AvailableMoves;
import common.CommunicationError;
import common.CreateAccountData;
import common.LoginData;
import common.PieceData;
import common.Player;
import common.PositionData;
import common.StartData;

public class Server extends AbstractServer {
	private Database database;
	private Queue<Player> userswaitingforgame = new LinkedList<>();
	private PieceData currentPiece;
	private Game currentGame;
	private HashMap<Integer,Game> games = new HashMap<Integer,Game>();

	public Server() {
		super(8300);
		currentGame = new Game();
		database = new Database();
	}
	
	public Server(int port) {
		super(port);
		database = new Database();
	}

	public static void main(String[] args) {
		Server s = new Server();
		try {
			s.listen();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public Game getGame() {
		return currentGame;
	}

	public PieceData getCurrentPiece() {
		return currentPiece;
	}

	@Override
	protected void handleMessageFromClient(Object arg0, ConnectionToClient arg1) {

		if (arg0 instanceof CreateAccountData) {

			CreateAccountData createAccountData = (CreateAccountData) arg0;

			// checks if username dose not exist
			if (!database.usernameExists(createAccountData)) {
				try {
					database.CreateAccount(createAccountData);
					Player newUser = new Player(createAccountData.getUsername(), createAccountData.getPassword());
					arg1.sendToClient("CreateAccountSuccessful");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				try {
					CommunicationError usernameAlreadyExistsError = new CommunicationError("Username already exists.",
							"CreateAccount");
					arg1.sendToClient(usernameAlreadyExistsError);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		if (arg0 instanceof LoginData) {
			LoginData loginData = (LoginData) arg0;

			// Checks if username and password exist in database;
			if (database.credentialsValid(loginData)) {
				try {
					Player user = new Player(loginData.getUsername(), loginData.getPassword());
					arg1.sendToClient("LoginSuccessful");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				try {
					CommunicationError invalidCredentialsError = new CommunicationError("Invalid credentials", "Login");
					arg1.sendToClient(invalidCredentialsError);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		if (arg0 instanceof StartData) {

			StartData startData = (StartData) arg0;
			System.out.println("start data recieved");
			userswaitingforgame.add(startData.getUser());
			if (userswaitingforgame.size()>=2) {
				currentGame = new Game();
				currentGame.setBlack( userswaitingforgame.remove().getUsername());
				currentGame.setWhite( userswaitingforgame.remove().getUsername());
				int gameId = (int)(Math.random()*999999);
				games.put(gameId, currentGame);
				currentGame.startGame();
				System.out.println(currentGame.toString());
				System.out.println("white: "+currentGame.getWhite());
				System.out.println("black: "+currentGame.getBlack());
			} else {
			System.out.println(startData.getUser().getUsername()+" is waiting");
			try {
				arg1.sendToClient(currentGame.waitUser());
			} catch (IOException e) {
				e.printStackTrace();
			}
			}
		}
			
			

		if (arg0 instanceof PositionData) {
			PositionData pos = (PositionData) arg0;
			System.out.println("position data has been recieved: " + pos.x + ", " + pos.y);
			//does position contain a piece
			
			PositionData position = (PositionData) arg0;
			if(currentGame.getPieces().containsKey(Arrays.asList(position.x,position.y))) {
				currentPiece = currentGame.getPieces().get(Arrays.asList(position.x,position.y));
				PositionData p;
				ArrayList<PositionData> moves = new ArrayList<PositionData>();
				int x = currentPiece.getPosition().x;
				int y = currentPiece.getPosition().y;
				int offset = 0;
				switch (currentPiece.getType()) {
				case "Rook":
					System.out.println("rook pressed");
					p = new PositionData(x-1,y);
					if(currentGame.inbounds(p.x, p.y)) {
					offset = 2;
					while(!currentGame.checkCollision(p) && currentGame.inbounds(p.x, p.y)) {
					moves.add(p);
					p = new PositionData(x-offset,y);
					offset++;
					}
					if(!currentPiece.getColor().equals(currentGame.getPieces().get(Arrays.asList(p.x,p.y)).getColor())
							&& currentGame.inbounds(p.x, p.y)) {
						moves.add(p);
					}
					}
					
					p = new PositionData(x+1,y);
					if(currentGame.inbounds(p.x, p.y)) {
					offset = 2;
					while(!currentGame.checkCollision(p) && currentGame.inbounds(p.x, p.y)) {
					moves.add(p);
					p = new PositionData(x+offset,y);
					offset++;
					}
					if(!currentPiece.getColor().equals(currentGame.getPieces().get(Arrays.asList(p.x,p.y)).getColor())
							&& currentGame.inbounds(p.x, p.y)) {
						moves.add(p);
					}
					}
					
					p = new PositionData(x,y+1);
					if(currentGame.inbounds(p.x, p.y)) {
					offset = 2;
					while(!currentGame.checkCollision(p) && currentGame.inbounds(p.x, p.y)) {
					moves.add(p);
					p = new PositionData(x,y+offset);
					offset++;
					}
					if(!currentPiece.getColor().equals(currentGame.getPieces().get(Arrays.asList(p.x,p.y)).getColor())
							&& currentGame.inbounds(p.x, p.y)) {
						moves.add(p);
					}
					}
					
					p = new PositionData(x,y-1);
					if(currentGame.inbounds(p.x, p.y)) {
					offset = 2;
					while(!currentGame.checkCollision(p) && currentGame.inbounds(p.x, p.y)) {
					moves.add(p);
					p = new PositionData(x,y-offset);
					offset++;
					}
					if(!currentPiece.getColor().equals(currentGame.getPieces().get(Arrays.asList(p.x,p.y)).getColor())
							&& currentGame.inbounds(p.x, p.y)) {
						moves.add(p);
					}
					}
					break;
				case "Knight":
					System.out.println("Knight pressed");
					p = new PositionData(x+2,y+1);
					if (currentGame.inbounds(p.x, p.y)) {
						if(!currentGame.checkCollision(p)) {
					moves.add(p);
						} else if(!currentPiece.getColor().equals(currentGame.getPieces().get(Arrays.asList(p.x,p.y)).getColor())){
							moves.add(p);
						}
					}
					p = new PositionData(x+2,y-1);
					if (currentGame.inbounds(p.x, p.y)) {
						if(!currentGame.checkCollision(p)) {
					moves.add(p);
						} else if(!currentPiece.getColor().equals(currentGame.getPieces().get(Arrays.asList(p.x,p.y)).getColor())){
							moves.add(p);
						}
					}
					p = new PositionData(x-2,y+1);
					if (currentGame.inbounds(p.x, p.y)) {
						if(!currentGame.checkCollision(p)) {
					moves.add(p);
						} else if(!currentPiece.getColor().equals(currentGame.getPieces().get(Arrays.asList(p.x,p.y)).getColor())){
							moves.add(p);
						}
					}
					p = new PositionData(x-2,y-1);
					if (currentGame.inbounds(p.x, p.y)) {
						if(!currentGame.checkCollision(p)) {
					moves.add(p);
						} else if(!currentPiece.getColor().equals(currentGame.getPieces().get(Arrays.asList(p.x,p.y)).getColor())){
							moves.add(p);
						}
					}
		
					p = new PositionData(x+1,y+2);
					if (currentGame.inbounds(p.x, p.y)) {
						if(!currentGame.checkCollision(p)) {
					moves.add(p);
						} else if(!currentPiece.getColor().equals(currentGame.getPieces().get(Arrays.asList(p.x,p.y)).getColor())){
							moves.add(p);
						}
					}
					p = new PositionData(x+1,y-2);
					if (currentGame.inbounds(p.x, p.y)) {
						if(!currentGame.checkCollision(p)) {
					moves.add(p);
						} else if(!currentPiece.getColor().equals(currentGame.getPieces().get(Arrays.asList(p.x,p.y)).getColor())){
							moves.add(p);
						}
					}
					p = new PositionData(x-1,y+2);
					if (currentGame.inbounds(p.x, p.y)) {
						if(!currentGame.checkCollision(p)) {
					moves.add(p);
						} else if(!currentPiece.getColor().equals(currentGame.getPieces().get(Arrays.asList(p.x,p.y)).getColor())){
							moves.add(p);
						}
					}
					p = new PositionData(x-1,y-2);
					if (currentGame.inbounds(p.x, p.y)) {
						if(!currentGame.checkCollision(p)) {
					moves.add(p);
						} else if(!currentPiece.getColor().equals(currentGame.getPieces().get(Arrays.asList(p.x,p.y)).getColor())){
							moves.add(p);
						}
					}
					break;
				case "Bishop":
					System.out.println("bishop pressed");
					p = new PositionData(x+1,y+1);
					if(currentGame.inbounds(p.x, p.y)) {
						offset=2;
						while(currentGame.inbounds(p.x, p.y)) {
						if(!currentGame.checkCollision(p)) {
							moves.add(p);
								} else if(!currentPiece.getColor().equals(currentGame.getPieces().get(Arrays.asList(p.x,p.y)).getColor())){
									moves.add(p);
								} else {
									break;
								}
						p = new PositionData(x+offset, y+offset);
						offset++;
					}
					}
					
					p = new PositionData(x-1,y-1);
					if(currentGame.inbounds(p.x, p.y)) {
						offset=2;
						while(currentGame.inbounds(p.x, p.y)) {
						if(!currentGame.checkCollision(p)) {
							moves.add(p);
								} else if(!currentPiece.getColor().equals(currentGame.getPieces().get(Arrays.asList(p.x,p.y)).getColor())){
									moves.add(p);
								} else {
									break;
								}
						p = new PositionData(x-offset, y-offset);
						offset++;
					}
					}
					
					p = new PositionData(x-1,y+1);
					if(currentGame.inbounds(p.x, p.y)) {
						offset=2;
						while(currentGame.inbounds(p.x, p.y)) {
						if(!currentGame.checkCollision(p)) {
							moves.add(p);
								} else if(!currentPiece.getColor().equals(currentGame.getPieces().get(Arrays.asList(p.x,p.y)).getColor())){
									moves.add(p);
								} else {
									break;
								}
						p = new PositionData(x-offset, y+offset);
						offset++;
					}
					}
					
					p = new PositionData(x+1,y-1);
					if(currentGame.inbounds(p.x, p.y)) {
						offset=2;
						while(currentGame.inbounds(p.x, p.y)) {
						if(!currentGame.checkCollision(p)) {
							moves.add(p);
								} else if(!currentPiece.getColor().equals(currentGame.getPieces().get(Arrays.asList(p.x,p.y)).getColor())){
									moves.add(p);
								} else {
									break;
								}
						p = new PositionData(x+offset, y-offset);
						offset++;
					}
					}
					break;
				case "Queen":
					System.out.println("queen pressed");
					
					p = new PositionData(x+1,y+1);
					if(currentGame.inbounds(p.x, p.y)) {
						offset=2;
						while(currentGame.inbounds(p.x, p.y)) {
						if(!currentGame.checkCollision(p)) {
							moves.add(p);
								} else if(!currentPiece.getColor().equals(currentGame.getPieces().get(Arrays.asList(p.x,p.y)).getColor())){
									moves.add(p);
								} else {
									break;
								}
						p = new PositionData(x+offset, y+offset);
						offset++;
					}
					}
					
					p = new PositionData(x-1,y-1);
					if(currentGame.inbounds(p.x, p.y)) {
						offset=2;
						while(currentGame.inbounds(p.x, p.y)) {
						if(!currentGame.checkCollision(p)) {
							moves.add(p);
								} else if(!currentPiece.getColor().equals(currentGame.getPieces().get(Arrays.asList(p.x,p.y)).getColor())){
									moves.add(p);
								} else {
									break;
								}
						p = new PositionData(x-offset, y-offset);
						offset++;
					}
					}
					
					p = new PositionData(x-1,y+1);
					if(currentGame.inbounds(p.x, p.y)) {
						offset=2;
						while(currentGame.inbounds(p.x, p.y)) {
						if(!currentGame.checkCollision(p)) {
							moves.add(p);
								} else if(!currentPiece.getColor().equals(currentGame.getPieces().get(Arrays.asList(p.x,p.y)).getColor())){
									moves.add(p);
								} else {
									break;
								}
						p = new PositionData(x-offset, y+offset);
						offset++;
					}
					}
					
					p = new PositionData(x+1,y-1);
					if(currentGame.inbounds(p.x, p.y)) {
						offset=2;
						while(currentGame.inbounds(p.x, p.y)) {
						if(!currentGame.checkCollision(p)) {
							moves.add(p);
								} else if(!currentPiece.getColor().equals(currentGame.getPieces().get(Arrays.asList(p.x,p.y)).getColor())){
									moves.add(p);
								} else {
									break;
								}
						p = new PositionData(x+offset, y-offset);
						offset++;
					}
					}
					
					p = new PositionData(x-1,y);
					if(currentGame.inbounds(p.x, p.y)) {
					//System.out.println(currentGame.inbounds(p.x, p.y));
					offset = 2;
					while(!currentGame.checkCollision(p) && currentGame.inbounds(p.x, p.y)) {
					moves.add(p);
					p = new PositionData(x-offset,y);
					offset++;
					}
					if(!currentPiece.getColor().equals(currentGame.getPieces().get(Arrays.asList(p.x,p.y)).getColor())
							&& currentGame.inbounds(p.x, p.y)) {
						moves.add(p);
					}
					}
					
					p = new PositionData(x+1,y);
					if(currentGame.inbounds(p.x, p.y)) {
					offset = 2;
					while(!currentGame.checkCollision(p) && currentGame.inbounds(p.x, p.y)) {
					moves.add(p);
					p = new PositionData(x+offset,y);
					offset++;
					}
					if(!currentPiece.getColor().equals(currentGame.getPieces().get(Arrays.asList(p.x,p.y)).getColor())
							&& currentGame.inbounds(p.x, p.y)) {
						moves.add(p);
					}
					}
					
					p = new PositionData(x,y+1);
					if(currentGame.inbounds(p.x, p.y)) {
					offset = 2;
					while(!currentGame.checkCollision(p) && currentGame.inbounds(p.x, p.y)) {
					moves.add(p);
					p = new PositionData(x,y+offset);
					offset++;
					}
					if(!currentPiece.getColor().equals(currentGame.getPieces().get(Arrays.asList(p.x,p.y)).getColor())
							&& currentGame.inbounds(p.x, p.y)) {
						moves.add(p);
					}
					}
					
					p = new PositionData(x,y-1);
					if(currentGame.inbounds(p.x, p.y)) {
					offset = 2;
					while(!currentGame.checkCollision(p) && currentGame.inbounds(p.x, p.y)) {
					moves.add(p);
					p = new PositionData(x,y-offset);
					offset++;
					}
					if(!currentPiece.getColor().equals(currentGame.getPieces().get(Arrays.asList(p.x,p.y)).getColor())
							&& currentGame.inbounds(p.x, p.y)) {
						moves.add(p);
					}
					}
					break;
				case "King":
					System.out.println("king pressed");
					p = new PositionData(x+1,y);
					if (currentGame.inbounds(p.x, p.y)) {
						if(!currentGame.checkCollision(p)) {
					moves.add(p);
						} else if(!currentPiece.getColor().equals(currentGame.getPieces().get(Arrays.asList(p.x,p.y)).getColor())){
							moves.add(p);
						}
					}
					p = new PositionData(x+1,y-1);
					if (currentGame.inbounds(p.x, p.y)) {
						if(!currentGame.checkCollision(p)) {
					moves.add(p);
						} else if(!currentPiece.getColor().equals(currentGame.getPieces().get(Arrays.asList(p.x,p.y)).getColor())){
							moves.add(p);
						}
					}
					p = new PositionData(x+1,y+1);
					if (currentGame.inbounds(p.x, p.y)) {
						if(!currentGame.checkCollision(p)) {
					moves.add(p);
						} else if(!currentPiece.getColor().equals(currentGame.getPieces().get(Arrays.asList(p.x,p.y)).getColor())){
							moves.add(p);
						}
					}
					p = new PositionData(x-1,y);
					if (currentGame.inbounds(p.x, p.y)) {
						if(!currentGame.checkCollision(p)) {
					moves.add(p);
						} else if(!currentPiece.getColor().equals(currentGame.getPieces().get(Arrays.asList(p.x,p.y)).getColor())){
							moves.add(p);
						}
					}
		
					p = new PositionData(x-1,y+1);
					if (currentGame.inbounds(p.x, p.y)) {
						if(!currentGame.checkCollision(p)) {
					moves.add(p);
						} else if(!currentPiece.getColor().equals(currentGame.getPieces().get(Arrays.asList(p.x,p.y)).getColor())){
							moves.add(p);
						}
					}
					p = new PositionData(x-1,y-1);
					if (currentGame.inbounds(p.x, p.y)) {
						if(!currentGame.checkCollision(p)) {
					moves.add(p);
						} else if(!currentPiece.getColor().equals(currentGame.getPieces().get(Arrays.asList(p.x,p.y)).getColor())){
							moves.add(p);
						}
					}
					p = new PositionData(x,y+1);
					if (currentGame.inbounds(p.x, p.y)) {
						if(!currentGame.checkCollision(p)) {
					moves.add(p);
						} else if(!currentPiece.getColor().equals(currentGame.getPieces().get(Arrays.asList(p.x,p.y)).getColor())){
							moves.add(p);
						}
					}
					p = new PositionData(x,y-1);
					if (currentGame.inbounds(p.x, p.y)) {
						if(!currentGame.checkCollision(p)) {
					moves.add(p);
						} else if(!currentPiece.getColor().equals(currentGame.getPieces().get(Arrays.asList(p.x,p.y)).getColor())){
							moves.add(p);
						}
					}
					break;
				case ("Pawn"):
					switch (currentPiece.getColor()) {
					case "w":
						p = new PositionData(x-1, y);
						if (!currentGame.checkCollision(p)) {
							moves.add(p);
						}
						if (!currentPiece.moved) {
							p = new PositionData(x-2, y);
							if (!currentGame.checkCollision(p)) {
								moves.add(p);
							}
						}
						break;
					case "b":
						p = new PositionData(x+1, y);
						if (!currentGame.checkCollision(p)) {
							moves.add(p);
						}
						if (!currentPiece.moved) {
							p = new PositionData(x+2, y);
							if (!currentGame.checkCollision(p)) {
								moves.add(p);
							}
						}
						break;
					}
					break;
				}
				AvailableMoves movelist = new AvailableMoves(moves);
				System.out.println(moves);
				
				//not working
//				try {
//					arg1.sendToClient(movelist);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					System.out.println("failed to send available moves to client");
//					e.printStackTrace();
//				}
				
			} else {
				System.out.println("blank space");
			}
			
			

		}
		

	}

	protected void listeningException(Throwable exception) {
		System.out.println("Listening Exception:" + exception);
		exception.printStackTrace();
		System.out.println(exception.getMessage());

		if (this.isListening()) {
			System.out.println("server not listening");
		}

	}

	protected void serverStarted() {
		System.out.println("Server Started");
	}

	protected void serverStopped() {
		System.out.println("Server Stopped");
	}

	protected void serverClosed() {
		System.out.println("server closed");
	}

	protected void clientConnected(ConnectionToClient client) {
		System.out.println("Client Connected");
	}
}
=======
package backend;

import java.io.IOException;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import database.Database;
import java.util.*;
import common.AvailableMoves;
import common.CommunicationError;
import common.CreateAccountData;
import common.GameLostData;
import common.GameTieData;
import common.GameWonData;
import common.LoginData;
import common.LogoutData;
import common.PieceData;
import common.Player;
import common.PositionData;
import common.StartData;

// TODO we need to actually get two players connected to the same game
public class Server extends AbstractServer {
	private Database database;
	private Queue<Player> userswaitingforgame = new LinkedList<>();
	private PieceData currentPiece;
	private Game currentGame;
	private HashMap<Integer, Game> games = new HashMap<Integer, Game>();
	private HashMap<Long, Player> players = new HashMap<Long, Player>();

	public Server() {
		super(8300);
		database = new Database();
	}

	public Server(int port) {
		super(port);
		database = new Database();
	}

	public void createGame(Player p1, Player p2) {
		currentGame = new Game(p1, p2);
		currentPiece = null;
		int gameId = (int) (Math.random() * 999999);
		games.put(gameId, currentGame);
		
//		currentGame = new Game();
//		currentGame.startGame();
	}

	public static void main(String[] args) {
		Server s = new Server();
		try {
			s.listen();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Game getGame() {
		return currentGame;
	}

	public PieceData getCurrentPiece() {
		return currentPiece;
	}

	@Override
	protected void handleMessageFromClient(Object arg0, ConnectionToClient arg1) {

		if (arg0 instanceof CreateAccountData) {
			CreateAccountData createAccountData = (CreateAccountData) arg0;

			// checks if username dose not exist
			if (!database.usernameExists(createAccountData)) {
				try {
					database.createAccount(createAccountData);
					Player newUser = new Player(createAccountData.getUsername(), createAccountData.getPassword());
					arg1.sendToClient("CreateAccountSuccessful");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				try {
					CommunicationError usernameAlreadyExistsError = new CommunicationError("Username already exists.",
							"CreateAccount");
					arg1.sendToClient(usernameAlreadyExistsError);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		if (arg0 instanceof LoginData) {
			LoginData loginData = (LoginData) arg0;

			// Checks if username and password exist in database;
			if (database.credentialsValid(loginData)) {
				try {
					Player player = new Player(loginData.getUsername(), loginData.getPassword());
					player.setConnectionToClient(arg1);
					players.put(arg1.getId(), player);

					arg1.sendToClient("LoginSuccessful");
					
					System.out.println("Client Logged In : " + player.getUsername() + ", " + arg1.getId());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				try {
					CommunicationError invalidCredentialsError = new CommunicationError("Invalid credentials", "Login");
					arg1.sendToClient(invalidCredentialsError);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		
		if (arg0 instanceof LogoutData) {
			System.out.print("user loged out");
			LogoutData logoutdata = (LogoutData) arg0;
			if(logoutdata.ingame()) {
			currentGame.endGame();
			ConnectionToClient blackConnection = this.currentGame.getBlackPlayer().getConnectionToClient();
			ConnectionToClient whiteConnection = this.currentGame.getWhitePlayer().getConnectionToClient();
			if(arg1 == blackConnection) {
				try {
					whiteConnection.sendToClient(new GameWonData());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				try {
					blackConnection.sendToClient(new GameWonData());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			} else {
				if(userswaitingforgame.size() > 0) {
					userswaitingforgame.remove();
				}
			}
			
			
			
		}

		if (arg0 instanceof StartData) {
			StartData startData = (StartData) arg0;
			System.out.println("start data recieved");
			Player playerRequestingStart = players.get(arg1.getId());
			if(userswaitingforgame.size()==0) {
				userswaitingforgame.add(playerRequestingStart);
				System.out.println("added to que");
			} else if(!userswaitingforgame.peek().getUsername().equals(playerRequestingStart.getUsername())) {
			userswaitingforgame.add(playerRequestingStart);
			System.out.println("added to que");
			} else {
				System.out.println("not added to que");
			}

			
			if (userswaitingforgame.size() >= 2) {
				// FIXME get player references
				Player p1 = userswaitingforgame.remove();
				Player p2 = userswaitingforgame.remove();
				GameInfoData info = new GameInfoData(p1.getUsername(), p2.getUsername());
				createGame(p1, p2);
				try {
					p2.getConnectionToClient().sendToClient(currentGame.getBoard());
					p1.getConnectionToClient().sendToClient(currentGame.getBoard());
					p2.getConnectionToClient().sendToClient(info);
					p1.getConnectionToClient().sendToClient(info);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(currentGame.toString());
				System.out.println("white: " + currentGame.getWhitePlayer().getUsername());
				System.out.println("black: " + currentGame.getBlackPlayer().getUsername());
			}
		}

		// user clicks on a checker on the board
		if (arg0 instanceof PositionData) {
			PositionData position = (PositionData)arg0;
			System.out.println("position data has been recieved: " + position.x + ", " + position.y);

			AvailableMoves moves = this.currentGame.getCurrentAvailableMoves();
			Player clientPlayer = players.get(arg1.getId());
			
			if (!position.inbounds() || clientPlayer != this.currentGame.getCurrentPlayer()) {
				try {
					arg1.sendToClient(null);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;
			}

			// if the user clicks on an availableMove checker
			if (moves != null && moves.containsPosition(position)) {
				// move the current piece
				this.currentGame.moveCurrentPieceToPosition(position);
				try {
					ConnectionToClient blackConnection = this.currentGame.getBlackPlayer().getConnectionToClient();
					ConnectionToClient whiteConnection = this.currentGame.getWhitePlayer().getConnectionToClient();

					blackConnection.sendToClient(this.currentGame.getBoard());
					whiteConnection.sendToClient(this.currentGame.getBoard());

					// TODO if win, send win/loss to both clients
					if (this.currentGame.isGameOver()) {
						System.out.println("game over");
						Player whitePlayer = this.currentGame.getWhitePlayer();
						Player blackPlayer = this.currentGame.getBlackPlayer();
						
						ConnectionToClient winnerConnection, loserConnection;
						
						if (this.currentGame.getWinner() == whitePlayer) {
							winnerConnection = whiteConnection;
							loserConnection = blackConnection;
							database.updatePlayerStats("win", whitePlayer.getUsername());
							database.updatePlayerStats("loss", blackPlayer.getUsername());
						} else {
							winnerConnection = blackConnection;
							loserConnection = whiteConnection;
							database.updatePlayerStats("win", blackPlayer.getUsername());
							database.updatePlayerStats("loss", whitePlayer.getUsername());
						}
						
						winnerConnection.sendToClient(new GameWonData());
						loserConnection.sendToClient(new GameLostData());
					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return;
			}

			// if the user clicks on a piece they do not own
			if (this.currentGame.playerDoesNotOwnPiece(position)) {
				try {
					arg1.sendToClient(null);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return;
			}

			// if the user clicks on an empty space or a new piece
			moves = this.currentGame.setCurrentPiece(position);

			try {
				arg1.sendToClient(moves);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("failed to send available moves to client");
				e.printStackTrace();
			}
			
		}

	}



	protected void listeningException(Throwable exception) {
		System.out.println("Listening Exception:" + exception);
		exception.printStackTrace();
		System.out.println(exception.getMessage());

		if (this.isListening()) {
			System.out.println("server not listening");
		}

	}

	protected void serverStarted() {
		System.out.println("Server Started");
	}

	protected void serverStopped() {
		System.out.println("Server Stopped");
	}

	protected void serverClosed() {
		System.out.println("server closed");
	}

	protected void clientConnected(ConnectionToClient clientConn) {
		System.out.println("Client Connected: " + clientConn.getId());
	}
}
