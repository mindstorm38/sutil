package io.sutil;

public class SingletonAlreadyInstantiatedException extends RuntimeException {

	private static final long serialVersionUID = -5324488674451046569L;
	
	public SingletonAlreadyInstantiatedException(Class<?> singletonClass) {
		super( "Singleton of class '" + singletonClass.getName() + "' already instancied. Use " + singletonClass.getName() + ".getInstance() to get the instance." );
	}
	
}
