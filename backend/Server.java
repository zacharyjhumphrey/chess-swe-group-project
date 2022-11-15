package backend;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;

public class Server extends AbstractServer
{
  private JTextArea log;
  private JLabel status;
  private Queue<Object> userswaitingforgame = new LinkedList<>();
  private PieceData pieceData;
  
  public Server()
  {
    super(12345);
  }
  
  public Server(int port)
  {
    super(port);
  }
  
  public void setLog(JTextArea log)
  {
    this.log = log;
  }
  
  public JTextArea getLog()
  {
    return log;
  }
  
  public void setStatus(JLabel status)
  {
    this.status = status;
  }
  
  public JLabel getStatus()
  {
    return status;
  }
  
  
  
  @Override
  protected void handleMessageFromClient(Object arg0, ConnectionToClient arg1)
  {
   
	  if (arg0 instanceof CreateAccountData)
	    {
		  
		  CreateAccountData createAccountData = (CreateAccountData)arg0;
		  User newUser = new User(createAccountData.getUsername(),createAccountData.getPassword());
	      DatabaseFile.add(newUser);
	    		   
	       
	    }
	  
	  if (arg0 instanceof LoginData)
	    {
	       LoginData loginData = (LoginData)arg0;
	       try {
			if(DatabaseFile.verify(loginData.getUsername(), loginData.getPassword())) {
			   User currentUser = new User(loginData.getUsername(),loginData.getPassword());
				currentUser.setId(Integer.valueOf(DatabaseFile.getid(currentUser.getUsername())));
			   }
		}catch (NumberFormatException e) {
			e.printStackTrace();
		}
	    } 
	  
	  if (arg0 instanceof StartData)
	    {
		  
		  StartData startData = (StartData)arg0;
		  userswaitingforgame.add(startData.getUser());
		  
		  //when user clicks to start game/ start searching for game
		  //takes in startData.user
		  //adds user to que to find game
		  
		  //plan on adding function to check if size of que is greater than or equal to 2 then pop of 2 users and pair
		  //them to play in game together
	       
	    }
	  
	  if (arg0 instanceof PieceData)
	    {
		  
		  pieceData = (PieceData)arg0;
		  //when peice is selected by user
		  //takes in peiceData location and moves list
		  //adds each move from list to current location resulting in possible location
		  //pushes possible location to list
		  //sends list of all possible move locations
		  //updates game to show possible moves highlighted.
	       
	    }
	  
	  if (arg0 instanceof MoveData)
	    {
		  
		  MoveData moveData = (MoveData)arg0;
		  //when user selects move to make
		//verifies location is valid
		  pieceData.setlocation(moveData.getX(), moveData.getY());
		  //updates game to show piece has been moved
	       
	    }
	  
	 
    

  }
  
  protected void listeningException(Throwable exception) 
  {
    System.out.println("Listening Exception:" + exception);
    exception.printStackTrace();
    System.out.println(exception.getMessage());
    
    if (this.isListening())
    {
      System.out.println("server not listening");
    }
    
  }
  
  protected void serverStarted() 
  {
    System.out.println("Server Started");
  }
  
  protected void serverStopped() 
  {
    System.out.println("Server Stopped");
  }
  
  protected void serverClosed() 
  {
	System.out.println("server closed");
  }

  
  protected void clientConnected(ConnectionToClient client) 
  {
    System.out.println("Client Connected");
  }
}
