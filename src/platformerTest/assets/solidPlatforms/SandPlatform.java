package platformerTest.assets.solidPlatforms;

import platformerTest.assets.SolidPlatform;

public class SandPlatform extends SolidPlatform {

	public SandPlatform(double x, double y, double size_x, double size_y) {
		super(x, y, size_x, size_y, COLOR_SAND);
		
		this.slipperiness = 0.955;
		
	}
	
}
