package io.sutil.pool;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Supplier;

public class FixedObjectPool<T> {

	private final Object lock = new Object();

	private final Deque<PoolObject> pool;
	
	public FixedObjectPool(Supplier<T> poolProvider, int poolSize) {
		
		this.pool = new ArrayDeque<>(poolSize);
		
		for (int i = 0; i < poolSize; ++i)
			this.pool.add(new PoolObject(poolProvider.get()));
		
	}
	
	public class PoolObject implements AutoCloseable {
		
		private final T obj;
		private boolean acquired = false;
		
		PoolObject(T obj) {
			this.obj = obj;
		}
		
		public T get() {
			return this.obj;
		}
		
		private PoolObject acquired() {
			this.acquired = true;
			return this;
		}
		
		@Override
		public void close() {
			FixedObjectPool.this.release(this);
		}
		
	}
	
	public PoolObject acquire() {
		synchronized (this.lock) {
			
			if (this.pool.isEmpty())
				throw new IllegalStateException("No more object in this pool.");
			
			return this.pool.pollFirst().acquired();
			
		}
	}
	
	public void release(PoolObject poolObject) {
		if (poolObject.acquired) {
			synchronized (this.lock) {
				poolObject.acquired = false;
				this.pool.addFirst(poolObject);
			}
		}
	}
	
}
