package frontend;

import javax.swing.*;

import common.AvailableMoves;
import common.PositionData;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class ClientGUI extends JFrame {

	// Constructor that creates the client GUI.
	public ClientGUI() {
		Client client = new Client();
		client.setHost("localhost");
		client.setPort(8300);
		try {
			client.openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Set the title and default close operation.
		this.setTitle("Client");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create the card layout container.
		CardLayout cardLayout = new CardLayout();
		JPanel container = new JPanel(cardLayout);

		// Create the Controllers
		InitialControl ic = new InitialControl(container);
		LoginControl lc = new LoginControl(container, client);
		CreateAccountControl ca = new CreateAccountControl(container, client);
		MenuControl mc = new MenuControl(container, client);
		GameControl gc = new GameControl(container, client);

		client.setLoginControl(lc);
		client.setCreateAccountControl(ca);
		client.setGameControl(gc);
		client.setMenuControl(mc);
		
		// Create the four views. (need the controller to register with the Panels
		JPanel view1 = new InitialPanel(ic);
		JPanel view2 = new LoginPanel(lc);
		JPanel view4 = new CreateAccountPanel(ca);
		JPanel view5 = new MenuPanel(mc);
		JPanel view6 = new GamePanel(gc);

		// Add the views to the card layout container.
		container.add(view1, "1");
		container.add(view2, "2");
		container.add(view4, "3");
		container.add(view5, "4");
		container.add(view6, "5");
		
		// Show the initial view in the card layout.
		cardLayout.show(container, "1");
//		cardLayout.show(container, "5");
//		gc.updateBoard(null);
		
		// Add the card layout container to the JFrame.
		this.add(container, BorderLayout.CENTER);
		
		// Show the JFrame.
		this.setSize(750, 900);
		this.setVisible(true);
	}
	
	// Main function that creates the client GUI when the program is started.
	public static void main(String[] args) {
		new ClientGUI();
	}
}