package io.sutil.resource;

import java.io.InputStream;

import io.sutil.StreamUtils;

public class Resource extends AccessorObject implements AutoCloseable {
	
	/**
	 * The resource input stream
	 */
	protected final InputStream stream;
	
	public Resource(ResourceAccessor accessor, String path, InputStream stream) {
		
		super( accessor, path );
		
		this.stream = stream;
		
	}
	
	public InputStream getStream() {
		return this.stream;
	}

	@Override
	public void close() throws Exception {
		
		StreamUtils.safeclose( this.stream );
		
	}
	
}
