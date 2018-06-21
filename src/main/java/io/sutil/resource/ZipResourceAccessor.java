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
	
	public ZipResourceAccessor(File file) throws IOException {
		this.zip = new ZipFile( file );
	}
	
	@Override
	public boolean resourceExists(String path) {
		return this.zip.getEntry( path ) != null;
	}

	@Override
	public InputStream resourceInputStream(String path) {
		ZipEntry entry = this.zip.getEntry( path );
		if ( entry != null ) {
			try {
				this.zip.getInputStream( entry );
			} catch (IOException e) {
				return null;
			}
		}
		return null;
	}

	@Override
	public List<String> listResourcePaths(String basePath) {
		
		Enumeration<? extends ZipEntry> entries = this.zip.entries();
		List<String> list = new ArrayList<>();
		int basePathLength = basePath.length();
		String name = null;
		ZipEntry entry = null;
		
		while ( entries.hasMoreElements() ) {
			
			entry = entries.nextElement();
			
			name = entry.getName().substring( basePathLength );
			
			if ( !name.startsWith( basePath ) ) continue;
			if ( name.indexOf('/') != -1 ) continue;
			
			list.add( name );
			
		}
		
		return list;
		
	}
	
}
