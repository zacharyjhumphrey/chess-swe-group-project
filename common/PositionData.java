package common;

import java.io.Serializable;

public class PositionData implements Serializable {
	public int x;
	public int y;

	public PositionData(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
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

	public PositionData getRelativePos(int i, int j) {
		return new PositionData(x + i, y + j);
	}

	public boolean inbounds() {
		return x >= 0 && x < 8 && y >= 0 && y < 8;
	}
}
