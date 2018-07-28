package io.sutil;

public class StackTraceThrowable extends Throwable {
	
	private static final long serialVersionUID = -5288590237340170616L;

	public StackTraceThrowable(StackTraceElement[] stacktrace) {
		
		this.setStackTrace( stacktrace );
		
	}
	
}
