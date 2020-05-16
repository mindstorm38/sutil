package io.sutil.pool;

import java.util.NoSuchElementException;
import java.util.function.Supplier;

public abstract class ObjectPool<T> {
	
	/**
	 * <p>Acquire an object from this pool.</p>
	 * <p><i><b>You can call this in a try-for-resource block an the object will be release automatically when leave the block.</b></i></p>
	 * @return Acquired object.
	 * @throws NoSuchElementException If no more object can be acquired.
	 */
	public abstract PoolObject acquire() throws NoSuchElementException;
	
	/**
	 * Release an previously acquired pool object.
	 * @param obj The pool object belong to this pool.
	 * @throws IllegalArgumentException If the given object does not belong to this pool.
	 */
	public void release(PoolObject obj) throws IllegalArgumentException {
		
		if (obj.getPool() != this) {
			throw new IllegalArgumentException("This object doesn't belong to this pool.");
		}
		
		if (obj.isAcquired()) {
			this.safeRelease(obj);
		}
		
	}
	
	/**
	 * Protected method that <i><b>must only be called</b></i> after the obj was checked to
	 * belong to this pool, and the object is not already acquired.
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
		}
		
		public T get() {
			return this.obj;
		}
		
		public ObjectPool<T> getPool() {
			return ObjectPool.this;
		}
		
		protected boolean isAcquired() {
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
	
	private static class SyncObjectPool<T> extends ObjectPool<T> {
		
		private final Object lock = new Object();
		private final ObjectPool<T> delegate;
		
		public SyncObjectPool(ObjectPool<T> delegate) {
			this.delegate = delegate;
		}
		
		@Override
		public PoolObject acquire() throws NoSuchElementException {
			synchronized (this.lock) {
				return this.delegate.acquire();
			}
		}
		
		@Override
		public void release(PoolObject obj) throws IllegalArgumentException {
			
			if (obj.getPool() != this.delegate) {
				throw new IllegalArgumentException("This object doesn't belong to this pool.");
			}
			
			if (obj.isAcquired()) {
				this.safeRelease(obj);
			}
			
		}
		
		@Override
		protected void safeRelease(PoolObject obj) {
			synchronized (this.lock) {
				this.delegate.safeRelease(obj);
			}
		}
		
		@Override
		public boolean hasMore() {
			synchronized (this.lock) {
				return this.delegate.hasMore();
			}
		}
		
	}
	
	// Synchronized //
	
	public static <T> ObjectPool<T> newSync(ObjectPool<T> notSynced) {
		if (notSynced instanceof SyncObjectPool) {
			return notSynced;
		} else {
			return new SyncObjectPool<>(notSynced);
		}
	}
	
	// Fixed //
	
	public static <T> ObjectPool<T> newFixed(Supplier<T> poolProvider, int count) {
		return new FixedObjectPool<>(poolProvider, count);
	}
	
	public static <T> ObjectPool<T> newSyncFixed(Supplier<T> poolProvider, int count) {
		return newSync(newFixed(poolProvider, count));
	}
	
	// Growing Limited //
	
	public static <T> ObjectPool<T> newGrowingLimited(Supplier<T> poolProvider, int initialSize, int limit) {
		return new GrowingObjectPool<>(poolProvider, initialSize, limit);
	}
	
	public static <T> ObjectPool<T> newGrowingLimited(Supplier<T> poolProvider, int limit) {
		return new GrowingObjectPool<>(poolProvider, 0, limit);
	}
	
	public static <T> ObjectPool<T> newSyncGrowingLimited(Supplier<T> poolProvider, int initialSize, int limit) {
		return newSync(newGrowingLimited(poolProvider, initialSize, limit));
	}
	
	public static <T> ObjectPool<T> newSyncGrowingLimited(Supplier<T> poolProvider, int limit) {
		return newSync(newGrowingLimited(poolProvider, limit));
	}
	
	// Growing //
	
	public static <T> ObjectPool<T> newGrowing(Supplier<T> poolProvider, int initialSize) {
		return newGrowingLimited(poolProvider, 0, initialSize);
	}
	
	public static <T> ObjectPool<T> newGrowing(Supplier<T> poolProvider) {
		return newGrowingLimited(poolProvider, 0);
	}
	
	public static <T> ObjectPool<T> newSyncGrowing(Supplier<T> poolProvider, int initialSize) {
		return newSyncGrowingLimited(poolProvider, initialSize, 0);
	}
	
	public static <T> ObjectPool<T> newSyncGrowing(Supplier<T> poolProvider) {
		return newSyncGrowingLimited(poolProvider, 0);
	}

}
