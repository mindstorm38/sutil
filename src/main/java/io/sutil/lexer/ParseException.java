package io.sutil.lexer;

public class ParseException extends RuntimeException {

	private static final long serialVersionUID = 3451124213386256941L;

	public ParseException(String message, Token token, Throwable cause) {
		super( "Error '" + message + "'" + ( token == null ? "" : ( " ( For " + token.toString() + ")" ) ), cause );
	}
	
	public ParseException(String message, Token token) {
		this( message, token, null );
	}
	
	public ParseException(String message, Throwable cause) {
		this( message, null, cause );
	}
	
	public ParseException(String message) {
		this( message, null, null );
	}
	
	public ParseException(Throwable cause) {
		this( null, null, cause );
	}
	
}
