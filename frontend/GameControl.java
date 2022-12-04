package frontend;

import javax.swing.*;

import common.AvailableMoves;
import common.Board;
import common.PositionData;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputFilter.Status;
import java.util.ArrayList;

public class GameControl implements ActionListener {
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
		GamePanel panel = (GamePanel) container.getComponent(4);
		panel.setAvailableMoves(availableMoves);
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
	
	public void showDialog(String msg) {
		int optionType = JOptionPane.DEFAULT_OPTION;
		CardLayout cardLayout = (CardLayout) container.getLayout();

		// Game end pop up
		int option = JOptionPane.showConfirmDialog(container, msg, "Game Ended", optionType);
		// if button pressed return to main menu
		if (option == 0) {
			cardLayout.show(container, "4");
		}

	}

	public void sendCheckerClickedToServer(PositionData p) {
		try {
			this.client.sendToServer(p);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void updateBoard(Board board) {
		GamePanel panel = (GamePanel) container.getComponent(4);
		panel.updateBoard(board);
	}

	public void setBlackUsername(String white) {
		GamePanel panel = (GamePanel) container.getComponent(4);
		panel.setWhiteUsername(white);
	}

	public void setWhiteUsername(String black) {
		GamePanel panel = (GamePanel) container.getComponent(4);
		panel.setBlackUsername(black);
	}
}