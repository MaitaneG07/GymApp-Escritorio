package modelo.exceptions;

public class FireBaseException extends Exception {

	private static final long serialVersionUID = -4213885495010419261L;
	
	public FireBaseException(String text) {
		super (text);
	}
}