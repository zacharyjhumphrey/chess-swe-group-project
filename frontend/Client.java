package frontend;

import ocsf.client.AbstractClient;
import common.AvailableMoves;
import common.CommunicationError;

public class Client extends AbstractClient {
	// Private data fields for storing the GUI controllers.
	private LoginControl loginControl;
	private CreateAccountControl createAccountControl;
	private GameControl gameControl;

	// Setters for the GUI controllers.
	public void setLoginControl(LoginControl loginControl) {
		this.loginControl = loginControl;
	}

	public void setCreateAccountControl(CreateAccountControl createAccountControl) {
		this.createAccountControl = createAccountControl;
	}

	public void setGameControl(GameControl gc) {
		this.gameControl = gc;
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
		
		if (arg0 instanceof AvailableMoves) {
			AvailableMoves moves = (AvailableMoves) arg0;
			System.out.println(moves.getMoves().get(0));
			gameControl.setAvailableMoves(moves);
		}
	}

}
