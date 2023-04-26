package platformerTest.assets.liquidPlatforms;

import java.awt.Color;

import platformerTest.assets.LiquidPlatform;

public class WaterPlatform extends LiquidPlatform {

	public WaterPlatform(double x, double y, double size_x, double size_y) {
		super(x, y, size_x, size_y, COLOR_WATER);
		
		this.slipperiness = 0.95;
		this.density = 1;
		
	}

}
