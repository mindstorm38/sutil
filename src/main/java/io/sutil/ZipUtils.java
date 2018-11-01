package io.sutil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 
 * @author Mindstorm38
 *
 */
public class ZipUtils {
	
	/**
	 * Extract the content of a ZIP archive to a directory
	 * @param file The file ZIP archive
	 * @param dir The directory where to extract 
	 * @param filter An optional filter for select entries
	 * @param progress An optional float consumer for progress
	 * @throws IOException For all stream operations
	 */
	public static void extractZipToDirectory(File file, File dir, Predicate<ZipEntry> filter, Consumer<Float> progress) throws IOException {
		
		try ( ZipFile zip = new ZipFile( file ) ) {
			
			final int size = zip.size();
			
			Enumeration<? extends ZipEntry> entries = zip.entries();
			ZipEntry entry;
			InputStream stream;
			File extractfile;
			
			int i = 0;
			
			while ( entries.hasMoreElements() ) {
				
				entry = entries.nextElement();
				
				if ( filter != null && !filter.test( entry ) )
					continue;
				
				extractfile = new File( dir, entry.getName() );
				
				if ( entry.isDirectory() ) {
					
					extractfile.mkdirs();
					
				} else {
					
					stream = zip.getInputStream( entry );
					
					try ( OutputStream output = new BufferedOutputStream( new FileOutputStream (extractfile ) ) ) {
						StreamUtils.copyStream( stream, output );
					}
					
					stream.close();
					
				}
				
				i++;
				
				if ( progress != null )
					progress.accept( (float) i / size );
				
			}
			
		}
		
	}
	
}
