package backend;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class testServer {

	public static void main(String[] args){
		
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
			client.sendToServer(server.getGame().getPieces().get(Arrays.asList(0,1)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//have to add rest here for moveData to register in server
		//should be fine in final implementation.
		 Thread ct = new Thread() {
			 public void run()
			 {
				 int t = 0;
				 t++;
			 }
		 };
		 
		 ct.start();
		 try {
			ct.sleep(500);
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		ct.stop();
		
		//send PositionData to client  //currently not working
		PositionData p = new PositionData(0,3);
		try {
			client.sendToServer(p);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// close client and server
		try {
			client.closeConnection();
			server.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
