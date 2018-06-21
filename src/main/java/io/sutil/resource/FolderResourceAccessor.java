package io.sutil.resource;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FolderResourceAccessor extends ResourceAccessor {

	private final File folder;
	
	public FolderResourceAccessor(File folder) {
		
		if ( folder == null || !folder.isDirectory() ) throw new IllegalArgumentException("Invalid folder File instance");
		
		this.folder = folder;
		
	}
	
	private File getFile(String path) {
		return new File( this.folder, path );
	}
	
	@Override
	public boolean resourceExists(String path) {
		return this.getFile( path ).isFile();
	}

	@Override
	public InputStream resourceInputStream(String path) {
		
		try {
			
			return new FileInputStream( this.getFile( path ) );
			
		} catch (FileNotFoundException e) {
			return null;
		}
		
	}

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

}
