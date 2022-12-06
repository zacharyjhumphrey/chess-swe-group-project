package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import backend.Server;
import common.*;
import frontend.Client;

public class ServerClientTest {
Server server;
Client client1;
Client client2;

		// FIXME I broke this. I couldn't send the Player inside of StartData with the current implementation. could you fix this somehow?
//		// create server
//		Server server = new Server(8300);
@Test
public void testServer() {
	 server = new Server(8300);
	 try {
		server.listen();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	assertNotNull("server",server);
}

@Test
public void testClients() {
	 client1 = new Client();
	 client2 = new Client();
	 try {
		client1.openConnection();
		client2.openConnection();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	assertNotNull("client",client1);
}
}





