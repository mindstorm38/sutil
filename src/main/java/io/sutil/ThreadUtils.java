package io.sutil;

public class ThreadUtils {

	public static void safesleep(long millis) {
		try { Thread.sleep( millis ); } catch (InterruptedException e) {}
	}
	
	public static StackTraceElement[] getThreadStackTrace(Thread thread) {
		return thread.getStackTrace();
	}
	
	public static StackTraceElement[] getCurrentStackTrace() {
		return Thread.currentThread().getStackTrace();
	}
	
}
