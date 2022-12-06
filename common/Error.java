package common;

public class Error extends Throwable {
	private String message;
	//constructor
	public Error(String message) {
		this.message = message;
	}
	//getting message
	@Override
	public String getMessage() {
		return message;
	}
}
