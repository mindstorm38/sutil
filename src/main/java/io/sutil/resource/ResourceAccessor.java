package io.sutil.resource;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import io.sutil.LazyLoadValue;

/**
 *
 * Resource accessors are special type of base directories,
 * implementations represents a base directory, such as a ZIP
 * archive or a simple file system directory.
 *
 * @author Théo Rozier (Mindstorm38)
 *
 */
public abstract class ResourceAccessor implements BaseDirectory {
	
	protected final String baseDirectoryPath;
	protected final LazyLoadValue<Directory> baseDirectory;
	
	public ResourceAccessor(String baseDirectoryPath) {
		
		// this.baseFolderPath = baseFolderPath == null || baseFolderPath.isEmpty() ? "" : baseFolderPath + ( baseFolderPath.endsWith("/") ? "" : "/" );
		this.baseDirectoryPath = baseDirectoryPath == null ? "" : BaseDirectory.formatDirectoryPath( baseDirectoryPath );
		
		this.baseDirectory = new LazyLoadValue<Directory>() {
			
			public Directory create() {
				return new Directory( ResourceAccessor.this, "" );
			}
			
		};
		
	}
	
	public ResourceAccessor() {
		this( null );
	}
	
	public Directory getBaseDirectory() {
		return this.baseDirectory.get();
	}
	
	@Override
	public String getFullPath(String path) {
		return BaseDirectory.join( this.baseDirectoryPath, path );
	}
	
	@Deprecated
	public List<String> listResourcePaths(String basePath) {
		return null;
	}
	
	// STATIC \\
	
	/**
	 * Construct for you the right resource accessor depending on 'src' entry type.<br>
	 * Current resource accessors can support either ZIP (also JAR) Archive or File System Directory.
	 * @param src The source file (also meaning directory).
	 * @param baseDirectoryPath The base directory path to preprend to all relative paths.
	 * @return The right resource accessor you can use for the 'src' entry type.
	 * @throws IllegalStateException If failed to find a resource accessor.
	 */
	public static ResourceAccessor findFileResourceAccessor(File src, String baseDirectoryPath) {
		
		try {
			
			if ( src.isDirectory() ) {
				return new DirectoryResourceAccessor( src, baseDirectoryPath);
			} else {
				return new ZipResourceAccessor(src, baseDirectoryPath);
			}
			
		} catch (IOException e) {
			throw new IllegalStateException("Unable to find a right resource accessor for the file '" + src.getAbsolutePath() + "'.", e );
		}
		
	}
	
	/**
	 * Same as {@link #findFileResourceAccessor(File, String)}, but passing to 'src' parameter
	 * the archive or directory containing the 'clazz'.
	 * @param clazz The class to search for containing archive or directory.
	 * @param baseDirectoryPath The base directory path to preprend to all relative paths.
	 * @return The right resource accessor you can use for the 'src' entry type.
	 * @throws IllegalStateException If failed to find a resource accessor.
	 */
	public static ResourceAccessor findClassResourceAccessor(Class<?> clazz, String baseDirectoryPath) {
		
		try {
			return findFileResourceAccessor(new File(clazz.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()), baseDirectoryPath);
		} catch (URISyntaxException e) {
			throw new IllegalStateException("Unable to convert class container URL to URI (this exception shouldn't happen).");
		}
		
	}
	
}
