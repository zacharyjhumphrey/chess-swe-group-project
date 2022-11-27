package backend;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import common.*;

public class Game {
	// private PieceData[][] board = new PieceData[8][8];
	private Board board = new Board();
	private HashMap<List<Integer>, PieceData> pieces = new HashMap<List<Integer>, PieceData>();

	private Board getBoard() {
		return board;
	}
	
	public void initializeBoard() {
//		this.board = InitialBoardPosition.getBoardStartingPosition();
	}

	public void updateBoard(Board newBoard) {
		board = newBoard;
		
		System.out.println(this.toString());
	}

	
	public AvailableMoves getAvailableMoves(PositionData pos) {
		return board.getPiece(pos).getAvailableMoves(board);
	}

	public HashMap<List<Integer>, PieceData> getPieces() {
		return pieces;
	}

	public String toString() {
		String r = "";
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board.getPiece(i, j) != null) {
					String t = String.format("%10s", board.getPiece(i, j).getColor() + "," + board.getPiece(i, j).getClass());
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
