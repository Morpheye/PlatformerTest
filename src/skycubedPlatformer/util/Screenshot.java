package skycubedPlatformer.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.imageio.ImageIO;

import skycubedPlatformer.Main;
import skycubedPlatformer.menu.ApplicationFrame;
import skycubedPlatformer.util.appdata.FileLoader;

public class Screenshot {
	
	public Screenshot() {
		
		BufferedImage img = new BufferedImage(Main.SIZE, Main.SIZE, BufferedImage.TYPE_INT_ARGB);
		
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");  
			LocalDateTime now = LocalDateTime.now();  
			File screenshot = new File(FileLoader.screenshotFileDirectory + dtf.format(now) + ".png");
			screenshot.createNewFile();
		
			ApplicationFrame.current.paint(img.getGraphics());
		
			ImageIO.write(img, "png", screenshot);
		
		} catch (Exception e) {e.printStackTrace();}
		
		img.flush();
		
	}
	
}
