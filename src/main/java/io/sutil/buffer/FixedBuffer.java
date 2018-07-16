package io.sutil.buffer;

public class FixedBuffer extends BaseBuffer {

	public FixedBuffer(byte[] bytes) {
		super(bytes);
	}
	
	public FixedBuffer(int count) {
		this.bytes = new byte[ count ];
	}
	
	private IllegalStateException cantAllocateException() {
		return new IllegalStateException("Can't allocate new bytes in FixedBuffer");
	}
	
	@Override
	protected int allocate(int count) {
		throw this.cantAllocateException();
	}
	
}
