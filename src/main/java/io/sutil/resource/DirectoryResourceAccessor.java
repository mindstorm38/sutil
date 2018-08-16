package io.sutil.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.sutil.FileUtils;

public class DirectoryResourceAccessor extends ResourceAccessor {

	private final File directory;
	
	public DirectoryResourceAccessor(File folder, String baseFolderPath) {
		
		super( baseFolderPath );
		
		if ( folder == null || !folder.isDirectory() ) throw new IllegalArgumentException("Invalid folder File instance");
		
		this.directory = folder;
		
	}
	
	public DirectoryResourceAccessor(File folder) {
		this( folder, "" );
	}
	
	private File getFile(String path) {
		File file = new File( this.directory, this.getFullPath( path ) );
		if ( FileUtils.getFileHierarchyLevel( file ) < FileUtils.getFileHierarchyLevel( this.directory ) ) return null;
		return FileUtils.simplifyFilePath( file );
	}
	
	@Override
	public boolean entryExists(String path) {
		File file = this.getFile( path );
		return file == null ? false : file.exists();
	}
	
	@Override
	public boolean resourceExists(String path) {
		File file = this.getFile( path );
		return file == null ? false : file.isFile();
	}
	
	@Override
	public boolean directoryExists(String path) {
		File file = this.getFile( path );
		return file == null ? false : file.isDirectory();
	}

	@Override
	public InputStream resourceInputStream(String path) {
		
		File file = this.getFile( path );
		if ( !file.isFile() ) return null;
		
		try {
			return new FileInputStream( file );
		} catch (FileNotFoundException e) {
			return null;
		}
		
	}
	
	@Override
	public Entry getEntry(String path) {
		File file = this.getFile( path );
		if ( file == null ) return null;
		return file.isDirectory() ? new Directory( this, path ) : file.isFile() ? new Resource( this, path ) : null;
	}

	@Override
	public List<Entry> listEntries(String path) {
		
		path = BaseDirectory.formatDirectoryPath( path );
		
		List<Entry> entries = new ArrayList<>();
		
		File directory = this.getFile( path );
		if ( directory == null || !directory.isDirectory() ) return null;
		
		int dirPathLength = this.directory.getAbsolutePath().length() + this.baseDirectoryPath.length() + 1;
		
		directory.listFiles( f -> {
			
			if ( f.isDirectory() )
				entries.add( new Directory( DirectoryResourceAccessor.this, f.getAbsolutePath().substring( dirPathLength ) ) );
			else if ( f.isFile() )
				entries.add( new Resource( DirectoryResourceAccessor.this, f.getAbsolutePath().substring( dirPathLength ) ) );
			
			return false;
			
		} );
		
		return entries;
		
	}
	
	@Override
	public String toString() {
		return "Directory Resource Accessor at '" + this.directory.getAbsolutePath() + "' & base directory '" + this.baseDirectoryPath + "'";
	}
	
	/*
	@Override
	public List<String> listResourcePaths(String basePath) {
		
		File folder = this.getFile( basePath );
		
		if ( !folder.isDirectory() ) return null;
		
		int basePathLength = folder.getAbsolutePath().length() - basePath.length();
		
		List<String> list = new ArrayList<>();
		
		folder.listFiles( new FileFilter() {
			public boolean accept(File file) {
				
				if ( !file.isFile() ) return false;
				
				list.add( file.getAbsolutePath().substring( basePathLength ) );
				
				return false;
				
			}
		} );
		
		return list;
		
	}
	*/

}
