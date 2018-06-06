package io.sutil.buffer;

public class VariableBuffer extends BaseBuffer {
	
	public VariableBuffer(byte[] bytes) {
		super( bytes );
	}
	
	public VariableBuffer() {
		super();
	}
	
	@Override
	protected int allocate(int count) {
		
		int offset = this.bytes.length;
		byte[] newBytes = new byte[offset + count ];
		System.arraycopy( this.bytes, 0, newBytes, 0, count );
		this.bytes = newBytes;
		return offset;
		
	}
	
}
