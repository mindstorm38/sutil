package io.sutil.resource;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public interface BaseDirectory {
	
	String getFullPath(String path);
	
	boolean entryExists(String path);
	boolean resourceExists(String path);
	boolean directoryExists(String path);
	
	InputStream resourceInputStream(String path);
	
	Entry getEntry(String path);
	
	default Resource getResource(String path) {
		Entry entry = this.getEntry( path );
		return entry == null ? null : entry.toResource();
	}
	
	default Directory getDirectory(String path) {
		Entry entry = this.getEntry( path );
		return entry == null ? null : entry.toDirectory();
	}
	
	List<Entry> listEntries(String path);
	
	default List<Resource> listResources(String path) {
		List<Resource> resources = new ArrayList<>();
		List<Entry> entries = this.listEntries( path );
		if ( entries == null ) return null;
		for ( Entry entry : entries )
			if ( entry.isResource() )
				resources.add( entry.toResource() );
		return resources;
	}
	
	default List<Directory> listDirectories(String path) {
		List<Directory> directories = new ArrayList<>();
		List<Entry> entries = this.listEntries( path );
		if ( entries == null ) return null;
		for ( Entry entry : entries )
			if ( entry.isDirectory() )
				directories.add( entry.toDirectory() );
		return directories;
	}
	
	public static char separator			= '/';
	public static String separatorString	= "/";
	public static String separatorEscaped	= "\\/";
	
	public static String join(String path1, String path2) {
		if ( path1.isEmpty() ) return path2;
		if ( path2.isEmpty() ) return path1;
		return path1 + ( path1.endsWith( separatorString ) || path2.startsWith( separatorString ) ? "" : separator ) + path2;
	}
	
	public static String formatDirectoryPath(String dirPath) {
		if ( dirPath.isEmpty() ) return dirPath;
		if ( dirPath.equals( separatorString ) ) return "";
		dirPath = dirPath.startsWith( separatorString ) ? dirPath.substring( 1 ) : dirPath;
		return dirPath + ( dirPath.endsWith( separatorString ) ? "" : "/" );
	}
	
}
