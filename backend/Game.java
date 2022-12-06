package backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import common.AvailableMoves;
import common.Board;
import common.GameInfoData;
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
	//getting white players
	public Player getWhitePlayer() {
		return this.whitePlayer;
	}
	//setting game over status
	public void endGame() {
		this.gameOver = true;
	}
	//getting black players
	public Player getBlackPlayer() {
		return this.blackPlayer;
	}
	//getting current players
	public Player getCurrentPlayer() {
		return this.currentPlayer;
	}
	//getting board
	public Board getBoard() {
		return board;
	}
	//getting available moves
	public AvailableMoves getCurrentAvailableMoves() {
		return this.currentAvailableMoves;
	}
	//checking color of player
	public boolean isWhiteCurrentPlayer() {
		return this.currentPlayer == this.whitePlayer;
	}
	//checking color of player
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
	//moving pieces
	public void movePiece(PositionData fromPos, PositionData toPos) {
		this.currentAvailableMoves = null;
		this.currentPiece = null;
		movePiece(fromPos, toPos);
	}
	//recreating board
	public void updateBoard(Board newBoard) {
		board = newBoard;
	}
	//setting pieces
	public AvailableMoves setCurrentPiece(PositionData pos) {
//		if (this.currentPlayer.getColor() != this.board.getPiece(pos).getColor()) {
//			return null;
//		}
		AvailableMoves moves = this.getAvailableMoves(pos);
		this.currentAvailableMoves = moves;
		this.currentPiece = this.board.getPiece(pos);
		return moves;
	}
	//moving current piece
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
	//getting available moves
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
	//get piece
	public HashMap<List<Integer>, PieceData> getPieces() {
		return pieces;
	}
	//setting game over status
	public boolean isGameOver() {
		return this.gameOver;
	}
	//setting game status
	public GameWonData winGame() {
		GameWonData win = new GameWonData();
		return win;
	}
	//setting game status
	public GameTieData tieGame() {
		GameTieData tie = new GameTieData();
		return tie;
	}
	//setting game status
	public GameLostData loseGame() {
		GameLostData lose = new GameLostData();
		return lose;
	}
	//creating the wait object
	public WaitForGameData waitUser() {
		WaitForGameData wait = new WaitForGameData();
		return wait;
	}
	//sending game players
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
	//verifying pieces
	public boolean playerDoesNotOwnPiece(PositionData pos) {
		PieceData piece = this.board.getPiece(pos);
		
		if (piece == null) {
			return false;
		}
		
		Player playerThatOwnsPiece = (piece.getColor() == PieceColor.w) ? this.whitePlayer : this.blackPlayer; 
		
		return this.currentPlayer != playerThatOwnsPiece;
	}
	//getting game winner
	public Player getWinner() {
		return this.winner;
	}

}
