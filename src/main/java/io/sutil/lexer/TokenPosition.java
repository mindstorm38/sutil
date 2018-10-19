package io.sutil.lexer;

public class TokenPosition {

	private final int index;
	private final int line;
	private final int column;
	private final int length;
	
	public TokenPosition(int index, int line, int column, int length) {
		
		this.index = index;
		this.line = line;
		this.column = column;
		this.length = length;
		
	}
	
	public int getIndex() {
		return this.index;
	}
	
	public int getLine() {
		return this.line;
	}
	
	public int getColumn() {
		return this.column;
	}
	
	public int getLength() {
		return this.length;
	}
	
	@Override
	public String toString() {
		return "index " + this.index + ", line " + ( this.line + 1 ) + " & column " + ( this.column + 1 ) + "";
	}
	
}
