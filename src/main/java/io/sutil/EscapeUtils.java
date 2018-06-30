package io.sutil;

public class EscapeUtils {

	public static String escapeJava(String str) {
		
		TextBuilder builder = new TextBuilder();
		
		char[] chars = str.toCharArray();
		
		for ( char c : chars ) {
			
			if ( c == '\\' || c == '\"' ) builder.append('\\');
			builder.append( c );
			
		}
		
		return builder.toString();
		
	}
	
}
