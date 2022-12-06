package common;

//setting error message text
public class InvalidPositionError extends Error {
	public InvalidPositionError() {
		super("Please input a valid position");
	}
}
