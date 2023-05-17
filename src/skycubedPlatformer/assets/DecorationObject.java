package skycubedPlatformer.assets;

import java.awt.Color;
import java.awt.Graphics;

import skycubedPlatformer.game.GameObject;
import skycubedPlatformer.game.ObjType;
import skycubedPlatformer.game.Player;

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