package common;

import java.io.Serializable;

public class LogoutData implements Serializable{
	private boolean ingame;
	public LogoutData(boolean b) {
		ingame = b;
	}
	public boolean inplay() {
		return ingame;
	}
}
