package io.sutil;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;

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
	
	/**
	 * Dynamically add a jar to the Java ClassLoader <code>classpath</code>
	 * @param jar Jar file
	 * @param loader The used {@link ClassLoader}
	 * @throws Exception Throws from {@link URI#toURL()}, {@link Class#getDeclaredMethod(String, Class...)}, {@link Method#setAccessible(boolean)} and {@link Method#invoke(Object, Object...)}
	 */
	public static void addJarToClasspath(File jar, ClassLoader loader) throws Exception {
		
		Class<?> clazz = loader.getClass();
		Method method = clazz.getSuperclass().getDeclaredMethod( "addURL", new Class[] { URL.class } );
		method.setAccessible( true );
		method.invoke( loader, new Object[] { jar.toURI().toURL() } );
		
	}
	
}
