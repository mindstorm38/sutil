package io.sutil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ChecksumUtils {

	public static String getFileSHA256(File file) throws NoSuchAlgorithmException, IOException {
		
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		
		try ( InputStream stream = new FileInputStream( file ) ) {
			
			final byte[] buffer = new byte[ 1024 ];
			int read;
			
			while ( ( read = stream.read( buffer ) ) != -1 ) {
				digest.update( buffer, 0, read );
			}
			
		}
		
		return StringUtils.byteArrayToHexString( digest.digest() );
		
	}
	
}
