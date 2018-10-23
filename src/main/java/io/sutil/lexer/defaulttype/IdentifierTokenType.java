package io.sutil.lexer.defaulttype;

import io.sutil.CollectionUtils;
import io.sutil.lexer.TokenResult;
import io.sutil.lexer.TokenType;

public class IdentifierTokenType extends TokenType {

	public static final char[] DEFAULT_VALID_CHARS				= "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_".toCharArray();
	public static final char[] DEFAULT_FORBIDDEN_FIRST_CHARS	= "01234567890".toCharArray();
	
	public IdentifierTokenType(String name) {
		super( name );
	}
	
	public IdentifierTokenType() {
		this("IDENTIFIER");
	}
	
	public char[] getValidChars() {
		return DEFAULT_VALID_CHARS;
	}
	
	public char[] getForbiddenFirstChars() {
		return DEFAULT_FORBIDDEN_FIRST_CHARS;
	}

	@Override
	public TokenResult decode(String input, int index, char first) {
		
		if ( !CollectionUtils.arrayContainsChar( this.getValidChars(), first ) || CollectionUtils.arrayContainsChar( this.getForbiddenFirstChars(), first ) )
			return null;
		
		index++;
		
		for ( int i = index; ; i++ ) {
			
			if ( i >= input.length() || !CollectionUtils.arrayContainsChar( this.getValidChars(), input.charAt( i ) ) )
				return new TokenResult( input.substring( index - 1, i ), i - index + 1 );
			
		}
		
	}

}
