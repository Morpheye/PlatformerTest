package skycubedPlatformer.assets.contraptions;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import skycubedPlatformer.Main;
import skycubedPlatformer.assets.SolidPlatform;
import skycubedPlatformer.game.Player;
import skycubedPlatformer.menu.GamePanel;

public class ConveyorBelt extends SolidPlatform {
	
	double real_vx;
	double conveyorSpeed;
	
	public ConveyorBelt(double x, double y, double size_x, double size_y, double vx) {
		super(x, y, size_x, size_y, Color.BLACK);
		this.conveyorSpeed = vx;
		this.real_vx = 0;
		this.slipperiness = 0.94;
	}
	
	public void move() {
		this.vx = real_vx;
		this.scroll += 1 * (Main.SIZE/GamePanel.camera_size);
		if (this.scroll > this.size_x) this.scroll -= this.size_x;
		
		super.move();
		
		this.vx = this.conveyorSpeed;
		
	}
	
	double scroll = 0;
	
	@Override
	public void draw(Graphics g, Player player, double x, double y, double size) {
		super.draw(g, player, x, y, size);
		
		//gradient here
		Graphics2D g2d = (Graphics2D) g;
		int drawX = (int) ( (this.x - (this.size_x)/2 - (x - size/2)) * (Main.SIZE/size));
		int drawY = (int) ( (size - (this.y + (this.size_y)/2) + (y - size/2)) * (Main.SIZE/size));
		GradientPaint gradient = new GradientPaint((int)(drawX+(scroll*this.conveyorSpeed)), drawY, new Color(255,255,255,100), 
			(int)(drawX+(scroll*this.conveyorSpeed)+(75)), drawY, new Color(0,0,0,0), true);
		g2d.setPaint(gradient);
		g2d.fillRect(drawX, drawY, (int) ((this.size_x) * Main.SIZE/size), (int) ((this.size_y) * Main.SIZE/size));
		
		//draw border
		int brushSize = (int)(5*(Main.SIZE/size));
		int borderX = (int) ( (this.x + brushSize/2 - (this.size_x)/2 - (x - size/2)) * (Main.SIZE/size));
		int borderY = (int) ( (size - (this.y - brushSize/2 + (this.size_y)/2) + (y - size/2)) * (Main.SIZE/size));
		
		g.setColor(Color.BLACK);
		((Graphics2D) g).setStroke(new BasicStroke(brushSize));
		g.drawRoundRect(borderX-1, borderY-1, (int) ((this.size_x-brushSize+2) * Main.SIZE/size), (int) ((this.size_y-brushSize+2) * Main.SIZE/size), 
		(int)(4*(Main.SIZE/size)), (int)(4*(Main.SIZE/size)));
	}

}
