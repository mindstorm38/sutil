package io.sutil;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamUtils {

	/**
	 * Safe close a stream, or return <code>null</code> if {@link Closeable#close()} throws an exception
	 * @param closeable Closeable
	 */
	public static void safeclose(Closeable closeable) {
		if ( closeable == null ) return;
		try { closeable.close(); } catch (IOException e) {}
	}
	
	/**
	 * Same as {@link #safeclose(Closeable)} but for {@link AutoCloseable}
	 * @param closeable Closeable
	 */
	public static void safeclose(AutoCloseable closeable) {
		if ( closeable == null ) return;
		try { closeable.close(); } catch (Exception e) {}
	}
	
	/**
	 * Copy an InputStream content to an OutputStream<br>
	 * This function close the stream at the end of reading if no exception thrown
	 * @param in The input stream
	 * @param out The output stream
	 * @throws IOException See {@link InputStream#read()} and {@link OutputStream#write(byte[], int, int)}
	 */
	public static void copyStream(InputStream in, OutputStream out) throws IOException {
		
		final byte[] buf = new byte[ 1024 ];
		int read;
		
		while ( ( read = in.read( buf ) ) > 0 ) {
			out.write( buf, 0, read );
		}
		
		safeclose( in );
		
	}
	
	/**
	 * Read all bytes from an InputStream and return it in a byte array<br>
	 * This function close the stream at the end of reading if no exception thrown
	 * @param stream The input stream
	 * @return The bytes array retrieved from stream
	 * @throws IOException See {@link #copyStream(InputStream, OutputStream)}
	 * @see #copyStream(InputStream, OutputStream)
	 */
	public static byte[] getStreamByteArray(InputStream stream) throws IOException {
		
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		copyStream( stream, bytes );
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
