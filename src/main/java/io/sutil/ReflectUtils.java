package io.sutil;

/**
 * 
 * Utilities for the Java Runtime Reflection
 * 
 * @author Mindstorm38
 *
 */
public class ReflectUtils {
	
	/**
	 * Initialize classes, it also call the static initializer of the class
	 * @param classes Classes to initialize
	 */
	public static void initClasses(Class<?>...classes) {
		for ( Class<?> clazz : classes ) {
			try { Class.forName( clazz.getName() ); } catch (ClassNotFoundException e) {}
		}
	}
	
	/**
	 * Safe cast an object or return <code>null</code> if unassignable
	 * @param object The object to cast
	 * @param clazz The cast class
	 * @param <E> The type of the <code>clazz</code> and of returned object
	 * @return Casted object
	 */
	public static <E> E safecast(Object object, Class<E> clazz) {
		if ( !clazz.isAssignableFrom( object.getClass() ) ) return null;
		return clazz.cast( object );
	}
	
}
