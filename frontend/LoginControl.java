package chess;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class LoginControl implements ActionListener
{
	// Private data fields for the container and chat client.
	private JPanel container;
	//private Client chatClient;
	private JTextField usernameField;
	private JTextField passwordField;
	
	// Constructor for the login controller.
	public LoginControl(JPanel container) //JPanel container, Client chatClient
	{
		this.container = container;
		//this.chatClient = chatClient;
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
			CardLayout cardLayout = (CardLayout)container.getLayout();
			cardLayout.show(container, "5");
		} 
	}

	// After the login is successful, set the User object and display the contacts screen. - this method would be invoked by 
	//the Client
	public void loginSuccess()
	{
		CardLayout cardLayout = (CardLayout)container.getLayout();
		cardLayout.show(container, "3");
	}

	// Method that displays a message in the error - could be invoked by Client or by this class (see above)
	public void displayError(String error)
	{
		LoginPanel loginPanel = (LoginPanel)container.getComponent(1);
		loginPanel.setError(error);

	}
}
