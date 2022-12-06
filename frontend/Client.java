package frontend;

import ocsf.client.AbstractClient;
import common.*;

public class Client extends AbstractClient {
	// Private data fields for storing the GUI controllers.
	private LoginControl loginControl;
	private CreateAccountControl createAccountControl;
	private GameControl gameControl;
	private MenuControl menuControl;

	// Setter for the LoginControl.
	public void setLoginControl(LoginControl loginControl) {
		this.loginControl = loginControl;
	}
	// Setter for the CreateAccountControl.
	public void setCreateAccountControl(CreateAccountControl createAccountControl) {
		this.createAccountControl = createAccountControl;
	}
	//Setter for the game control
	public void setGameControl(GameControl gc) {
		this.gameControl = gc;
	}
	//Setter for the MenuControl control
	public void setMenuControl(MenuControl mc) {
		this.menuControl = mc;
	}
	// Constructor for initializing the client with default settings.
	public Client() {
		super("localhost", 8300);
	}
	
	// Method that handles messages from the server.
	public void handleMessageFromServer(Object arg0) {
		System.out.println("recieved msg from server");
		// If we received a String, figure out what this event is.
		if (arg0 instanceof String) {
			// Get the text of the message.
			String message = (String) arg0;

			// If we successfully logged in, tell the login controller.
			if (message.equals("LoginSuccessful")) {
				loginControl.loginSuccess();
			}

			// If we successfully created an account, tell the create account controller.
			else if (message.equals("CreateAccountSuccessful")) {
				createAccountControl.createAccountSuccess();
			}
		}

		// If we received an Error, figure out where to display it.
		else if (arg0 instanceof CommunicationError) {
			// Get the Error object.
			CommunicationError error = (CommunicationError) arg0;

			// Display login errors using the login controller.
			if (error.getType().equals("Login")) {
				loginControl.displayError(error.getMessage());
			}

			// Display account creation errors using the create account controller.
			else if (error.getType().equals("CreateAccount")) {
				createAccountControl.displayError(error.getMessage());
			}
		}
		
		if (arg0 instanceof GameInfoData) {
			System.out.println("game info data recieved");
			GameInfoData info = (GameInfoData) arg0;
			menuControl.enterGame();
			gameControl.setBlackUsername(info.getBlack());
			gameControl.setWhiteUsername(info.getWhite());
		}
		
		if (arg0 instanceof AvailableMoves) {
			AvailableMoves temp = (AvailableMoves) arg0;
			AvailableMoves moves = new AvailableMoves(temp.x);
			System.out.println(moves.x);
			System.out.println("recieved moves from server");
			for (PositionData pos : moves.getMoves()) {
				System.out.println(pos);
			}
			gameControl.setAvailableMoves(moves);
		}
		
		if (arg0 instanceof Board) {
			System.out.println("board recieved");
			Board board = (Board) arg0;
			gameControl.updateBoard(board);
		}
		
		if (arg0 instanceof GameWonData) {
			this.gameControl.showDialog("You Win!");
		}
		
		if (arg0 instanceof GameLostData) {
			this.gameControl.showDialog("You lose");
		}
		
		if (arg0 instanceof GameTieData) {
			this.gameControl.showDialog("You tied");
		}
	}
}
