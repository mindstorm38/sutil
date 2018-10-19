package io.sutil;

public class DebugDescriptionBuilder extends TextBuilder {
	
	public DebugDescriptionBuilder() {
	}
	
	public void addLine(String option, String value) {
		this.append( option ).space().append(":").space().append( value ).nl();
	}
	
}
