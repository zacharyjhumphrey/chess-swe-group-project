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
}
