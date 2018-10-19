package io.sutil.lexer;

public class ParseException extends RuntimeException {

	private static final long serialVersionUID = 3451124213386256941L;

	public ParseException(String message, Token token) {
		super( "Error '" + message + "'" + ( token == null ? "" : ( " (At " + token.getPosition().toString() + ")" ) ) );
	}
	
}
