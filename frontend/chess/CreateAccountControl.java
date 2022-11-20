package chess;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class CreateAccountControl implements ActionListener
{
	// Private data fields for the container and client.
	private JPanel container;
	//private Client Client;
	public JTextField usernameField;
	public JTextField passwordField;
	public JTextField verifypasswordField;
	//private Server server;

	// Constructor for the login controller.
	public CreateAccountControl(JPanel container) //JPanel container, Client chatClient
	{
		this.container = container;
		//this.Client = Client;
	}
	// Handle button clicks.
	public void actionPerformed(ActionEvent ae)
	{
		// Get the name of the button clicked.
		String command = ae.getActionCommand();

		// The Cancel button takes the user back to the initial panel.
		if (command == "Cancel")
		{
			CardLayout cardLayout = (CardLayout)container.getLayout();
			cardLayout.show(container, "1");
		}

		// The Submit button submits the login information to the server.
		else if (command == "Submit")
		{
			
			//send to server
			
			
			//clearing the fields
			///
			///
			///
			///
			//take user back to login
			CardLayout cardLayout = (CardLayout)container.getLayout();
			cardLayout.show(container, "1");
		}
	}
	//once the create contact is successful display a pop-up to show verification and send info to server
	  public void CreateAccountSuccess()
	  {
	    //Send message to client
	  }
	// Method that displays a message in the error - could be invoked by Client or by this class (see above)
	public void displayError(String error)
	{
		LoginPanel loginPanel = (LoginPanel)container.getComponent(1);
		loginPanel.setError(error);

	}
}

