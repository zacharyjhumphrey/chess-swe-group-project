package chess;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class MenuControl implements ActionListener
{
  // Private data field for storing the container.
  private JPanel container;
 
  // Constructor for the initial controller.
  public MenuControl(JPanel container)
  {
    this.container = container;
   
  }
  
  // Handle button clicks.
  public void actionPerformed(ActionEvent ae)
  {
    // Get the name of the button clicked.
    String command = ae.getActionCommand();
    
    // The Login button takes the user to the login panel.
    if (command.equals("Start Game"))
    {
    	CardLayout cardLayout = (CardLayout)container.getLayout();
        cardLayout.show(container, "6");
    }
    
    // The Create button takes the user to the create account panel.
    else if (command.equals("Friends List"))
    {
      //Handle CreateAccount Here
        CardLayout cardLayout = (CardLayout)container.getLayout();
        cardLayout.show(container, "3");
    }
  }
}
