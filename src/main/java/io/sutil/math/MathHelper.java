package io.sutil.math;

/**
 * 
 * Math helper inspired by the Minecraft class
 * 
 * @author Theo Rozier
 *
 */
public class MathHelper {
	
	public static final double PI_TWICE = Math.PI * 2.0;
	public static final double PI_HALF = Math.PI / 2.0;
	
	public static int floor_float_int(float value) {
		
		int i = (int) value;
		return value < i ? i - 1 : i;
		
	}
	
	public static int floor_double_int(double value) {
		
		int i = (int) value;
		return value < i ? i - 1 : i;
		
	}
	
	public static long floor_double_long(double value) {
		
		long i = (long) value;
		return value < i ? i - 1L : i;
		
	}
	
	public static int ceiling_float_int(float value) {
		
        int i = (int) value;
        return value > i ? i + 1 : i;
        
    }

    public static int ceiling_double_int(double value) {
    	
        int i = (int) value;
        return value > i ? i + 1 : i;
        
    }

    public static double distance_int(int x1, int y1, int x2, int y2) {
    	int d1 = ( x1 - x2 );
    	int d2 = ( y1 - y2 );
    	return Math.sqrt( ( d1 * d1 ) + ( d2 * d2 ) );
    }
    
    public static double distance_float(float x1, float y1, float x2, float y2) {
    	float d1 = ( x1 - x2 );
    	float d2 = ( y1 - y2 );
    	return Math.sqrt( ( d1 * d1 ) + ( d2 * d2 ) );
    }
    
    public static float map(float value, float inInf, float inSup, float outInf, float outSup, boolean constrain) {
    	float ret = ( value - inInf ) / ( inSup - inInf ) * ( outSup - outInf ) + outInf;
    	if ( !constrain ) return ret;
    	if ( outInf < outSup ) {
    		return constrain( value, outInf, outSup );
    	} else {
    		return constrain( value, outSup, outInf );
    	}
    }
    
    public static float map(float value, float inInf, float inSup, float outInf, float outSup) {
    	return map(value, inInf, inSup, outInf, outSup, false );
    }
    
    public static float constrain(float value, float low, float high) {
    	return Math.max( Math.min( value, high ), low );
    }
    
    public static float interpolate(float alpha, float current, float last) {
		if ( current == last ) return current;
		return ( ( current - last ) * alpha ) + last;
	}
    
    public static double round_off(double n, int decimals) {
    	if ( decimals < 0 ) throw new IndexOutOfBoundsException("Invalid decimal count");
    	final double p = Math.pow( 10, decimals );
    	return Math.round( n * p ) / p;
    }
    
}
