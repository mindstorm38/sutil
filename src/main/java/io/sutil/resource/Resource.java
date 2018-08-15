package io.sutil.resource;

import java.io.InputStream;

import io.sutil.LazyLoadValue;
import io.sutil.StreamUtils;

public class Resource extends Entry implements AutoCloseable {
	
	/**
	 * The resource input stream
	 */
	protected final LazyLoadValue<InputStream> inputStream;
	
	public Resource(ResourceAccessor accessor, String path) {
		
		super( accessor, path );
		
		this.inputStream = new LazyLoadValue<InputStream>() {
			
			public InputStream create() {
				return Resource.this.accessor.resourceInputStream( Resource.this.path );
			}
			
		};
		
	}
	
	public InputStream getInputStream() {
		return this.inputStream.get();
	}

	@Override
	public void close() throws Exception {
		
		if ( this.inputStream.loaded() )
			StreamUtils.safeclose( this.inputStream.get() );
		
	}

	@Override
	public boolean isDirectory() {
		return false;
	}

	@Override
	public Directory toDirectory() {
		return null;
	}

	@Override
	public boolean isResource() {
		return true;
	}

	@Override
	public Resource toResource() {
		return this;
	}
	
	@Override
	public String toString() {
		return "Resource at '" + this.path + "'\n" +
				"\tIn accessor : " + this.accessor;
	}
	
}
