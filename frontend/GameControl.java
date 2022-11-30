package frontend;

import javax.swing.*;

import common.AvailableMoves;
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