package io.sutil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class LoggerUtils {
	
	private static final DateFormat DATE_FORMAT;
	
	public static final Logger DEFAULT_PARENT_LOGGER;
	public static final Logger LOGGER;
	
	static {
		
		DATE_FORMAT = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss.SSS");
		
		DEFAULT_PARENT_LOGGER = Logger.getLogger("SUtil Default Logger");
		DEFAULT_PARENT_LOGGER.setUseParentHandlers( false );
		DEFAULT_PARENT_LOGGER.addHandler( new Handler() {
			
			@Override
			public void publish(LogRecord record) {
				
				String stacktrace = record.getThrown() == null ? "" : "\n" + StringUtils.getStackTraceString( record.getThrown() );
				
				System.out.println( "[" + DATE_FORMAT.format( new Date() ) + "] [" + record.getLoggerName() + "] [" + record.getLevel().getName() + "] " + ( record.getMessage() == null ? "" : String.format( record.getMessage(), record.getParameters() ) + " " ) + ( record.getThrown() == null ? "" : record.getThrown().getClass().getName() + " : " + record.getThrown().getMessage() + stacktrace ) );
				
			}
			
			@Override
			public void flush() {}
			
			@Override
			public void close() throws SecurityException {}
			
		} );
		
		LOGGER = Logger.getLogger("SUtil");
		LOGGER.setUseParentHandlers( true );
		LOGGER.setLevel( Level.ALL );
		LOGGER.setParent( DEFAULT_PARENT_LOGGER );
		
	}
	
	public static void setParentLogger(Logger logger) {
		LOGGER.setParent( logger == null ? DEFAULT_PARENT_LOGGER : logger );
	}
	
}
