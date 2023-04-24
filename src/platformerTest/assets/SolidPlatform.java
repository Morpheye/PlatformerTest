package platformerTest.assets;

import java.awt.Color;

import platformerTest.game.GameObject;

public class SolidPlatform extends GameObject {
	
	public double slipperiness = 1.0;

	public SolidPlatform(double x, double y, double size_x, double size_y, Color color) {
		super(x, y, size_x, size_y, color);
		this.movable = false;
	}
	
	@Override
	public void move() {
		this.y += this.vy;
		this.x += this.vx;
	}
	
}
