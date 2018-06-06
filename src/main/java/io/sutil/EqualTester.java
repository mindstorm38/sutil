package io.sutil;

public interface EqualTester<T> {
	
	boolean testEquals(T o1, T o2);
	
	public static class EqualTesterDefault<T> implements EqualTester<T> {

		@Override
		public boolean testEquals(T o1, T o2) {
			return o1.equals( o2 );
		}
		
	}
	
}
