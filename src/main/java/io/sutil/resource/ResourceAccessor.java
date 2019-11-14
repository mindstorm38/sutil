package io.sutil.resource;

import java.util.List;

import io.sutil.LazyLoadValue;

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
	
}
