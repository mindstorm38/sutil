package io.sutil;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CollectionUtils {
	
	// ARRAY
	
	public static <E> int arrayIndexOf(E[] arr, E elt) {
		for ( int i = 0; i < arr.length; i++ ) if ( elt.equals( arr[ i ] ) ) return i;
		return -1;
	}
	
	public static <E> int arrayLastIndexOf(E[] arr, E elt) {
		for ( int i = arr.length - 1; i >= 0; i-- ) if ( elt.equals( arr[ i ] ) ) return i;
		return -1;
	}
	
	public static <E> boolean arrayContains(E[] array, E elt) {
		for ( E e : array )
			if ( e.equals( elt ) )
				return true;
		return false;
	}
	
	// LIST
	
	@SafeVarargs
	public static <E> List<E> createListInline(E...elts) {
		List<E> l = new ArrayList<>();
		for ( E elt : elts ) l.add( elt );
		return l;
	}
	
	public static <E> List<E> enumToList(Enumeration<E> enumeration) {
		List<E> list = new ArrayList<>();
		while ( enumeration.hasMoreElements() ) list.add( enumeration.nextElement() );
		return list;
	}
	
	public static <E> boolean contains(List<E> l, E t, EqualTester<E> equalTester) {
		for ( E e : l ) if ( equalTester.testEquals( e, t ) ) return true;
		return false;
	}
	
	public static <E> void listDiff(List<E> from, List<E> to, EqualTester<E> equalTester) {
		
		to.forEach( e -> {
			if ( !contains( from, e, equalTester ) ) from.add( e );
		} );
		
		Iterator<E> it = from.iterator();
		
		while ( it.hasNext() ) {
			if ( !contains( to, it.next(), equalTester ) ) it.remove();
		}
		
	}
	
	public static <E> void listDiff(List<E> from, List<E> to) {
		listDiff( from, to, new EqualTester.EqualTesterDefault<>() );
	}
	
	public static <E> E getOrNull(List<E> list, int idx) {
		return idx >= 0 && idx < list.size() ? list.get( idx ) : null;
	}
	
	public static void stringListSubstring(List<String> list, int beginIndex, int endIndex) {
		for ( int i = 0; i < list.size(); i++ )
			list.set( i, list.get( i ).substring( beginIndex, endIndex ) );
	}
	
	public static void stringListSubstring(List<String> list, int beginIndex) {
		for ( int i = 0; i < list.size(); i++ )
			list.set( i, list.get( i ).substring( beginIndex ) );
	}
	
	@SuppressWarnings("unchecked")
	public static <E> E[] toArray(List<E> list, Class<E> clazz) {
		
		try {
			
			E[] arr = (E[]) Array.newInstance( clazz, list.size() );
			return list.toArray( arr );
			
		} catch (NegativeArraySizeException e) {
			return null;
		}
		
	}
	
	// MAP

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
			for ( int i = 0; i < kva.length; i += 2 ) {
				map.put( (K) kva[ i ], (V) kva[ i + 1 ] );
			}
		} catch (ClassCastException e) {
			throw new IllegalStateException( "Illegal key or value class", e );
		}
		return map;
	}
	
}
