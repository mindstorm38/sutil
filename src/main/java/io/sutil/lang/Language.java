package io.sutil.lang;

import java.util.HashMap;
import java.util.Map;

public class Language {
	
	private final String path;
	private final String identifier;
	private final String name;
	
	protected final Map<String, String> entries = new HashMap<>();
	
	public Language(String path, String identifier, String name) {
		
		this.path = path;
		this.identifier = identifier;
		this.name = name;
		
	}
	
	public String getPath() {
		return this.path;
	}
	
	public String getIdentifier() {
		return this.identifier;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getEntry(String key) {
		return this.entries.get( key );
	}
	
}
