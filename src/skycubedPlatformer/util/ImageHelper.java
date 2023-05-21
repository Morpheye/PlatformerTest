package skycubedPlatformer.util;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class ImageHelper {
	
	public static BufferedImage screenshotImage;
	
	public void init() {
		try {
			screenshotImage = ImageIO.read(this.getClass().getResource("/gui/screenshot.png"));
			
		} catch (Exception e) {}
	}
}
