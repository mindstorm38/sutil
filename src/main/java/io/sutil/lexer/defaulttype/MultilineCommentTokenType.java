package io.sutil.lexer.defaulttype;

import io.sutil.lexer.TokenResult;
import io.sutil.lexer.TokenType;

public class MultilineCommentTokenType extends TokenType {

	public static final char[] DEFAULT_START_CHARACTERS = { '/', '*' };
	public static final char[] DEFAULT_END_CHARACTERS = { '*', '/' };
	
	public MultilineCommentTokenType(String name) {
		super( name );
	}
	
	public MultilineCommentTokenType() {
		this("MULTILINE_COMMENT");
	}
	
	public char[] getStartCharacters() {
		return DEFAULT_START_CHARACTERS;
	}
	
	public char[] getEndCharacters() {
		return DEFAULT_END_CHARACTERS;
	}

	@Override
	public TokenResult decode(String input, int index, char first) {
		
		char[] startCharacters = this.getStartCharacters();
		
		for ( int i = 0; i < startCharacters.length; i++ )
			if ( input.charAt( index + i ) != startCharacters[ i ] )
				return null;
		
		char[] endCharacters = this.getEndCharacters();
		int length = input.length() - endCharacters.length;
		
		boolean end;
		
		for ( int i = index + startCharacters.length; ; i++ ) {
			
			if ( i >= length )
				return new TokenResult( input.substring( index, i ), i - index, true );
			
			end = true;
			
			for ( int j = i; j < endCharacters.length; j++ ) {
				if ( input.charAt( j ) != endCharacters[ j ] ) {
					end = true;
					break;
				}
			}
			
			if ( end )
				return new TokenResult( input.substring( index, i ), i - index + endCharacters.length, true );
			
		}
		
	}

}
