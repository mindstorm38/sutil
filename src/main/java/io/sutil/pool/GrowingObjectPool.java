package io.sutil.pool;

import java.util.ArrayDeque;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.function.Supplier;

public class GrowingObjectPool<T> extends ObjectPool<T> {
	
	private final Supplier<T> provider;
	private final Queue<PoolObject> pool;
	private final int limit;
	private int acquired = 0;
	
	public GrowingObjectPool(Supplier<T> poolProvider, int initialSize, int limit, boolean sync) {
		
		super(sync);
		
		this.provider = poolProvider;
		this.pool = new ArrayDeque<>();
		this.limit = limit;
		
		for (int i = 0; i < initialSize; ++i) {
			this.pool.add(this.new PoolObject(poolProvider.get()));
		}
		
	}
	
	public int getLimit() {
		return this.limit;
	}
	
	public boolean hasLimit() {
		return this.limit > 0;
	}
	
	@Override
	public PoolObject safeAcquire() throws NoSuchElementException {
		
		if (this.hasLimit() && this.acquired >= this.limit) {
			throw new NoSuchElementException("No more object in this pool (limited to " + this.limit + " objects).");
		} else {
			
			PoolObject obj;
			
			if (this.pool.isEmpty()) {
				obj = this.new PoolObject(this.provider.get());
			} else {
				obj = this.pool.poll();
			}
			
			++this.acquired;
			return obj;
			
		}
		
	}
		
	
	@Override
	protected void safeRelease(PoolObject obj) {
		this.pool.add(obj);
		--this.acquired;
	}
	
	@Override
	public boolean hasMore() {
		return !this.hasLimit() || this.acquired < this.limit;
	}
	
	// Growing Limited //
	
	public static <T> GrowingObjectPool<T> newGrowingLimited(Supplier<T> poolProvider, int initialSize, int limit) {
		return new GrowingObjectPool<>(poolProvider, initialSize, limit, false);
	}
	
	public static <T> GrowingObjectPool<T> newGrowingLimited(Supplier<T> poolProvider, int limit) {
		return new GrowingObjectPool<>(poolProvider, 0, limit, false);
	}
	
	public static <T> GrowingObjectPool<T> newSyncGrowingLimited(Supplier<T> poolProvider, int initialSize, int limit) {
		return new GrowingObjectPool<>(poolProvider, initialSize, limit, true);
	}
	
	public static <T> GrowingObjectPool<T> newSyncGrowingLimited(Supplier<T> poolProvider, int limit) {
		return new GrowingObjectPool<>(poolProvider, 0, limit, true);
	}
	
	// Growing //
	
	public static <T> GrowingObjectPool<T> newGrowing(Supplier<T> poolProvider, int initialSize) {
		return newGrowingLimited(poolProvider, 0, initialSize);
	}
	
	public static <T> GrowingObjectPool<T> newGrowing(Supplier<T> poolProvider) {
		return newGrowingLimited(poolProvider, 0);
	}
	
	public static <T> GrowingObjectPool<T> newSyncGrowing(Supplier<T> poolProvider, int initialSize) {
		return newSyncGrowingLimited(poolProvider, initialSize, 0);
	}
	
	public static <T> GrowingObjectPool<T> newSyncGrowing(Supplier<T> poolProvider) {
		return newSyncGrowingLimited(poolProvider, 0);
	}
	
}
