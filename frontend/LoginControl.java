package frontend;

import java.awt.*;
import javax.swing.*;

import common.LoginData;

import java.awt.event.*;
import java.io.IOException;

public class LoginControl implements ActionListener {
	// Private data fields for the container and chat client.
	private JPanel container;
	private Client client;

	// Constructor for the login controller.
	public LoginControl(JPanel container, Client client) {
		this.container = container;
		this.client = client;
	}

	// Handle button clicks.
	public void actionPerformed(ActionEvent ae) {
		// Get the name of the button clicked.
		String command = ae.getActionCommand();

		// The Cancel button takes the user back to the initial panel.
		if (command == "Cancel") {
			CardLayout cardLayout = (CardLayout) container.getLayout();
			cardLayout.show(container, "1");
		}

		// The Submit button submits the login information to the server.
		else if (command == "Submit") {
			// Get the username and password the user entered.
			LoginPanel loginPanel = (LoginPanel) container.getComponent(1);
			LoginData data = new LoginData(loginPanel.getUsername(), loginPanel.getPassword());

			// Check the validity of the information locally first.
			if (data.getUsername().equals("") || data.getPassword().equals("")) {
				displayError("You must enter a username and password.");
				return;
			}
			// Submit the login information to the server.
			try {
				client.sendToServer(data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// Login successful takes UI to Menu panel, invoked in Client class
	public void loginSuccess() {
		CardLayout cardLayout = (CardLayout) container.getLayout();
		cardLayout.show(container, "4");
	}

	// Method that displays a message in the error
	public void displayError(String error) {
		LoginPanel loginPanel = (LoginPanel) container.getComponent(1);
		loginPanel.setError(error);
	}
}
