package io.sutil;

public class JavaVersion {
	
	// Constants \\
	
	private static final LazyLoadValue<JavaVersion> currentJavaVersion = new LazyLoadValue<JavaVersion>() {
		public JavaVersion create() {
			return parse( System.getProperty("java.version") );
		}
	};
	
	public static class JavaVersionParseException extends RuntimeException {
		
		private static final long serialVersionUID = -180958854698616044L;
		
		public JavaVersionParseException(String msg) {
			super(msg);
		}
		
		public JavaVersionParseException(String msg, Throwable e) {
			super(msg, e);
		}
		
	}
	
	// Static \\
	
	public static JavaVersion getCurrentJavaVersion() {
		return currentJavaVersion.get();
	}
	
	public static JavaVersion parse(String stringVersion) {
		
		String[] parts = stringVersion.split("[\\._]");
		
		if ( parts.length == 0 ) throw new IllegalArgumentException("Invalid string version");
		
		int major = 0;
		int minor = 0;
		int build = 0;
		int revision = 0;
		
		try {
			
			if ( parts.length >= 1 ) major = Integer.valueOf( parts[ 0 ] );
			if ( parts.length >= 2 ) minor = Integer.valueOf( parts[ 1 ] );
			if ( parts.length >= 3 ) build = Integer.valueOf( parts[ 2 ] );
			if ( parts.length >= 4 ) revision = Integer.valueOf( parts[ 3 ] );
			
		} catch (NumberFormatException e) {
			
			throw new JavaVersionParseException( "Invalid number format", e );
			
		}
		
		return new JavaVersion( major, minor, build, revision );
		
	}
	
	public static boolean areEquals(JavaVersion v1, JavaVersion v2) {
		return v1.major == v2.major && v1.minor == v2.minor && v1.build == v2.build && v1.revision == v2.revision;
	}
	
	public static boolean isGreater(JavaVersion original, JavaVersion comparison) {
		if ( comparison.major < original.major ) return false;
		if ( comparison.minor < original.minor ) return false;
		if ( comparison.build < original.build ) return false;
		if ( comparison.revision <= original.revision ) return false;
		return true;
	}
	
	public static boolean isSmaller(JavaVersion original, JavaVersion comparison) {
		if ( comparison.major > original.major ) return false;
		if ( comparison.minor > original.minor ) return false;
		if ( comparison.build > original.build ) return false;
		if ( comparison.revision >= original.revision ) return false;
		return true;
	}
	
	public static boolean isGreaterOrEquals(JavaVersion original, JavaVersion comparison) {
		return areEquals( original, comparison ) || isGreater( original, comparison );
	}
	
	public static boolean isSmallerOrEquals(JavaVersion original, JavaVersion comparison) {
		return areEquals( original, comparison ) || isSmaller( original, comparison );
	}
	
	// Class \\
	
	private final int major;
	private final int minor;
	private final int build;
	private final int revision;
	
	public JavaVersion(int major, int minor, int build, int revision) {
		
		this.major = major;
		this.minor = minor;
		this.build = build;
		this.revision = revision;
		
	}
	
	public int major() { return this.major; }
	public int minor() { return this.minor; }
	public int build() { return this.build; }
	public int revision() { return this.revision; }
	
	@Override
	public String toString() {
		return this.major + "." + this.minor + "." + this.build + "_" + this.revision;
	}
	
	@Override
	public boolean equals(Object obj) {
		if ( obj == this ) return true;
		if ( !( obj instanceof JavaVersion ) ) return false;
		return areEquals( this, (JavaVersion) obj );
	}
	
	public boolean isGreater(JavaVersion version) { return isGreater( this, version ); }
	public boolean isGreaterThan(JavaVersion version) { return isGreater( version, this ); }
	
	public boolean isSmaller(JavaVersion version) { return isSmaller( this, version ); }
	public boolean isSmallerThan(JavaVersion version) { return isSmaller( version, this ); }
	
	public boolean isGreaterOrEquals(JavaVersion version) { return isGreaterOrEquals( this, version ); }
	public boolean isGreaterOrEqualsThan(JavaVersion version) { return isGreaterOrEquals( version, this ); }
	
	public boolean isSmallerOrEquals(JavaVersion version) { return isSmallerOrEquals( this, version ); }
	public boolean isSmallerOrEqualsThan(JavaVersion version) { return isSmallerOrEquals( version, this ); }
	
}
