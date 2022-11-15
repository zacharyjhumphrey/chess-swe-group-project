package chess;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class FriendsListControl implements ActionListener
{
  // Private data fields
  private JPanel container;
  //private Client Client;
  
  // Constructor for the login controller.
  public FriendsListControl(JPanel container) //JPanel container, Client Client
  {
    this.container = container;
    //this.Client = Client;
  }
  
  // Handle button clicks.
  public void actionPerformed(ActionEvent ae)
  {
    // Get the name of the button clicked.
    String command = ae.getActionCommand();

    // The Cancel button takes the user back to the initial panel.
    if (command == "Delete Contact")
    {
      //
    }

    // The Add Contact button submits the contact information to the server.
    else if (command == "Add Contact")
    {
      //
    }
    // Log out send user back to initial panel
    else if (command == "Log Out")
    {
    	CardLayout cardLayout = (CardLayout)container.getLayout();
        cardLayout.show(container, "1");
    }
  }

  //once the add contact is successful display a pop-up to show verification and send info to server
  public void AddContactSuccess()
  {
    
  }

  //if add contact unsuccessful display pop-up to show error message
  public void displayError(String error)
  {
	  //
  }
}