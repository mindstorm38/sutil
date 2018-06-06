package io.sutil.buffer;

public class LimitedStackBuffer extends BaseBuffer {

	private final int maxCapacity;
	
	public LimitedStackBuffer(int maxCapacity, byte[] bytes) {
		
		super( bytes );
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
	
	public void clearReadBytes() {
		// TODO
	}
	
}
