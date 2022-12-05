package backend;

import java.io.Serializable;

public class GameInfoData implements Serializable {
	private String white;
	private String black;
	public String info = "white username = "+white+"black username = "+black+".";
	
	public GameInfoData(String w, String b) {
		white = w;
		black = b;
	}
	
	public String getWhite() {
		return this.white;
	}
	
	public String getBlack() {
		return this.black;
	}
}

