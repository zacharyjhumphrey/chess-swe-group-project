package chess;

import java.awt.*;
import javax.swing.*;

public class MenuPanel extends JPanel
{
  // Constructor for the initial panel.
  public MenuPanel(MenuControl ic)
  {
    
    // Create the information label.
    JLabel label = new JLabel("Main Menu", JLabel.CENTER);
    
    // Create the login button.
    JButton loginButton = new JButton("Start Game");	
    loginButton.addActionListener(ic);
    JPanel loginButtonBuffer = new JPanel();
    loginButtonBuffer.add(loginButton);
    
    // Create the create account button.
    JButton createButton = new JButton("Friends List");
    createButton.addActionListener(ic);
    JPanel createButtonBuffer = new JPanel();
    createButtonBuffer.add(createButton);

    // Arrange the components in a grid.
    JPanel grid = new JPanel(new GridLayout(3, 1, 5, 5));
    grid.add(label);
    grid.add(loginButtonBuffer);
    grid.add(createButtonBuffer);
    this.add(grid);
  }
}
