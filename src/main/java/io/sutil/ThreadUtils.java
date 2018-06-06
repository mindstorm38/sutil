package io.sutil;

public class ThreadUtils {

	public static void safesleep(long millis) {
		try { Thread.sleep( millis ); } catch (InterruptedException e) {}
	}
	
}
