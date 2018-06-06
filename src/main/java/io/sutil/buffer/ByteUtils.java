package io.sutil.buffer;

public class ByteUtils {

	// Write \\
	
	public static void writeShort(byte[] array, int offset, short shrt) {
		array[ offset + 0 ] = (byte) ( shrt >> 8 );
		array[ offset + 1 ] = (byte) ( shrt >> 0 );
	}
	
	public static void writeInteger(byte[] array, int offset, int integer) {
		array[ offset + 0 ] = (byte) ( integer >> 24 );
		array[ offset + 1 ] = (byte) ( integer >> 16 );
		array[ offset + 2 ] = (byte) ( integer >> 8 );
		array[ offset + 3 ] = (byte) ( integer >> 0 );
	}
	
	public static void writeLong(byte[] array, int offset, long lng) {
		array[ offset + 0 ] = (byte) ( lng >> 56 );
		array[ offset + 1 ] = (byte) ( lng >> 48  );
		array[ offset + 2 ] = (byte) ( lng >> 40 );
		array[ offset + 3 ] = (byte) ( lng >> 32 );
		array[ offset + 4 ] = (byte) ( lng >> 24 );
		array[ offset + 5 ] = (byte) ( lng >> 16 );
		array[ offset + 6 ] = (byte) ( lng >> 8 );
		array[ offset + 7 ] = (byte) ( lng >> 0 );
	}
	
	public static void writeFloat(byte[] array, int offset, float flt) {
		writeInteger( array, offset, Float.floatToRawIntBits( flt ) );
	}
	
	public static void writeDouble(byte[] array, int offset, double dbl) {
		writeLong( array, offset, Double.doubleToRawLongBits( dbl ) );
	}
	
	// Read \\
	
	public static short readShort(byte[] array, int offset) {
		return (short) (
				( array[ offset + 0 ] ) << 8 |
				( array[ offset + 1 ] & 0xFF )
		);
	}
	
	public static int readInteger(byte[] array, int offset) {
		return (
				( array[ offset + 0 ] ) << 24 |
				( array[ offset + 1 ] & 0xFF ) << 16 |
				( array[ offset + 2 ] & 0xFF ) << 8 |
				( array[ offset + 3 ] & 0xFF )
		);
	}
	
	public static long readLong(byte[] array, int offset) {
		return (long) (
				( (long) array[ offset + 0 ] ) << 56 |
				( (long) array[ offset + 1 ] & 0xFF ) << 48 |
				( (long) array[ offset + 2 ] & 0xFF ) << 40 |
				( (long) array[ offset + 3 ] & 0xFF ) << 32 |
				( (long) array[ offset + 4 ] & 0xFF ) << 24 |
				( (long) array[ offset + 5 ] & 0xFF ) << 16 |
				( (long) array[ offset + 6 ] & 0xFF ) << 8 |
				( (long) array[ offset + 7 ] & 0xFF )
		);
	}
	
	public static float readFloat(byte[] array, int offset) {
		return Float.intBitsToFloat( readInteger( array, offset ) );
	}
	
	public static double readDouble(byte[] array, int offset) {
		return Double.longBitsToDouble( readLong( array, offset ) );
	}
	
}
