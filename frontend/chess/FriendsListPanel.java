package chess;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class FriendsListPanel extends JPanel
{
  // Private data fields for the important GUI components.
  private JTextField usernameField;
  private JPasswordField passwordField;
  private JLabel errorLabel;
  
  // Getter for the text in the username field.
  public String getUsername()
  {
    return usernameField.getText();
  }
  
  // Getter for the text in the password field.
  public String getPassword()
  {
    return new String(passwordField.getPassword());
  }
  
  // Setter for the error text.
  public void setError(String error)
  {
    errorLabel.setText(error);
  }
  
  // Constructor for the login panel.
  public FriendsListPanel(FriendsListControl cc)
  {
    // Create the controller and set it in the chat client.
    //LoginControl controller = new LoginControl(container, client);
    //client.setLoginControl(controller);
        
    // Create a panel for the labels at the top of the GUI.
    JPanel labelPanel = new JPanel(new GridLayout(2, 1));
    errorLabel = new JLabel("", JLabel.CENTER);
    errorLabel.setForeground(Color.RED);
    JLabel friends = new JLabel("Friends", JLabel.CENTER);
    labelPanel.add(errorLabel);
    labelPanel.add(friends);
    
    // Create a panel for the login information form.
    JPanel contactPanel = new JPanel(new GridLayout(1, 2, 5, 5));
    JTextArea area = new JTextArea();
    
    area = new JTextArea(5, 10);
    JScrollPane contactArea = new JScrollPane(area);
    contactPanel.add(contactArea);
    
    // Create a panel for the buttons.
    JPanel buttonPanel = new JPanel();
    JButton deleteContacts = new JButton("Delete Friend");
    deleteContacts.addActionListener(cc);
    JButton addContacts = new JButton("Add Friend");
    addContacts.addActionListener(cc);
    JButton logout = new JButton("Log Out");
    logout.addActionListener(cc);
    buttonPanel.add(deleteContacts);
    buttonPanel.add(addContacts);
    buttonPanel.add(logout);

    // Arrange the three panels in a grid.
    JPanel grid = new JPanel(new GridLayout(4, 1, 0, 10));
    grid.add(labelPanel);
    grid.add(contactPanel);
    grid.add(buttonPanel);
    this.add(grid);
  }
}
