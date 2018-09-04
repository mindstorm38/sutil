package io.sutil.profiler;

import java.util.ArrayList;
import java.util.List;

public class ProfilerSection {
	
	private final ProfilerSection parentSection;
	private final String identifier;
	
	int count;
	long time;
	
	final List<ProfilerSection> childrenSections;
	
	public ProfilerSection(ProfilerSection parentSection, String identifier) {
		
		this.parentSection = parentSection;
		this.identifier = identifier;
		
		this.count = 0;
		this.time = 0L;
		
		this.childrenSections = new ArrayList<>();
		
	}
	
	public ProfilerSection getParentSection() {
		return this.parentSection;
	}
	
	public String getIdentifier() {
		return this.identifier;
	}
	
	public int getCount() {
		return this.count;
	}
	
	public long getTime() {
		return this.time;
	}
	
	public double getAverageTime() {
		return (double) this.time / (double) this.count;
	}
	
	public double getAverageTimeMillis() {
		return this.getAverageTime() / 1000000.0;
	}
	
	public List<ProfilerSection> getChildrenSections() {
		return this.childrenSections;
	}
	
	@Override
	public String toString() {
		return ( this.parentSection == null ? "" : ( this.parentSection.toString() + "." ) ) + this.identifier;
	}
	
}
