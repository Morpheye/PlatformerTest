package platformerTest.assets;

import java.awt.Color;
import java.awt.Graphics;

import platformerTest.game.GameObject;
import platformerTest.game.ObjType;
import platformerTest.game.Player;

public class DecorationObject extends GameObject {

	public DecorationObject(double x, double y, double size_x, double size_y, Color color) {
		super(x, y, size_x, size_y, color);
		
		this.type = ObjType.DecorationObject;
		
		this.solid = false;
	}
	
	@Override
	public void move() {
		this.y += this.vy;
		this.x += this.vx;
	}
	
}