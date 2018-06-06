package io.sutil.argparser;

/**
 * 
 * Define a command line argument
 * 
 * @author Mindstorm38
 *
 * @param <T> Type of the argument value
 */
public class Argument<T> {
	
	protected final String full;
	protected final String shrt;
	protected final ArgumentTypeParser<T> parser;
	
	protected T value;
	
	public Argument(String full, String shrt, ArgumentTypeParser<T> parser, T defaultValue) {
		
		this.full = full;
		this.shrt = shrt;
		this.parser = parser;
		this.value = defaultValue;
		
	}
	
	public Argument(String full, ArgumentTypeParser<T> parser, T defaultValue) {
		this(full, full, parser, defaultValue);
	}
	
	public T get() {
		return this.value;
	}
	
	@Override
	public int hashCode() {
		return this.full.hashCode();
	}
	
}
