package io.sutil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.CRC32;

/**
 * 
 * @author Mindstorm38
 *
 */
public class ChecksumUtils {

	/**
	 * Get a stream digest using a specified algorithm by reading while not reached the end of stream
	 * @param stream The stream to read
	 * @param algorithm The algorithm to use
	 * @return Byte array computed by the algorithm from the stream
	 * @throws NoSuchAlgorithmException See {@link MessageDigest#getInstance(String)}
	 * @throws IOException See {@link StreamUtils#readStreamBuffered(InputStream, StandardByteConsumer)}
	 */
	public static byte[] getStreamDigest(InputStream stream, String algorithm) throws NoSuchAlgorithmException, IOException {
		
		MessageDigest digest = MessageDigest.getInstance( algorithm );
		StreamUtils.readStreamBuffered( stream, digest::update );
		return digest.digest();
		
	}
	
	/**
	 * Get a file digest using a specified algorithm
	 * @param file The file to read
	 * @param algorithm The algorithm to use
	 * @return Byte array computed by the algorithm from the file
	 * @throws NoSuchAlgorithmException See {@link MessageDigest#getInstance(String)}
	 * @throws IOException See {@link StreamUtils#readStreamBuffered(InputStream, StandardByteConsumer)} and {@link FileInputStream#FileInputStream(File)}
	 */
	public static byte[] getFileDigest(File file, String algorithm) throws NoSuchAlgorithmException, IOException {
		try ( InputStream stream = new FileInputStream( file) ) {
			return getStreamDigest( stream, algorithm );
		}
	}
	
	/**
	 * Get a stream digest hexadecimal string using a specified algorithm
	 * @param stream The stream to read
	 * @param algorithm The algorithm to use
	 * @return String representing the byte array returned by {@link #getStreamDigest(InputStream, String)} in hexadecimal
	 * @see #getStreamDigest(InputStream, String)
	 */
	public static String getHexStringStreamDigest(InputStream stream, String algorithm) throws NoSuchAlgorithmException, IOException {
		return StringUtils.byteArrayToHexString( getStreamDigest( stream, algorithm ) );
	}
	
	/**
	 * Get a file digest hexadecimal string using a specified algorithm
	 * @param file The file to read
	 * @param algorithm The algorithm to use
	 * @return String representing the byte array returned by {@link #getFileDigest(File, String)} in hexadecimal
	 * @see #getFileDigest(File, String)
	 */
	public static String getHexStringFileDigest(File file, String algorithm) throws NoSuchAlgorithmException, IOException {
		return StringUtils.byteArrayToHexArrayString( getFileDigest( file, algorithm ) );
	}
	
	/**
	 * Get a file SHA-256 digest
	 * @param file The to get the digest
	 * @return Hexadecimal SHA-256 representation of the file
	 * @see #getHexStringFileDigest(File, String)
	 */
	public static String getFileSHA256(File file) throws NoSuchAlgorithmException, IOException {
		return getHexStringFileDigest( file, "SHA-256" );
	}
	
	/**
	 * Get the CRC-32 checksum value for an array of bytes
	 * @param bytes The array of bytes
	 * @return CRC-32 value
	 */
	public static long getCRC32(byte[] bytes) {
		
		final CRC32 crc = new CRC32();
		crc.update( bytes );
		return crc.getValue();
		
	}
	
}
