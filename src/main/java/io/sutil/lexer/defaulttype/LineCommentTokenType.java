package io.sutil.lexer.defaulttype;

import io.sutil.lexer.TokenResult;
import io.sutil.lexer.TokenType;

public class LineCommentTokenType extends TokenType {

	public static final char[] DEFAULT_START_CHARACTERS = { '/', '/' };
	
	public LineCommentTokenType(String name) {
		super( name );
	}
	
	public LineCommentTokenType() {
		this("LINE_COMMENT");
	}
	
	public char[] getStartCharacters() {
		return DEFAULT_START_CHARACTERS;
	}
	
	@Override
	public TokenResult decode(String input, int index, char first) {
		
		char[] startCharacters = this.getStartCharacters();
		
		for ( int i = 0; i < startCharacters.length; i++ )
			if ( input.charAt( index + i ) != startCharacters[ i ] )
				return null;
		
		for ( int i = index + startCharacters.length; ; i++ )
			if ( i >= input.length() || input.charAt( i ) == '\n' )
				return new TokenResult( input.substring( index, i ), i - index + 1, true );
		
	}

}
