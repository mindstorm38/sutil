package io.sutil.resource;

import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * This class is derived from ResourceAccessor, and is similar to a wrapper,
 * but can contains multiple "sub" ResourceAccessors. It can be used to specify
 * multiple sources for resources, like in a system where a game needs to access
 * its own resources, and at a time permet modifications (mods) to access their owns.
 *
 * @author Théo Rozier (Mindstorm38)
 *
 */
public class ResourceManager extends ResourceAccessor {

	private final List<ResourceAccessor> accessors;
	
	public ResourceManager() {
		
		this.accessors = new ArrayList<>();
		
	}
	
	public void registerAccessor(ResourceAccessor accessor, int at) {
		
		if (this.accessors.contains(accessor))
			throw new RuntimeException("Accessor already registered !");
		
		this.accessors.add(at, accessor);
		
	}
	
	public void registerAccessor(ResourceAccessor accessor) {
		this.registerAccessor(accessor, this.accessors.size());
	}
	
	protected static RuntimeException getIncoherentCallError() {
		return new RuntimeException("Incoherent call for a ResourceManager, or missing entry.");
	}
	
	@Override
	public String getFullPath(String path) {
		throw getIncoherentCallError();
	}
	
	@Override
	public boolean entryExists(String path) {
		return this.accessors.stream().anyMatch(ra -> ra.entryExists(path));
	}
	
	@Override
	public boolean resourceExists(String path) {
		return this.accessors.stream().anyMatch(ra -> ra.resourceExists(path));
	}
	
	@Override
	public boolean directoryExists(String path) {
		return this.accessors.stream().anyMatch(ra -> ra.directoryExists(path));
	}
	
	@Override
	public InputStream resourceInputStream(String path) {
		
		InputStream stream;
		for (ResourceAccessor ra : this.accessors)
			if ((stream = ra.resourceInputStream(path)) != null)
				return stream;
			
		return null;
		
	}
	
	@Override
	public Entry getEntry(String path) {
		
		Entry ent;
		for (ResourceAccessor ra : this.accessors)
			if ((ent = ra.getEntry(path)) != null)
				return ent;
		
		return null;
		
	}
	
	@Override
	public Resource getResource(String path) {
		
		Resource res;
		for (ResourceAccessor ra : this.accessors)
			if ((res = ra.getResource(path)) != null)
				return res;
		
		return null;
		
	}
	
	@Override
	public Directory getDirectory(String path) {
		
		Directory dir;
		for (ResourceAccessor ra : this.accessors)
			if ((dir = ra.getDirectory(path)) != null)
				return dir;
		
		return null;
		
	}
	
	@Override
	public List<Entry> listEntries(String path) {
		
		// return this.accessors.stream().map(ra -> ra.listEntries(path)).flatMap(List::stream).collect(Collectors.toList());
		
		final List<Entry> res = new ArrayList<>();
		
		for (ResourceAccessor ra : this.accessors)
			res.addAll(ra.listEntries(path));
		
		return res;
		
	}
	
	@Override
	public List<Resource> listResources(String path) {
		
		final List<Resource> res = new ArrayList<>();
		
		for (ResourceAccessor ra : this.accessors)
			res.addAll(ra.listResources(path));
		
		return res;
		
	}
	
	@Override
	public List<Directory> listDirectories(String path) {
		
		final List<Directory> res = new ArrayList<>();
		
		for (ResourceAccessor ra : this.accessors)
			res.addAll(ra.listDirectories(path));
		
		return res;
		
	}
	
}
