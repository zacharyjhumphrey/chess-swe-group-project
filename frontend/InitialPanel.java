package frontend;

import java.awt.*;
import javax.swing.*;

public class InitialPanel extends JPanel {

	// Constructor for the initial panel.
	public InitialPanel(InitialControl menuControl) {
		// Create the information label.
		JLabel label = new JLabel("Account Information", JLabel.CENTER);
		// Create the login button.
		JButton loginBtn = new JButton("Login");
		loginBtn.addActionListener(menuControl);
		JPanel loginButtonBuffer = new JPanel();
		loginButtonBuffer.add(loginBtn);

		// Create the create account button.
		JButton createAccountBtn = new JButton("Create Account");
		createAccountBtn.addActionListener(menuControl);
		JPanel createButtonBuffer = new JPanel();
		createButtonBuffer.add(createAccountBtn);

		// Arrange the components in a grid.
		JPanel grid = new JPanel(new GridLayout(3, 1, 5, 5));
		grid.add(label);
		grid.add(loginButtonBuffer);
		grid.add(createButtonBuffer);
		this.add(grid);
	}

}
