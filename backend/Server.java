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
