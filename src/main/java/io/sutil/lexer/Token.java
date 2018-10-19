package io.sutil.lexer;

import io.sutil.StringUtils;

public class Token {

	private final TokenType type;
	private final String content;
	private final TokenPosition position;
	
	public Token(TokenType type, String content, TokenPosition position) {
		
		this.type = type;
		this.content = content;
		this.position = position;
		
	}
	
	public TokenType getType() {
		return this.type;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public TokenPosition getPosition() {
		return this.position;
	}
	
	public boolean is(TokenType type) {
		return this.type == type;
	}
	
	public boolean is(String typename) {
		return this.type.name().equals( typename );
	}
	
	public boolean has(String content, boolean ignoreCase) {
		return ignoreCase ? this.content.equalsIgnoreCase( content ) : this.content.equals( content );
	}
	
	public boolean has(String content) {
		return this.content.equals( content );
	}
	
	public boolean has(String[] contentList) {
		for ( String content : contentList )
			if ( this.content.equals( content ) )
				return true;
		return false;
	}
	
	@Override
	public String toString() {
		return this.type.name() + "[\"" + StringUtils.escapeLineChars( this.content ) + "\"]";
	}
	
}
