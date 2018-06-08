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
	
	public void reset() {
		
		this.bytes = new byte[0];
		this.readIndex = 0;
		
	}
	
	protected void checkIndex(int idx) {
		if ( idx < 0 || idx >= this.bytes.length ) throw new IndexOutOfBoundsException( "Invalid index : " + idx );
	}
	
	protected void checkIndex(int offset, int count) {
		this.checkIndex( offset );
		this.checkIndex( offset + count );
	}
	
	/**
	 * Allocate a number of bytes on the top of the buffer
	 * @param count Number of bytes to allocate
	 * @return Byte offset
	 */
	protected abstract int allocate(int count);
	
	protected void allocateOffset(int offset, int count) {
		this.checkIndex( offset );
		int dif = offset + count - this.bytes.length;
		if ( dif > 0 ) this.allocate( dif );
	}
	
	protected void allocateOffset(int offset) {
		this.checkIndex( offset );
		int dif = offset - this.bytes.length;
		if ( dif > 0 ) this.allocate( dif );
	}
	
	// WRITE \\
	
	// - Bytes array
	
	public BaseBuffer writeBytes(byte[] bytes, int count) {
		int offset = this.allocate( count );
		System.arraycopy( bytes, 0, this.bytes, offset, count );
		return this;
	}
	
	public BaseBuffer writeBytes(byte[] bytes) {
		return this.writeBytes( bytes, bytes.length );
	}
	
	public BaseBuffer writeBytesOffset(byte[] bytes, int offset, int count) {
		this.allocateOffset( offset, count );
		System.arraycopy( bytes, 0, this.bytes, offset, count );
		return this;
	}
	
	public BaseBuffer writeBytesOffset(byte[] bytes, int offset) {
		return this.writeBytesOffset( bytes, offset, bytes.length );
	}
	
	// - Byte
	
	public BaseBuffer writeByte(byte byt) {
		int offset = this.allocate( 1 );
		this.bytes[ offset ] = byt;
		return this;
	}
	
	public BaseBuffer writeByteOffset(byte byt, int offset) {
		this.allocateOffset( offset );
		this.bytes[ offset ] = byt;
		return this;
	}
	
	// - Unsigned byte
	
	public BaseBuffer writeUnsignedByte(short unsignedByte) {
		int offset = this.allocate( 1 );
		ByteUtils.writeUnsignedByte( this.bytes, offset, unsignedByte );
		return this;
	}
	
	public BaseBuffer writeUnsignedByteOffset(short unsignedByte, int offset) {
		this.allocateOffset( offset );
		ByteUtils.writeUnsignedByte( this.bytes, offset, unsignedByte );
		return this;
	}
	
	// - Short
	
	public BaseBuffer writeShort(short shrt) {
		int offset = this.allocate( 2 );
		ByteUtils.writeShort( this.bytes, offset, shrt );
		return this;
	}
	
	public BaseBuffer writeShortOffset(short shrt, int offset) {
		this.allocateOffset( offset, 2 );
		ByteUtils.writeShort( this.bytes, offset, shrt );
		return this;
	}
	
	// - Unsigned Short
	
	public BaseBuffer writeUnsignedShort(int unsignedShort) {
		int offset = this.allocate( 2 );
		ByteUtils.writeUnsignedShort( this.bytes, offset, unsignedShort );
		return this;
	}
	
	public BaseBuffer writeUnsignedShortOffset(int unsignedShort, int offset) {
		this.allocateOffset( offset, 2 );
		ByteUtils.writeUnsignedShort( this.bytes, offset, unsignedShort );
		return this;
	}
	
	// - Integer
	
	public BaseBuffer writeInteger(int integer) {
		int offset = this.allocate( 4 );
		ByteUtils.writeInteger( this.bytes, offset, integer );
		return this;
	}
	
	public BaseBuffer writeIntegerOffset(int integer, int offset) {
		this.allocateOffset( offset, 4 );
		ByteUtils.writeInteger( this.bytes, offset, integer );
		return this;
	}
	
	// - Unsigned Integer
	
	public BaseBuffer writeUnsignedInteger(long unsignedInteger) {
		int offset = this.allocate( 4 );
		ByteUtils.writeUnsignedInteger( this.bytes, offset, unsignedInteger );
		return this;
	}
	
	public BaseBuffer writeUnsignedIntegerOffset(long unsignedInteger, int offset) {
		this.allocateOffset( offset, 4 );
		ByteUtils.writeUnsignedInteger( this.bytes, offset, unsignedInteger );
		return this;
	}
	
	// - Long
	
	public BaseBuffer writeLong(long lng) {
		int offset = this.allocate( 8 );
		ByteUtils.writeLong( this.bytes, offset, lng );
		return this;
	}
	
	public BaseBuffer writeLongOffset(long lng, int offset) {
		this.allocateOffset( offset, 8 );
		ByteUtils.writeLong( this.bytes, offset, lng );
		return this;
	}
	
	// - Float
	
	public BaseBuffer writeFloat(float flt) {
		int offset = this.allocate( 4 );
		ByteUtils.writeFloat( this.bytes, offset, flt );
		return this;
	}
	
	public BaseBuffer writeFloatOffset(float flt, int offset) {
		this.allocateOffset( offset, 4 );
		ByteUtils.writeFloat( this.bytes, offset, flt );
		return this;
	}
	
	// - Double
	
	public BaseBuffer writeDouble(double dbl) {
		int offset = this.allocate( 8 );
		ByteUtils.writeDouble( this.bytes, offset, dbl );
		return this;
	}
	
	public BaseBuffer writeDoubleOffset(double dbl, int offset) {
		this.allocateOffset( offset, 8 );
		ByteUtils.writeDouble( this.bytes, offset, dbl );
		return this;
	}
	
	// - Other
	
	public BaseBuffer writeBoolean(boolean bool) {
		return this.writeByte( (byte) ( bool ? 0x0 : 0x1 ) );
	}
	
	public BaseBuffer writeBuffer(BaseBuffer buffer, int count) {
		return this.writeBytes( buffer.bytes, count );
	}
	
	public BaseBuffer writeBuffer(BaseBuffer buffer) {
		return this.writeBytes( buffer.bytes );
	}
	
	// READ \\
	
	public int getReadIndex() {
		return this.readIndex;
	}
	
	public int remaining() {
		return this.bytes.length - this.readIndex;
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
	
	// - Bytes array
	
	public byte[] readBytes(int count) {
		byte[] b = new byte[ count ];
		System.arraycopy( this.bytes, this.readIndex, b, 0, count );
		this.increaseReadIndex( count );
		return b;
	}
	
	public byte[] readBytesOffset(int offset, int count) {
		this.checkIndex( offset, count );
		byte[] b = new byte[ count ];
		System.arraycopy( this.bytes, offset, b, 0, count );
		return b;
	}
	
	// - Byte
	
	public byte readByte() {
		this.increaseReadIndex( 1 );
		return this.bytes[ this.readIndex - 1 ];
	}
	
	public byte readByteOffset(int offset) {
		this.checkIndex( offset );
		return this.bytes[ offset ];
	}
	
	// - Unsigned Byte
	
	public short readUnsignedByte() {
		this.increaseReadIndex( 1 );
		return ByteUtils.readUnsignedByte( this.bytes, this.readIndex - 1 );
	}
	
	public short readUnsignedByteOffset(int offset) {
		this.checkIndex( offset );
		return ByteUtils.readUnsignedByte( this.bytes, offset );
	}
	
	// - Short
	
	public short readShort() {
		this.increaseReadIndex( 2 );
		return ByteUtils.readShort( this.bytes, this.readIndex - 2 );
	}
	
	public short readShortOffset(int offset) {
		this.checkIndex( offset, 2 );
		return ByteUtils.readShort( this.bytes, offset );
	}
	
	// - Unsigned Short
	
	public int readUnsignedShort() {
		this.increaseReadIndex( 2 );
		return ByteUtils.readUnsignedShort( this.bytes, this.readIndex - 2 );
	}
	
	public int readUnsignedShortOffset(int offset) {
		this.checkIndex( offset, 2 );
		return ByteUtils.readUnsignedShort( this.bytes, offset );
	}
	
	// - Integer
	
	public int readInteger() {
		this.increaseReadIndex( 4 );
		return ByteUtils.readInteger( this.bytes, this.readIndex - 4 );
	}
	
	public int readIntegerOffset(int offset) {
		this.checkIndex( offset, 4 );
		return ByteUtils.readInteger( this.bytes, offset );
	}
	
	// - Unsigned Integer
	
	public long readUnsignedInteger() {
		this.increaseReadIndex( 4 );
		return ByteUtils.readUnsignedInteger( this.bytes, this.readIndex - 4 );
	}
	
	public long readUnsignedIntegerOffset(int offset) {
		this.checkIndex( offset, 4 );
		return ByteUtils.readUnsignedInteger( this.bytes, offset );
	}

	// - Long
	
	public long readLong() {
		this.increaseReadIndex( 8 );
		return ByteUtils.readLong( this.bytes, this.readIndex - 8 );
	}
	
	public long readLongOffset(int offset) {
		this.checkIndex( offset, 8 );
		return ByteUtils.readLong( this.bytes, offset );
	}
	
	// - Float
	
	public float readFloat() {
		this.increaseReadIndex( 4 );
		return ByteUtils.readFloat( this.bytes, this.readIndex - 4 );
	}
	
	public float readFloatOffset(int offset) {
		this.checkIndex( offset, 4 );
		return ByteUtils.readFloat( this.bytes, offset );
	}
	
	// - Double
	
	public double readDouble() {
		this.increaseReadIndex( 8 );
		return ByteUtils.readDouble( this.bytes, this.readIndex - 8 );
	}
	
	public double readDoubleOffset(int offset) {
		this.checkIndex( offset, 8 );
		return ByteUtils.readDouble( this.bytes, offset );
	}
	
	// - Other
	
	public boolean readBoolean() {
		return this.readByte() == 0x1;
	}
	
}
