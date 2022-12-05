package frontend;

import javax.swing.*;

import common.AvailableMoves;
<<<<<<< Updated upstream
=======
import common.Board;
import common.LogoutData;
>>>>>>> Stashed changes
import common.PositionData;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class GameControl {
	private JPanel container;
	private Client client;
	private AvailableMoves availableMoves;

	// Constructor for the game controller.
	public GameControl(JPanel container, Client client) {
		this.container = container;
		this.client = client;
	}
	
	public AvailableMoves getAvailableMoves() {
		return this.availableMoves;
	}
	
	public void setAvailableMoves(AvailableMoves availableMoves) {
		this.availableMoves = availableMoves;
	}

	public void actionPerformed(ActionEvent ae) {
		// Get the name of the button clicked.
		String command = ae.getActionCommand();

		int optionType;
		// set game status here
		String status = "win";// tie, loss
		String gameText = "";
		// creating cardlayout object
		CardLayout cardLayout = (CardLayout) container.getLayout();

		// if logout pressed
		if (command.equals("Log Out")) {
			optionType = JOptionPane.WARNING_MESSAGE;
			int verify = JOptionPane.showConfirmDialog(container, "Are you sure you want to log out?", "Log Out",
					optionType);
			// if button pressed return to main menu
			if (verify == 0) {
				LogoutData logout = new LogoutData(true);
				try {
					client.sendToServer(logout);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cardLayout.show(container, "1");
			}
		}

		// TEMP button to check game ending popup
		if (command.equals("Temp")) {

			optionType = JOptionPane.DEFAULT_OPTION;
			// Game end pop up
			int option = JOptionPane.showConfirmDialog(container, gameText, "Game Ended", optionType);
			// if button pressed return to main menu
			if (option == 0) {
				cardLayout.show(container, "4");
			}
		}

	}
	
	public void sendCheckerClickedToServer(PositionData p) {
		try {
			this.client.sendToServer(p);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}