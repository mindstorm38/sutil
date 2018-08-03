package io.sutil;

public class CommonUtils {

	public static boolean isBoolean(Object o) {
		return o instanceof Boolean;
	}
	
	public static int booleanInteger(boolean b) {
		return b ? 1 : 0;
	}
	
	public static int booleanIntegerTotal(boolean...bs) {
		int i = 0;
		for ( boolean b : bs )
			if ( b )
				i++;
		return i;
	}
	
	public static double getTime() {
		return System.nanoTime() / 1000000000.0;
	}

	public static long currentTimeSeconds() {
		return System.currentTimeMillis() / 1000;
	}
	
}
