package io.sutil.resource;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * Base interface for either accessors or directories.<br>
 * <u>Note that</u> here entry means a resource or a directory, and a resource means a file, which can return a valid InputStream.
 *
 * @author Théo Rozier (Mindstorm38)
 *
 */
public interface BaseDirectory {
	
	/**
	 * Implementations should return the full path internaly used to find the entry,
	 * either in ZIP archive type or file system.
	 * @param path The relative entry path.
	 * @return Internal full path, or Null if incoherent relative path.
	 */
	String getFullPath(String path);
	
	/**
	 * Specify if an entry (resource or directory) exists in this directory or accessor.
	 * @param path The relative entry path.
	 * @return True if exists.
	 */
	boolean entryExists(String path);
	
	/**
	 * Specify if a resource exists in this directory or accessor.
	 * @param path The relative resource path.
	 * @return True if exists.
	 */
	boolean resourceExists(String path);
	
	/**
	 * Specify if a directory exists in this directory or accessor.
	 * @param path The relative directory path.
	 * @return True if exists.
	 */
	boolean directoryExists(String path);
	
	/**
	 * Acquire a resource {@link InputStream} from the resource path.
	 * @param path The relative resource path.
	 * @return The resource input stream, or Null if failed to acquire, or resource doesn't exists.
	 */
	InputStream resourceInputStream(String path);
	
	/**
	 * Get an {@link Entry} object, you can then convert it to Resource or Directory if matches requirements.
	 * @param path The relative entry path.
	 * @return Entry object from path, or Null if entry doesn't exists.
	 */
	Entry getEntry(String path);
	
	/**
	 * Get a {@link Resource} object, from which you can acquire, for exemple
	 * a {@link InputStream}, but also get file extension or name (without path.
	 * @param path The relative resource path.
	 * @return Resource object from path, or Null if resource doesn't exists.
	 */
	default Resource getResource(String path) {
		Entry entry = this.getEntry( path );
		return entry == null ? null : entry.toResource();
	}
	
	/**
	 * Get a {@link Directory} object, you can use to do all {@link BaseDirectory} interface operations.
	 * @param path The relative resource path.
	 * @return Resource object from path, or Null if resource doesn't exists.
	 */
	default Directory getDirectory(String path) {
		Entry entry = this.getEntry( path );
		return entry == null ? null : entry.toDirectory();
	}
	
	/**
	 * List entries in a directory denoted by given path.
	 * @param path The directory path.
	 * @return Entry object list, or Null if path doesn't describe a directory.
	 */
	List<Entry> listEntries(String path);
	
	/**
	 * Same as {@link BaseDirectory#listEntries(String)}, but filtering resources.
	 */
	default List<Resource> listResources(String path) {
		// return this.listEntries(path).stream().filter(Entry::isResource).map(Entry::toResource).collect(Collectors.toList());
		List<Resource> resources = new ArrayList<>();
		List<Entry> entries = this.listEntries( path );
		if ( entries == null ) return null;
		for ( Entry entry : entries )
			if ( entry.isResource() )
				resources.add( entry.toResource() );
		return resources;
	}
	
	/**
	 * Same as {@link BaseDirectory#listEntries(String)}, but filtering directories.
	 */
	default List<Directory> listDirectories(String path) {
		List<Directory> directories = new ArrayList<>();
		List<Entry> entries = this.listEntries( path );
		if ( entries == null ) return null;
		for ( Entry entry : entries )
			if ( entry.isDirectory() )
				directories.add( entry.toDirectory() );
		return directories;
	}
	
	char separator           = '/';
	String separatorString   = "/";
	String separatorEscaped  = "\\/";
	
	static String join(String path1, String path2) {
		if ( path1.isEmpty() ) return path2;
		if ( path2.isEmpty() ) return path1;
		return path1 + ( path1.endsWith( separatorString ) || path2.startsWith( separatorString ) ? "" : separator ) + path2;
	}
	
	static String formatDirectoryPath(String dirPath) {
		if ( dirPath.isEmpty() ) return dirPath;
		if ( dirPath.equals( separatorString ) ) return "";
		dirPath = dirPath.startsWith( separatorString ) ? dirPath.substring( 1 ) : dirPath;
		return dirPath + ( dirPath.endsWith( separatorString ) ? "" : "/" );
	}
	
}
