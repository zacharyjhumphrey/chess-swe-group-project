package common;

public class Board {
	private PieceData[][] checkers;
	
	public Board() {
		this(InitialBoardPosition.getBoardStartingPosition());
	}
	
	public Board(PieceData[][] checkers) {
		this.checkers = checkers;
	}
	
	public PieceData getPiece(PositionData pos) {
		return getPiece(pos.x, pos.y);
	}
	
	public PieceData getPiece(int x, int y) {
		return checkers[x][y];
	}
	
	public PieceData[][] getCheckers() {
		return checkers;
	}

	public void setCheckers(PieceData[][] checkers) {
		this.checkers = checkers;
	}

	public boolean isValidPosition(PositionData pos) {
		return pos.x >= 0 && pos.x < 8 && pos.y >= 0 && pos.y < 8;
	}
		
	public Board getFlippedBoard() {
		return new Board(checkers);
	}
}
