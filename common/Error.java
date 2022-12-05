package common;

public class Error extends Throwable {
	private String message;

	public Error(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
