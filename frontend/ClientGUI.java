package chess;

import javax.swing.*;
import java.awt.*;

public class ClientGUI extends JFrame
{


	// Constructor that creates the client GUI.
	public ClientGUI()
	{
		// Set the title and default close operation.
		this.setTitle("Client");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create the card layout container.
		CardLayout cardLayout = new CardLayout();
		JPanel container = new JPanel(cardLayout);

		//Create the Controllers
		InitialControl ic = new InitialControl(container); 
		LoginControl lc = new LoginControl(container);
		FriendsListControl fl = new FriendsListControl(container);
		CreateAccountControl ca = new CreateAccountControl(container);
		MenuControl	mc = new MenuControl(container);
		GameControl	gc = new GameControl(container);

		//Probably will want to pass in Client here

		//new Client();

		// Create the four views. (need the controller to register with the Panels
		JPanel view1 = new InitialPanel(ic);
		JPanel view2 = new LoginPanel(lc);
		JPanel view3 = new FriendsListPanel(fl);
		JPanel view4 = new CreateAccountPanel(ca);
		JPanel view5 = new MenuPanel(mc);
		JPanel view6 = new GamePanel(gc);

		// Add the views to the card layout container.
		container.add(view1, "1");
		container.add(view2, "2");
		container.add(view3, "3");
		container.add(view4, "4");
		container.add(view5, "5");
		container.add(view6, "6");


		// Show the initial view in the card layout.
		cardLayout.show(container, "1");

		// Add the card layout container to the JFrame.
		this.add(container, BorderLayout.CENTER);

		// Show the JFrame.
		this.setSize(1000, 900);
		this.setVisible(true);
		this.setResizable(false);
	}

	// Main function that creates the client GUI when the program is started.
	public static void main(String[] args)
	{
		new ClientGUI();
	}
}