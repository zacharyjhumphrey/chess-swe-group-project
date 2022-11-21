package frontend;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuControl implements ActionListener
{
	//handle button click
	public void actionPerformed(ActionEvent ae) 
	{
		// Get the name of the button clicked.
		String command = ae.getActionCommand();
		
		//The random button sends the user into the match queue 
		if (command.equals("Random"))
		{
			//add functionality here
		}
	}

}
