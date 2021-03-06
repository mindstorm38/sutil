package io.sutil;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.regex.Pattern;

public class StringUtils {
	
	public static final Charset CHARSET_UTF_8		= Charset.forName("UTF-8");
	public static final Charset CHARSET_US_ASCII	= Charset.forName("US-ASCII");

	public static String byteArrayToHexArrayString(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		for ( int i = 0; i < bytes.length; i++ ) {
			if ( i != 0 ) sb.append(", ");
			sb.append( byteToHexString( bytes[ i ] ) );
		}
		return sb.append(']').toString();
	}
	
	public static String byteToHexString(byte b) {
		String s = Integer.toHexString( 0xFF & b );
		return ( s.length() == 1 ? "0" : "" ) + s.toUpperCase();
	}
	
	public static String byteArrayToHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for ( byte b : bytes ) sb.append( byteToHexString( b ) );
		return sb.toString();
	}
	
	public static String unsignedByteToHexString(short shrt) {
		String s = Integer.toHexString( 0xFF & shrt );
		return getFilledString( '0', 2 - s.length() ) + s.toUpperCase();
	}
	
	public static String unsignedShortToHexString(int i) {
		String s = Integer.toHexString( 0xFFFF & i );
		return getFilledString( '0', Short.BYTES * 2 - s.length() ) + s.toUpperCase();
	}
	
	public static String getFilledString(char c, int count) {
		StringBuilder sb = new StringBuilder();
		for ( int i = 0; i < count; i++ ) sb.append( c );
		return sb.toString();
	}
	
	public static String getStackTraceString(StackTraceElement[] elts) {
		return getStackTraceString( new StackTraceThrowable( elts ) );
	}
	
	public static String getStackTraceString(Throwable t) {
		StringWriter writer = new StringWriter();
		t.printStackTrace( new PrintWriter( writer ) );
		return writer.toString();
	}
	
	public static byte[] getStringBytesZeroFilled(String str, Charset charset, int length) {
		byte[] strbytes = str.getBytes( charset );
		if ( strbytes.length > length ) throw new IllegalArgumentException( "Invalid string bytes length, must be less or equals to length (" + length + ")" );
		byte[] bytes = new byte[ length ];
		System.arraycopy( strbytes, 0, bytes, 0, strbytes.length );
		return bytes;
	}
	
	public static byte[] getStringBytesZeroFilled(String str, int length) {
		return getStringBytesZeroFilled( str, Charset.defaultCharset(), length );
	}
	
	public static String escapeLineChars(String str) {
		return str.replace( "\n", "\\n" );
	}
	
	public static String removeLeadingChars(String str, char c) {
		return str.replaceAll( "^" + Pattern.quote( Character.toString( c ) ) + "+\\B", "" );
	}
	
	public static String requireNonNullAndEmpty(String str, String message) {
		
		if (str == null || str.isEmpty())
			throw new NullPointerException(message);
		
		return str;
		
	}
	
	public static String requireNonNullAndEmpty(String str) {
		return requireNonNullAndEmpty(str, "Empty or null string.");
	}
	
}
