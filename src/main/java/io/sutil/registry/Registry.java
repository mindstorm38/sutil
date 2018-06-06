package io.sutil.registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 
 * This class is similar to a map but with unique value and key can be find from their values
 * 
 * @author Mindstorm38
 *
 * @param <K> Key type
 * @param <V> Value type
 */
public class Registry<K, V> {
	
	private final Map<K, V> map = new HashMap<>();
	
	public Registry() {}
	
	public V get(K key) {
		return this.map.get( key );
	}
	
	public void register(K key, V value) {
		if ( this.getKey( value ) != null ) throw new IllegalStateException("This value is already used"); // Check if the value is unique
		this.map.put( key, value );
	}
	
	public void unregister(K key) {
		this.map.remove( key );
	}
	
	public K getKey(V value) {
		for ( Entry<K, V> e : this.map.entrySet() )
			if ( e.getValue().equals( value ) ) return e.getKey();
		return null;
	}
	
	public boolean containsKey(K key) {
		return this.map.containsKey( key );
	}
	
	public boolean containsValue(V value) {
		return this.map.containsValue( value );
	}
	
	public int size() {
		return this.map.size();
	}
	
	public Set<Entry<K, V>> entries() {
		return this.map.entrySet();
	}
	
	public Set<K> keys() {
		return this.map.keySet();
	}
	
	public List<V> values() {
		return new ArrayList<>( this.map.values() );
	}
	
}
