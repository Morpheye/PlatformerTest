package platformerTest;

import java.awt.Color;
import java.awt.Graphics;

import platformerTest.game.MovableObject;
import platformerTest.game.ObjType;
import platformerTest.game.Player;

public class Projectile extends MovableObject {

	int lifetime;
	
	public Projectile(double x, double y, double size_x, double size_y, double vx, double vy) {
		super(x, y, size_x, size_y, Color.black, 1);
		this.vx = vx;
		this.vy = vy;
		this.lifetime = 0;
		this.type = ObjType.Projectile;
	}
	
	@Override
	public void move() {
		
	}
	
	@Override
	public void draw(Graphics g, Player player, double cam_x, double cam_y, double size) {
		int drawX = (int) ( (this.x - (this.size_x)/2 - (cam_x - size/2)) * (Main.SIZE/size));
		int drawY = (int) ( (size - (this.y + (this.size_y)/2) + (cam_y - size/2)) * (Main.SIZE/size));

	}
	
}
