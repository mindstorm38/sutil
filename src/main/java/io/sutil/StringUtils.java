package io.sutil;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;

public class StringUtils {
	
	public static final Charset CHARSET_UTF_8 = Charset.forName("UTF-8");

	public static String byteArrayToHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		for ( int i = 0; i < bytes.length; i++ ) {
			if ( i != 0 ) sb.append(", ");
			sb.append( byteToHexString( bytes[ i ] ) );
		}
		return sb.append(']').toString();
	}
	
	public static String byteToHexString(byte b) {
		String s = Integer.toHexString( b );
		return ( s.length() == 1 ? "0" : "" ) + s.toUpperCase();
	}
	
	public static String getStackTraceString(StackTraceElement[] elts) {
		Throwable t = new Throwable();
		t.setStackTrace( elts );
		return getStackTraceString( t );
	}
	
	public static String getStackTraceString(Throwable t) {
		StringWriter writer = new StringWriter();
		t.printStackTrace( new PrintWriter( writer ) );
		return writer.toString();
	}
	
}
