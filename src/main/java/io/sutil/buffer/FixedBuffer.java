package io.sutil.buffer;

import java.nio.ByteOrder;

public class FixedBuffer extends BaseBuffer {

	public FixedBuffer(ByteOrder order, byte[] bytes) {
		super( order, bytes );
	}
	
	public FixedBuffer(byte[] bytes) {
		super( bytes );
	}
	
	public FixedBuffer(ByteOrder order, int capacity) {
		super( order, capacity );
	}
	
	public FixedBuffer(int capacity) {
		super( capacity );
	}
	
	@Override
	protected int allocate(int count) {
		throw new IllegalStateException("Can't allocate new bytes in FixedBuffer");
	}
	
}
