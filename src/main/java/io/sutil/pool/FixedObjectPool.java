package io.sutil.pool;

import java.util.ArrayDeque;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.function.Supplier;

public class FixedObjectPool<T> extends ObjectPool<T> {

	private final Queue<PoolObject> pool;
	
	public FixedObjectPool(Supplier<T> poolProvider, int poolSize) {
		
		this.pool = new ArrayDeque<>();
		
		for (int i = 0; i < poolSize; ++i) {
			this.pool.add(this.new PoolObject(poolProvider.get()));
		}
		
	}
	
	@Override
	public PoolObject safeAcquire() throws NoSuchElementException {
		if (this.pool.isEmpty()) {
			throw new NoSuchElementException("No more object in this pool.");
		} else {
			return this.pool.poll();
		}
	}
	
	@Override
	protected void safeRelease(PoolObject obj) {
		this.pool.add(obj);
	}
	
	@Override
	public boolean hasMore() {
		return !this.pool.isEmpty();
	}
	
}
