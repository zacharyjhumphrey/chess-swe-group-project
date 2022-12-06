package common;

import java.util.ArrayList;

public class Bishop extends PieceData {
	//constructor
	public Bishop(PieceColor color, int x, int y) {
		super(color, x, y, "Bishop");
	}
	//Creating the Bishop object
	// TODO Create test
	@Override
	public AvailableMoves getAvailableMoves(Board board) {
		ArrayList<PositionData> toReturn = new ArrayList<>();
		int x = this.position.x;
		int y = this.position.y;

		// upper left pieces
		for (PositionData pos = new PositionData(x - 1, y - 1); pos.inbounds(); pos = pos.getRelativePos(-1, -1)) {
			// TODO This code is really repetitve, we should try to condense to a function somehow
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

		// upper right pieces
		for (PositionData pos = new PositionData(x + 1, y - 1); pos.inbounds(); pos = pos.getRelativePos(1, -1)) {
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

		// bottom right pieces
		for (PositionData pos = new PositionData(x + 1, y + 1); pos.inbounds(); pos = pos.getRelativePos(1, 1)) {
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

		// bottom left pieces
		for (PositionData pos = new PositionData(x - 1, y + 1); pos.inbounds(); pos = pos.getRelativePos(-1, 1)) {
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

		return new AvailableMoves(toReturn);
	}
}