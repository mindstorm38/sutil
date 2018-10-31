package io.sutil;

public class TextBuilder {

	private char[] data;
	
	public TextBuilder() {
		
		this.data = new char[0];
		
	}
	
	public TextBuilder append(Object obj) {
		
		if ( obj == null ) obj = "null";
		
		if ( obj instanceof Character ) {
			
			char[] newData = new char[ data.length + 1 ];
			System.arraycopy( this.data, 0, newData, 0, this.data.length );
			newData[ this.data.length ] = (char) obj;
			this.data = newData;
			
		} else {
			
			char[] chars = obj.toString().toCharArray();
			
			char[] newData = new char[ data.length + chars.length ];
			System.arraycopy( this.data, 0, newData, 0, this.data.length );
			System.arraycopy( chars, 0, newData, this.data.length, chars.length );
			this.data = newData;
			
		}
		
		return this;
		
	}
	
	public TextBuilder nl() {
		return this.append('\n');
	}
	
	public TextBuilder nl(int count) {
		if ( count <= 0 ) return this;
		return this.append( StringUtils.getFilledString( '\n', count ) );
	}
	
	public TextBuilder tab() {
		return this.append('\t');
	}
	
	public TextBuilder tab(int count) {
		if ( count <= 0 ) return this;
		return this.append( StringUtils.getFilledString( '\t', count ) );
	}
	
	public TextBuilder space() {
		return this.append(' ');
	}
	
	public TextBuilder space(int count) {
		if ( count <= 0 ) return this;
		return this.append( StringUtils.getFilledString( ' ', count ) );
	}
	
	@Override
	public String toString() {
		return new String( this.data );
	}
	
}
