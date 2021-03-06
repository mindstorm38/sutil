package io.sutil.buffer;

import java.nio.ByteOrder;
import java.nio.charset.Charset;

import io.sutil.StringUtils;

public abstract class BaseBuffer {
	
	// Constants \\
	
	private static final byte BYTE_ZERO	= 0;
	private static final byte BYTE_ONE	= 1;
	
	// Class \\
	
	protected byte[] bytes;
	protected int writeIndex = 0;
	protected int readIndex = 0;
	
	protected ByteOrder order;
	protected boolean be;
	
	protected Charset charset = StringUtils.CHARSET_UTF_8; 
	
	public BaseBuffer(ByteOrder order, byte[] bytes) {
		
		this.setByteOrder( order );
		
		this.bytes = new byte[ bytes.length ];
		System.arraycopy( bytes, 0, this.bytes, 0, bytes.length );
		
	}
	
	public BaseBuffer(byte[] bytes) {
		this( ByteOrder.nativeOrder(), bytes );
	}
	
	public BaseBuffer(ByteOrder order, int capacity) {
		
		this.setByteOrder( order );
		
		this.bytes = new byte[capacity];
		
	}
	
	public BaseBuffer(int capacity) {
		this( ByteOrder.nativeOrder(), capacity );
	}
	
	public BaseBuffer(ByteOrder order) {
		this( order, 0 );
	}
	
	public BaseBuffer() {
		this( 0 );
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
		this.writeIndex = 0;
		
	}
	
	public FixedBuffer createFixedBuffer() {
		
		byte[] fixedBytes = new byte[ this.bytes.length ];
		System.arraycopy( this.bytes, 0, fixedBytes, 0, this.bytes.length );
		return new FixedBuffer( fixedBytes );
		
	}
	
	protected IndexOutOfBoundsException outOfBoundsException(int idx) {
		return new IndexOutOfBoundsException( "Invalid index : " + idx );
	}
	
	protected void checkIndex(int idx) {
		if ( idx < 0 || idx > this.bytes.length ) throw this.outOfBoundsException( idx );
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
	
	protected void allocateOffset(int offset, int count, boolean check) {
		if ( check ) this.checkIndex( offset );
		int dif = offset + count - this.bytes.length;
		if ( dif > 0 ) this.allocate( dif );
	}
	
	protected void allocateOffset(int offset, boolean check) {
		if ( check ) this.checkIndex( offset );
		int dif = offset + 1 - this.bytes.length;
		if ( dif > 0 ) this.allocate( dif );
	}
	
	// Byte order
	
	public void setByteOrder(ByteOrder order) {
		if ( order == null ) throw new NullPointerException();
		this.order = order;
		this.be = order == ByteOrder.BIG_ENDIAN;
	}
	
	public ByteOrder getByteOrder() {
		return this.order;
	}
	
	// Charset
	
	public void setCharset(Charset charset) {
		if ( charset == null ) throw new NullPointerException();
		this.charset = charset;
	}
	
	public Charset getCharset() {
		return this.charset;
	}
	
	// WRITE \\
	
	public int getWriteIndex() {
		return this.writeIndex;
	}
	
	public BaseBuffer setWriteIndex(int index) {
		this.checkIndex( index );
		this.writeIndex = index;
		return this;
	}
	
	private void increaseWriteIndex(int count) {
		this.writeIndex += count;
		this.checkIndex( this.writeIndex );
	}
	
	// - Bytes array
	
	public BaseBuffer writeBytes(byte[] bytes, int count) {
		this.allocateOffset( this.writeIndex, count, false );
		// int offset = this.allocate( count ); TODO
		System.arraycopy( bytes, 0, this.bytes, this.writeIndex, count );
		this.increaseWriteIndex( count );
		return this;
	}
	
	public BaseBuffer writeBytes(byte[] bytes) {
		return this.writeBytes( bytes, bytes.length );
	}
	
	public BaseBuffer writeBytesOffset(byte[] bytes, int offset, int count) {
		this.allocateOffset( offset, count, true );
		System.arraycopy( bytes, 0, this.bytes, offset, count );
		return this;
	}
	
	public BaseBuffer writeBytesOffset(byte[] bytes, int offset) {
		return this.writeBytesOffset( bytes, offset, bytes.length );
	}
	
	// - Byte
	
	public BaseBuffer writeByte(byte byt) {
		this.allocateOffset( this.writeIndex, false );
		// int offset = this.allocate( 1 ); TODO
		this.bytes[ this.writeIndex ] = byt;
		this.increaseWriteIndex( 1 );
		return this;
	}
	
	public BaseBuffer writeByteOffset(byte byt, int offset) {
		this.allocateOffset( offset, true );
		this.bytes[ offset ] = byt;
		return this;
	}
	
	// - Unsigned byte
	
	public BaseBuffer writeUnsignedByte(short unsignedByte) {
		this.allocateOffset( this.writeIndex, false );
		// int offset = this.allocate( 1 ); TODO
		ByteUtils.writeUnsignedByte( this.bytes, this.writeIndex, unsignedByte );
		this.increaseWriteIndex( 1 );
		return this;
	}
	
	public BaseBuffer writeUnsignedByteOffset(short unsignedByte, int offset) {
		this.allocateOffset( offset, true );
		ByteUtils.writeUnsignedByte( this.bytes, offset, unsignedByte );
		return this;
	}
	
	// - Short
	
	public BaseBuffer writeShort(short shrt) {
		this.allocateOffset( this.writeIndex, 2, false );
		// int offset = this.allocate( 2 ); TODO
		ByteUtils.writeShort( this.bytes, this.writeIndex, shrt, this.be );
		this.increaseWriteIndex( 2 );
		return this;
	}
	
	public BaseBuffer writeShortOffset(short shrt, int offset) {
		this.allocateOffset( offset, 2, true );
		ByteUtils.writeShort( this.bytes, offset, shrt, this.be );
		return this;
	}
	
	// - Unsigned Short
	
	public BaseBuffer writeUnsignedShort(int unsignedShort) {
		this.allocateOffset( this.writeIndex, 2, false );
		// int offset = this.allocate( 2 ); TODO
		ByteUtils.writeUnsignedShort( this.bytes, this.writeIndex, unsignedShort, this.be );
		this.increaseWriteIndex( 2 );
		return this;
	}
	
	public BaseBuffer writeUnsignedShortOffset(int unsignedShort, int offset) {
		this.allocateOffset( offset, 2, true );
		ByteUtils.writeUnsignedShort( this.bytes, offset, unsignedShort, this.be );
		return this;
	}
	
	// - Integer
	
	public BaseBuffer writeInteger(int integer) {
		this.allocateOffset( this.writeIndex, 4, false );
		// int offset = this.allocate( 4 ); TODO
		ByteUtils.writeInteger( this.bytes, this.writeIndex, integer, this.be );
		this.increaseWriteIndex( 4 );
		return this;
	}
	
	public BaseBuffer writeIntegerOffset(int integer, int offset) {
		this.allocateOffset( offset, 4, true );
		ByteUtils.writeInteger( this.bytes, offset, integer, this.be );
		return this;
	}
	
	// - Unsigned Integer
	
	public BaseBuffer writeUnsignedInteger(long unsignedInteger) {
		this.allocateOffset( this.writeIndex, 4, false );
		// int offset = this.allocate( 4 ); TODO
		ByteUtils.writeUnsignedInteger( this.bytes, this.writeIndex, unsignedInteger, this.be );
		this.increaseWriteIndex( 4 );
		return this;
	}
	
	public BaseBuffer writeUnsignedIntegerOffset(long unsignedInteger, int offset) {
		this.allocateOffset( offset, 4, true );
		ByteUtils.writeUnsignedInteger( this.bytes, offset, unsignedInteger, this.be );
		return this;
	}
	
	// - Long
	
	public BaseBuffer writeLong(long lng) {
		this.allocateOffset( this.writeIndex, 8, false );
		// int offset = this.allocate( 8 ); TODO
		ByteUtils.writeLong( this.bytes, this.writeIndex, lng, this.be );
		this.increaseWriteIndex( 8 );
		return this;
	}
	
	public BaseBuffer writeLongOffset(long lng, int offset) {
		this.allocateOffset( offset, 8, true );
		ByteUtils.writeLong( this.bytes, offset, lng, this.be );
		return this;
	}
	
	// - Float
	
	public BaseBuffer writeFloat(float flt) {
		this.allocateOffset( this.writeIndex, 4, false );
		// int offset = this.allocate( 4 ); TODO
		ByteUtils.writeFloat( this.bytes, this.writeIndex, flt, this.be );
		this.increaseWriteIndex( 4 );
		return this;
	}
	
	public BaseBuffer writeFloatOffset(float flt, int offset) {
		this.allocateOffset( offset, 4, true );
		ByteUtils.writeFloat( this.bytes, offset, flt, this.be );
		return this;
	}
	
	// - Double
	
	public BaseBuffer writeDouble(double dbl) {
		this.allocateOffset( this.writeIndex, 8, false );
		// int offset = this.allocate( 8 ); TODO
		ByteUtils.writeDouble( this.bytes, this.writeIndex, dbl, this.be );
		this.increaseWriteIndex( 8 );
		return this;
	}
	
	public BaseBuffer writeDoubleOffset(double dbl, int offset) {
		this.allocateOffset( offset, 8, true );
		ByteUtils.writeDouble( this.bytes, offset, dbl, this.be );
		return this;
	}
	
	// - Other
	
	public BaseBuffer writeBoolean(boolean bool) {
		return this.writeByte( bool ? BYTE_ONE : BYTE_ZERO );
	}
	
	public BaseBuffer writeBooleanOffset(boolean bool, int offset) {
		return this.writeByteOffset( bool ? BYTE_ONE : BYTE_ZERO, offset );
	}
	
	public BaseBuffer writeBuffer(BaseBuffer buffer, int count) {
		return this.writeBytes( buffer.bytes, count );
	}
	
	public BaseBuffer writeBuffer(BaseBuffer buffer) {
		return this.writeBytes( buffer.bytes );
	}
	
	public BaseBuffer writeString(String string) {
		return this.writeBytes( string.getBytes( this.charset ) );
	}
	
	public BaseBuffer writeStringOffset(String string, int offset) {
		return this.writeBytesOffset( string.getBytes( this.charset ), offset );
	}
	
	public BaseBuffer writeStringNullTerminated(String string) {
		this.writeBytes( string.getBytes( this.charset ) );
		return this.writeByte( BYTE_ZERO );
	}
	
	public BaseBuffer writeStringNullTerminatedOffset(String string, int offset) {
		byte[] bytes = string.getBytes( this.charset );
		this.writeBytesOffset( bytes, offset );
		return this.writeByteOffset( BYTE_ZERO, offset + bytes.length );
	}
	
	public BaseBuffer writeStringIndexed(String string) {
		byte[] bytes = string.getBytes( this.charset );
		this.writeInteger( bytes.length );
		return this.writeBytes( bytes );
	}
	
	public BaseBuffer writeStringIndexedOffset(String string, int offset) {
		byte[] bytes = string.getBytes( this.charset );
		this.writeIntegerOffset( bytes.length, offset );
		return this.writeBytesOffset( bytes, offset + Integer.BYTES );
	}
	
	public BaseBuffer writeStringZeroFilled(String string, int length) {
		byte[] bytes = StringUtils.getStringBytesZeroFilled( string, this.charset, length );
		return this.writeBytes( bytes );
	}
	
	public BaseBuffer writeStringZeroFilled(String string, int offset, int length) {
		byte[] bytes = StringUtils.getStringBytesZeroFilled( string, this.charset, length );
		return this.writeBytesOffset( bytes, offset );
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
	
	public int getByteOffset(int startOff, byte b) {
		for ( int i = startOff; i < this.bytes.length; i++ )
			if ( this.bytes[ b ] == b )
				return i;
		return -1;
	}
	
	public int getNextByteOffset(byte b) {
		return this.getByteOffset( this.readIndex, b );
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
		return ByteUtils.readShort( this.bytes, this.readIndex - 2, this.be );
	}
	
	public short readShortOffset(int offset) {
		this.checkIndex( offset, 2 );
		return ByteUtils.readShort( this.bytes, offset, this.be );
	}
	
	// - Unsigned Short
	
	public int readUnsignedShort() {
		this.increaseReadIndex( 2 );
		return ByteUtils.readUnsignedShort( this.bytes, this.readIndex - 2, this.be );
	}
	
	public int readUnsignedShortOffset(int offset) {
		this.checkIndex( offset, 2 );
		return ByteUtils.readUnsignedShort( this.bytes, offset, this.be );
	}
	
	// - Integer
	
	public int readInteger() {
		this.increaseReadIndex( 4 );
		return ByteUtils.readInteger( this.bytes, this.readIndex - 4, this.be);
	}
	
	public int readIntegerOffset(int offset) {
		this.checkIndex( offset, 4 );
		return ByteUtils.readInteger( this.bytes, offset, this.be );
	}
	
	// - Unsigned Integer
	
	public long readUnsignedInteger() {
		this.increaseReadIndex( 4 );
		return ByteUtils.readUnsignedInteger( this.bytes, this.readIndex - 4, this.be );
	}
	
	public long readUnsignedIntegerOffset(int offset) {
		this.checkIndex( offset, 4 );
		return ByteUtils.readUnsignedInteger( this.bytes, offset, this.be );
	}

	// - Long
	
	public long readLong() {
		this.increaseReadIndex( 8 );
		return ByteUtils.readLong( this.bytes, this.readIndex - 8, this.be );
	}
	
	public long readLongOffset(int offset) {
		this.checkIndex( offset, 8 );
		return ByteUtils.readLong( this.bytes, offset, this.be );
	}
	
	// - Float
	
	public float readFloat() {
		this.increaseReadIndex( 4 );
		return ByteUtils.readFloat( this.bytes, this.readIndex - 4, this.be );
	}
	
	public float readFloatOffset(int offset) {
		this.checkIndex( offset, 4 );
		return ByteUtils.readFloat( this.bytes, offset, this.be );
	}
	
	// - Double
	
	public double readDouble() {
		this.increaseReadIndex( 8 );
		return ByteUtils.readDouble( this.bytes, this.readIndex - 8, this.be );
	}
	
	public double readDoubleOffset(int offset) {
		this.checkIndex( offset, 8 );
		return ByteUtils.readDouble( this.bytes, offset, this.be );
	}
	
	// - Other
	
	public boolean readBoolean() {
		return this.readByte() == 0x1;
	}
	
	public boolean readBooleanOffset(int offset) {
		return this.readByteOffset( offset ) == 0x1;
	}
	
	public String readString(int length) {
		byte[] bytes = this.readBytes( length );
		return new String( bytes, this.charset );
	}
	
	public String readStringOffset(int offset, int length) {
		byte[] bytes = this.readBytesOffset( offset, length );
		return new String( bytes, this.charset );
	}
	
	public String readStringNullTerminated() {
		int zeroByteOff = this.getNextByteOffset( BYTE_ZERO );
		return this.readString( zeroByteOff - this.readIndex ); // TODO Check that
	}
	
	public String readStringNullTerminatedOffset(int offset) {
		int zeroByteOff = this.getByteOffset( offset, BYTE_ZERO );
		return this.readString( zeroByteOff - offset );
	}
	
	public String readStringIndexed() {
		int length = this.readInteger();
		byte[] bytes = this.readBytes( length );
		return new String( bytes, this.charset );
	}
	
	public String readStringIndexedOffset(int offset) {
		int length = this.readIntegerOffset( offset );
		byte[] bytes = this.readBytesOffset( offset + Integer.BYTES, length );
		return new String( bytes, this.charset );
	}
	
}
