package io.sutil.lexer.defaulttype;

import io.sutil.TextBuilder;
import io.sutil.lexer.TokenResult;
import io.sutil.lexer.TokenType;

public class StringLiteralTokenType extends TokenType {

	public StringLiteralTokenType(String name) {
		super( name );
	}
	
	public StringLiteralTokenType() {
		this("STRING_LITERAL");
	}
	
	public char escape(char c) {
		if ( c == 'n' ) return '\n';
		if ( c == 'r' ) return '\r';
		if ( c == 't' ) return '\t';
		return 0;
	}
	
	@Override
	public TokenResult decode(String input, int index, char first) {
		
		if ( first != '"' ) return null;
		index++;
		
		char c, r;
		boolean escape = false;
		
		TextBuilder builder = new TextBuilder();
		
		for ( int i = index; i < input.length() ; i++ ) {
			
			c = input.charAt( i );
			
			if ( c == '\n' || ( !escape && c == '"' ) ) {
				return new TokenResult( builder.toString()/*input.substring( index, i )*/, i - index + 2 );
			} else if ( !escape && c == '\\' ) {
				escape = true;
				continue;
			} else if ( escape && c != '\\' ) {
				r = this.escape( c );
				if ( r == 0 ) builder.append( c );
				else builder.append( r );
				escape = false;
			} else {
				builder.append( c );
			}
			
		}
		
		return null;
		
	}

}
