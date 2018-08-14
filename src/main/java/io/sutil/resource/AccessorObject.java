package io.sutil.resource;

import io.sutil.FileUtils;
import io.sutil.LazyLoadValue;

public abstract class AccessorObject {

	/**
	 * The {@link ResourceAccessor} used to create this resource
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
	
	public AccessorObject(ResourceAccessor accessor, String path) {
		
		this.accessor = accessor;
		this.path = path;
		
		this.name = new LazyLoadValue<String>() {
			
			@Override
			public String create() {
				return FileUtils.getFileName( AccessorObject.this.path );
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
	
}
