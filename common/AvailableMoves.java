package common;

import java.io.Serializable;
import java.util.ArrayList;

public class AvailableMoves implements Serializable {
	private ArrayList<PositionData> moves;

	public AvailableMoves(ArrayList<PositionData> moves) {
		this.moves = moves;
	}

	public ArrayList<PositionData> getMoves() {
		// TODO Auto-generated method stub
		return this.moves;
	}

	public boolean containsPosition(PositionData pos) {
		return this.moves.contains(pos);
	}

//	@Override
	@Override
	public boolean equals(Object otherObj) {
		if (!(otherObj instanceof AvailableMoves)) {
			return false;
		}
		AvailableMoves other = (AvailableMoves) otherObj;

		return (this.getMoves().size() == other.getMoves().size() && this.getMoves().containsAll(other.getMoves())
				&& other.getMoves().containsAll(this.getMoves()));
	}
}
