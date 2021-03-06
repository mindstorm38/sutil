package io.sutil.buffer;

import java.nio.ByteOrder;

public class LimitedStackBuffer extends BaseBuffer {

	private final int maxCapacity;
	
	public LimitedStackBuffer(ByteOrder order, int maxCapacity, byte[] bytes) {
		
		super( order, bytes );
		this.maxCapacity = maxCapacity;
		
	}
	
	public LimitedStackBuffer(int maxCapacity, byte[] bytes) {
		
		super( bytes );
		this.maxCapacity = maxCapacity;
		
	}
	
	public LimitedStackBuffer(ByteOrder order, int maxCapacity) {
		
		super( order );
		this.maxCapacity = maxCapacity;
		
	}
	
	public LimitedStackBuffer(int maxCapacity) {
		
		super();
		this.maxCapacity = maxCapacity;
		
	}
	
	@Override
	protected int allocate(int count) {
		
		int newLength = this.bytes.length + count;
		int offset = Math.max( 0, newLength - this.maxCapacity );
		int bytesLength = this.bytes.length - offset;
		
		byte[] newBytes = new byte[ newLength - offset ];
		System.arraycopy( this.bytes, offset, newBytes, 0, bytesLength );
		
		this.bytes = newBytes;
		
		if ( this.readIndex > 0 ) {
			
			this.readIndex -= offset;
			if ( this.readIndex < 0 ) this.readIndex = 0;
			
		}
		
		return bytesLength;
		
	}
	
	public void clearStartBytes(int count) {
		
		if ( count == 0 ) return;
		
		int newLength = this.bytes.length - count;
		byte[] newBytes = new byte[ newLength ];
		System.arraycopy( this.bytes, count, newBytes, 0, newLength );
		this.bytes = newBytes;
		
		this.readIndex -= count;
		if ( this.readIndex < 0 ) this.readIndex = 0;
		
		this.writeIndex -= count;
		if ( this.writeIndex < 0 ) this.writeIndex = 0;
		
	}
	
	public void clearReadBytes() {
		this.clearStartBytes( this.readIndex );
	}
	
}
