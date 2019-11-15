package io.sutil.resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.List;

public class ResourceAccessorWrapper extends ResourceAccessor {

	protected final ResourceAccessor accessor;
	
	public ResourceAccessorWrapper(File src, String baseFolderPath) {
		this.accessor = ResourceAccessor.findFileResourceAccessor(src, baseFolderPath);
	}
	
	public ResourceAccessorWrapper(Class<?> clazz, String baseFolderPath) throws URISyntaxException {
		this(new File(clazz.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()), baseFolderPath);
	}
	
	public ResourceAccessorWrapper(Class<?> owner) throws URISyntaxException {
		this( owner, null );
	}
	
	public ResourceAccessor getWrappedAccessor() {
		return this.accessor;
	}
	
	@Override
	public String getFullPath(String path) {
		return this.accessor.getFullPath( path );
	}
	
	@Override
	public boolean entryExists(String path) {
		return this.accessor.entryExists( path );
	}
	
	@Override
	public boolean resourceExists(String path) {
		return this.accessor.resourceExists( path );
	}
	
	@Override
	public boolean directoryExists(String path) {
		return this.accessor.directoryExists( path );
	}

	@Override
	public InputStream resourceInputStream(String path) {
		return this.accessor.resourceInputStream( path );
	}

	@Override
	public Entry getEntry(String path) {
		return this.accessor.getEntry( path );
	}
	
	@Override
	public Resource getResource(String path) {
		return this.accessor.getResource( path );
	}
	
	@Override
	public Directory getDirectory(String path) {
		return this.accessor.getDirectory( path );
	}

	@Override
	public List<Entry> listEntries(String path) {
		return this.accessor.listEntries( path );
	}
	
	@Override
	public List<Resource> listResources(String path) {
		return this.accessor.listResources( path );
	}
	
	@Override
	public List<Directory> listDirectories(String path) {
		return this.accessor.listDirectories( path );
	}
	
	@Override
	public String toString() {
		return "Resource Accessor Wrapper.\n" +
				"\tWrapped Accessor : " + this.accessor.toString();
	}

}
