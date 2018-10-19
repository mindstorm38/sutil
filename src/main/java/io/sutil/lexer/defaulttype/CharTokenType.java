package io.sutil.lexer.defaulttype;

import io.sutil.lexer.TokenResult;
import io.sutil.lexer.TokenType;

public class CharTokenType extends TokenType {

	private final char c;
	
	public CharTokenType(String name, char c) {
		
		super( name );
		this.c = c;
		
	}

	@Override
	public TokenResult decode(String input, int index, char first) {
		return first == this.c ? new TokenResult( Character.toString( first ) ) : null;
	}

}
