package common;

import java.util.ArrayList;
import java.util.Arrays;

public class King extends PieceData {
	public King(PieceColor color, int x, int y) {
		super(color, x, y, "King");
	}

	// TODO Test
	@Override
	public AvailableMoves getAvailableMoves(Board board) {
		ArrayList<PositionData> toReturn = new ArrayList<>();
		int x = this.position.x;
		int y = this.position.y;

		ArrayList<PositionData> positions = new ArrayList<>(
				Arrays.asList(new PositionData[] { new PositionData(x - 1, y - 1), new PositionData(x, y - 1),
						new PositionData(x + 1, y - 1), new PositionData(x - 1, y), new PositionData(x + 1, y),
						new PositionData(x - 1, y + 1), new PositionData(x, y + 1), new PositionData(x + 1, y + 1),
				}));

		// (-1, -1), (0 , -1), (1 , -1)
		// (-1, 0), (0 , 0), (1 , 0)
		// (-1, 1), (0 , 1), (1 , 1)

		for (PositionData pos : positions) {
			PieceData otherPiece = board.getPiece(pos);
			if (pos.inbounds() && this.onSameTeam(otherPiece)) {
				toReturn.add(pos);
			}
		}

		return new AvailableMoves(toReturn);
	}
}
