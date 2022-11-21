<<<<<<< HEAD
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

=======
package frontend;

import java.awt.CardLayout;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.JPanel;
import backend.CreateAccountData;

public class CreateAccountControl implements ActionListener
{
	// Private data fields for the container and chat client.
	private JPanel container;
	private Client client;
	  
	// Constructor for the login controller.
	public CreateAccountControl(JPanel container, Client client)
	{
		this.container = container;
		this.client = client;
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
			// Get the username and password the user entered.
			CreateAccountPanel createAccountPanel = (CreateAccountPanel)container.getComponent(2);
			CreateAccountData data = new CreateAccountData(createAccountPanel.getUsername(), createAccountPanel.getPassword());
	      
			// Check the validity of the information locally first.
			if (data.getUsername().equals("") || data.getPassword().equals(""))
			{
				displayError("You must enter a username and password.");
				return;
			}
			else if (data.getPassword().length() < 6)
			{
				displayError("Password must contain 6 or more characters.");
				return;
			}
			else if (!data.getPassword().equals(createAccountPanel.getVerifyPassword()))
			{
				displayError("Password does not match verified password.");
				return;
			}
			
			// Submit the login information to the server.
			else 
				try 
			{
				client.sendToServer(data);
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	// After the login is successful, set the User object and display the contacts screen. - this method would be invoked by 
	//the ChatClient
	public void createAccountSuccess()
	{
		CardLayout cardLayout = (CardLayout)container.getLayout();
		cardLayout.show(container, "2");
	}
	
	// Method that displays a message in the error - could be invoked by ChatClient or by this class (see above)
	public void displayError(String error)
	{
		CreateAccountPanel createAccountPanel = (CreateAccountPanel)container.getComponent(2);
		createAccountPanel.setError(error);
	}
}
>>>>>>> 716601b (implemnted databse connectivity + panels/controllers)
