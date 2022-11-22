package chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameControl {
	private JPanel container;
	
	// Constructor for the game controller.
	  public GameControl(JPanel container)
	  {
	    this.container = container;
	   
	  }
	  public void actionPerformed(ActionEvent ae)
	  {
	    // Get the name of the button clicked.
	    String command = ae.getActionCommand();
	    
	  }
}