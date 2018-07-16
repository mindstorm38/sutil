package io.sutil;

/**
 * 
 * Lazy loadable value holder (<a href="https://en.wikipedia.org/wiki/Lazy_loading">Lazy Loading - Wikipedia</a>)
 * 
 * @author Mindstorm38
 *
 * @param <T> Value type
 */
public abstract class LazyLoadValue<T> {
	
	protected T value;
	protected boolean loaded;
	
	public LazyLoadValue() {
		
		this.value = null;
		this.loaded = false;
		
	}
	
	public T get() {
		
		if ( !this.loaded ) {
			
			this.value = this.create();
			this.loaded = true;
		}
		
		return this.value;
		
	}
	
	public abstract T create();
	
	public void reset() {
		
		this.loaded = false;
		
	}
	
}
