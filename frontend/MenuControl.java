package frontend;

import java.awt.*;
import javax.swing.*;

import common.LogoutData;
import common.StartData;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MenuControl implements ActionListener {
	private JPanel container;
	private Client client;
	private JDialog popUp;
	private CardLayout cardLayout;
	
	//constructor
	public MenuControl(JPanel container, Client client) {
		this.container = container;
		this.client = client;
	}
	// handle button click
	public void actionPerformed(ActionEvent ae) {
		// Get the name of the button clicked.
		String command = ae.getActionCommand();
		cardLayout = (CardLayout) container.getLayout();
		
		// TODO move this to constructor? 
		// If join bottom press create popup
		if (command.equals("Join")) {
			//creating pop up within pane
			final JOptionPane optionPane = new JOptionPane("Waiting to join...", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
			popUp = new JDialog();
			
			popUp.setTitle("Joining Game");
			popUp.setLocationRelativeTo(container);
			popUp.setResizable(false);
			//Makes it so you cannot close it
			popUp.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
			popUp.setContentPane(optionPane);
			popUp.pack();
			
			popUp.setVisible(true);
			try {
				this.client.sendToServer(new StartData());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// The Logout button sends the user to initial screen
		if (command.equals("Log Out")) {	
			try {
				client.sendToServer(new LogoutData(false));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cardLayout.show(container, "1");
		}	
	}
	//enters client into game and clears the pop up
	public void enterGame() {
    	cardLayout.show(container, "5");
    	popUp.dispose();
	}
	
}





