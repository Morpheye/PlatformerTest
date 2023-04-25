package platformerTest.assets;

import java.awt.Color;

import platformerTest.game.GameObject;

public class DecorationObject extends GameObject {

	public DecorationObject(double x, double y, double size_x, double size_y, Color color) {
		super(x, y, size_x, size_y, color);
		this.solid = false;
	}
	
	@Override
	public void move() {
		this.y += this.vy;
		this.x += this.vx;
	}
	
}