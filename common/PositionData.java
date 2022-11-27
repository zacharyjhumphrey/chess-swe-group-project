package common;

import java.io.Serializable;

public class PositionData implements Serializable {
	public int x;
	public int y;

	public PositionData(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public String toString() {
		return x + ", " + y;
	}
	
	@Override
	public boolean equals(Object otherObj) {
		if (!(otherObj instanceof PositionData)) {
			return false;
		}
		PositionData other = (PositionData) otherObj;
		return other.x == x && other.y == y;
	}
}
