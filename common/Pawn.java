package common;

import java.util.ArrayList;

public class Pawn extends PieceData {
	public Pawn(PieceColor color, int x, int y) {
		super(color, x, y, "Pawn");
	}

	@Override
	public AvailableMoves getAvailableMoves(Board board) {
		// TODO Determine if the piece is pinned
		if (isPinned(board)) {
			return null;
		}
		
		ArrayList<PositionData> toReturn = new ArrayList<>();
		int x = this.position.x;

		// Get positions above piece
		for (int y = this.position.y - 1; y > 0 && y >= this.position.y - 2; y--) {
			PositionData pos = new PositionData(x, y);
			PieceData currentPiece = board.getPiece(pos);

			if (currentPiece == null) {
				toReturn.add(pos);
				continue;
			}

			if (!this.onSameTeam(currentPiece)) {
				toReturn.add(pos);
			}

			break;
		}

		PositionData upperLeftPos = new PositionData(x - 1, this.position.y - 1);
		PieceData upperLeftPiece = board.getPiece(upperLeftPos);
		if (upperLeftPiece != null && isValidPosition(upperLeftPos) && !onSameTeam(upperLeftPiece)) {
			toReturn.add(upperLeftPos);
		}

		PositionData upperRightPos = new PositionData(x + 1, this.position.y - 1);
		PieceData upperRightPiece = board.getPiece(upperRightPos);
		if (upperRightPiece != null && isValidPosition(upperRightPos) && !onSameTeam(upperRightPiece)) {
			toReturn.add(upperRightPos);
		}

		return new AvailableMoves(toReturn);
	}
}