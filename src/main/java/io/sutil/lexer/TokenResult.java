package io.sutil.lexer;

import io.sutil.StringUtils;

public class TokenResult {
	
	private final String content;
	private final int lengthRead;
	private final boolean ignore;
	
	public TokenResult(String content, int lengthRead, boolean ignore) {
		
		this.content = content;
		this.lengthRead = lengthRead;
		this.ignore = ignore;
		
	}
	
	public TokenResult(String content, int lengthRead) {
		this( content, lengthRead, false );
	}
	
	public TokenResult(String content, boolean ignore) {
		this( content, content.length(), ignore );
	}
	
	public TokenResult(String content) {
		this( content, content.length() );
	}
	
	public String getContent() {
		return this.content;
	}
	
	public int getLengthRead() {
		return this.lengthRead;
	}
	
	public boolean isIgnore() {
		return this.ignore;
	}
	
	@Override
	public String toString() {
		return "[\"" + StringUtils.escapeLineChars( this.content ) + "\"](" + this.lengthRead + ( this.ignore ? ", ignored" : "" ) + ")"; 
	}
	
}
