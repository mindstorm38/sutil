package io.sutil;

public class StringUtils {

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
	
}
