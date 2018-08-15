package io.sutil.resource;

import java.io.InputStream;
import java.util.List;

public class Directory extends Entry implements BaseDirectory {
	
	public Directory(ResourceAccessor accessor, String path) {
		
		super( accessor, path );
		
	}
	
	public boolean isBaseDirectory() {
		return this.accessor.getBaseDirectory() == this;
	}
	
	@Override
	public String getFullPath(String path) {
		return BaseDirectory.join( this.path, path );
	}
	
	public boolean entryExists(String path) {
		return this.accessor.entryExists( this.getFullPath( path ) );
	}
	
	public boolean resourceExists(String path) {
		return this.accessor.resourceExists( this.getFullPath( path ) );
	}
	
	public boolean directoryExists(String path) {
		return this.accessor.directoryExists( this.getFullPath( path ) );
	}
	
	@Override
	public InputStream resourceInputStream(String path) {
		return this.accessor.resourceInputStream( this.getFullPath( path ) );
	}

	@Override
	public Entry getEntry(String path) {
		return this.accessor.getEntry( this.getFullPath( path ) );
	}
	
	@Override
	public Resource getResource(String path) {
		return this.accessor.getResource( this.getFullPath( path ) );
	}

	@Override
	public Directory getDirectory(String path) {
		return this.accessor.getDirectory( this.getFullPath( path ) );
	}

	@Override
	public List<Entry> listEntries(String path) {
		return this.accessor.listEntries( this.getFullPath( path ) );
	}

	@Override
	public List<Resource> listResources(String path) {
		return this.accessor.listResources( this.getFullPath( path ) );
	}

	@Override
	public List<Directory> listDirectories(String path) {
		return this.accessor.listDirectories( this.getFullPath( path ) );
	}

	@Override
	public boolean isDirectory() {
		return true;
	}

	@Override
	public Directory toDirectory() {
		return this;
	}

	@Override
	public boolean isResource() {
		return false;
	}

	@Override
	public Resource toResource() {
		return null;
	}
	
	@Override
	public String toString() {
		return "Directory '" + this.path + "'\n" +
				"\tIn accessor : " + this.accessor;
	}

}
