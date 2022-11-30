package backend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import common.AvailableMoves;
import common.CommunicationError;
import common.CreateAccountData;
import common.LoginData;
import common.PieceData;
import common.Player;
import common.PositionData;
import common.StartData;
import database.Database;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

// TODO we need to actually get two players connected to the same game
public class Server extends AbstractServer {
	private Database database;
	private Queue<Object> userswaitingforgame = new LinkedList<>();
	private PieceData pieceData;
	private PieceData currentPiece;
	
	// FIXME
	private Game game = new Game(null, null);

	public Server() {
		super(8300);
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

	public Server(int port) {
		super(port);
		database = new Database();
	}

	public Game getGame() {
		return game;
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
			userswaitingforgame.add(startData.getUser());

			// when user clicks to start game/ start searching for game
			// takes in startData.user
			// adds user to que to find game

			// plan on adding function to check if size of que is greater than or equal to 2
			// then pop of 2 users and pair
			// them to play in game together

		}

//		if (arg0 instanceof PieceData) {
//
//			PieceData pieceData = (PieceData) arg0;
//			currentPiece = pieceData;
//			List<PositionData> moves = new ArrayList<PositionData>();
//			int x = pieceData.getPosition().x;
//			int y = pieceData.getPosition().y;
//			int offset = 0;
//			PositionData p;
//			System.out.println("x: " + x + ", y: " + y);
//			switch (pieceData.getColor()) {
//			case ("w"):
//				switch (pieceData.getType()) {
//				case "pawn":
//					p = new PositionData(x, y - 1);
//					if (!gameBoard.checkCollision(p)) {
//						moves.add(p);
//					}
//					if (!pieceData.moved) {
//						p = new PositionData(x, y - 2);
//						if (!gameBoard.checkCollision(p)) {
//							moves.add(p);
//						}
//					}
//					break;
//				case "rook":
//					offset = 1;
//					p = new PositionData(x, y - offset);
//					while (!gameBoard.checkCollision(p)) {
//						moves.add(p);
//						offset++;
//						p = new PositionData(x, y - offset);
//					}
//					if (gameBoard.getPieces().get(Arrays.asList(p.x, p.y)).getColor() != "w") {
//						moves.add(p);
//					}
//					break;
//				case "knight":
//					break;
//				case "bishop":
//					break;
//				case "queen":
//					break;
//				case "king":
//					break;
//				}
//				break;
//			case ("b"):
//				switch (pieceData.getType()) {
//				case "pawn":
//					p = new PositionData(x, y + 1);
//					moves.add(p);
//					if (!pieceData.moved) {
//						p = new PositionData(x, y + 2);
//						moves.add(p);
//					}
//					break;
//				case "rook":
//					offset = 1;
//					p = new PositionData(x, y + offset);
//					while (!gameBoard.checkCollision(p)) {
//						moves.add(p);
//						offset++;
//						p = new PositionData(x, y + offset);
//					}
//					if (gameBoard.getPieces().get(Arrays.asList(p.x, p.y)).getColor() != "b") {
//						moves.add(p);
//					}
//
//					break;
//				case "knight":
//					break;
//				case "bishop":
//					break;
//				case "queen":
//					break;
//				case "king":
//					break;
//				}
//				break;
//			}
//			// send array list to user
//			System.out.println(moves.toString());
//		}

		// user clicks on a checker on the board
		if (arg0 instanceof PositionData) {
			PositionData pos = (PositionData) arg0;
			System.out.println("position data has been recieved: " + pos.x + ", " + pos.y);
			PositionData position = (PositionData) arg0;
			AvailableMoves moves = this.game.getCurrentAvailableMoves();
			
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
				this.game.moveCurrentPieceToPosition(position);
				try {
					// TODO send board to both clients in users' game
					arg1.sendToClient(this.game.getBoard());
					// TODO if win, send win/loss to both clients
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return;
			}

			// if the user clicks on an empty space or a new piece
			moves = this.game.setCurrentPiece(position);
			
			try {
				arg1.sendToClient(moves);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("failed to send available moves to client");
				e.printStackTrace();
			}

		}

	}

	// Before this todo is implemented, we need to consider the use case of selecting a new piece to move
	// TODO check that the position is valid. send error to user if is not. (inbounds, position is not occupied by friendly piece)
	// if so
		// remove the opposing piece
	// update the board for both players
	
	private void userClickedPosition(ConnectionToClient conn, PositionData clickedPosition) {
		AvailableMoves movesForCurrentPiece = this.game.getCurrentAvailableMoves();
		Player currentPlayer = this.game.getCurrentPlayer();
//		Player clientPlayer = this.playersMap.get(conn.id);
//		if (clientPlayer != currentPlayer) {
//			conn.sendtoClient(new Error("wait your turn"));
//		}
		
		if (movesForCurrentPiece == null || !movesForCurrentPiece.containsPosition(clickedPosition)) {
			try {
				conn.sendToClient(this.game.setCurrentPiece(clickedPosition));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		
		this.game.moveCurrentPieceToPosition(clickedPosition);
		try {
			conn.sendToClient(this.game.getBoard());
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
		System.out.println("Client Connected");
	}
}
