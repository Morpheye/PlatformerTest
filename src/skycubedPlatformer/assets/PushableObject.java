package skycubedPlatformer.assets;

import java.awt.Color;

import skycubedPlatformer.game.GameObject;
import skycubedPlatformer.game.MovableObject;
import skycubedPlatformer.menu.GamePanel;

public class PushableObject extends MovableObject {

	public boolean attackable = false;
	
	public PushableObject(double x, double y, double size_x, double size_y, Color color, double density, Double slipperiness) {
		super(x, y, size_x, size_y, color, density);
		this.slipperiness = slipperiness;
	}
	
	@Override
	public void move() {
		super.move();
	}
	
	@Override
	public void crush() {
		GamePanel.createShake(10, 10, 2);
		super.crush();
	}
	
	public void damage(int damage, GameObject source) {
		
	}

}
