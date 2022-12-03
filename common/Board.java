package common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Board implements Serializable {
	private PieceData[][] checkers;
	private King whiteKing;
	private King blackKing;
	private ArrayList<PieceData> whitePieces;
	private ArrayList<PieceData> blackPieces;

	public Board() {
		this.checkers = initializeBoard();
	}

	// TODO Doesn't set kings or pieces
	public Board(PieceData[][] checkers) {
		this.checkers = checkers;
		setWhitePieces(Board.getWhitePiecesFrom2DArray(checkers));
		setBlackPieces(Board.getBlackPiecesFrom2DArray(checkers));
		setWhiteKing(Board.getWhiteKingFrom2DArray(checkers));
		setBlackKing(Board.getBlackKingFrom2DArray(checkers));
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

	public void setPiece(PieceData piece, PositionData pos) {
		this.setPiece(piece, pos.x, pos.y);
	}

	// TODO test
	private void setPiece(PieceData piece, int x, int y) {
		PositionData oldPosition = piece.getPosition();
		this.checkers[oldPosition.x][oldPosition.y] = null;
		this.checkers[x][y] = piece;
		piece.setPosition(x, y);
	}

	// TODO test
	public void movePiece(PositionData fromPos, PositionData toPos) {
		PieceData movingPiece = this.getPiece(fromPos);
		PieceData pieceToBeRemoved = this.getPiece(toPos);
		if (pieceToBeRemoved != null) {
			pieceToBeRemoved.removeFromBoard();
			ArrayList<PieceData> removedPieceList = (pieceToBeRemoved.getColor() == PieceColor.w) ? whitePieces : blackPieces; 
			removedPieceList.remove(pieceToBeRemoved);
		}
		this.setPiece(movingPiece, toPos);
	}

	// TODO Possbily remove this, use positiondata.inbounds instead
	public boolean isValidPosition(PositionData pos) {
		return pos.x >= 0 && pos.x < 8 && pos.y >= 0 && pos.y < 8;
	}

	// TODO
	public Board getFlippedBoard() {
		PieceData[][] original = this.getCheckers();
		
		for (int i = 0; i < 8; i++) {
//			List<Object> list = Arrays.asList(array);
//		    Collections.reverse(list);
		}
		return new Board(checkers);
	}

	private static ArrayList<PieceData> getWhitePiecesFrom2DArray(PieceData[][] checkers) {
		return (ArrayList<PieceData>) Arrays.stream(checkers).flatMap(Arrays::stream)
				.filter(p -> p.getColor() == PieceColor.w).collect(Collectors.toList());
	}

	private static ArrayList<PieceData> getBlackPiecesFrom2DArray(PieceData[][] checkers) {
		return (ArrayList<PieceData>) Arrays.stream(checkers).flatMap(Arrays::stream)
				.filter(p -> p.getColor() == PieceColor.b).collect(Collectors.toList());
	}

	private static King getWhiteKingFrom2DArray(PieceData[][] checkers) {
		return (King) Arrays.stream(checkers).flatMap(Arrays::stream)
				.filter(p -> p.getColor() == PieceColor.w && p.getClass() == King.class).collect(Collectors.toList()).get(0);
	}

	private static King getBlackKingFrom2DArray(PieceData[][] checkers) {
		return (King) Arrays.stream(checkers).flatMap(Arrays::stream)
				.filter(p -> p.getColor() == PieceColor.b && p.getClass() == King.class).collect(Collectors.toList()).get(0);
	}

	public PieceData[][] initializeBoard() {
		PieceData[][] toReturn = new PieceData[8][8];

		ArrayList<PieceData> whitePieces = new ArrayList<PieceData>();
		ArrayList<PieceData> blackPieces = new ArrayList<PieceData>();

		King blackKing = new King(PieceColor.b, 4, 0);
		blackPieces.add(new Rook(PieceColor.b, 0, 0));
		blackPieces.add(new Knight(PieceColor.b, 1, 0));
		blackPieces.add(new Bishop(PieceColor.b, 2, 0));
		blackPieces.add(new Queen(PieceColor.b, 3, 0));
		blackPieces.add(blackKing);
		blackPieces.add(new Bishop(PieceColor.b, 5, 0));
		blackPieces.add(new Knight(PieceColor.b, 6, 0));
		blackPieces.add(new Rook(PieceColor.b, 7, 0));
		for (int x = 0; x < 8; x++) {
			blackPieces.add(new Pawn(PieceColor.b, x, 1));
		}

		King whiteKing = new King(PieceColor.w, 4, 7);
		whitePieces.add(new Rook(PieceColor.w, 0, 7));
		whitePieces.add(new Knight(PieceColor.w, 1, 7));
		whitePieces.add(new Bishop(PieceColor.w, 2, 7));
		whitePieces.add(new Queen(PieceColor.w, 3, 7));
		whitePieces.add(whiteKing);
		whitePieces.add(new Bishop(PieceColor.w, 5, 7));
		whitePieces.add(new Knight(PieceColor.w, 6, 7));
		whitePieces.add(new Rook(PieceColor.w, 7, 7));
		for (int x = 0; x < 8; x++) {
			whitePieces.add(new Pawn(PieceColor.w, x, 6));
		}

		for (PieceData piece : blackPieces) {
			PositionData pos = piece.getPosition();
			toReturn[pos.x][pos.y] = piece;
		}

		for (PieceData piece : whitePieces) {
			PositionData pos = piece.getPosition();
			toReturn[pos.x][pos.y] = piece;
		}

		setBlackKing(blackKing);
		setWhiteKing(whiteKing);
		setWhitePieces(whitePieces);
		setBlackPieces(blackPieces);

		return toReturn;
	}

	public ArrayList<PieceData> getWhitePieces() {
		return whitePieces;
	}

	public void setWhitePieces(ArrayList<PieceData> whitePieces) {
		this.whitePieces = whitePieces;
	}

	public ArrayList<PieceData> getBlackPieces() {
		return blackPieces;
	}

	public void setBlackPieces(ArrayList<PieceData> blackPieces) {
		this.blackPieces = blackPieces;
	}

	public King getWhiteKing() {
		return whiteKing;
	}

	public void setWhiteKing(King whiteKing) {
		this.whiteKing = whiteKing;
	}

	public King getBlackKing() {
		return blackKing;
	}

	public void setBlackKing(King blackKing) {
		this.blackKing = blackKing;
	}
}
