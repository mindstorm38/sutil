package io.sutil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
	
	/**
	 * Simplify a path by resolving relative names
	 * @param path Path to simplify
	 * @return Simplified string path
	 */
	public static String simplifyPath(Path path) {
		
		List<String> newNames = new ArrayList<>();
		Path n;
		
		try {
			
			for ( int i = 0; i < path.getNameCount(); i++ ) {
				n = path.getName( i );
				if ( n.toString().equals("..") )
					newNames.remove( newNames.size() - 1 );
				else
					newNames.add( n.toString() );
			}
			
		} catch (IndexOutOfBoundsException e) {
			throw new IllegalArgumentException("Invalid path, can't be simplified");
		}
		
		return path.getRoot().toString() + String.join( File.separator, newNames );
		
	}
	
	/**
	 * Simplify a string path
	 * @param path Path to simplify
	 * @return Simplified string path
	 */
	public static String simplifyPath(String path) {
		return simplifyPath( Paths.get( path ) );
	}
	
	/**
	 * Simplify a path and return a {@link Path} object
	 * @param path The {@link Path} object to simplify
	 * @return The simplified {@link Path}
	 */
	public static Path simplifyPathPath(Path path) {
		return Paths.get( simplifyPath( path ) );
	}
	
	/**
	 * Simplify a {@link File} path
	 * @param file The {@link File} to simplify
	 * @return Simplified {@link File}
	 */
	public static File simplifyFilePath(File file) {
		return new File( simplifyPath( file.toPath() ) );
	}
	
	/**
	 * Get a path hierarchy level
	 * @param path The path
	 * @return The path hierarchy level
	 */
	public static int getPathHierarchyLevel(Path path) {
		int lvl = 0;
		for ( int i = 0; i < path.getNameCount(); i++ )
			if ( path.getName( i ).toString().equals("..") )
				lvl--;
			else lvl++;
		return lvl;
	}
	
	/**
	 * Get a path hierarchy level
	 * @param path The path
	 * @return The path hierarchy level
	 */
	public static int getPathHierarchyLevel(String path) {
		return getPathHierarchyLevel( Paths.get( path ) );
	}
	
	/**
	 * Get a file hierarchy level
	 * @param file The file
	 * @return The file hierarchy level
	 */
	public static int getFileHierarchyLevel(File file) {
		return getPathHierarchyLevel( file.toPath() );
	}
	
}
