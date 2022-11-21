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
    
		// Create the random button.
		JButton randomBtn = new JButton("Random");
		randomBtn.addActionListener(menuControl);
	    JPanel randomButtonBuffer = new JPanel();
	    randomButtonBuffer.add(randomBtn);
	
	    // Arrange the components in a grid.
	    JPanel grid = new JPanel(new GridLayout(2, 1, 5, 5));
	    grid.add(label);
	    grid.add(randomButtonBuffer);
	    this.add(grid);
	}
}
