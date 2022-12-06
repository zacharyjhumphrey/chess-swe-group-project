package backend;

import java.io.Serializable;

public class GameInfoData implements Serializable {
	private String white;
	private String black;
	public String info = "white username = "+white+"black username = "+black+".";
	//constructor
	public GameInfoData(String w, String b) {
		white = w;
		black = b;
	}
	//get white
	public String getWhite() {
		return this.white;
	}
	//get black
	public String getBlack() {
		return this.black;
	}
}

