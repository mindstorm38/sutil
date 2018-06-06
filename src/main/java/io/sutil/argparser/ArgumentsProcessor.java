package io.sutil.argparser;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * Used to parse command line arguments
 * 
 * @author Mindstorm38
 *
 */
public class ArgumentsProcessor {
	
	private final Set<Argument<?>> arguments;
	
	public ArgumentsProcessor() {
		
		this.arguments = new HashSet<>();
		
	}
	
	public void addArgument(Argument<?> arg) {
		this.arguments.add( arg );
	}
	
	public void addAllArguments(Argument<?>...arguments) {
		this.arguments.addAll( Arrays.asList( arguments ) );
	}
	
	public Argument<?> getArgumentFull(String full) {
		for ( Argument<?> arg : this.arguments )
			if ( arg.full.equals( full ) )
				return arg;
		return null;
	}
	
	public Argument<?> getArgumentShort(String shrt) {
		for ( Argument<?> arg : this.arguments )
			if ( arg.shrt.equals( shrt ) )
				return arg;
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public void process(String[] args) {
		
		Argument<Object> next = null;
		
		for ( int i = 0; i < args.length; i++ ) {
			
			String arg = args[ i ];
			
			if ( next == null ) {
				
				if ( arg.startsWith("--") ) {
					
					String full = arg.substring( 2 );
					next = (Argument<Object>) this.getArgumentFull( full );
					
				} else if ( arg.startsWith("-") ) {
					
					String shrt = arg.substring( 1 );
					next = (Argument<Object>) this.getArgumentShort( shrt );
					
				}
				
			} else {
				
				next.value = next.parser.parse( arg );

				next = null;
				
			}
			
		}
		
	}
	
}
