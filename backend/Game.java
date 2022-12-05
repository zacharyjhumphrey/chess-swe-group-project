package backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import common.AvailableMoves;
import common.Board;
import common.GameLostData;
import common.GameTieData;
import common.GameWonData;
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
	private Player winner;
	private boolean gameOver = false;

	public Game(Player white, Player black) {
		this.whitePlayer = white;
		this.blackPlayer = black;
		this.currentPlayer = this.whitePlayer;
	}

	public Player getWhitePlayer() {
		return this.whitePlayer;
	}
	public void endGame() {
		this.gameOver = true;
	}
	public Player getBlackPlayer() {
		return this.blackPlayer;
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
	 * TODO test move piece, set current piece and moves to null set current player
	 * to the other player
	 * 
	 * @param fromPos
	 * @param toPos
	 */
	public void movePiece(PositionData fromPos, PositionData toPos) {
		this.currentAvailableMoves = null;
		this.currentPiece = null;
		movePiece(fromPos, toPos);
	}

	public void updateBoard(Board newBoard) {
		board = newBoard;
	}

	public AvailableMoves setCurrentPiece(PositionData pos) {
//		if (this.currentPlayer.getColor() != this.board.getPiece(pos).getColor()) {
//			return null;
//		}
		AvailableMoves moves = this.getAvailableMoves(pos);
		this.currentAvailableMoves = moves;
		this.currentPiece = this.board.getPiece(pos);
		return moves;
	}

	public void moveCurrentPieceToPosition(PositionData toPos) {
		this.board.movePiece(this.currentPiece.getPosition(), toPos);
		this.currentAvailableMoves = null;
		this.currentPlayer = (this.isWhiteCurrentPlayer()) ? this.blackPlayer : this.whitePlayer;
		this.gameOver = (this.board.getBlackKing().isRemoved() || this.board.getWhiteKing().isRemoved());
		System.out.println("game status is : " + this.isGameOver());
		if (this.isGameOver()) {
			this.winner = (this.board.getBlackKing().isRemoved()) ? this.whitePlayer : this.blackPlayer;
		}
	}

	public AvailableMoves getAvailableMoves(PositionData pos) {
		return board.getPiece(pos).getAvailableMoves(board);
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
	
	public boolean isGameOver() {
		return this.gameOver;
	}

	public GameWonData winGame() {
		GameWonData win = new GameWonData();
		return win;
	}

	public GameTieData tieGame() {
		GameTieData tie = new GameTieData();
		return tie;
	}

	public GameLostData loseGame() {
		GameLostData lose = new GameLostData();
		return lose;
	}

	public WaitForGameData waitUser() {
		WaitForGameData wait = new WaitForGameData();
		return wait;
	}

	public GameInfoData sendGameInfo() {
		GameInfoData info = new GameInfoData(this.whitePlayer.getUsername(), this.blackPlayer.getUsername());
		return info;
	}

	// TODO I believe this is printing the board sideways, plz fix
	public String toString() {
		String r = "";
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board.getPiece(i, j) != null) {
					String t = String.format("%10s",
							board.getPiece(i, j).getColorAsString() + "," + board.getPiece(i, j).getClass());
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

	public boolean playerDoesNotOwnPiece(PositionData pos) {
		PieceData piece = this.board.getPiece(pos);
		
		if (piece == null) {
			return false;
		}
		
		Player playerThatOwnsPiece = (piece.getColor() == PieceColor.w) ? this.whitePlayer : this.blackPlayer; 
		
		return this.currentPlayer != playerThatOwnsPiece;
	}

	public Player getWinner() {
		return this.winner;
	}

}
