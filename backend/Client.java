package backend;

import ocsf.client.AbstractClient;

public class Client extends AbstractClient {

	public Client() {
		super("localhost", 8300);
	}

	@Override
	protected void handleMessageFromServer(Object arg0) {
		
	}

}
