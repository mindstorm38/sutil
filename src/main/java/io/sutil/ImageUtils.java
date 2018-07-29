package io.sutil;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageUtils {
	
	/**
	 * Read a {@link BufferedImage} from an {@link InputStream} without throwing error like {@link ImageIO#read(InputStream)} but returning <code>null</code><br>
	 * This function close the stream at the end of reading
	 * @param stream Image input stream
	 * @return 
	 */
	public static BufferedImage readBufferedImageSafe(InputStream stream) {
		
		try {
			return ImageIO.read( stream );
		} catch (IOException e) {
			return null;
		} finally {
			StreamUtils.safeclose( stream );
		}
		
	}
	
}
