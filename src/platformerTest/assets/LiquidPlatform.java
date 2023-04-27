package platformerTest.assets;

import java.awt.Color;
import java.awt.Graphics;

import platformerTest.Main;
import platformerTest.game.GameObject;
import platformerTest.game.ObjType;
import platformerTest.game.Player;

public class LiquidPlatform extends GameObject {
	
	public double slipperiness = 0.6;

	public LiquidPlatform(double x, double y, double size_x, double size_y, Color color) {
		super(x, y, size_x, size_y, color);
		
		this.type = ObjType.LiquidPlatform;
		
		this.movable = false;
		this.solid = false;
	}
	
	@Override
	public void draw(Graphics g, Player player, double x, double y, double size) {
		int drawX = (int) (this.x - this.size_x/2 - (x - Main.SIZE_X/2));
		int drawY = (int) (Main.SIZE_Y - (this.y + this.size_y/2) + (y - Main.SIZE_Y/2));
		
		g.setColor(this.color);
		g.fillRoundRect(drawX, drawY, (int) this.size_x, (int) this.size_y, 5, 5);
	}
	
	@Override
	public void move() {
		this.y += this.vy;
		this.x += this.vx;
	}
	
}