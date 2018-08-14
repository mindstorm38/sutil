package io.sutil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Paths;

public class FileUtils {
	
	/**
	 * Get the common AppData directory for current operating system
	 * If no AppData can be found, a {@link IllegalStateException} is thrown
	 * @return AppData directory
	 */
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

	/**
	 * Write a byte array to a file
	 * @param file The file
	 * @param bytes The byte array
	 * @throws IOException Thrown by {@link FileOutputStream} methods
	 */
	public static void writeBytesToFile(File file, byte[] bytes) throws IOException {
		
		FileOutputStream out = new FileOutputStream( file );
		out.write( bytes );
		out.close();
		
	}
	
	/**
	 * Write a string using specific charset in a file
	 * @param file The file
	 * @param string The string
	 * @param charset The string charset 
	 * @throws IOException Thrown by {@link FileOutputStream} methods
	 */
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
	
	/**
	 * Get a file name
	 * @param file A file
	 * @return The file name
	 */
	public static String getFileName(File file) {
		return file.toPath().getFileName().toString();
	}
	
	/**
	 * Get a file path name
	 * @param path A file path
	 * @return The file path name
	 */
	public static String getFileName(String path) {
		return Paths.get( path ).getFileName().toString();
	}
	
	/**
	 * Get a file path extension
	 * @param path A file path
	 * @return The file extension
	 */
	public static String getFileExtension(String path) {
		int l = path.lastIndexOf('.');
		if ( l == -1 || l == path.length() - 1 ) return null;
		return path.substring( l + 1 );
	}
	
	/**
	 * Get a file extension
	 * @param file The file
	 * @return The file extension
	 */
	public static String getFileExtension(File file) {
		return getFileExtension( file.getAbsolutePath() );
	}
	
}
