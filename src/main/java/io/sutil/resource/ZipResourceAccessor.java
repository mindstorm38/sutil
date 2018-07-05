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

	private final ZipFile zip;
	
	public ZipResourceAccessor(File file, String baseFolderPath) throws IOException {
		
		super( baseFolderPath );
		
		this.zip = new ZipFile( file );
		
	}
	
	public ZipResourceAccessor(File file) throws IOException {
		this( file, "" );
	}
	
	@Override
	public boolean resourceExists(String path) {
		return this.zip.getEntry( this.baseFolderPath + path ) != null;
	}

	@Override
	public InputStream resourceInputStream(String path) {
		ZipEntry entry = this.zip.getEntry( this.baseFolderPath + path );
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
	
}
