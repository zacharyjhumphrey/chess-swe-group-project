package common;

import java.util.ArrayList;
public class Pawn extends PieceData {
	//constructor
	public Pawn(PieceColor color, int x, int y) {
		super(color, x, y, "Pawn");
	}

	// TODO should create and use flip board function
	@Override
	public AvailableMoves getAvailableMoves(Board board) {		
		ArrayList<PositionData> toReturn = new ArrayList<>();
		int x = this.position.x;

		int moveLength = (this.moved) ? 1 : 2;
		int direction = (this.getColor() == PieceColor.w) ? -1 : 1; 
		
		// Get positions above piece
		for (int y = this.position.y + direction; y >= 0 && y < 8 && y >= this.position.y - moveLength && y <= this.position.y + moveLength; y += direction) {
			PositionData pos = new PositionData(x, y);
			PieceData currentPiece = board.getPiece(pos);

			if (currentPiece != null) {
				break;
			}
			
			toReturn.add(pos);
		}

		PositionData upperLeftPos = new PositionData(x - 1, this.position.y + direction);
		if (upperLeftPos.inbounds()) {
			PieceData upperLeftPiece = board.getPiece(upperLeftPos);
			if (upperLeftPiece != null && isValidPosition(upperLeftPos) && !onSameTeam(upperLeftPiece)) {
				toReturn.add(upperLeftPos);
			}
		}

		PositionData upperRightPos = new PositionData(x + 1, this.position.y + direction);
		if (upperRightPos.inbounds()) {
			PieceData upperRightPiece = board.getPiece(upperRightPos);
			if (upperRightPiece != null && isValidPosition(upperRightPos) && !onSameTeam(upperRightPiece)) {
				toReturn.add(upperRightPos);
			}
		}

		return new AvailableMoves(toReturn);
	}
}