package skycubedPlatformer.assets;

import java.awt.Color;
import java.util.ArrayList;

import skycubedPlatformer.game.GameObject;
import skycubedPlatformer.game.MovableObject;
import skycubedPlatformer.game.ObjType;
import skycubedPlatformer.menu.GamePanel;

public class PushableObject extends MovableObject {

	public PushableObject(double x, double y, double size_x, double size_y, Color color, double density, Double slipperiness) {
		super(x, y, size_x, size_y, color, density);
		this.slipperiness = slipperiness;
	}
	
	@Override
	public void move() {
		super.move();
	}

}
