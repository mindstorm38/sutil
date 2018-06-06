package io.sutil;

import java.util.HashMap;
import java.util.Map;

public class MapUtils {

	public static <K, V> V getOrDefaultKey(Map<K, V> map, K key, K defkey) {
		V o = map.get( key );
		return o == null ? map.get( defkey ) : o;
	}
	
	public static <K, V> V getOrDefault(Map<K, V> map, K key, V defValue) {
		V o = map.get( key );
		return o == null ? defValue : o;
	}
	
	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> createMapInline(Object...kva) {
		Map<K, V> map = new HashMap<>();
		try {
			for ( int i = 0; i < kva.length; i++ ) {
				map.put( (K) kva[ i ], (V) kva[ i + 1 ] );
			}
		} catch (ClassCastException e) {
			throw new IllegalStateException( "Illegal key or value class", e );
		}
		return map;
	}
	
}
