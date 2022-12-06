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

	//constructor
	public PieceData(PieceColor color, int x, int y, String name) {
		this.color = color;
		this.position = new PositionData(x, y);
		this.name = name;
	}
	//getting file path
	public String getFilePath() {
		return "assets/" + this.getColorAsString() + this.name + ".png";
	}
	//setting position data
	public void setPosition(int x, int y) {
		this.position = new PositionData(x, y);
		moved = true;
	}
	//getting piece position
	public PositionData getPosition() {
		return position;
	}
	//geting piece type
	public String getType() {
		return type;
	}
	//getting color of piece as string
	public String getColorAsString() {
		return color.name().toLowerCase();
	}
	//get color
	public PieceColor getColor() {
		return color;
	}
	//move piece
	public void move(PositionData toPos) {
		this.moved = true;
		this.setPosition(toPos.x, toPos.y);
	}
	//reading image
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
	//check status of piece
	public boolean isRemoved() {
		return this.removed;
	}
	//getting if pieces are on same team
	public boolean onSameTeam(PieceData piece) {
		if (piece == null) {
			return false;
		}
		return piece.color.equals(this.color);
	}
	//checking for piece validity
	public boolean isValidPosition(PositionData pos) {
		return pos.x >= 0 && pos.x < 8 && pos.y >= 0 && pos.y < 8;
	}
	//setting removed piece to null
	public void removeFromBoard() {
		removed = true;
		this.position = null;
	}
}