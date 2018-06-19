package io.sutil;

public class ReflectUtils {
	
	public static void initClasses(Class<?>...classes) {
		for ( Class<?> clazz : classes ) {
			try { Class.forName( clazz.getName() ); } catch (ClassNotFoundException e) {}
		}
	}
	
}
