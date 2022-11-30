package common;

import java.util.ArrayList;

public class Queen extends PieceData {
	public Queen(PieceColor color, int x, int y) {
		super(color, x, y, "Queen");
	}

	// TODO (qol) Queen is a good example of code that is really repetitive. we
	// should
	// create .getHorizontalMoves and .getVerticalMoves in PieceData, combine these
	// in queen getMoves
	// TODO test
	@Override
	public AvailableMoves getAvailableMoves(Board board) {
		ArrayList<PositionData> toReturn = new ArrayList<>();
		int x = this.position.x;
		int y = this.position.y;

		// upper positions
		for (PositionData pos = new PositionData(x, y - 1); pos.inbounds(); pos = pos.getRelativePos(0, -1)) {
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

		// bottom positions
		for (PositionData pos = new PositionData(x, y + 1); pos.inbounds(); pos = pos.getRelativePos(0, 1)) {
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

		// left positions
		for (PositionData pos = new PositionData(x - 1, y); pos.inbounds(); pos = pos.getRelativePos(-1, 0)) {
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

		// right positions
		for (PositionData pos = new PositionData(x + 1, y); pos.inbounds(); pos = pos.getRelativePos(1, 0)) {
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

		// upper left pieces
		for (PositionData pos = new PositionData(x - 1, y - 1); pos.inbounds(); pos = pos.getRelativePos(-1, -1)) {
			// TODO This code is really repetitve, we should try to condense to a function
			// somehow
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
