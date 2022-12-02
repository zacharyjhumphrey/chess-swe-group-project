package frontend;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuControl implements ActionListener {
	private JPanel container;
	private Client client;

	public MenuControl(JPanel container, Client client) {
		this.container = container;
		this.client = client;
	}
	// handle button click
	public void actionPerformed(ActionEvent ae) {
		// Get the name of the button clicked.
		String command = ae.getActionCommand();
		CardLayout cardLayout = (CardLayout) container.getLayout();
		// If join bottom press create popup
		if (command.equals("Join")) {
			//creating pop up within pane
			final JOptionPane optionPane = new JOptionPane("Waiting to join...", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
			final JDialog popUp = new JDialog();
			
			popUp.setTitle("Joining Game");
			popUp.setLocationRelativeTo(container);
			popUp.setResizable(false);
			//Makes it so you cannot close it
			popUp.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
			popUp.setContentPane(optionPane);
			popUp.pack();
			
			
			//FIXME change to if game found DO THIS
			//create timer to dispose of dialog after 5 seconds
			Timer timer = new Timer(3000, new AbstractAction() {
			    @Override
			    public void actionPerformed(ActionEvent ae) {
			    	//DO THIS 
			    	cardLayout.show(container, "5");
			    	popUp.dispose();
			    }
			});
			//FIXME remove timer
			timer.start();
			popUp.setVisible(true);
		}
		// The Logout button sends the user to initial screen
		if (command.equals("Log Out")) {	
			cardLayout.show(container, "1");
		}	
	}
}