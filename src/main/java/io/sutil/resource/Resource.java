package io.sutil.resource;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

import io.sutil.FileUtils;
import io.sutil.LazyLoadValue;
import io.sutil.StreamUtils;

public class Resource extends Entry implements Closeable {
	
	/**
	 * The resource input stream
	 */
	protected final LazyLoadValue<InputStream> inputStream;
	
	/**
	 * Lazy value for the resource extension
	 */
	protected final LazyLoadValue<String> extension;
	
	/**
	 * Lazy value for the resource strict name
	 */
	protected final LazyLoadValue<String> strictName;
	
	public Resource(ResourceAccessor accessor, String path) {
		
		super( accessor, path );
		
		this.inputStream = new LazyLoadValue<InputStream>() {
			
			public InputStream create() {
				return Resource.this.accessor.resourceInputStream( Resource.this.path );
			}
			
		};
		
		this.extension = new LazyLoadValue<String>() {
			
			public String create() {
				return FileUtils.getFileExtension( Resource.this.path );
			}
			
		};
		
		this.strictName = new LazyLoadValue<String>() {
			
			public String create() {
				return FileUtils.getFileStrictName( Resource.this.path );
			}
			
		};
		
	}
	
	public InputStream getInputStream() {
		return this.inputStream.get();
	}
	
	public String getExtension() {
		return this.extension.get();
	}
	
	public String getStrictName() {
		return this.strictName.get();
	}

	@Override
	public void close() throws IOException {
		
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
