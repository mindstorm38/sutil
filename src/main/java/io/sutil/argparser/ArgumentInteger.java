package io.sutil.argparser;

/**
 * 
 * Parser for the java Integer type
 * 
 * @author Mindstorm38
 *
 */
public class ArgumentInteger extends Argument<Integer> {
	
	private static final ArgumentTypeParser<Integer> PARSER = new ArgumentTypeParser<Integer>() {
		public Integer parse(String str) {
			try {
				return Integer.valueOf( str );
			} catch (NumberFormatException e) {
				return null;
			}
		}
	};
	
	public ArgumentInteger(String full, String shrt, Integer defaultValue) {
		super( full, shrt, PARSER, defaultValue );
	}
	
	public ArgumentInteger(String full, Integer defaultValue) {
		super(full, PARSER, defaultValue);
	}
	
}
