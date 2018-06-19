package io.sutil;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

public class ClipboardUtils {

public static String getClipboardString() {
		
		try {
			
			Transferable transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents( null );
			
			if ( transferable != null && transferable.isDataFlavorSupported( DataFlavor.stringFlavor ) ) {
				
				return (String) transferable.getTransferData( DataFlavor.stringFlavor );
				
			}
			
		} catch (Exception e) {}
		
		return "";
		
	}
	
	public static void setClipboardString(String string) {
		
		if ( !string.isEmpty() ) {
			
			try {
				
				StringSelection selection = new StringSelection( string );
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents( selection, null );
				
			} catch (Exception e) {}
			
		}
		
	}
	
}
