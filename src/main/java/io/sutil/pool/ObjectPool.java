package io.sutil.pool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

public abstract class ObjectPool<T> {
	
	private final boolean sync;
	
	protected final List<PoolObject> objects = new ArrayList<>();
	private List<PoolObject> unmodifiableObjects = null;
	
	public ObjectPool(boolean sync) {
		this.sync = sync;
	}
	
	public boolean isSynchronized() {
		return this.sync;
	}
	
	public List<PoolObject> getObjects() {
		
		if (this.unmodifiableObjects == null) {
			this.unmodifiableObjects = Collections.unmodifiableList(this.objects);
		}
		
		return this.unmodifiableObjects;
		
	}
	
	/**
	 * <p>Acquire an object from this pool.</p>
	 * <p><i><b>You can call this in a try-for-resource block an the object will be release automatically when leave the block.</b></i></p>
	 * @return Acquired object.
	 * @throws NoSuchElementException If no more object can be acquired.
	 */
	public final PoolObject acquire() throws NoSuchElementException {
		if (this.sync) {
			synchronized (this) {
				return this.safeAcquire().setAcquired(true);
			}
		} else {
			return this.safeAcquire().setAcquired(true);
		}
	}
	
	/**
	 * Release an previously acquired pool object.
	 * @param obj The pool object belong to this pool.
	 * @throws IllegalArgumentException If the given object does not belong to this pool.
	 */
	public final void release(PoolObject obj) throws IllegalArgumentException {
		
		if (obj.getPool() != this) {
			throw new IllegalArgumentException("This object doesn't belong to this pool.");
		}
		
		if (obj.isAcquired()) {
			
			if (this.sync) {
				synchronized (this) {
					this.safeRelease(obj);
					obj.setAcquired(false);
				}
			} else {
				this.safeRelease(obj);
				obj.setAcquired(false);
			}
			
		}
		
	}
	
	/**
	 * Protected method called to acquire next object available in the pool. <b>The returned object
	 * is set to acquired.</b>
	 * @return The object acquired.
	 * @throws NoSuchElementException If no more object can be acquired.
	 */
	protected abstract PoolObject safeAcquire() throws NoSuchElementException;
	
	/**
	 * Protected method that <i><b>must only be called</b></i> after the obj was checked to
	 * belong to this pool, and the object is not already acquired. <b>After this method is called,
	 * the given object is set to not acquired.</b>
	 * @param obj The object to release.
	 */
	protected abstract void safeRelease(PoolObject obj);
	
	/**
	 * @return True if an object can be acquired.
	 */
	public abstract boolean hasMore();
	
	public class PoolObject implements AutoCloseable {
		
		private final T obj;
		private boolean acquired = false;
		
		PoolObject(T obj) {
			this.obj = obj;
			ObjectPool.this.objects.add(this);
		}
		
		public T get() {
			return this.obj;
		}
		
		public ObjectPool<T> getPool() {
			return ObjectPool.this;
		}
		
		public boolean isAcquired() {
			return this.acquired;
		}
		
		protected PoolObject setAcquired(boolean acquired) {
			this.acquired = acquired;
			return this;
		}
		
		@Override
		public void close() {
			ObjectPool.this.release(this);
		}
		
	}

}
