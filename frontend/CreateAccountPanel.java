package chess;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class CreateAccountPanel extends JPanel
{
  // Private data fields for the important GUI components.
  public JTextField usernameField;
  public JPasswordField passwordField;
  public JPasswordField verifypasswordField;
  private JLabel errorLabel;
  private JLabel verifyLabel;
  private JLabel instructionLabel;
  
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
  public CreateAccountPanel(CreateAccountControl crc)
  {
    // Create a panel for the labels at the top of the GUI.
    JPanel labelPanel = new JPanel(new GridLayout(3, 1, 5, 5));
    errorLabel = new JLabel("");
    errorLabel.setForeground(Color.RED);
    instructionLabel = new JLabel("Enter a username and password to create an account.", JLabel.CENTER);
    verifyLabel = new JLabel("Your password must be at least 6 characters.", JLabel.CENTER);

    
    labelPanel.add(errorLabel);
    labelPanel.add(instructionLabel);
    labelPanel.add(verifyLabel);
    
    
    // Create a panel for the login information form.
    JPanel createPanel = new JPanel(new GridLayout(3, 2, 5, 5));
    JLabel usernameLabel = new JLabel("Username:", JLabel.RIGHT);
    usernameField = new JTextField(10);
    usernameField.setText("");
    JLabel passwordLabel = new JLabel("Password:", JLabel.RIGHT);
    passwordField = new JPasswordField(10);
    passwordField.setText("");
    JLabel verifypasswordLabel = new JLabel("Verify Password:", JLabel.RIGHT);
    verifypasswordField = new JPasswordField(10);
    verifypasswordField.setText("");
    createPanel.add(usernameLabel);
    createPanel.add(usernameField);
    createPanel.add(passwordLabel);
    createPanel.add(passwordField);
    createPanel.add(verifypasswordLabel);
    createPanel.add(verifypasswordField);
    
    // Create a panel for the buttons.
    JPanel buttonPanel = new JPanel();
    JButton submitButton = new JButton("Submit");
    submitButton.addActionListener(crc);
    JButton cancelButton = new JButton("Cancel");
    cancelButton.addActionListener(crc);    
    buttonPanel.add(submitButton);
    buttonPanel.add(cancelButton);
    
    // Arrange the three panels in a grid.
    JPanel grid = new JPanel(new GridLayout(3, 1, 0, 10));
    grid.add(labelPanel);
    grid.add(createPanel);
    grid.add(buttonPanel);
    this.add(grid);
  }
}
