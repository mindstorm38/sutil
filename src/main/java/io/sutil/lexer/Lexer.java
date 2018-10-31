package io.sutil.lexer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.sutil.StreamUtils;

public class Lexer {

	public static final String DEFAULT_CHARSET = "UTF-8";
	
	private final List<TokenType> types;
	
	public Lexer() {
		
		this.types = new ArrayList<>();
		
	}
	
	public List<TokenType> getTypes() {
		return this.types;
	}
	
	public void addType(TokenType type) {
		this.types.add( type );
	}
	
	public void addType(int index, TokenType type) {
		this.types.add( index, type );
	}
	
	public List<Token> lex(File file, String charset) throws IOException {
		try ( InputStream stream = new FileInputStream( file ) ) {
			return this.lex( stream, charset );
		}
	}
	
	public List<Token> lex(File file) throws IOException {
		return this.lex( file, DEFAULT_CHARSET );
	}
	
	public List<Token> lex(InputStream stream, String charset) {
		return this.lex( StreamUtils.getStreamString( stream, charset ) );
	}
	
	public List<Token> lex(InputStream stream) {
		return this.lex( stream, DEFAULT_CHARSET );
	}
	
	public List<Token> lex(String input) {
		
		List<Token> tokens = new ArrayList<>();
		
		TokenResult result;
		char c;
		Token token;
		boolean found;
		int lengthRead;
		int line, row;
		
		List<Integer> lines = new ArrayList<>();
		for ( int i = 0; i < input.length(); i++ ) {
			if ( input.charAt( i ) == '\n' ) {
				lines.add( i );
			}
		}
		lines.add( input.length() );
		
		for ( int i = 0; i < input.length(); ) {
			
			c = input.charAt( i );
			
			found = false;
			
			for ( TokenType type : this.types ) {
				
				try {
					
					result = type.decode( input, i, c );
					
					if ( result != null ) {
						
						lengthRead = result.getLengthRead();
						if ( lengthRead < 1 ) lengthRead = 1;
						
						if ( !result.isIgnore() ) {
							
							line = getLine( lines, i );
							row = getRow( lines, line, i );
							
							token = new Token( type, result.getContent(), new TokenPosition( i, line, row, lengthRead ) ); // TODO : Token Line/Column
							tokens.add( token );
							
						}

						i += lengthRead;
						
						found = true;
						break;
						
					}
				
				} catch (IndexOutOfBoundsException e) {
					e.printStackTrace();
				}
				
			}
			
			if ( !found ) i++;
			
		}
		
		return tokens;
		
	}
	
	public static int getLine(List<Integer> lines, int index) {
		for ( int i = 0; i < lines.size(); i++ )
			if ( lines.get( i ) >= index )
				return i;
		throw new IllegalStateException("Invalid index " + index + ", can't find a corresponding line");
	}
	
	public static int getRow(List<Integer> lines, int line, int index) {
		int lsi = line == 0 ? 0 : lines.get( line - 1 ) + 1;
		return index - lsi;
	}
	
}
