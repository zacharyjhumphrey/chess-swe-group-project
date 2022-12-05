package common;

public class InvalidPositionError extends Error {
	public InvalidPositionError() {
		super("Please input a valid position");
	}
}
