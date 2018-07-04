package io.sutil;

public class ReflectUtils {
	
	public static void initClasses(Class<?>...classes) {
		for ( Class<?> clazz : classes ) {
			try { Class.forName( clazz.getName() ); } catch (ClassNotFoundException e) {}
		}
	}
	
	public static <E> E safecast(Object buffer, Class<E> clazz) {
		if ( !clazz.isAssignableFrom( buffer.getClass() ) ) return null;
		return clazz.cast( buffer );
	}
	
}
