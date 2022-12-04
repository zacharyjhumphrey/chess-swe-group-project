package common;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

public abstract class PieceData implements Serializable {
	private PieceColor color;
	private String type;
	protected PositionData position;
	public boolean moved = false;
	private String name;
	private boolean removed = false;

	// TODO remove name
	public PieceData(PieceColor color, int x, int y, String name) {
		this.color = color;
		this.position = new PositionData(x, y);
		this.name = name;
	}
	
	public String getFilePath() {
		return "assets/" + this.getColorAsString() + this.name + ".png";
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

	public void move(PositionData toPos) {
		this.moved = true;
		this.setPosition(toPos.x, toPos.y);
	}
	
	// TODO Change to static
	public BufferedImage getImage() throws IOException {
		return ImageIO.read(new File(getFilePath()));
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