package io.sutil;

/**
 * 
 * Tuple class
 * 
 * @author Mindstorm38
 *
 * @param <A> First element's type
 * @param <B> Second element's type
 */
public class Tuple<A, B> {
	
	public final A a;
	public final B b;
	
	public Tuple(A a, B b) {
		this.a = a;
		this.b = b;
	}
	
	public Tuple<A, B> copy() {
		return new Tuple<>( a, b );
	}
	
	@Override
	public String toString() {
		return this.a.toString() + ":" + this.b.toString();
	}
	
}
