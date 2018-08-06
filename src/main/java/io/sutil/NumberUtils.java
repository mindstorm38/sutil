package io.sutil;

public class NumberUtils {
	
	public static boolean isNumber(Object obj) {
		return obj instanceof Number;
	}
	
	public static boolean isByte(Object obj) {
		return obj instanceof Byte;
	}
	
	public static boolean isShort(Object obj) {
		return obj instanceof Short;
	}
	
	public static boolean isInteger(Object obj) {
		return obj instanceof Integer;
	}
	
	public static boolean isLong(Object obj) {
		return obj instanceof Long;
	}
	
	public static boolean isFloat(Object obj) {
		return obj instanceof Float;
	}
	
	public static boolean isDouble(Object obj) {
		return obj instanceof Double;
	}
	
	public static boolean isFloatingPointFormat(Number obj) {
		return isFloat( obj ) || isDouble( obj );
	}
	
	public static boolean isIntegerFormat(Number obj) {
		return !isFloatingPointFormat( obj );
	}
	
	public static boolean areEquals(Number n1, Number n2) {
		if ( isFloatingPointFormat( n1 ) )
			if ( isFloatingPointFormat( n2 ) )
				return n1.doubleValue() == n2.doubleValue();
			else
				return n1.doubleValue() == n2.longValue();
		else
			if ( isFloatingPointFormat( n2 ) )
				return n1.longValue() == n2.doubleValue();
			else
				return n1.longValue() == n2.longValue();
	}
	
	public static boolean isGreater(Number num, Number other) {
		if ( isFloatingPointFormat( num ) )
			if ( isFloatingPointFormat( other ) )
				return num.doubleValue() > other.doubleValue();
			else
				return num.doubleValue() > other.longValue();
		else
			if ( isFloatingPointFormat( other ) )
				return num.longValue() > other.doubleValue();
			else
				return num.longValue() > other.longValue();
	}
	
	public static boolean isSmaller(Number num, Number other) {
		if ( isFloatingPointFormat( num ) )
			if ( isFloatingPointFormat( other ) )
				return num.doubleValue() < other.doubleValue();
			else
				return num.doubleValue() < other.longValue();
		else
			if ( isFloatingPointFormat( other ) )
				return num.longValue() < other.doubleValue();
			else
				return num.longValue() < other.longValue();
	}
	
}
