package skycubedPlatformer.assets.decoration.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import skycubedPlatformer.Main;
import skycubedPlatformer.assets.DecorationObject;
import skycubedPlatformer.game.Player;

public class Gravestone extends DecorationObject {

	DecorationObject stone;
	DecorationObject cross;
	
	public Gravestone(double x, double y, double size_x, double size_y) {
		super(x, y, size_x, size_y, Color.DARK_GRAY);
		this.drawLayer = -7;
		
		if (size_x > size_y) { 
			this.stone = new DecorationObject(x, y, size_x, size_y, Color.DARK_GRAY);
			
		} else {
			this.stone = new DecorationObject(x, y-size_y/4, size_x, size_y/2, Color.DARK_GRAY);
			this.cross = new DecorationObject(x, y+size_y/4, size_x/5, size_y/2, Color.GRAY);
		}
		
	}
	
	@Override
	public void move() {
		this.stone.vx = this.vx;
		this.stone.vy = this.vy;
		
		if (this.cross != null) {
			this.cross.vx = this.vx;
			this.cross.vy = this.vy;
			this.cross.x += this.cross.vx;
			this.cross.y += this.cross.vy;
		}
		
		this.stone.x += this.stone.vx;
		this.stone.y += this.stone.vy;

		
		this.x += this.vx;
		this.y += this.vy;
	}
	
	@Override
	public void draw(Graphics g, Player player, double cam_x, double cam_y, double size) {
		Graphics2D g2d = (Graphics2D) g;
		if (size_x > size_y) { 
			int drawX = (int) ( (this.stone.x - (this.stone.size_x)/2 - (cam_x - size/2)) * (Main.SIZE/size));
			int drawY = (int) ( (size - (this.stone.y + (this.stone.size_y)/2) + (cam_y - size/2)) * (Main.SIZE/size));
			
			g.setColor(this.stone.color);
			g.fillRoundRect(drawX, drawY, (int) (this.stone.size_x * Main.SIZE/size), (int) (this.stone.size_y * Main.SIZE/size), 
			(int)(this.size_y/2*(Main.SIZE/size)), (int)(this.size_y/2*(Main.SIZE/size)));
			g.fillRect(drawX, drawY+(int)(this.stone.size_y/2*(Main.SIZE/size)), (int) (this.stone.size_x * Main.SIZE/size),
					(int) (this.stone.size_y * Main.SIZE/size - (int)(this.stone.size_y/2*(Main.SIZE/size))));
			
		} else {
			//cross stem
			int drawX = (int) ( (this.cross.x - (this.cross.size_x)/2 - (cam_x - size/2)) * (Main.SIZE/size));
			int drawY = (int) ( (size - (this.cross.y + (this.cross.size_y)/2) + (cam_y - size/2)) * (Main.SIZE/size));
			g.setColor(this.cross.color);
			g.fillRect(drawX, drawY, (int) (this.cross.size_x * Main.SIZE/size), (int) (this.cross.size_y*1.5* Main.SIZE/size));
			
			//cross horizontal part
			drawX = (int) ( (this.stone.x - (this.stone.size_x)/3 - (cam_x - size/2)) * (Main.SIZE/size));
			drawY = (int) ( (size - (this.cross.y + (this.cross.size_y)/4) + (cam_y - size/2)) * (Main.SIZE/size));
			g.fillRect(drawX, drawY, (int) (this.stone.size_x*2/3 * Main.SIZE/size), (int) (this.cross.size_x* Main.SIZE/size));
			
			//then stone
			drawX = (int) ( (this.stone.x - (this.stone.size_x)/2 - (cam_x - size/2)) * (Main.SIZE/size));
			drawY = (int) ( (size - (this.stone.y + (this.stone.size_y)/2) + (cam_y - size/2)) * (Main.SIZE/size));
			
			g.setColor(this.stone.color);
			g.fillRoundRect(drawX, drawY, (int) (this.stone.size_x * Main.SIZE/size), (int) (this.stone.size_y * Main.SIZE/size), 
			(int)(this.size_y/2*(Main.SIZE/size)), (int)(this.size_y/2*(Main.SIZE/size)));
			g.fillRect(drawX, drawY+(int)(this.stone.size_y/2*(Main.SIZE/size)), (int) (this.stone.size_x * Main.SIZE/size),
					(int) (this.stone.size_y * Main.SIZE/size - (int)(this.stone.size_y/2*(Main.SIZE/size))));

			
		}
	}

}
