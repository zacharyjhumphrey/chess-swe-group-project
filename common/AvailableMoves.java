package common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class AvailableMoves implements Serializable {
	private ArrayList<PositionData> moves;
	public PositionData[] x = new PositionData[50];
	
	//constructor
	public AvailableMoves(ArrayList<PositionData> moves) {
		this.moves = moves;
		for (int i = 0; i < moves.size(); i++) {
			x[i] = moves.get(i);
		}
	}
	//setting available moves
	public AvailableMoves(PositionData[] moves) {
		this.moves = new ArrayList<>(Arrays.asList(moves));
		this.moves.removeAll(Collections.singleton(null));
		x = moves;
	}
	//getting the available moves
	public ArrayList<PositionData> getMoves() {
		// TODO Auto-generated method stub
		return this.moves;
	}
	//creating contains position
	public boolean containsPosition(PositionData pos) {
		return this.moves.contains(pos);
	}
	//getting other available moves
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
