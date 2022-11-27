package common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class PieceData implements Serializable {
	private String color;
	private String type;
	protected PositionData position;
	private int value;
	public boolean moved = false;
	private String name;

	// TODO Test color
	public PieceData(PieceColor color, int x, int y, String name) {
		this.color = color.name().toLowerCase();
		this.position = new PositionData(x, y);
		this.name = name;
	}
	
	public String getFilePath() {
		return "assets/" + this.color + this.name + ".png";
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
	
	public AvailableMoves getAvailableMoves(Board b) {
//		ArrayList<PositionData> moves = new ArrayList<PositionData>();
//		PositionData currentPos = this.position;
//		moves.add(b[currentPos.x][currentPos.y].getPosition());
//		return new AvailableMoves(moves);
		return null;
	}
	
	public boolean onSameTeam(PieceData piece) {
		return piece.color.equals(this.color);
	}
	
	public boolean isValidPosition(PositionData pos) {
		return pos.x >= 0 && pos.x < 8 && pos.y >= 0 && pos.y < 8;
	}
	
	private ArrayList<PositionData> getVerticalMoves(PieceData[][] board) {
		ArrayList<PositionData> toReturn = new ArrayList<PositionData>();
		int x = this.position.x;
		
		// Get positions below piece
		for (int y = this.position.y; y < board.length; y++) {
			PieceData currentPiece = board[x][y];
			
			if (currentPiece == null) {
				toReturn.add(new PositionData(x, y));
				continue;
			}
			
			if (!this.onSameTeam(currentPiece)) {
				toReturn.add(new PositionData(x, y));
			}
			
			break;
		}
		return toReturn;
	}
	
	

}