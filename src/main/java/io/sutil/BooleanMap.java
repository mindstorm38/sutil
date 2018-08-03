package io.sutil;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class BooleanMap<V> implements Map<Boolean, V> {
	
	private V t;
	private V f;
	
	public BooleanMap(V t, V f) {
		
		this.t = t;
		this.f = f;
		
	}
	
	public BooleanMap() {
		this( null, null );
	}
	
	public V getTrue() { return this.t; }
	public V getFalse() { return this.f; }
	public V get(boolean b) { return b ? this.t : this.f; }
	
	public void set(boolean b, V value) {
		if ( b ) this.t = value;
		else this.f = value;
	}
	
	private IllegalArgumentException invalidKeyException() {
		return new IllegalArgumentException("Invalid key type, must be boolean");
	}
	
	private void checkKey(Object key) {
		if ( !CommonUtils.isBoolean( key ) ) throw this.invalidKeyException();
	}
	
	@Override
	public int size() {
		return 2;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public boolean containsKey(Object key) {
		return CommonUtils.isBoolean( key );
	}

	@Override
	public boolean containsValue(Object value) {
		return Objects.equals( this.t, value ) || Objects.equals( this.f, value );
	}

	@Override
	public V get(Object key) {
		if ( !CommonUtils.isBoolean( key ) ) return null;
		return this.get( (boolean) key );
	}

	@Override
	public V put(Boolean key, V value) {
		
		if ( key == null ) throw this.invalidKeyException();
		
		V previous;
		
		if ( key ) {
			
			previous = this.t;
			this.t = value;
			
		} else {
			
			previous = this.f;
			this.f = value;
			
		}
		
		return previous;
		
	}

	@Override
	public V remove(Object key) {
		
		this.checkKey( key );
		
		boolean b = (boolean) key;
		V previous;
		
		if ( b ) {
			
			previous = this.t;
			this.t = null;
			
		} else {
			
			previous = this.f;
			this.f = null;
			
		}
		
		return previous;
		
	}

	@Override
	public void putAll(Map<? extends Boolean, ? extends V> m) {
		
		this.t = m.get( true );
		this.f = m.get( false );
		
	}

	@Override
	public void clear() {
		
		this.t = null;
		this.f = null;
		
	}

	@Override
	public Set<Boolean> keySet() {
		return CollectionUtils.createSetInline( true, false );
	}

	@Override
	public Collection<V> values() {
		return CollectionUtils.createListInline( this.t, this.f );
	}

	@Override
	public Set<Entry<Boolean, V>> entrySet() {
		return CollectionUtils.createSetInline( new BooleanEntry( true ), new BooleanEntry( false ) );
	}
	
	private class BooleanEntry implements Entry<Boolean, V> {

		private final boolean b;
		
		public BooleanEntry(boolean b) {
			this.b = b;
		}
		
		@Override
		public Boolean getKey() {
			return b;
		}

		@Override
		public V getValue() {
			return BooleanMap.this.get( this.b );
		}

		@Override
		public V setValue(V value) {
			return BooleanMap.this.put( this.b, value );
		}
		
	}
	
}
