package io.sutil;

import java.io.Closeable;
import java.io.IOException;

public class StreamUtils {

	public static void safeclose(Closeable closeable) {
		if ( closeable == null ) return;
		try { closeable.close(); } catch (IOException e) {}
	}
	
}
