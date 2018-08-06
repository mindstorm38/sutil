package io.sutil;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public class StreamUtils {

	public static void safeclose(Closeable closeable) {
		if ( closeable == null ) return;
		try { closeable.close(); } catch (IOException e) {}
	}
	
	public static void safeclose(AutoCloseable closeable) {
		if ( closeable == null ) return;
		try { closeable.close(); } catch (Exception e) {}
	}
	
	/**
	 * Read all bytes from an InputStream and return it in a byte array<br>
	 * This function close the stream at the end of reading if no exception throws
	 * @param stream {@link InputStream} where to read bytes
	 * @return The bytes array retrieved from stream
	 * @throws IOException See {@link InputStream#read(byte[])}
	 */
	public static byte[] getStreamByteArray(InputStream stream) throws IOException {
		
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		
		final byte[] buf = new byte[ 1024 ];
		int read;
		
		while ( ( read = stream.read( buf ) ) > 0 ) {
			bytes.write( buf, 0, read );
		}
		
		safeclose( stream );
		
		return bytes.toByteArray();
		
	}
	
	/**
	 * Same as {@link #getStreamByteArray(InputStream)} but return null instead of throwing {@link IOException}<br>
	 * This function close the stream at the end of reading if no exception throws
	 * @param stream {@link InputStream} where to read bytes
	 * @return The bytes array retrieved from stream
	 * @see #getStreamByteArray(InputStream)
	 */
	public static byte[] getStreamByteArraySafe(InputStream stream) {
		try { return getStreamByteArray( stream ); } catch (IOException e) { return null; }
	}
	
}
