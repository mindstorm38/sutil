package io.sutil.argparser;

/**
 * 
 * Parser for the java Boolean type
 * 
 * @author Mindstorm38
 *
 */
public class ArgumentBoolean extends Argument<Boolean> {

	private static final ArgumentTypeParser<Boolean> PARSER = new ArgumentTypeParser<Boolean>() {
		public Boolean parse(String str) {
			return Boolean.valueOf( str );
		}
	};
	
	public ArgumentBoolean(String full, String shrt, Boolean defaultValue) {
		super(full, shrt, PARSER, defaultValue);
	}
	
	public ArgumentBoolean(String full, Boolean defaultValue) {
		super(full, PARSER, defaultValue);
	}

}
