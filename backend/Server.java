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
import common.GameWonData;
import common.LoginData;
import common.LogoutData;
import common.PieceData;
import common.Player;
import common.PositionData;
import common.StartData;

public class Server extends AbstractServer {
	private Database database;
	private Queue<Player> userswaitingforgame = new LinkedList<>();
	private PieceData currentPiece;
	private Game currentGame;
	private HashMap<Integer, Game> games = new HashMap<Integer, Game>();
	private HashMap<Long, Player> players = new HashMap<Long, Player>();

	//constructors
	public Server() {
		super(8300);
		database = new Database();
	}
	public Server(int port) {
		super(port);
		database = new Database();
	}
	
	public void addPlayer(Long l, Player p) {
		players.put(l, p);
	}
	
	
	//create a new game and assign it an id
	//assign the id to the players in the game 
	//put the game id and game into hashmap of games
	public void createGame(Player p1, Player p2) {
		currentGame = new Game(p1, p2);
		int gameId = (int) (Math.random() * 999999);
		p1.setGameNumber(gameId);
		p2.setGameNumber(gameId);
		games.put(gameId, currentGame);
	}
	
	
	//return the current game
	public Game getGame() {
		return currentGame;
	}
	//return the currentPiece
	public PieceData getCurrentPiece() {
		return currentPiece;
	}
	//handling messages from client
	@Override
	protected void handleMessageFromClient(Object arg0, ConnectionToClient arg1) {

		//if create account data is received
		//check that username is not in database
		//then add user if not
		if (arg0 instanceof CreateAccountData) {
			CreateAccountData createAccountData = (CreateAccountData) arg0;

			if (!database.usernameExists(createAccountData)) {
				try {
					database.createAccount(createAccountData);
					Player newUser = new Player(createAccountData.getUsername(), createAccountData.getPassword());
					arg1.sendToClient("CreateAccountSuccessful");
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				try {
					CommunicationError usernameAlreadyExistsError = new CommunicationError("Username already exists.",
							"CreateAccount");
					arg1.sendToClient(usernameAlreadyExistsError);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		//if login data received
		//check against data base 
		//allow user to login
		//or block user if error in credentials
		if (arg0 instanceof LoginData) {
			LoginData loginData = (LoginData) arg0;
			if (database.credentialsValid(loginData)) {
				try {
					Player player = new Player(loginData.getUsername(), loginData.getPassword());
					player.setConnectionToClient(arg1);
					players.put(arg1.getId(), player);

					arg1.sendToClient("LoginSuccessful");
					
					System.out.println("Client Logged In : " + player.getUsername() + ", " + arg1.getId());
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				try {
					CommunicationError invalidCredentialsError = new CommunicationError("Invalid credentials", "Login");
					arg1.sendToClient(invalidCredentialsError);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		//if logout data is received
		//check if player is in a game
		//if so make that person the loser and the other the winner
		//if person is in waiting queue
		//remove the person from queue
		if (arg0 instanceof LogoutData) {
			LogoutData logout = (LogoutData)arg0;
			if(logout.inplay()) {
				Player p = players.get(arg1.getId());
				currentGame = games.get(p.getGameNumber());
				currentGame.endGame();
				if (this.currentGame.isGameOver()) {
					System.out.println("game over");
					Player whitePlayer = this.currentGame.getWhitePlayer();
					Player blackPlayer = this.currentGame.getBlackPlayer();
					
					ConnectionToClient winnerConnection, loserConnection;
					
					if (whitePlayer.getConnectionToClient().equals(arg1)) {
						winnerConnection = blackPlayer.getConnectionToClient();
						loserConnection = whitePlayer.getConnectionToClient();
						database.updatePlayerStats("win", blackPlayer.getUsername());
						database.updatePlayerStats("loss", whitePlayer.getUsername());
					} else {
						winnerConnection = whitePlayer.getConnectionToClient();
						loserConnection = blackPlayer.getConnectionToClient();
						database.updatePlayerStats("loss", blackPlayer.getUsername());
						database.updatePlayerStats("win", whitePlayer.getUsername());
					}
					
					try {
						winnerConnection.sendToClient(new GameWonData());
						loserConnection.sendToClient(new GameLostData());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else {
				if(userswaitingforgame.size()>0) {
					userswaitingforgame.remove();
				}
			}
		}
		
		//if start data is received
		//add the player into the queue if it is empty
		//if not empty pair players from queue and create a new game
		//also checks if same player is trying to enter queue and prevents it
		if (arg0 instanceof StartData) {
			StartData startData = (StartData) arg0;
			System.out.println("start data recieved");
			Player playerRequestingStart = players.get(arg1.getId());
			if(userswaitingforgame.size()==0) {
				userswaitingforgame.add(playerRequestingStart);
			} else if (!userswaitingforgame.peek().getUsername().equals(playerRequestingStart.getUsername())) {
				userswaitingforgame.add(playerRequestingStart);
			} else {
				try {
					arg1.sendToClient(null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if (userswaitingforgame.size() >= 2) {
				// FIXME get player references
				Player p1 = userswaitingforgame.remove();
				Player p2 = userswaitingforgame.remove();
				GameInfoData info = new GameInfoData(p1.getUsername(), p2.getUsername());
				createGame(p1, p2);
				
				try {
					p2.getConnectionToClient().sendToClient(currentGame.getBoard());
					p2.getConnectionToClient().sendToClient(info);
					p1.getConnectionToClient().sendToClient(currentGame.getBoard());
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

		//if position data is recieved
		//checks if position contains piece 
		//also checks if position is an available move
		//moves pieces and sends end of game data (win,tie,lose)
		if (arg0 instanceof PositionData) {
			PositionData position = (PositionData)arg0;
			System.out.println("position data has been recieved: " + position.x + ", " + position.y);
			Player p = players.get(arg1.getId());
			currentGame = games.get(p.getGameNumber());
			//set currentgame to the players game
			AvailableMoves moves = this.currentGame.getCurrentAvailableMoves();
			Player clientPlayer = players.get(arg1.getId());

			// only accept valid positions
			if (!position.inbounds() || clientPlayer != this.currentGame.getCurrentPlayer()) {
				try {
					arg1.sendToClient(null);
				} catch (IOException e) {
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

	//handling thrown exceptions
	protected void listeningException(Throwable exception) {
		System.out.println("Listening Exception:" + exception);
		exception.printStackTrace();
		System.out.println(exception.getMessage());

		if (this.isListening()) {
			System.out.println("server not listening");
		}

	}
	//printing message to console if server started
	protected void serverStarted() {
		System.out.println("Server Started");
	}
	//printing message to console if server Stopped
	protected void serverStopped() {
		System.out.println("Server Stopped");
	}
	//printing message to console if server closed
	protected void serverClosed() {
		System.out.println("server closed");
	}
	//printing message to console if client connected
	protected void clientConnected(ConnectionToClient clientConn) {
		System.out.println("Client Connected: " + clientConn.getId());
	}
	
	//create server
		public static void main(String[] args) {
			Server s = new Server();
			try {
				s.listen();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
}
