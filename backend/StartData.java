package backend;

import java.io.Serializable;

import common.Player;

public class StartData implements Serializable {
	private Player user;

	public Player getUser() {
		return user;
	}

	public void setUser(Player u) {
		user = u;
	}

	public StartData(Player p1) {
		setUser(p1);
	}
}
