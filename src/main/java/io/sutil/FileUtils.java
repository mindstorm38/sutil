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
	
	/**
	 * Return the array of stored bytes in the file, not work on very large file (see {@link CollectionUtils#ARRAY_MAX_LENGTH}
	 * @param file File where to read bytes
	 * @return Array of stored bytes in the file
	 * @throws IOException If the file is too large or if delegate function {@link StreamUtils#getStreamByteArray(InputStream)} throws an exception
	 */
	public static byte[] getFileByteArray(File file) throws IOException {
		if ( file.length() > CollectionUtils.ARRAY_MAX_LENGTH ) throw new IOException();
		return StreamUtils.getStreamByteArray( new FileInputStream( file ) );
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
