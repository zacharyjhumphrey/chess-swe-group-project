package backend;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class GameTest {

	public static void main(String[] args) {

		// create server
		Server server = new Server(8300);

		// start server
		try {
			server.listen();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// create client
		Client client1 = new Client();
		Client client2 = new Client();
		// connect client
		try {
			client1.openConnection();
			client2.openConnection();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		Player p1 = new Player("player1","password");
		Player p2 = new Player("player2","password");
		StartData p1s = new StartData(p1);
		StartData p2s = new StartData(p2);
		
		try {
			client1.sendToServer(p1s);
			//client2.sendToServer(p2s);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		try {
			client2.sendToServer(p2s);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		
		// close client and server
		try {
			client1.closeConnection();
			client2.closeConnection();
			server.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
