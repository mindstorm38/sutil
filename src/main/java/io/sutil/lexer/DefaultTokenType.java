package io.sutil.lexer;

public final class DefaultTokenType {

	public static final TokenType DOT = new TokenType("DOT") {
		public TokenResult decode(String input, int index, char first) {
			return first == '.' ? new TokenResult(".") : null;
		}
	};
	
	public static final TokenType ASSIGN = new TokenType("ASSIGN") {
		public TokenResult decode(String input, int index, char first) {
			return first == '=' ? new TokenResult("=") : null;
		}
	};
	
	public static final TokenType COMMA = new TokenType("COMMA") {
		public TokenResult decode(String input, int index, char first) {
			return first == ',' ? new TokenResult(",") : null;
		}
	};
	
	public static final TokenType SEMI_COLON = new TokenType("SEMI_COLON") {
		public TokenResult decode(String input, int index, char first) {
			return first == ';' ? new TokenResult(";") : null;
		}
	};
	
	public static final TokenType NEW_LINE = new TokenType("NEW_LINE") {
		public TokenResult decode(String input, int index, char first) {
			return first == '\n' ? new TokenResult( "\n" ) : null;
		}
	};
	
	public static final TokenType OPEN_PARAN = new TokenType("OPEN_PARAN") {
		public TokenResult decode(String input, int index, char first) {
			return first == '(' ? new TokenResult("(") : null;
		}
	};
	
	public static final TokenType CLOSE_PARAN = new TokenType("CLOSE_PARAN") {
		public TokenResult decode(String input, int index, char first) {
			return first == ')' ? new TokenResult(")") : null;
		}
	};
	
	public static final TokenType OPEN_BRACKET = new TokenType("OPEN_BRACKET") {
		public TokenResult decode(String input, int index, char first) {
			return first == '[' ? new TokenResult("[") : null;
		}
	};
	
	public static final TokenType CLOSE_BRACKET = new TokenType("CLOSE_BRACKET") {
		public TokenResult decode(String input, int index, char first) {
			return first == ']' ? new TokenResult("]") : null;
		}
	};
	
	public static final TokenType OPEN_BLOCK = new TokenType("OPEN_BLOCK") {
		public TokenResult decode(String input, int index, char first) {
			return first == '{' ? new TokenResult("{") : null;
		}
	};
	
	public static final TokenType CLOSE_BLOCK = new TokenType("CLOSE_BLOCK") {
		public TokenResult decode(String input, int index, char first) {
			return first == '}' ? new TokenResult("}") : null;
		}
	};
	
	public static final TokenType ASTERISK = new TokenType("ASTERISK") {
		public TokenResult decode(String input, int index, char first) {
			return first == '*' ? new TokenResult("*") : null;
		}
	};
			
	private DefaultTokenType() {}
	
}
