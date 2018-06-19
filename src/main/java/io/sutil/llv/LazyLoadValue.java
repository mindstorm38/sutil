package io.sutil.llv;

public abstract class LazyLoadValue<T> extends LazyLoadFinalValue<T> {

	public void reset() {
		this.loaded = false;
	}
	
}
