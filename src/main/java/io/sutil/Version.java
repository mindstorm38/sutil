package io.sutil;

/**
 * 
 * Utility class to easily encode game version as an integer
 * 
 * @author Mindstorm38
 *
 */
public class Version {
	
	/**
	 * 
	 * Version type
	 * 
	 * @author Mindstorm38
	 *
	 */
	public static enum Type {
		
		ALPHA ( (byte) 0x0, "a", "Alpha" ),
		BETA ( (byte) 0x1, "b", "Beta" ),
		RELEASE ( (byte) 0x2, "r", "Release" );
		
		private final byte id;
		private final String identifier;
		private final String name;
		
		private Type(byte id, String identifier, String name) {
			this.id = id;
			this.identifier = identifier;
			this.name = name;
		}
		
		public static Type get(int id) {
			for ( Type type : Type.values() )
				if ( type.id == id )
					return type;
			return null;
		}
		
		public static Type getByIdentifier(String identifier) {
			for ( Type type : Type.values() )
				if ( identifier.equals( type.identifier ) )
					return type;
			return null;
		}
		
		public static Type getByName(String name) {
			for ( Type type : Type.values() )
				if ( name.equalsIgnoreCase( type.name ) )
					return type;
			return null;
		}
		
	}
	
	public static class InvalidVersionFormatException extends RuntimeException {
		
		private static final long serialVersionUID = 197561322585928402L;

		public InvalidVersionFormatException(String msg) {
			super( "Invalid version format : " + msg );
		}
		
		public InvalidVersionFormatException() {
			super( "Invalid version format" );
		}
		
	}
	
	private final Type type;
	private final byte major;
	private final byte minor;
	private final byte build;
	
	private final int uid;
	
	public Version(Type type, byte major, byte minor, byte build) {
		
		this.type = type;
		this.major = major;
		this.minor = minor;
		this.build = build;
		
		this.uid =
				build << ( Byte.SIZE * 0 ) |
				minor << ( Byte.SIZE * 1 ) |
				major << ( Byte.SIZE * 2 ) |
				type.id << ( Byte.SIZE * 3 );
		
	}
	
	public Type type() { return this.type; }
	public byte major() { return this.major; }
	public byte minor() { return this.minor; }
	public byte build() { return this.build; }
	
	/**
	 * Get version uid, version uid is the representation of its 4 bytes
	 * @return Version uid
	 */
	public int uid() { return this.uid; }
	
	/**
	 * Get the representation of the version as a string<br><br>
	 * Uncompressed representation (for alpha type) : <blockquote>Alpha X.X.X</blockquote><br>
	 * Compressed representation (for alpha type) : <blockquote>aX.X.X</blockquote>
	 * @param compressed Compressed representation
	 * @return String version representation
	 */
	public String repr(boolean compressed) {
		return ( compressed ? this.type.identifier : ( this.type.name + " " ) ) + this.major + "." + this.minor + "." + this.build;
	}
	
	/**
	 * Return uncompressed representation of the version<br>
	 * {@inheritDoc}
	 * @see #repr(boolean)
	 * @return {@inheritDoc}
	 */
	public String repr() {
		return this.repr( false );
	}
	
	@Override
	public int hashCode() {
		return this.uid;
	}
	
	@Override
	public String toString() {
		return this.repr( false );
	}
	
	@Override
	public boolean equals(Object obj) {
		if ( obj == this ) return true;
		if ( !( obj instanceof Version ) ) return false;
		return ( (Version) obj ).uid == this.uid;
	}
	
	/**
	 * Parse a version from its uid
	 * @param uid Version uid
	 * @return Parsed version or null if invalid parsed {@link Type}
	 */
	public static Version parse(int uid) {
		
		byte typeId = (byte) ( ( uid & 0xFF000000 ) >> ( Byte.SIZE * 3 ) );
		Type type = Type.get( typeId );
		if ( type == null ) return null;
		byte major = (byte) ( ( uid & 0x00FF0000 ) >> ( Byte.SIZE * 2 ) );
		byte minor = (byte) ( ( uid & 0x0000FF00 ) >> ( Byte.SIZE * 1 ) );
		byte build = (byte) ( ( uid & 0x000000FF ) >> ( Byte.SIZE * 0 ) );
		
		return new Version( type, major, minor, build );
		
	}
	
	public static Version parse(String repr) {
		
		String[] parts = repr.split("\\.");
		
		Type type = null;
		byte major = (byte) 0;
		byte minor = (byte) 0;
		byte build = (byte) 0;
		
		String part0 = parts[ 0 ];
		
		for ( Type typeRaw : Type.values() ) {
			if ( part0.startsWith( typeRaw.name ) ) {
				type = typeRaw;
				if ( part0.length() == typeRaw.name.length() ) throw new InvalidVersionFormatException("No major version number specified with version type name");
				part0 = part0.substring( typeRaw.name.length() + 1 );
				break;
			}
		}
		
		if ( type == null ) {
			
			for ( Type typeRaw : Type.values() ) {
				if ( part0.startsWith( typeRaw.identifier ) ) {
					type = typeRaw;
					if ( part0.length() == 1 ) throw new InvalidVersionFormatException("No major version number specified with version type identifier");
					break;
				}
			}
			
			if ( type == null ) type = Type.ALPHA;
			
		}
		
		try {
			major = Byte.valueOf( part0.trim() );
		} catch (NumberFormatException e) {
			throw new InvalidVersionFormatException("Invalid major version format");
		}
		
		if ( parts.length >= 2 ) {
			
			try {
				minor = Byte.valueOf( parts[1].trim() );
			} catch (NumberFormatException e) {
				throw new InvalidVersionFormatException("Invalid minor version format");
			}
			
			if ( parts.length >= 3 ) {
				
				try {
					build = Byte.valueOf( parts[2].trim() );
				} catch (NumberFormatException e) {
					throw new InvalidVersionFormatException("Invalid build version format");
				}
				
			}
			
		}
		
		return new Version( type, major, minor, build );
		
	}
	
}
