package common;

import java.io.Serializable;

public class StartData implements Serializable {
	private Player user;

	public Player getUser() {
		return user;
	}

	public void setUser(Player u) {
		user = u;
	}

	public StartData(Player u) {
		setUser(u);
	}
}
