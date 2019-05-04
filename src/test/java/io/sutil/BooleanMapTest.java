package io.sutil;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BooleanMapTest {

	@Test
	@DisplayName("Test with string boolean map")
	void test() {
		
		final BooleanMap<Integer> map = new BooleanMap<>();
		map.set( false, 234034 );
		map.set( true, 343423 );
		
		assertEquals( map.getFalse(), 234034 );
		assertEquals( map.getTrue(), 343423 );
		
	}
	
}
