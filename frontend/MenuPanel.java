package frontend;

import java.awt.*;
import javax.swing.*;

public class MenuPanel extends JPanel
{
	// Constructor for the menu panel.
	public MenuPanel(MenuControl menuControl)
	{    
		// Create the information label.
		JLabel label = new JLabel("Main Menu", JLabel.CENTER);
		JLabel game = new JLabel("Join Random Game", JLabel.CENTER);
		
		// Create the random button.
		JButton randomBtn = new JButton("Join");
		randomBtn.addActionListener(menuControl);
		JPanel randomButtonBuffer = new JPanel();
		randomButtonBuffer.add(randomBtn);

		// Create the logout button.
		JButton logout = new JButton("Log Out");
		logout.addActionListener(menuControl);
		
		// Arrange the panels in a for layout.
		JPanel grid = new JPanel(new BorderLayout());
		JPanel center = new JPanel(new FlowLayout());
		JPanel menupanel = new JPanel(new GridLayout(4,2));
		
		//setting sizes
		menupanel.setPreferredSize(new Dimension(150, 400));
		center.setPreferredSize(new Dimension(150, 400));
		
		//adding components
		menupanel.add(label);
		center.add(game);
		center.add(randomButtonBuffer);
		grid.add(menupanel,BorderLayout.NORTH);
		grid.add(center, BorderLayout.CENTER);
		grid.add(logout, BorderLayout.SOUTH);
		
		this.add(grid);
	}
}
