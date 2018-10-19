package io.sutil.lexer;

public abstract class TokenType {
	
	private final String name;
	
	public TokenType(String name) {
		this.name = name;
	}
	
	public abstract TokenResult decode(String input, int index, char first);
	
	public String name() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
}
