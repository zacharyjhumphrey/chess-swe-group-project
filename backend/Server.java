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
		int gameId = (int) (Math.random() * 999999);
		games.put(gameId, currentGame);
		
		//currentGame = new Game();
		//currentGame.startGame();
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
	//get game
	public Game getGame() {
		return currentGame;
	}
	//getting current piece
	public PieceData getCurrentPiece() {
		return currentPiece;
	}
	//handing messages from client
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
			LogoutData logout = (LogoutData)arg0;
			if(logout.inplay()) {
				currentGame.endGame();
				if (this.currentGame.isGameOver()) {
					System.out.println("game over");
					Player whitePlayer = this.currentGame.getWhitePlayer();
					Player blackPlayer = this.currentGame.getBlackPlayer();
					
					ConnectionToClient winnerConnection, loserConnection;
					
					if (whitePlayer.getConnectionToClient().equals(arg1)) {
						winnerConnection = blackPlayer.getConnectionToClient();
						database.updatePlayerStats("win", blackPlayer.getUsername());
					} else {
						winnerConnection = whitePlayer.getConnectionToClient();
						database.updatePlayerStats("win", whitePlayer.getUsername());
					}
					
					try {
						winnerConnection.sendToClient(new GameWonData());
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
					// TODO Auto-generated catch block
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

		// user clicks on a checker on the board
		if (arg0 instanceof PositionData) {
			PositionData position = (PositionData)arg0;
			System.out.println("position data has been recieved: " + position.x + ", " + position.y);

			AvailableMoves moves = this.currentGame.getCurrentAvailableMoves();
			Player clientPlayer = players.get(arg1.getId());

			// only accept valid positions
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

	//handling throw expections
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
}
