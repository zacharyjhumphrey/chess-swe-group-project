package backend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PieceData implements Serializable{
	private String color;
	private String type;
	private PositionData position;
	private int value;
	public boolean moved = false;
	
	
	public PieceData(String c,String t, int x, int y) {
		this.color = c.toLowerCase();
		this.type = t.toLowerCase();
		this.position = new PositionData(x,y);
		switch(type) {
			case "pawn":
				value = 1;
				break;
			case "rook":
				value = 5;
				break;
			case "knight":
				value = 3;
				break;
			case "bishop":
				value = 3;
				break;
			case "queen":
				value = 9;
				break;
			case "king":
				value = 0;
				break;
		}
	}
	public void setPosition(int x, int y) {
		this.position = new PositionData(x, y);
		moved = true;
	}
	
	public PositionData getPosition(){
		return position;
	}
	
	public int getValue() {
		return value;
	}
	
	public String getType() {
		return type;
	}
	
	public String getColor() {
		return color;
	}
	
	public String toString() {
		return color +","+ type +" | position ("+position.toString()+")";
	}
	
	

}
