package platformerTest.appdata;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ScreenRecorder {
	
	int frameCount;
	Component component;
	ArrayList<BufferedImage> images;
	
	public ScreenRecorder(Component component, int frames) {
		this.frameCount = frames;
		this.component = component;
		this.images = new ArrayList<BufferedImage>();
	}
	
	public void record(Graphics g) {
		if (images.size() < frameCount) {
			BufferedImage img = new BufferedImage(component.getWidth(), component.getHeight(), BufferedImage.TYPE_INT_ARGB);
			component.paint(img.getGraphics());
			images.add(img);
			
		}
		
		g.drawImage(images.get(images.size()-1), 0, 0, 100, 100, null);
	}
	
}
