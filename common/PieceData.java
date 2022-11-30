package common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class PieceData implements Serializable {
	private String color;
	private String type;
	private PositionData position;
	private int value;
	public boolean moved = false;

	// TODO Test color
	public PieceData(Color color, int x, int y, String type) {
		this.color = color.name().toLowerCase();
		this.position = new PositionData(x, y);
		this.type = type;
	}
	
	public String getFilePath() {
		return "assets/" + this.color + this.type + ".png";
	}

	public void setPosition(int x, int y) {
		this.position = new PositionData(x, y);
		moved = true;
	}

	public PositionData getPosition() {
		return position;
	}

	public String getType() {
		return type;
	}

	public String getColor() {
		return color;
	}

	public String toString() {
		return color + "," + type + " | position (" + position.toString() + ")";
	}

}

enum Color {
	b,
	w
}
