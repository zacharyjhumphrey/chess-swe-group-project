package frontend;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import database.Database;

public class MenuPanel extends JPanel
{
	// Constructor for the menu panel.
	public MenuPanel(MenuControl menuControl)
	{    
		// Create the labels.
		JLabel label = new JLabel("Main Menu", JLabel.CENTER);
		JLabel join = new JLabel("Join Random Game", JLabel.CENTER);
		JLabel playerRecordsLabel = new JLabel("Player Records", JLabel.CENTER);

		// Creating all panels for menu panel.
		JPanel grid = new JPanel(new BorderLayout());
		JPanel center = new JPanel(new GridLayout(2,1));
		JPanel centerTop = new JPanel(new BorderLayout());
		JPanel centerBottom = new JPanel(new BorderLayout());
		JPanel centerBottomPadding = new JPanel(new BorderLayout());
		JPanel joinPanel = new JPanel(new BorderLayout());
		JPanel bottom = new JPanel(new FlowLayout());
		JPanel menupanel = new JPanel(new GridLayout(4,2));


		//ScrollPane Panel
		JTextArea playerRecords = new JTextArea();
		JScrollPane playerRecordsSP = new JScrollPane(playerRecords);
		playerRecords.setLineWrap(false);
		
		//setting text for text area
		this.setPlayerRecords(playerRecords);

		// Create the random button.
		JButton randomBtn = new JButton("Join");
		randomBtn.addActionListener(menuControl);
		JPanel randomButtonBuffer = new JPanel();
		randomButtonBuffer.add(randomBtn);
		
		// Create the logout button.
		JButton logout = new JButton("Log Out");
		logout.addActionListener(menuControl);

		//setting sizes
		menupanel.setPreferredSize(new Dimension(450, 60));
		center.setPreferredSize(new Dimension(200, 730));
		centerBottomPadding.setPreferredSize(new Dimension(300, 100));

		//adding to center top 
		centerTop.add(playerRecordsLabel,BorderLayout.NORTH);
		centerTop.add(playerRecordsSP,BorderLayout.CENTER);
		//adding to joinPanel south bottom
		joinPanel.add(join,BorderLayout.NORTH);
		joinPanel.add(randomButtonBuffer,BorderLayout.CENTER);
		//adding padding to center north bottom
		centerBottom.add(centerBottomPadding,BorderLayout.NORTH);
		centerBottom.add(joinPanel,BorderLayout.CENTER);
		//adding top and bottom to center
		center.add(centerTop);
		center.add(centerBottom);
		//adding components
		menupanel.add(label);
		//adding logout to bottom
		bottom.add(logout);
		
		//adding panels
		grid.add(menupanel,BorderLayout.NORTH);
		grid.add(center, BorderLayout.CENTER);
		grid.add(bottom, BorderLayout.SOUTH);

		this.add(grid);
	}
	//Setting text for text area
	public void setPlayerRecords(JTextArea  textArea)
	{
		Database database = new Database();
		
		ArrayList<String> stats =database.getPlayerStats();
		stats.size();
		this.repaint();
		//get record size here
		int recordSize = stats.size();
		//get player wins
		
		if (recordSize==0)
			textArea.append("No Records Found");
		else
		{
			
			for (int i=0; i<recordSize;)
			{
				String username = stats.get(i).toString();
				String win = stats.get(i+1).toString();
				String ties = stats.get(i+2).toString();
				String losses = stats.get(+3).toString();
				stats.get(i).toString();
				textArea.append(username+"	Wins:"+ win +"	Losses:" +ties+"	Ties:"+ losses+ "\n");
				i+=4;
			}
		}
	}
}
