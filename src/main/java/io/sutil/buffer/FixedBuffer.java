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
	
	@Override
	public BaseBuffer writeBytes(byte[] bytes) {
		throw this.cantAllocateException();
	}
	
	@Override
	public BaseBuffer writeBytes(byte[] bytes, int count) {
		throw this.cantAllocateException();
	}
	
	@Override
	public BaseBuffer writeByte(byte byt) {
		throw this.cantAllocateException();
	}
	
	@Override
	public BaseBuffer writeUnsignedByte(short unsignedByte) {
		throw this.cantAllocateException();
	}
	
	@Override
	public BaseBuffer writeShort(short shrt) {
		throw this.cantAllocateException();
	}
	
	@Override
	public BaseBuffer writeUnsignedShort(int unsignedShort) {
		throw this.cantAllocateException();
	}
	
	@Override
	public BaseBuffer writeInteger(int integer) {
		throw this.cantAllocateException();
	}
	
	@Override
	public BaseBuffer writeUnsignedInteger(long unsignedInteger) {
		throw this.cantAllocateException();
	}
	
	@Override
	public BaseBuffer writeLong(long lng) {
		throw this.cantAllocateException();
	}
	
	@Override
	public BaseBuffer writeFloat(float flt) {
		throw this.cantAllocateException();
	}
	
	@Override
	public BaseBuffer writeDouble(double dbl) {
		throw this.cantAllocateException();
	}
	
}
