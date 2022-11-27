package common;

import java.util.ArrayList;
import java.util.Arrays;

public class Knight extends PieceData {
	public Knight(PieceColor color, int x, int y) {
		super(color, x, y, "Knight");
	} 
	
	public AvailableMoves getAvailableMoves(PieceData[][] board) {
		ArrayList<PositionData> toReturn = new ArrayList<PositionData>();
		int x = this.position.x;
		int y = this.position.y;
		
		ArrayList<PositionData> positions = new ArrayList<PositionData>(Arrays.asList(new PositionData[] { 
						new PositionData(x + 1, y - 2), 
						new PositionData(x + 2, y - 1), 
						new PositionData(x + 2, y + 1), 
						new PositionData(x + 1, y + 2), 
						new PositionData(x - 1, y + 2), 
						new PositionData(x - 2, y + 1), 
						new PositionData(x - 2, y - 1), 
						new PositionData(x - 1, y - 2) }));
		
		for (PositionData pos : positions) {
			if (isValidPosition(pos)) {
				toReturn.add(pos);
			}
		}
	
		return new AvailableMoves(toReturn);
	}
}