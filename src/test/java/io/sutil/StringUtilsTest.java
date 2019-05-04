package io.sutil;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StringUtilsTest {

	@Test
	@DisplayName("Check default charset availability")
	void charsets() {
		
		assertNotNull( StringUtils.CHARSET_UTF_8, "UTF-8 charset constant is not available." );
		assertNotNull( StringUtils.CHARSET_US_ASCII, "ASCII charset constant is not available." );
		
	}
	
	@Test
	@DisplayName("Check methods for bytes")
	void bytes() {
		
		assertEquals( "[45, F7, 00]", StringUtils.byteArrayToHexArrayString( new byte[] {
				(byte) 0x45,
				(byte) 0xF7,
				(byte) 0x00
		} ) );
		
		assertEquals( "00", StringUtils.byteToHexString( (byte) 0x00 ) );
		assertEquals( "7F", StringUtils.byteToHexString( (byte) 0x7F ) );
		assertEquals( "FF", StringUtils.byteToHexString( (byte) 0xFF ) );
		
		assertEquals( "45E902", StringUtils.byteArrayToHexString( new byte[] {
				(byte) 0x45,
				(byte) 0xE9,
				(byte) 0x02
		} ) );
		
		assertEquals( "00" , StringUtils.unsignedByteToHexString( (short) 0x00 ) );
		assertEquals( "7F" , StringUtils.unsignedByteToHexString( (short) 0x7F ) );
		assertEquals( "FF" , StringUtils.unsignedByteToHexString( (short) 0xFF ) );

		assertEquals( "0000", StringUtils.unsignedShortToHexString( 0x0000 ) );
		assertEquals( "FFFF", StringUtils.unsignedShortToHexString( 0xFFFF ) );
		assertEquals( "6F3E", StringUtils.unsignedShortToHexString( 0x6F3E ) );
		
		assertArrayEquals( 
				new byte[] {0x48, 0x65, 0x6C, 0x6C, 0x6F, 0x00 },
				StringUtils.getStringBytesZeroFilled( "Hello", StringUtils.CHARSET_US_ASCII, 6 )
			);
		
	}
	
	@Test
	@DisplayName("Check string manipulation method")
	void string() {
		
		assertEquals( "", StringUtils.getFilledString( 'x', 0 ) );
		assertEquals( "xxxxxxxxxx", StringUtils.getFilledString( 'x', 10 ) );
		
		assertEquals( "\\n\\n", StringUtils.escapeLineChars("\n\n") );
		
		assertEquals( "Hello", StringUtils.removeLeadingChars( "aaaaaHello", 'a' ) );
		
	}
	
}
