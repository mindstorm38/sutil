package io.sutil;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class LoggerUtils {
	
	public static final Logger LOGGER;
	
	static {
		
		LOGGER = Logger.getLogger("SUtil");
		LOGGER.setUseParentHandlers( false );
		LOGGER.setLevel( Level.ALL );
		LOGGER.setParent( Logger.getLogger("SUtil Default Parent Logger") );
		
		LOGGER.addHandler( new Handler() {
			
			public void publish(LogRecord record) {
				Logger logger = LOGGER.getParent();
				if ( logger == null ) return;
				record.setMessage( "[" + record.getLoggerName() + "] " + record.getMessage() );
			}
			
			public void flush() {}
			public void close() throws SecurityException {}
			
		} );
		
	}
	
	public static void setParentLogger(Logger logger) {
		LOGGER.setParent( logger );
	}
	
}
