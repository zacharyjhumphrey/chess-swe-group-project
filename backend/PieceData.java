package backend;

import java.io.Serializable;

public class PieceData implements Serializable{
	private int[][] possiblemoves;
	private String pieceType;
	private int x;
	private int y;
	
	public int[][] getMoves() {
		return possiblemoves;
	}

	public String getType() {
		return pieceType;
	}
	
	public int[] getLocation() {
		int[] currentlocation = {x, y};
		return currentlocation;
	}
	
	public void setMoves(int[][] locations) {
		possiblemoves = locations;
	}
	
	public void setType(String type) {
		pieceType = type;
	}
	
	public void setlocation(int xcord, int ycord) {
		x = xcord;
		y = ycord;
	}
	
	public PieceData(String type, int[][] locations, int x, int y) {
		setMoves(locations);
		setType(type);
		setlocation(x,y);
	}
}
