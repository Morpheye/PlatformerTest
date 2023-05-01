package platformerTest.assets.decoration.walls;

import java.awt.Color;
import java.awt.Graphics;

import platformerTest.Main;
import platformerTest.assets.DecorationObject;
import platformerTest.game.Player;
import platformerTest.menu.GamePanel;

public class FadingWallObject extends DecorationObject {
	
	public FadingWallObject(double x, double y, double size_x, double size_y, Color color) {
		super(x, y, size_x, size_y, color);
	}
	
	int alpha = 255;
	
	@Override
	public void draw(Graphics g, Player player, double cam_x, double cam_y, double size) { //move object center to top left edge
		
		if (this.hasCollided(GamePanel.player)) {
			if (this.alpha > 0) this.alpha -= 5; 
		} else {
			if (this.alpha < 255) this.alpha += 5;
		}
		
		int drawX = (int) ( (this.x - (this.size_x)/2 - (cam_x - size/2)) * (Main.SIZE/size));
		int drawY = (int) ( (size - (this.y + (this.size_y)/2) + (cam_y - size/2)) * (Main.SIZE/size));
		
		g.setColor(new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), this.alpha));
		
		g.fillRoundRect(drawX, drawY, (int) (this.size_x * Main.SIZE/size), (int) (this.size_y * Main.SIZE/size), 
		(int)(5*(Main.SIZE/size)), (int)(5*(Main.SIZE/size)));
	}


}
