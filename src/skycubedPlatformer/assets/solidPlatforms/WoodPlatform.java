package skycubedPlatformer.assets.solidPlatforms;

import skycubedPlatformer.assets.SolidPlatform;

public class WoodPlatform extends SolidPlatform {

	public WoodPlatform(double x, double y, double size_x, double size_y) {
		super(x, y, size_x, size_y, COLOR_WOOD);
		
		this.slipperiness = 0.965;
		
	}
	
}
