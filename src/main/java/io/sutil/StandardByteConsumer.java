package io.sutil;

/**
 * 
 * A standart byte consumer, some function that take bytes use a function like {@link #update(byte[], int, int)} to update their internal stream.
 * 
 * @author Mindstorm38
 *
 */
public interface StandardByteConsumer {

	void update(byte[] arr, int off, int len);
	
}
