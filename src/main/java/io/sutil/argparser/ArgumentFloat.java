package io.sutil.argparser;

/**
 * 
 * Parser for the java Float type
 * 
 * @author Mindstorm38
 *
 */
public class ArgumentFloat extends Argument<Float> {
	
	private static final ArgumentTypeParser<Float> PARSER = new ArgumentTypeParser<Float>() {
		public Float parse(String str) {
			try {
				return Float.valueOf( str );
			} catch (NumberFormatException e) {
				return null;
			}
		}
	};
	
	public ArgumentFloat(String full, String shrt, Float defaultValue) {
		super(full, shrt, PARSER, defaultValue);
	}
	
	public ArgumentFloat(String full, Float defaultValue) {
		super(full, PARSER, defaultValue);
	}
	
}
