package backend;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Game {
	private PieceData[][] board = new PieceData[8][8];
	private HashMap<List<Integer>, PieceData> pieces = new HashMap<List<Integer>,PieceData>();
	
	public void initializeBoard() {
		pieces.put(Arrays.asList(0,0), new PieceData("b","rook",0,0));
		pieces.put(Arrays.asList(1,0), new PieceData("b","knight",1,0));
		pieces.put(Arrays.asList(2,0), new PieceData("b","bishop",2,0));
		pieces.put(Arrays.asList(3,0), new PieceData("b","queen",3,0));
		pieces.put(Arrays.asList(4,0), new PieceData("b","king",4,0));
		pieces.put(Arrays.asList(5,0), new PieceData("b","bishop",5,0));
		pieces.put(Arrays.asList(6,0), new PieceData("b","knight",6,0));
		pieces.put(Arrays.asList(7,0), new PieceData("b","rook",7,0));
		for (int x = 0; x<board.length;x++) {
			pieces.put(Arrays.asList(x,1), new PieceData("b","pawn",x,1));
		}
		for (int x = 0; x<board.length;x++) {
			pieces.put(Arrays.asList(x,6), new PieceData("w","pawn",x,6));
		}
		pieces.put(Arrays.asList(0,7), new PieceData("w","rook",0,7));
		pieces.put(Arrays.asList(1,7), new PieceData("w","knight",1,7));
		pieces.put(Arrays.asList(2,7), new PieceData("w","bishop",2,7));
		pieces.put(Arrays.asList(3,7), new PieceData("w","queen",3,7));
		pieces.put(Arrays.asList(4,7), new PieceData("w","king",4,7));
		pieces.put(Arrays.asList(5,7), new PieceData("w","bishop",5,7));
		pieces.put(Arrays.asList(6,7), new PieceData("w","knight",6,7));
		pieces.put(Arrays.asList(7,7), new PieceData("w","rook",7,7));
		
		List<Integer> p = Arrays.asList(0,1);

		updateBoard();
	}
	
	public void updateBoard() {
		board = new PieceData[8][8];
		
		for(PieceData p : pieces.values()) {
			board[p.getPosition().y][p.getPosition().x] = p;
		}
		System.out.println(this.toString());
	}
	
	
	public boolean inbounds(int x, int y) {
		
		if(x<0 || x>board.length) {
			return false;
		}
		if(y<0 || y>board[x].length) {
			return false;
		} 
		
		return true;
	}
	
	public boolean checkCollision(PositionData p) {
		for (List<Integer> key : pieces.keySet()) {
			if(key.get(0) == p.x) {
				if(key.get(1) == p.y) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public HashMap<List<Integer>, PieceData> getPieces() {
		return pieces;
	}
	
	public String toString() {
		String r = "";
		for(int i = 0; i<board.length; i++) {
			for(int j = 0; j<board[i].length; j++) {
				if(board[i][j]!=null) {
					String t=String.format("%10s", board[i][j].getColor()+","+board[i][j].getType());
				r+="|"+t+"|";
				}
				else {
					String t=String.format("%10s", "NULL");
					r+="|"+t+"|";
				}
			}
			r+="\n";
		}
		
		return r;
	}

}
