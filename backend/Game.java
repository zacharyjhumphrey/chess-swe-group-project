package backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import common.AvailableMoves;
import common.Board;
import common.King;
import common.PieceColor;
import common.PieceData;
import common.Player;
import common.PositionData;

public class Game {
	private Board board = new Board();
	private HashMap<List<Integer>, PieceData> pieces = new HashMap<List<Integer>, PieceData>();
	private AvailableMoves currentAvailableMoves = null;
	private PieceData currentPiece = null;
	private Player whitePlayer;
	private Player blackPlayer;
	private Player currentPlayer;
	
	public Game(Player white, Player black) {
		this.whitePlayer = white;
		this.blackPlayer = black;
		this.currentPlayer = this.whitePlayer;
	}
	
	public Player getCurrentPlayer() {
		return this.currentPlayer;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public AvailableMoves getCurrentAvailableMoves() {
		return this.currentAvailableMoves;
	}
		
	public boolean isWhiteCurrentPlayer() {
		return this.currentPlayer == this.whitePlayer;
	}
	
	public boolean isBlackCurrentPlayer() {
		return this.currentPlayer == this.blackPlayer;
	}
	
	/**
	 * TODO test
	 * move piece, set current piece and moves to null
	 * set current player to the other player
	 * @param fromPos
	 * @param toPos
	 */
	public void movePiece(PositionData fromPos, PositionData toPos) {
		this.currentAvailableMoves = null;
		this.currentPiece = null;
		movePiece(fromPos, toPos);
		this.currentPlayer = (this.isWhiteCurrentPlayer()) ? this.blackPlayer : this.whitePlayer;
	}
	
	public void updateBoard(Board newBoard) {
		board = newBoard;
	}
	
	public AvailableMoves setCurrentPiece(PositionData pos) {
		if (this.currentPlayer.getColor() != this.board.getPiece(pos).getColor()) {
			return null;
		}
		AvailableMoves moves = this.getAvailableMoves(pos);
		this.currentAvailableMoves = moves;
		this.currentPiece = this.board.getPiece(pos);
		return moves;
	}
	
	public void moveCurrentPieceToPosition(PositionData toPos) {
		this.board.movePiece(this.currentPiece.getPosition(), toPos);
	}
	
	public AvailableMoves getAvailableMoves(PositionData pos) {
		return board.getPiece(pos).getAvailableMoves(board);
	}

	public HashMap<List<Integer>, PieceData> getPieces() {
		return pieces;
	}

	// TODO I believe this is printing the board sideways, plz fix
	public String toString() {
		String r = "";
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board.getPiece(i, j) != null) {
					String t = String.format("%10s", board.getPiece(i, j).getColorAsString() + "," + board.getPiece(i, j).getClass());
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
