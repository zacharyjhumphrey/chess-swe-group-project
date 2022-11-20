package backend;

import java.io.IOException;

public class testServer {

	public static void main(String[] args) {
		
		//create server
		Server server = new Server(8300);
		
		//start server
		try {
			server.listen();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//create client
		Client client = new Client();
		
		//connect client
		try {
			client.openConnection();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//initialize server gameboard
		server.getGame().initializeBoard();
		
		//send piecedata to server
		try {
			
			client.sendToServer(server.getGame().getPieces()[8]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//send movedata to client  //currently not working
		MoveData m = new MoveData(0,2);
//		
//		try {
//			client.sendToServer(m);
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
		server.getGame().getPieces()[8].setPosition(m.getX(), m.getY());
		server.getGame().updateBoard();
		
		
		
		
		try {
			client.closeConnection();
			server.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
