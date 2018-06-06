package io.sutil;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ListUtils {
	
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
		
		List<E> removed = new ArrayList<>();
		
		from.forEach( e -> {
			if ( !contains( to, e, equalTester ) ) removed.add( e );
		} );
		
		from.removeAll( removed );
		
	}
	
	public static <E> void listDiff(List<E> from, List<E> to) {
		listDiff( from, to, new EqualTester.EqualTesterDefault<>() );
	}
	
}
