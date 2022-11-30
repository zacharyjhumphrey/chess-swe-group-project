package common;

import java.io.Serializable;

public abstract class PieceData implements Serializable {
	private PieceColor color;
	private String type;
	protected PositionData position;
	private int value;
	public boolean moved = false;
	private String name;
	private boolean removed = false;

	// TODO remove name
	public PieceData(PieceColor color, int x, int y, String name) {
		this.color = color;
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

	public String getColorAsString() {
		return color.name().toLowerCase();
	}
	
	public PieceColor getColor() {
		return color;
	}

	@Override
	public String toString() {
		return color + "," + type + " | position (" + position.toString() + ")";
	}

	public AvailableMoves getAvailableMoves(Board b) {
		return null;
	}

	public boolean onSameTeam(PieceData piece) {
		if (piece == null) {
			return false;
		}
		return piece.color.equals(this.color);
	}

	public boolean isValidPosition(PositionData pos) {
		return pos.x >= 0 && pos.x < 8 && pos.y >= 0 && pos.y < 8;
	}

	public void removeFromBoard() {
		removed = true;
		this.position = null;
	}
}