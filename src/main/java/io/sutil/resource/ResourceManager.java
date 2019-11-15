package io.sutil.resource;

import java.io.InputStream;
import java.util.*;

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
	private final Map<String, ResourceAccessor> namespaced;
	
	public ResourceManager() {
		
		this.accessors = new ArrayList<>();
		this.namespaced = new HashMap<>();
		
	}
	
	/**
	 * Register a {@link ResourceAccessor} with a namespace and a specific position.
	 * @param namespace The namespace of the accessor, can be used to specify the accessor
	 *                  to use in the relative path given {@link BaseDirectory} implemented
	 *                  methods.
	 * @param accessor The accessor to register.
	 * @param at The position, or the priority of the accessor if no namespace is given in
	 *           {@link BaseDirectory} implemented method. 0 is the highest priority.
	 * @throws IllegalStateException If their are some problems with parameters or namespace collision.
	 */
	public void registerAccessor(String namespace, ResourceAccessor accessor, int at) {
		
		if (namespace == null || namespace.isEmpty())
			throw new IllegalStateException("Invalid empty or null namespace.");
		
		if (this.accessors.contains(Objects.requireNonNull(accessor, "Invalid null resource accessor.")))
			throw new IllegalStateException("Accessor already registered !");
		
		if (this.namespaced.containsKey(namespace))
			throw new IllegalStateException("Accessor with namespace '" + namespace + "' already registerd.");
		
		this.accessors.add(at, accessor);
		this.namespaced.put(namespace, accessor);
		
	}
	
	/**
	 * Register a {@link ResourceAccessor} with a namespace, same as
	 * {@link #registerAccessor(String, ResourceAccessor, int)}, but inserting at the last priority.
	 * @param namespace The namespace of the accessor, can be used to specify the accessor
	 * 	                to use in the relative path given {@link BaseDirectory} implemented
	 *                  methods.
	 * @param accessor The accessor to register.
	 * @throws IllegalStateException If their are some problems with parameters or namespace collision.
	 */
	public void registerAccessor(String namespace, ResourceAccessor accessor) {
		this.registerAccessor(namespace, accessor, this.accessors.size());
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
		
		List<Entry> l;
		for (ResourceAccessor ra : this.accessors)
			if ((l = ra.listEntries(path)) != null)
				res.addAll(l);
		
		return res;
		
	}
	
	@Override
	public List<Resource> listResources(String path) {
		
		final List<Resource> res = new ArrayList<>();
		
		List<Resource> l;
		for (ResourceAccessor ra : this.accessors)
			if ((l = ra.listResources(path)) != null)
				res.addAll(l);
		
		return res;
		
	}
	
	@Override
	public List<Directory> listDirectories(String path) {
		
		final List<Directory> res = new ArrayList<>();
		
		List<Directory> l;
		for (ResourceAccessor ra : this.accessors)
			if ((l = ra.listDirectories(path)) != null)
				res.addAll(l);
		
		return res;
		
	}
	
}
