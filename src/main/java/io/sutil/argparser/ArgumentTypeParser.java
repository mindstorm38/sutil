package io.sutil.argparser;

/**
 * 
 * Used to parse command line argument to specific type
 * 
 * @author Mindstorm38
 *
 * @param <T> Type of the parsed value
 */
public interface ArgumentTypeParser<T> {
	
	T parse(String str);
	
}
