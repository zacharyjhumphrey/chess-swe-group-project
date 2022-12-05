package frontend;


import java.awt.CardLayout;
=======
import java.awt.*;
import javax.swing.*;

import common.LogoutData;
import common.StartData;

>>>>>>> Stashed changes
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class MenuControl implements ActionListener {
	private JPanel container;
	private Client client;

	public MenuControl(JPanel container, Client client) {
		this.container = container;
		this.client = client;
	}
	
	// handle button click
	public void actionPerformed(ActionEvent ae) {
		// Get the name of the button clicked.
		String command = ae.getActionCommand();

		// The random button sends the user into the match queue
		if (command.equals("Random")) {
			// add functionality here
			CardLayout cardLayout = (CardLayout) container.getLayout();
			cardLayout.show(container, "6");
		}

		// The Logout button sends the user to initial screen
		if (command.equals("Log Out")) {	
			try {
				client.sendToServer(new LogoutData(false));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cardLayout.show(container, "1");
		}	

	}

}
