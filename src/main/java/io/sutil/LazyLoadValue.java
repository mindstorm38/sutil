package io.sutil;

/**
 * 
 * Lazy loadable value holder (<a href="https://en.wikipedia.org/wiki/Lazy_loading">Lazy Loading - Wikipedia</a>)
 * 
 * @author Théo Rozier (Mindstorm38)
 *
 * @param <T> Value type
 */
public abstract class LazyLoadValue<T> {
	
	private T value;
	private boolean loaded;
	protected RuntimeException exception;
	
	public LazyLoadValue() {
		
		this.value = null;
		this.loaded = false;
		this.exception = null;
		
	}
	
	public T get() {
		
		if ( !this.loaded ) {
			
			this.value = this.create();
			this.loaded = true;
		}
		
		if ( this.exception != null )
			throw this.exception;
		
		return this.value;
		
	}
	
	public abstract T create();
	
	public void reset() {
		
		this.loaded = false;
		this.exception = null;
		
	}
	
	public boolean loaded() {
		return this.loaded;
	}
	
}
