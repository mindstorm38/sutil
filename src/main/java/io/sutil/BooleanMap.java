package io.sutil;

public class BooleanMap<V> {
	
	private final V t;
	private final V f;
	
	public BooleanMap(V t, V f) {
		
		this.t = t;
		this.f = f;
		
	}
	
	public V getTrue() { return this.t; }
	public V getFalse() { return this.f; }
	public V get(boolean b) { return b ? this.t : this.f; }
	
}
