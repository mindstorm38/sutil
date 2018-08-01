package io.sutil;

public class CommonUtils {

	public static boolean isBoolean(Object o) {
		return o == Boolean.FALSE || o == Boolean.TRUE;
	}
	
	public static int integerBoolean(boolean b) {
		return b ? 1 : 0;
	}
	
	public static int integerBooleans(boolean...bs) {
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
