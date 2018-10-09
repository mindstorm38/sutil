package io.sutil.resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipResourceAccessor extends ResourceAccessor {

	private final File file;
	private final ZipFile zip;
	
	public ZipResourceAccessor(File file, String baseFolderPath) throws IOException {
		
		super( baseFolderPath );
		
		this.file = file;
		this.zip = new ZipFile( file );
		
	}
	
	public ZipResourceAccessor(File file) throws IOException {
		this( file, null );
	}
	
	private boolean isBaseDir(String path) {
		return path.isEmpty() || path.equals("/");
	}
	
	@Override
	public boolean entryExists(String path) {
		if ( this.isBaseDir( path ) ) return true;
		return this.zip.getEntry( this.getFullPath( path ) ) != null;
	}
	
	@Override
	public boolean resourceExists(String path) {
		ZipEntry entry = this.zip.getEntry( this.getFullPath( path ) );
		return entry == null ? false : !entry.isDirectory();
	}

	@Override
	public boolean directoryExists(String path) {
		if ( this.isBaseDir( path ) ) return true;
		ZipEntry entry = this.zip.getEntry( this.getFullPath( path ) );
		return entry == null ? false : entry.isDirectory();
	}
	
	@Override
	public InputStream resourceInputStream(String path) {
		ZipEntry entry = this.zip.getEntry( this.getFullPath( path ) );
		if ( entry != null ) {
			try {
				return this.zip.getInputStream( entry );
			} catch (IOException e) {
				return null;
			}
		}
		return null;
	}
	
	@Override
	public Entry getEntry(String path) {
		if ( this.isBaseDir( path ) ) return this.baseDirectory.get();
		ZipEntry entry = this.zip.getEntry( this.getFullPath( path ) );
		return entry == null ? null : entry.isDirectory() ? new Directory( this, path ) : new Resource( this, path );
	}
	
	@Override
	public Resource getResource(String path) {
		ZipEntry entry = this.zip.getEntry( this.getFullPath( path ) );
		return entry == null ? null : entry.isDirectory() ? null : new Resource( this, path );
	}
	
	@Override
	public Directory getDirectory(String path) {
		if ( this.isBaseDir( path ) ) return this.baseDirectory.get();
		ZipEntry entry = this.zip.getEntry( this.getFullPath( path ) );
		return entry == null ? null : entry.isDirectory() ? new Directory( this, path ) : null;
	}

	@Override
	public List<Entry> listEntries(String rawPath) {
		
		rawPath = BaseDirectory.formatDirectoryPath( rawPath );
		
		if ( !this.directoryExists( rawPath ) )
			return null; // Return null if path don't point to a directory
		
		List<Entry> entries = new ArrayList<>();
		
		String path = this.getFullPath( rawPath );
		int pathLength = path.length();
		
		ZipEntry zipEntry;
		String zipEntryName;
		int sepIndex;
		
		Enumeration<? extends ZipEntry> zipEntries = this.zip.entries();
		
		while ( zipEntries.hasMoreElements() ) {
			
			zipEntry = zipEntries.nextElement();
			zipEntryName = zipEntry.getName();
			
			if ( !zipEntryName.startsWith( path ) ) continue;
			zipEntryName = zipEntryName.substring( pathLength );
			
			if ( zipEntryName.isEmpty() ) continue;
			sepIndex = zipEntryName.indexOf( separator );
			if ( sepIndex != -1 && sepIndex != ( zipEntryName.length() - 1 ) ) continue;
			
			zipEntryName = rawPath + zipEntryName;
			
			if ( zipEntry.isDirectory() )
				entries.add( new Directory( this, zipEntryName ) );
			else
				entries.add( new Resource( this, zipEntryName ) );
			
		}
		
		return entries;
		
	}
	
	@Override
	public String toString() {
		return "Zip Resource Accessor for zip file at '" + this.file.getAbsolutePath() + "' & base directory '" + this.baseDirectoryPath + "'";
	}

	/*
	@Override
	public List<String> listResourcePaths(String basePath) {
		
		basePath = this.baseFolderPath + basePath;
		
		Enumeration<? extends ZipEntry> entries = this.zip.entries();
		List<String> list = new ArrayList<>();
		
		int baseFolderPathLength = this.baseFolderPath.length();
		
		String name;
		ZipEntry entry;
		
		while ( entries.hasMoreElements() ) {
			
			entry = entries.nextElement();
			
			name = entry.getName();
			
			if ( !name.startsWith( basePath ) ) continue;
			name = name.substring( baseFolderPathLength );
			
			if ( name.endsWith("/") ) continue;
			if ( name.startsWith("/") ) name = name.substring( 1 );
			
			list.add( name );
			
		}
		
		return list;
		
	}
	*/

}
