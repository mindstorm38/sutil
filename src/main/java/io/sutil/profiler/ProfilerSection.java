package io.sutil.profiler;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.sutil.tree.CharTreeItem;

public class ProfilerSection {
	
	// Constants \\
	
	public static final int BUFFER_LENGTH = 128;
	
	private static final DecimalFormat SHORT_MILLIS_FORMAT = new DecimalFormat("#");
	
	static {
		
		SHORT_MILLIS_FORMAT.setMinimumIntegerDigits( 1 );
		SHORT_MILLIS_FORMAT.setMaximumFractionDigits( 6 );
		SHORT_MILLIS_FORMAT.setDecimalFormatSymbols( new DecimalFormatSymbols( Locale.US ) );
		
	}
	
	// Class \\
	
	private final ProfilerSection parentSection;
	private final String identifier;
	
	private final long[] timesBuffer;
	
	final List<ProfilerSection> childrenSections;
	
	public ProfilerSection(ProfilerSection parentSection, String identifier) {
		
		this.parentSection = parentSection;
		this.identifier = identifier;
		
		this.timesBuffer = new long[ BUFFER_LENGTH ];
		
		this.childrenSections = new ArrayList<>();
		
	}
	
	public ProfilerSection getParentSection() {
		return this.parentSection;
	}
	
	public String getIdentifier() {
		return this.identifier;
	}
	
	void addEntry(long time) {
		
		for ( int i = BUFFER_LENGTH - 2; i >= 0; i-- )
			this.timesBuffer[ i + 1 ] = this.timesBuffer[ i ];
		
		this.timesBuffer[ 0 ] = time;
		
	}
	
	public long getLastTime() {
		return this.timesBuffer[ 0 ];
	}
	
	public double getLastTimeMillis() {
		return (double) this.timesBuffer[ 0 ] / 10000.0;
	}
	
	public double getAverageTimeMillis() {
		
		double total = 0.0;
		
		for ( int i = 0; i < BUFFER_LENGTH; i++ )
			total += (double) this.timesBuffer[ i ] / 10000.0;
		
		return total / BUFFER_LENGTH;
		
	}
	
	public List<ProfilerSection> getChildrenSections() {
		return this.childrenSections;
	}
	
	public String repr() {
		return ( this.parentSection == null ? "" : ( this.parentSection.toString() + "." ) ) + this.identifier;
	}
	
	@Override
	public String toString() {
		return this.repr();
	}
	
	CharTreeItem createCharTreeItem() {
		
		CharTreeItem item = new CharTreeItem( this.identifier + " : " + SHORT_MILLIS_FORMAT.format( this.getAverageTimeMillis() ) + " ms" );
		
		for ( ProfilerSection child : this.childrenSections )
			item.addChild( child.createCharTreeItem() );
			
		return item;
		
	}
	
}
