package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameControl {
	private JPanel container;
	private Client client;

	// Constructor for the game controller.
	public GameControl(JPanel container, Client client) {
		this.container = container;
		this.client = client;
	}


	public void actionPerformed(ActionEvent ae) {
		// Get the name of the button clicked.
		String command = ae.getActionCommand();

	}
}