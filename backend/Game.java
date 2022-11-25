package backend;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import common.InitialBoardPosition;
import common.PieceData;
import common.PositionData;

public class Game {
	private PieceData[][] board = new PieceData[8][8];
//	private ArrayList<ArrayList<PieceData>> board;
	private HashMap<List<Integer>, PieceData> pieces = new HashMap<List<Integer>, PieceData>();

	public void initializeBoard() {
		this.board = InitialBoardPosition.getBoardStartingPosition();
	}

	public void updateBoard() {
//		board = new PieceData[8][8];

//		for(PieceData p : pieces.values()) {
//			board[p.getPosition().y][p.getPosition().x] = p;
//		}
		System.out.println(this.toString());
	}

	public boolean inbounds(int x, int y) {

		if (x < 0 || x > board.length) {
			return false;
		}
		if (y < 0 || y > board[x].length) {
			return false;
		}

		return true;
	}

	public boolean checkCollision(PositionData p) {
		for (List<Integer> key : pieces.keySet()) {
			if (key.get(0) == p.x) {
				if (key.get(1) == p.y) {
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
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] != null) {
					String t = String.format("%10s", board[i][j].getColor() + "," + board[i][j].getType());
					r += "|" + t + "|";
				} else {
					String t = String.format("%10s", "NULL");
					r += "|" + t + "|";
				}
			}
			r += "\n";
		}

		return r;
	}

}
