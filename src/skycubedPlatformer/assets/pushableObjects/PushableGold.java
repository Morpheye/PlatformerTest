package skycubedPlatformer.assets.pushableObjects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import skycubedPlatformer.Main;
import skycubedPlatformer.assets.PushableObject;
import skycubedPlatformer.assets.decoration.particles.CoinParticle;
import skycubedPlatformer.game.GameObject;
import skycubedPlatformer.game.Player;

public class PushableGold extends PushableObject {

	public PushableGold(double x, double y, double size_x, double size_y) {
		super(x, y, size_x, size_y, GameObject.COLOR_GOLD, 13, 0.965);
		
	}
	
	@Override
	public void draw(Graphics g, Player player, double cam_x, double cam_y, double size) {
		super.draw(g, player, cam_x, cam_y, size);
		
		int brushSize = (int)(5*(Main.SIZE/size));
		
		int drawX = (int) ( (this.x + brushSize/2 - (this.size_x)/2 - (cam_x - size/2)) * (Main.SIZE/size));
		int drawY = (int) ( (size - (this.y - brushSize/2 + (this.size_y)/2) + (cam_y - size/2)) * (Main.SIZE/size));
		
		//shine
		int drawX2 = (int) ( (this.x - (this.size_x)/2 - (cam_x - size/2)) * (Main.SIZE/size));
		int drawY2 = (int) ( (size - (this.y + (this.size_y)/2) + (cam_y - size/2)) * (Main.SIZE/size));
		Graphics2D g2d = (Graphics2D) g;
		g2d.setPaint(new GradientPaint(drawX2, drawY2, new Color(255, 255, 255, 200),
				drawX2+(int)(this.size_x* Main.SIZE/size), drawY2+(int)(this.size_y* Main.SIZE/size), new Color(255, 255, 255, 0)));
		g2d.fillRect(drawX2, drawY2, (int)(this.size_x* Main.SIZE/size), (int)(this.size_y* Main.SIZE/size));
		
		g.setColor(this.color.darker());
		((Graphics2D) g).setStroke(new BasicStroke(brushSize));
		g.drawRoundRect(drawX-1, drawY-1, (int) ((this.size_x-brushSize+2) * Main.SIZE/size), (int) ((this.size_y-brushSize+2) * Main.SIZE/size), 
		(int)(4*(Main.SIZE/size)), (int)(4*(Main.SIZE/size)));
	}
	
	@Override
	public void crush() {
		CoinParticle.spawnCoins(this.x, this.y, 1, 20);
		super.crush();
	}

}
