package backend;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import database.Database;
import java.util.*;
import common.AvailableMoves;
import common.CommunicationError;
import common.CreateAccountData;
import common.LoginData;
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

	public Server() {
		super(8300);
		currentGame = new Game(null, null);
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
			if (userswaitingforgame.size() >= 2) {
				// FIXME get player references
				currentGame = new Game(userswaitingforgame.remove(), userswaitingforgame.remove());
				int gameId = (int) (Math.random() * 999999);
				games.put(gameId, currentGame);
				currentGame.startGame();
				System.out.println(currentGame.toString());
				System.out.println("white: " + currentGame.getWhitePlayer().getUsername());
				System.out.println("black: " + currentGame.getBlackPlayer().getUsername());
			} else {
				System.out.println(startData.getUser().getUsername() + " is waiting");
				try {
					arg1.sendToClient(currentGame.waitUser());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		// user clicks on a checker on the board
		if (arg0 instanceof PositionData) {
			PositionData position = (PositionData) arg0;
			System.out.println("position data has been recieved: " + position.x + ", " + position.y);

			AvailableMoves moves = this.currentGame.getCurrentAvailableMoves();

			// only accept valid positions
			if (!position.inbounds()) {
				try {
					// TODO send board to both clients in users' game
					arg1.sendToClient(null);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// if the user clicks on an availableMove checker
			if (moves != null && moves.containsPosition(position)) {
				// move the current piece
				this.currentGame.moveCurrentPieceToPosition(position);
				try {
					// TODO send board to both clients in users' game
					arg1.sendToClient(this.currentGame.getBoard());
					// TODO if win, send win/loss to both clients
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

	private void userClickedPosition(ConnectionToClient conn, PositionData clickedPosition) {
		AvailableMoves movesForCurrentPiece = this.currentGame.getCurrentAvailableMoves();
		Player currentPlayer = this.currentGame.getCurrentPlayer();
//		Player clientPlayer = this.playersMap.get(conn.id);
//		if (clientPlayer != currentPlayer) {
//			conn.sendtoClient(new Error("wait your turn"));
//		}

		if (movesForCurrentPiece == null || !movesForCurrentPiece.containsPosition(clickedPosition)) {
			try {
				conn.sendToClient(this.currentGame.setCurrentPiece(clickedPosition));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}

		this.currentGame.moveCurrentPieceToPosition(clickedPosition);
		try {
			conn.sendToClient(this.currentGame.getBoard());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		System.out.println("Client Connected: " + client.getId());
	}
}
