package io.sutil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Paths;

public class FileUtils {
	
	public static File getAppDataDirectory() {
		
		String path = null;
		
		String os = System.getProperty("os.name").toUpperCase();
		
		if ( os.contains("WIN") ) {
			path = System.getenv("APPDATA");
		} else if ( os.contains("MAC") ) {
			path = System.getProperty("user.home") + File.separator + "Library" + File.separator + "Application Support";
		} else if ( os.contains("NUX") ) {
			path = System.getProperty("user.home");
		} else {
			path = System.getProperty("user.dir");
		}
		
		if ( path == null ) throw new IllegalStateException("Failed to find AppData path");
		
		return new File( path );
		
	}

	public static void writeBytesToFile(File file, byte[] bytes) throws IOException {
		
		FileOutputStream out = new FileOutputStream( file );
		out.write( bytes );
		out.close();
		
	}
	
	public static void writeStringToFile(File file, String string, Charset charset) throws IOException {
		writeBytesToFile( file, string.getBytes( charset ) );
	}

	public static byte[] getBytesFromFile(File file) throws IOException {
		
		InputStream is = new FileInputStream(file);
	
		long length = file.length();
	
		if (length > Integer.MAX_VALUE) {
			is.close();
			throw new IOException("File too large > " + Integer.MAX_VALUE);
		}
	
		byte[] bytes = new byte[(int) length];
	
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}
	
		if (offset < bytes.length) {
			is.close();
			throw new IOException("Could not completely read file " + file.getName());
		}
	
		is.close();
		return bytes;
		
	}
	
	public static String getFileName(File file) {
		return file.toPath().getFileName().toString();
	}
	
	public static String getFileName(String path) {
		return Paths.get( path ).getFileName().toString();
	}
	
	public static String getFileExtension(String path) {
		int l = path.lastIndexOf('.');
		if ( l == -1 || l == path.length() - 1 ) return null;
		return path.substring( l + 1 );
	}
	
	public static String getFileExtension(File file) {
		return getFileExtension( file.getAbsolutePath() );
	}
	
}
