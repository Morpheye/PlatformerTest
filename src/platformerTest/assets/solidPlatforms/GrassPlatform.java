package platformerTest.assets.solidPlatforms;

import platformerTest.assets.SolidPlatform;

public class GrassPlatform extends SolidPlatform {

	public GrassPlatform(double x, double y, double size_x, double size_y) {
		super(x, y, size_x, size_y, COLOR_GRASS);
		
		this.slipperiness = 0.96;
		
	}
	
}
