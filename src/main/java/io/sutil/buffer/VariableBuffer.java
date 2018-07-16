package io.sutil.buffer;

import java.nio.ByteOrder;

public class VariableBuffer extends BaseBuffer {
	
	public VariableBuffer(ByteOrder order, byte[] bytes) {
		super( order, bytes );
	}
	
	public VariableBuffer(byte[] bytes) {
		super( bytes );
	}
	
	public VariableBuffer(ByteOrder order, int capacity) {
		super( order, capacity );
	}
	
	public VariableBuffer(int capacity) {
		super( capacity );
	}
	
	public VariableBuffer(ByteOrder order) {
		super( order );
	}
	
	public VariableBuffer() {
		super();
	}
	
	@Override
	protected int allocate(int count) {
		
		int offset = this.bytes.length;
		byte[] newBytes = new byte[ offset + count ];
		System.arraycopy( this.bytes, 0, newBytes, 0, offset );
		this.bytes = newBytes;
		return offset;
		
	}
	
}
