package io.sutil.tree;

import java.util.ArrayList;
import java.util.List;

public class CharTreeItem {

	private final String title;
	private CharTreeItem parent;
	private final List<CharTreeItem> children;
	
	public CharTreeItem(String title) {
		
		this.parent = null;
		this.children = new ArrayList<>();
		this.title = title;
		
	}
	
	public CharTreeItem getParent() {
		return this.parent;
	}
	
	public void addChild(CharTreeItem item) {
		
		this.children.add( item );
		item.parent = this;
		
	}
	
	List<CharTreeItem> getChildren() {
		return this.children;
	}
	
	public String getTitle() {
		return this.title;
	}
	
}
