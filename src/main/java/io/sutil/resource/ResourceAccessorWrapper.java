package io.sutil.resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.List;

public class ResourceAccessorWrapper extends ResourceAccessor {

	protected final ResourceAccessor accessor;
	
	public ResourceAccessorWrapper(String baseFolderPath) {
		
		try {
			
			File src = new File( ResourceAccessorWrapper.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath() );
			
			if ( src.isDirectory() ) {
				
				this.accessor = new FolderResourceAccessor( src, baseFolderPath );
				
			} else {
				
				this.accessor = new ZipResourceAccessor( src, baseFolderPath );
				
			}
			
		} catch (URISyntaxException | IOException e) {
			throw new IllegalStateException( "Unable to create this RessourceAccessorWrapper", e );
		}
		
	}
	
	@Override
	public boolean resourceExists(String path) {
		return this.accessor.resourceExists( path );
	}

	@Override
	public InputStream resourceInputStream(String path) {
		return this.accessor.resourceInputStream( path );
	}

	@Override
	public List<String> listResourcePaths(String basePath) {
		return this.accessor.listResourcePaths( basePath );
	}

}
