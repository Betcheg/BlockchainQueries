package exceptions;

public class NotParsableJSONException extends Exception {

	public NotParsableJSONException(String msg) {
		super("Not parsable JSON: "+msg);
	}

}
