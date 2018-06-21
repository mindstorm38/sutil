package io.sutil.resource;

import java.io.InputStream;
import java.util.List;

public abstract class ResourceAccessor {
	
	public abstract boolean resourceExists(String path);
	public abstract InputStream resourceInputStream(String path);
	public abstract List<String> listResourcePaths(String basePath);
	
}
