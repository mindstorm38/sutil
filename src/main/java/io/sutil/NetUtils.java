package io.sutil;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetUtils {

	public static Tuple<InetAddress, Integer> parseFullAddress(String fullAddress) {
		
		String[] splited = fullAddress.split(":");
		if ( splited.length != 2 ) return null;
		
		InetAddress host;
		try {
			
			host = InetAddress.getByName( splited[0] );
			
			int port = Integer.valueOf( splited[1] );
			
			if ( port < 0 || port > 65535 ) throw new IllegalStateException("Port must be between 0 and 65535");
			
			return new Tuple<InetAddress, Integer>( host, port );
			
		} catch (UnknownHostException e) {
			throw new IllegalStateException("Invalid host name");
		} catch (NumberFormatException e) {
			throw new IllegalStateException("Invalid port");
		}
		
	}
	
}
