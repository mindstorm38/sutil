package io.sutil.argparser;

/**
 * 
 * Parser for the java String type
 * 
 * @author Mindstorm38
 *
 */
public class ArgumentString extends Argument<String> {

	private static final ArgumentTypeParser<String> PARSER = new ArgumentTypeParser<String>() {
		public String parse(String str) {
			return str;
		}
	};
	
	public ArgumentString(String full, String shrt, String defaultValue) {
		super(full, shrt, PARSER, defaultValue);
	}
	
	 public ArgumentString(String full, String defaultValue) {
		 super(full, PARSER, defaultValue);
	}
	
}
