package skycubedPlatformer.assets.decoration.walls;

import java.awt.Color;

import skycubedPlatformer.assets.DecorationObject;

public class SolidBackgroundObject extends DecorationObject {

	public SolidBackgroundObject(double x, double y, double size_x, double size_y, Color color) {
		super(x, y, size_x, size_y, color);
		this.drawLayer = -10;
	}

}
