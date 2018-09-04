package io.sutil.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.sutil.TextBuilder;

public class CharTree {
	
	private final List<CharTreeItem> items;
	
	public CharTree(CharTreeItem...items) {
		
		this.items = new ArrayList<>( items.length );
		for ( CharTreeItem item : items ) this.items.add( item );
		
	}
	
	public CharTree() {
		
		this.items = new ArrayList<>();
		
	}
	
	public void addItem(CharTreeItem item) {
		this.items.add( item );
	}
	
	@Override
	public String toString() {
		
		if ( this.items.size() == 0 ) return "";
		
		TextBuilder builder = new TextBuilder();
		
		for ( Iterator<CharTreeItem> it = this.items.iterator(); it.hasNext(); )
			appendItem( builder, it.next(), true, new boolean[ 0 ], !it.hasNext() );
		
		return builder.toString();
		
	}
	
	private static void appendItem(TextBuilder builder, CharTreeItem item, boolean firstLevel, boolean[] states, boolean end) {
		
		if ( !firstLevel ) {
			
			for ( int i = 0; i < states.length; i++  )
				builder.append( states[ i ] ? "   " : "│  " );
			
			builder.append( end ? "└─ " : "├─ " );
			
		}
		
		builder.append( item.getTitle() ).nl();
		
		boolean[] newStates = new boolean[ states.length + 1 ];
		System.arraycopy( states, 0, newStates, 0, states.length );
		newStates[ states.length ] = end;
		
		for ( Iterator<CharTreeItem> it = item.getChildren().iterator(); it.hasNext(); )
			appendItem( builder, it.next(), false, newStates, !it.hasNext() );
		
	}
	
}
