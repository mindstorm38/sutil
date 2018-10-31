package io.sutil;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;
import java.util.function.BiConsumer;

public class StreamUtils {

	/**
	 * The default buffer size used in buffered methods
	 */
	public static final int DEFAULT_BUFFER_SIZE = 1024;
	
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
		
	}
	
	/**
	 * Read all bytes from an InputStream and return it in a byte array<br>
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
	 * @param stream {@link InputStream} where to read bytes
	 * @return The bytes array retrieved from stream
	 * @see #getStreamByteArray(InputStream)
	 */
	public static byte[] getStreamByteArraySafe(InputStream stream) {
		try { return getStreamByteArray( stream ); } catch (IOException e) { return null; }
	}
	
	/**
	 * Convert a stream to a string with a specified charset
	 * @param stream The stream to convert
	 * @param charset The charset used to decode stream bytes
	 * @return The string converted from stream bytes
	 */
	public static String getStreamString(InputStream stream, String charset) {
		try ( Scanner s = new Scanner( stream, charset ) ) {
			s.useDelimiter("\\A");
			return s.hasNext() ? s.next() : "";
		}
	}
	
	/**
	 * Read an input stream through a buffer of <code>buffersize</code>
	 * @param stream The input stream to read
	 * @param consumer See {@link StandardByteConsumer}
	 * @param buffersize The buffer size used to read the buffer
	 * @throws IOException See {@link InputStream#read()}
	 */
	public static void readStreamBuffered(InputStream stream, StandardByteConsumer consumer, int buffersize) throws IOException {
		
		final byte[] buf = new byte[ buffersize ];
		int read;
		
		while ( ( read = stream.read( buf ) ) > 0 ) {
			consumer.update( buf, 0, read );
		}
		
	}
	
	/**
	 * Read an input stream through a buffer of {@value #DEFAULT_BUFFER_SIZE}
	 * @param stream The input stream to read
	 * @param consumer See {@link StandardByteConsumer}
	 * @throws IOException See {@link InputStream#read()}
	 * @see #readStreamBuffered(InputStream, BiConsumer, int)
	 */
	public static void readStreamBuffered(InputStream stream, StandardByteConsumer consumer) throws IOException {
		readStreamBuffered( stream, consumer, DEFAULT_BUFFER_SIZE );
	}
	
}
