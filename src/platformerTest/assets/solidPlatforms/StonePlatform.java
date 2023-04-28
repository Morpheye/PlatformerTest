package platformerTest.assets.solidPlatforms;

import platformerTest.assets.SolidPlatform;

public class StonePlatform extends SolidPlatform {

	public StonePlatform(double x, double y, double size_x, double size_y) {
		super(x, y, size_x, size_y, COLOR_STONE);
		
		this.slipperiness = 0.965;
		
	}
	
}
