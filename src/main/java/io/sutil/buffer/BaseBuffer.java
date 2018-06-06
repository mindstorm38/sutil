package io.sutil.buffer;

public abstract class BaseBuffer {
	
	protected byte[] bytes;
	protected int readIndex = 0;
	
	public BaseBuffer(byte[] bytes) {
		
		this.bytes = new byte[ bytes.length ];
		System.arraycopy( bytes, 0, this.bytes, 0, bytes.length );
		
	}
	
	public BaseBuffer() {
		
		this.bytes = new byte[0];
		
	}
	
	public byte[] getBytes() {
		return this.bytes;
	}
	
	public int size() {
		return this.bytes.length;
	}
	
	protected void checkIndex(int idx) {
		if ( idx < 0 || idx >= this.bytes.length ) throw new IndexOutOfBoundsException( "Invalid index : " + idx );
	}
	
	/**
	 * Allocate a number of bytes on the top of the buffer
	 * @param count Number of bytes to allocate
	 * @return Byte offset
	 */
	protected abstract int allocate(int count);
	
	// WRITE \\
	
	public BaseBuffer writeBytes(byte[] bytes, int count) {
		System.arraycopy( bytes, 0, this.bytes, this.allocate( count ), count );
		return this;
	}
	
	public BaseBuffer writeBytes(byte[] bytes) {
		return this.writeBytes( bytes, bytes.length );
	}
	
	public BaseBuffer writeByte(byte byt) {
		this.bytes[ this.allocate( 1 ) ] = byt;
		return this;
	}
	
	public BaseBuffer writeShort(short shrt) {
		ByteUtils.writeShort( this.bytes, this.allocate( 2 ), shrt );
		return this;
	}
	
	public BaseBuffer writeInteger(int integer) {
		ByteUtils.writeInteger( this.bytes, this.allocate( 4 ), integer );
		return this;
	}
	
	public BaseBuffer writeLong(long lng) {
		ByteUtils.writeLong( this.bytes, this.allocate( 8 ), lng );
		return this;
	}
	
	public BaseBuffer writeFloat(float flt) {
		ByteUtils.writeFloat( this.bytes, this.allocate( 4 ), flt );
		return this;
	}
	
	public BaseBuffer writeDouble(double dbl) {
		ByteUtils.writeDouble( this.bytes, this.allocate( 8 ), dbl );
		return this;
	}
	
	public BaseBuffer writeBoolean(boolean bool) {
		return this.writeByte( (byte) ( bool ? 0x0 : 0x1 ) );
	}
	
	// READ \\
	
	public int getReadIndex() {
		return this.readIndex;
	}
	
	public BaseBuffer setReadIndex(int readIndex) {
		this.checkIndex( readIndex );
		this.readIndex = readIndex;
		return this;
	}
	
	private void increaseReadIndex(int count) {
		this.readIndex += count;
		this.checkIndex( this.readIndex );
	}
	
	public byte[] readBytes(int count) {
		byte[] b = new byte[ count ];
		System.arraycopy( this.bytes, this.readIndex, b, 0, count );
		this.increaseReadIndex( count );
		return b;
	}
	
	public byte readByte() {
		this.increaseReadIndex( 1 );
		return this.bytes[ this.readIndex - 1 ];
	}
	
	public short readShort() {
		this.increaseReadIndex( 2 );
		return ByteUtils.readShort( this.bytes, this.readIndex - 2 );
	}
	
	public int readInteger() {
		this.increaseReadIndex( 4 );
		return ByteUtils.readInteger( this.bytes, this.readIndex - 4 );
	}
	
	public long readLong() {
		this.increaseReadIndex( 8 );
		return ByteUtils.readLong( this.bytes, this.readIndex - 8 );
	}
	
	public float readFloat() {
		this.increaseReadIndex( 4 );
		return ByteUtils.readFloat( this.bytes, this.readIndex - 4 );
	}
	
	public double readDouble() {
		this.increaseReadIndex( 8 );
		return ByteUtils.readDouble( this.bytes, this.readIndex - 8 );
	}
	
	public boolean readBoolean() {
		return this.readByte() == 0x1;
	}
	
}
