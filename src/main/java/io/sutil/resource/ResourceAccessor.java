package io.sutil.resource;

import java.io.InputStream;
import java.util.List;

public abstract class ResourceAccessor {
	
	protected final String baseFolderPath;
	
	public ResourceAccessor(String baseFolderPath) {
		
		this.baseFolderPath = baseFolderPath == null || baseFolderPath.isEmpty() ? "" : baseFolderPath + ( baseFolderPath.endsWith("/") ? "" : "/" );
		
	}
	
	public ResourceAccessor() {
		this( null );
	}
	
	public String getFullPath(String path) {
		return this.baseFolderPath + path;
	}
	
	public abstract boolean resourceExists(String path);
	public abstract InputStream resourceInputStream(String path);
	public abstract List<String> listResourcePaths(String basePath);
	
}