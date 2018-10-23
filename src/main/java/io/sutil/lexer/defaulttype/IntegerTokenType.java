package io.sutil.lexer.defaulttype;

import io.sutil.CollectionUtils;
import io.sutil.StringUtils;
import io.sutil.lexer.TokenResult;
import io.sutil.lexer.TokenType;

public class IntegerTokenType extends TokenType {
	
	public static final char[] VALID_BINARY_DIGITS				= { '0', '1' };
	public static final char[] VALID_DECIMAL_DIGITS				= "0123456789".toCharArray();
	public static final char[] VALID_HEXADECIMAL_DIGITS			= "0123456789ABCDEFGabcdefg".toCharArray();
	
	public static int parseTokenContent(String content) {
		String[] splited = content.split(" ");
		if ( splited.length == 1 ) {
			return Integer.parseInt( splited[0] );
		} else {
			int radix = Integer.parseInt( splited[0] );
			return Integer.parseInt( splited[1], radix );
		}
	}
	
	public IntegerTokenType(String name) {
		super( name );
	}
	
	public IntegerTokenType() {
		this( "INTEGER" );
	}
	
	public int getDefaultBase() {
		return 10;
	}
	
	public int getBase(char c) {
		switch ( c ) {
			case 'b': return 2;
			case 'x': return 16;
			case 'd': return 10;
		}
		return 0;
	}

	public char[] getBaseDigits(int base) {
		switch ( base ) {
			case 2: return VALID_BINARY_DIGITS;
			case 10: return VALID_DECIMAL_DIGITS;
			case 16: return VALID_HEXADECIMAL_DIGITS;
		}
		return null;
	}
	
	@Override
	public TokenResult decode(String input, int index, char first) {
		
		boolean signchar = ( first == '+' || first == '-' );
		boolean negate = signchar ? first == '-' : false;
		
		if ( signchar )
			first = input.charAt( ++index );
		
		if ( first != '0' && !CollectionUtils.arrayContainsChar( this.getBaseDigits( this.getDefaultBase() ), first ) )
			return null;
		
		int lengthRead = signchar ? 1 : 0;
		int base = 0;
		
		if ( first == '0' ) {
			
			base = this.getBase( input.charAt( index + 1 ) );
			
			if ( base != 0 ) {
				
				index += 2;
				lengthRead += 2;
				
			}
			
		}
		
		if ( base == 0 ) base = this.getDefaultBase();
		
		char[] digits = this.getBaseDigits( base );
		if ( digits == null ) return null;
		
		char c;
		for ( int i = index; ; i++ ) {
			
			c = input.charAt( i );
			
			if ( !CollectionUtils.arrayContainsChar( digits, c ) )
				return new TokenResult( base + " " + ( negate ? "-" : "+" ) + StringUtils.removeLeadingChars( input.substring( index, i ), '0' ), lengthRead );
			
			lengthRead++;
			
		}
		
	}

}
