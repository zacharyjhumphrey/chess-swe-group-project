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
import java.util.List;

public class Server extends AbstractServer
{
  private Queue<Object> userswaitingforgame = new LinkedList<>();
  private PieceData currentPiece;
  private Game gameBoard = new Game();
  
  public Server()
  {
    super(8300);
  }
  
  public Server(int port)
  {
    super(port);
  }
  
  public Game getGame() {
	  return gameBoard;
  }
  
  public PieceData getCurrentPiece() {
	  return currentPiece;
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
		  
		  PieceData pieceData = (PieceData)arg0;
		  currentPiece = pieceData;
		  List<PositionData> moves = new ArrayList<PositionData>();
		  int x = pieceData.getPosition().x;
		  int y = pieceData.getPosition().y;
		  int offset = 0;
		  PositionData p;
		  System.out.println("x: "+x+", y: "+y);
		  switch(pieceData.getColor()) {
		  case("w"):
			  switch(pieceData.getType()) {
			  case "pawn":
				  p = new PositionData(x,y-1);
				  if(!gameBoard.checkCollision(p)) {
					  moves.add(p);
				  }
				  if(!pieceData.moved) {
					  p = new PositionData(x,y-2);
					  if(!gameBoard.checkCollision(p)) {
					  moves.add(p);
					  }
				  }
				break;
			  case "rook":
				  offset = 1;
				  p = new PositionData(x,y-offset);
				  while(!gameBoard.checkCollision(p)) {
					  moves.add(p);
					  offset++;
					  p = new PositionData(x,y-offset);
				  }
				  if(gameBoard.getPieces().get(Arrays.asList(p.x,p.y)).getColor() !="w") {
					  moves.add(p);
				  }
				break;
			  case "knight":
				break;
			  case "bishop":
				break;
			  case "queen":
				break;
			  case "king":
				break;
			  } 
		  	break;
		  case("b"):
			  switch(pieceData.getType()) {
			  case "pawn":
				  p = new PositionData(x,y+1);
				  moves.add(p);
				  if(!pieceData.moved) {
					  p = new PositionData(x,y+2);
					  moves.add(p);
				  }
				break;
			  case "rook":
				  offset = 1;
				  p = new PositionData(x,y+offset);
				  while(!gameBoard.checkCollision(p)) {
					  moves.add(p);
					  offset++;
					  p = new PositionData(x,y+offset);
				  }
				  if(gameBoard.getPieces().get(Arrays.asList(p.x,p.y)).getColor() !="b") {
					  moves.add(p);
				  }
				  
				break;
			  case "knight":
				break;
			  case "bishop":
				break;
			  case "queen":
				break;
			  case "king":
				break;
			  } 
		  	break;
		  }
	      	//send array list to user
		  System.out.println(moves.toString());
	    }
	  
	  if (arg0 instanceof PositionData)
	    {
		  //when user selects move to make
		  PositionData position = (PositionData)arg0;
		//verifies location is valid
		  List<Integer> oldp = Arrays.asList(currentPiece.getPosition().x,currentPiece.getPosition().y);
		  List<Integer> newp = Arrays.asList(position.x,position.y);
		  currentPiece.setPosition(position.x, position.y);
		  gameBoard.getPieces().remove(oldp);
		  gameBoard.getPieces().put(newp, currentPiece);
		  //updates game to show piece has been moved
		  gameBoard.updateBoard();
	       
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
