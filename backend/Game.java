package backend;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import common.InitialBoardPosition;
import common.PieceData;
import common.PositionData;
import backend.GameWonData;

public class Game {
	private PieceData[][] board = new PieceData[8][8];
	private String whiteName;
	private String blackName;
	private boolean gameStarted = false;
	private HashMap<List<Integer>, PieceData> pieces = new HashMap<List<Integer>, PieceData>();

	public Game() {
		initializeBoard();
	}
	public String getWhite() {
		return whiteName;
	}
	public String getBlack() {
		return blackName;
	}
	public void initializeBoard() {
		this.board = InitialBoardPosition.getBoardStartingPosition();
		for(int x=0;x<board.length;x++) {
			for(int y=0;y<board[x].length;y++) {
				if(board[x][y] != null) {
					pieces.put(Arrays.asList(x+1,y+1), board[x][y]);
				} 
			}
		}
		
		
	}

	public void updateBoard() {
//		board = new PieceData[8][8];

//		for(PieceData p : pieces.values()) {
//			board[p.getPosition().y][p.getPosition().x] = p;
//		}
		System.out.println(this.toString());
	}

	public boolean inbounds(int x, int y) {

		if (x < 1 || x > 8) {
			return false;
		}
		if (y < 1 || y > 8) {
			return false;
		}

		return true;
	}

	public boolean checkCollision(PositionData p) {
		for ( List<Integer> key : pieces.keySet()) {
			if (key.get(0) == p.x) {
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
		GameInfoData info = new GameInfoData(whiteName,blackName);
		return info;
	}
	
	public void setWhite(String n) {
		whiteName = n;
	}
	public void setBlack(String n) {
		blackName = n;
	}
	
	public void startGame() {
		gameStarted = true;
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
