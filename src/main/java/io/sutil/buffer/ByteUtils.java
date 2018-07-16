package io.sutil.buffer;

public class ByteUtils {

	// Write \\
	
	public static void writeUnsignedByte(byte[] array, int offset, short unsignedByte) {
		array[ offset ] = (byte) ( unsignedByte & 0xFF );
	}
	
	public static void writeShort(byte[] array, int offset, short shrt, boolean be) {
		if ( be ) writeShortBE( array, offset, shrt );
		else writeShortLE( array, offset, shrt );
	}
	
	public static void writeUnsignedShort(byte[] array, int offset, int unsignedShort, boolean be) {
		if ( be ) writeUnsignedShortBE( array, offset, unsignedShort );
		else writeUnsignedShortLE( array, offset, unsignedShort );
	}
	
	public static void writeInteger(byte[] array, int offset, int integer, boolean be) {
		if ( be ) writeIntegerBE( array, offset, integer );
		else writeIntegerLE( array, offset, integer );
	}
	
	public static void writeUnsignedInteger(byte[] array, int offset, long unsignedInteger, boolean be) {
		if ( be ) writeUnsignedIntegerBE( array, offset, unsignedInteger );
		else writeUnsignedIntegerLE( array, offset, unsignedInteger );
	}
	
	public static void writeLong(byte[] array, int offset, long lng, boolean be) {
		if ( be ) writeLongBE( array, offset, lng );
		else writeLongLE( array, offset, lng );
	}
	
	public static void writeFloat(byte[] array, int offset, float flt, boolean be) {
		if ( be ) writeFloatBE( array, offset, flt );
		else writeFloatLE( array, offset, flt );
	}
	
	public static void writeDouble(byte[] array, int offset, double dbl, boolean be) {
		if ( be ) writeDoubleBE( array, offset, dbl );
		else writeDoubleLE( array, offset, dbl );
	}
	
	// - Big Endian
	
	public static void writeShortBE(byte[] array, int offset, short shrt) {
		array[ offset + 0 ] = (byte) ( shrt >> 8 );
		array[ offset + 1 ] = (byte) ( shrt >> 0 );
	}
	
	public static void writeUnsignedShortBE(byte[] array, int offset, int unsignedShort) {
		array[ offset + 0 ] = (byte) ( unsignedShort >> 8 );
		array[ offset + 1 ] = (byte) ( unsignedShort >> 0 );
	}
	
	public static void writeIntegerBE(byte[] array, int offset, int integer) {
		array[ offset + 0 ] = (byte) ( integer >> 24 );
		array[ offset + 1 ] = (byte) ( integer >> 16 );
		array[ offset + 2 ] = (byte) ( integer >> 8 );
		array[ offset + 3 ] = (byte) ( integer >> 0 );
	}
	
	public static void writeUnsignedIntegerBE(byte[] array, int offset, long unsignedInteger) {
		array[ offset + 0 ] = (byte) ( unsignedInteger >> 24 );
		array[ offset + 1 ] = (byte) ( unsignedInteger >> 16 );
		array[ offset + 2 ] = (byte) ( unsignedInteger >> 8 );
		array[ offset + 3 ] = (byte) ( unsignedInteger >> 0 );
	}
	
	public static void writeLongBE(byte[] array, int offset, long lng) {
		array[ offset + 0 ] = (byte) ( lng >> 56 );
		array[ offset + 1 ] = (byte) ( lng >> 48  );
		array[ offset + 2 ] = (byte) ( lng >> 40 );
		array[ offset + 3 ] = (byte) ( lng >> 32 );
		array[ offset + 4 ] = (byte) ( lng >> 24 );
		array[ offset + 5 ] = (byte) ( lng >> 16 );
		array[ offset + 6 ] = (byte) ( lng >> 8 );
		array[ offset + 7 ] = (byte) ( lng >> 0 );
	}
	
	public static void writeFloatBE(byte[] array, int offset, float flt) {
		writeIntegerBE( array, offset, Float.floatToRawIntBits( flt ) );
	}
	
	public static void writeDoubleBE(byte[] array, int offset, double dbl) {
		writeLongBE( array, offset, Double.doubleToRawLongBits( dbl ) );
	}
	
	// - Little Endian
	
	public static void writeShortLE(byte[] array, int offset, short shrt) {
		array[ offset + 1 ] = (byte) ( shrt >> 8 );
		array[ offset + 0 ] = (byte) ( shrt >> 0 );
	}
	
	public static void writeUnsignedShortLE(byte[] array, int offset, int unsignedShort) {
		array[ offset + 1 ] = (byte) ( unsignedShort >> 8 );
		array[ offset + 0 ] = (byte) ( unsignedShort >> 0 );
	}
	
	public static void writeIntegerLE(byte[] array, int offset, int integer) {
		array[ offset + 3 ] = (byte) ( integer >> 24 );
		array[ offset + 2 ] = (byte) ( integer >> 16 );
		array[ offset + 1 ] = (byte) ( integer >> 8 );
		array[ offset + 0 ] = (byte) ( integer >> 0 );
	}
	
	public static void writeUnsignedIntegerLE(byte[] array, int offset, long unsignedInteger) {
		array[ offset + 3 ] = (byte) ( unsignedInteger >> 24 );
		array[ offset + 2 ] = (byte) ( unsignedInteger >> 16 );
		array[ offset + 1 ] = (byte) ( unsignedInteger >> 8 );
		array[ offset + 0 ] = (byte) ( unsignedInteger >> 0 );
	}
	
	public static void writeLongLE(byte[] array, int offset, long lng) {
		array[ offset + 7 ] = (byte) ( lng >> 56 );
		array[ offset + 6 ] = (byte) ( lng >> 48  );
		array[ offset + 5 ] = (byte) ( lng >> 40 );
		array[ offset + 4 ] = (byte) ( lng >> 32 );
		array[ offset + 3 ] = (byte) ( lng >> 24 );
		array[ offset + 2 ] = (byte) ( lng >> 16 );
		array[ offset + 1 ] = (byte) ( lng >> 8 );
		array[ offset + 0 ] = (byte) ( lng >> 0 );
	}
	
	public static void writeFloatLE(byte[] array, int offset, float flt) {
		writeIntegerLE( array, offset, Float.floatToRawIntBits( flt ) );
	}
	
	public static void writeDoubleLE(byte[] array, int offset, double dbl) {
		writeLongLE( array, offset, Double.doubleToRawLongBits( dbl ) );
	}
	
	// Read \\
	
	public static short readUnsignedByte(byte[] array, int offset) {
		return (short) array[ offset ];
	}
	
	public static short readShort(byte[] array, int offset, boolean be) {
		return be ? readShortBE( array, offset ) : readShortLE( array, offset );
	}
	
	public static int readUnsignedShort(byte[] array, int offset, boolean be) {
		return be ? readUnsignedShortBE( array, offset ) : readUnsignedShortLE( array, offset );
	}
	
	public static int readInteger(byte[] array, int offset, boolean be) {
		return be ? readIntegerBE( array, offset ) : readIntegerLE( array, offset );
	}
	
	public static long readUnsignedInteger(byte[] array, int offset, boolean be) {
		return be ? readUnsignedIntegerBE( array, offset ) : readUnsignedIntegerLE( array, offset );
	}
	
	public static long readLong(byte[] array, int offset, boolean be) {
		return be ? readLongBE( array, offset ) : readLongLE( array, offset );
	}
	
	public static float readFloat(byte[] array, int offset, boolean be) {
		return Float.intBitsToFloat( be ? readIntegerBE( array, offset ) : readIntegerLE( array, offset ) );
	}
	
	public static double readDouble(byte[] array, int offset, boolean be) {
		return Double.longBitsToDouble( be ? readLongBE( array, offset ) : readLongLE( array, offset ) );
	}
	
	// - Big Endian
	
	public static short readShortBE(byte[] array, int offset) {
		return (short) (
				( array[ offset + 0 ] & 0xFF ) << 8 |
				( array[ offset + 1 ] & 0xFF )
		);
	}
	
	public static int readUnsignedShortBE(byte[] array, int offset) {
		return (
				( array[ offset + 0 ] & 0xFF ) << 8 |
				( array[ offset + 1 ] & 0xFF )
		);
	}
	
	public static int readIntegerBE(byte[] array, int offset) {
		return (
				( array[ offset + 0 ] & 0xFF ) << 24 |
				( array[ offset + 1 ] & 0xFF ) << 16 |
				( array[ offset + 2 ] & 0xFF ) << 8 |
				( array[ offset + 3 ] & 0xFF )
		);
	}
	
	public static long readUnsignedIntegerBE(byte[] array, int offset) {
		return (long) (
				( (long) array[ offset + 0 ] & 0xFF ) << 24 |
				( (long) array[ offset + 1 ] & 0xFF ) << 16 |
				( (long) array[ offset + 2 ] & 0xFF ) << 8 |
				( (long) array[ offset + 3 ] & 0xFF )
		);
	}
	
	public static long readLongBE(byte[] array, int offset) {
		return (long) (
				( (long) array[ offset + 0 ] & 0xFF ) << 56 |
				( (long) array[ offset + 1 ] & 0xFF ) << 48 |
				( (long) array[ offset + 2 ] & 0xFF ) << 40 |
				( (long) array[ offset + 3 ] & 0xFF ) << 32 |
				( (long) array[ offset + 4 ] & 0xFF ) << 24 |
				( (long) array[ offset + 5 ] & 0xFF ) << 16 |
				( (long) array[ offset + 6 ] & 0xFF ) << 8 |
				( (long) array[ offset + 7 ] & 0xFF )
		);
	}
	
	public static float readFloatBE(byte[] array, int offset) {
		return Float.intBitsToFloat( readIntegerBE( array, offset ) );
	}
	
	public static double readDoubleBE(byte[] array, int offset) {
		return Double.longBitsToDouble( readLongBE( array, offset ) );
	}
	
	// - Little Endian
	
	public static short readShortLE(byte[] array, int offset) {
		return (short) (
				( array[ offset + 1 ] & 0xFF ) << 8 |
				( array[ offset + 0 ] & 0xFF )
		);
	}
	
	public static int readUnsignedShortLE(byte[] array, int offset) {
		return (
				( array[ offset + 1 ] & 0xFF ) << 8 |
				( array[ offset + 0 ] & 0xFF )
		);
	}
	
	public static int readIntegerLE(byte[] array, int offset) {
		return (
				( array[ offset + 3 ] & 0xFF ) << 24 |
				( array[ offset + 2 ] & 0xFF ) << 16 |
				( array[ offset + 1 ] & 0xFF ) << 8 |
				( array[ offset + 0 ] & 0xFF )
		);
	}
	
	public static long readUnsignedIntegerLE(byte[] array, int offset) {
		return (long) (
				( (long) array[ offset + 3 ] & 0xFF ) << 24 |
				( (long) array[ offset + 2 ] & 0xFF ) << 16 |
				( (long) array[ offset + 1 ] & 0xFF ) << 8 |
				( (long) array[ offset + 0 ] & 0xFF )
		);
	}
	
	public static long readLongLE(byte[] array, int offset) {
		return (long) (
				( (long) array[ offset + 7 ] & 0xFF ) << 56 |
				( (long) array[ offset + 6 ] & 0xFF ) << 48 |
				( (long) array[ offset + 5 ] & 0xFF ) << 40 |
				( (long) array[ offset + 4 ] & 0xFF ) << 32 |
				( (long) array[ offset + 3 ] & 0xFF ) << 24 |
				( (long) array[ offset + 2 ] & 0xFF ) << 16 |
				( (long) array[ offset + 1 ] & 0xFF ) << 8 |
				( (long) array[ offset + 0 ] & 0xFF )
		);
	}
	
	public static float readFloatLE(byte[] array, int offset) {
		return Float.intBitsToFloat( readIntegerLE( array, offset ) );
	}
	
	public static double readDoubleLE(byte[] array, int offset) {
		return Double.longBitsToDouble( readLongLE( array, offset ) );
	}
	
}
