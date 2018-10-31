package io.sutil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.function.Consumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipUtils {
	
	public static void extractZipToDirectory(File file, File dir, Consumer<Float> progress) throws IOException {
		
		try ( ZipFile zip = new ZipFile( file ) ) {
			
			final int size = zip.size();
			
			Enumeration<? extends ZipEntry> entries = zip.entries();
			ZipEntry entry;
			InputStream stream;
			File extractfile;
			
			int i = 0;
			
			while ( entries.hasMoreElements() ) {
				
				entry = entries.nextElement();
				extractfile = new File( dir, entry.getName() );
				
				if ( entry.isDirectory() ) {
					
					extractfile.mkdirs();
					
				} else {
					
					stream = zip.getInputStream( entry );
					
					try ( OutputStream output = new FileOutputStream( extractfile ) ) {
						StreamUtils.copyStream( stream, output );
					}
					
					stream.close();
					
				}
				
				i++;
				
				progress.accept( (float) i / size );
				
			}
			
		}
		
	}
	
}
