package exceptions;

public class NotParsableException extends Exception {

	public NotParsableException(String msg) {
		super("Not parsable: "+msg);
	}
}
