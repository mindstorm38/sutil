package io.sutil.resource;

import io.sutil.FileUtils;
import io.sutil.LazyLoadValue;

public abstract class Entry {

	/**
	 * The {@link ResourceAccessor} used to acquire this resource
	 */
	protected final ResourceAccessor accessor;
	
	/**
	 * The relative path to the accessor base folder
	 */
	protected final String path;
	
	/**
	 * Lazy value for the resource/directory name
	 */
	protected final LazyLoadValue<String> name;
	
	public Entry(ResourceAccessor accessor, String path) {
		
		this.accessor = accessor;
		this.path = path.contains("\\") ? path.replace('\\', '/') : path;
		
		this.name = new LazyLoadValue<String>() {
			
			@Override
			public String create() {
				return FileUtils.getFileName( Entry.this.path );
			}
			
		};
		
	}
	
	public ResourceAccessor getAccessor() {
		return this.accessor;
	}
	
	public String getPath() {
		return this.path;
	}
	
	public String getName() {
		return this.name.get();
	}
	
	public abstract boolean isDirectory();
	public abstract Directory toDirectory();
	public abstract boolean isResource();
	public abstract Resource toResource();
	
	@Override
	public String toString() {
		return "Entry at '" + this.path + "'\n" +
				"\tIn accessor : " + this.accessor;
	}
	
}
