package platformerTest.assets.projectiles;

import java.awt.Graphics;

import platformerTest.Main;
import platformerTest.Projectile;
import platformerTest.game.Player;

public class ProjectileDart extends Projectile {

	public ProjectileDart(double x, double y, double vx, double vy) {
		super(x, y, 5, 5, vx, vy);
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
