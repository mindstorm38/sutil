package io.sutil.profiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.sutil.LoggerUtils;

public class Profiler {
	
	private final Map<String, ProfilerSection> sections;
	private final List<Long> times;
	private ProfilerSection currentSection;
	
	private boolean enabled;
	
	public Profiler() {
		
		this.sections = new HashMap<>();
		this.times = new ArrayList<>();
		this.currentSection = null;
		
		this.enabled = false;
		
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public void startSection(String identifier) {
		
		if ( this.enabled ) {
			
			ProfilerSection section = new ProfilerSection( this.currentSection, identifier );
			if ( this.currentSection != null ) this.currentSection.childrenSections.add( section );
			this.currentSection = section;
			this.sections.put( section.toString(), section );
			this.times.add( System.nanoTime() );
			
		}
		
	}
	
	public void endSection() {
		
		if ( this.enabled && this.currentSection != null && this.times.size() > 0 ) {
			
			long now = System.nanoTime();
			long start = this.times.remove( this.times.size() - 1 );
			
			this.currentSection.time += now - start;
			this.currentSection.count++;
			
			if ( this.currentSection.time > 100000000L ) {
				
				LoggerUtils.LOGGER.warning( "Something's take too long ! '" + this.currentSection.toString() + "' took " + ( (double) this.currentSection.time / 1000000.0 ) + " ms." );
				
			}
			
			this.currentSection = this.currentSection.getParentSection();
			
		}
		
	}
	
	public void endStartSection(String identifier) {
		
		this.endSection();
		this.startSection( identifier );
		
	}
	
	public ProfilerSection getSection(String identifier) {
		return this.sections.get( identifier );
	}
	
}
