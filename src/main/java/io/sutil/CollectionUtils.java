package io.sutil;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CollectionUtils {
	
	// ARRAY
	
	public static <E> int arrayIndexOf(E[] arr, E elt) {
		for ( int i = 0; i < arr.length; i++ ) if ( arr[ i ] == elt ) return i;
		return -1;
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
			for ( int i = 0; i < kva.length; i++ ) {
				map.put( (K) kva[ i ], (V) kva[ i + 1 ] );
			}
		} catch (ClassCastException e) {
			throw new IllegalStateException( "Illegal key or value class", e );
		}
		return map;
	}
	
}
