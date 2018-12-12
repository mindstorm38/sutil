package io.sutil.math;

import java.util.Iterator;

public class IntRange implements Iterable<Integer> {

	private final int from, to;
	
	public IntRange(int from, int to) {
		
		this.from = from;
		this.to = to;
		
	}
	
	private abstract class RangeItBase implements Iterator<Integer> {
		
		protected int i = IntRange.this.from;
		
	}
	
	private class RangeIt extends RangeItBase {

		@Override
		public boolean hasNext() {
			return this.i < IntRange.this.to;
		}

		@Override
		public Integer next() {
			return ++this.i;
		}
		
	}
	
	private class RangeInvertIt extends RangeItBase {

		@Override
		public boolean hasNext() {
			return this.i > IntRange.this.to;
		}

		@Override
		public Integer next() {
			return --this.i;
		}
		
	}
	
	@Override
	public Iterator<Integer> iterator() {
		return this.from < this.to ? new RangeIt() : new RangeInvertIt();
	}

}
