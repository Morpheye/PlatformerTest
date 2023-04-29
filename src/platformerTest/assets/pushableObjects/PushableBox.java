package platformerTest.assets.pushableObjects;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;

import platformerTest.Main;
import platformerTest.assets.PushableObject;
import platformerTest.game.GameObject;
import platformerTest.game.Player;

public class PushableBox extends PushableObject {

	public PushableBox(double x, double y, double size_x, double size_y) {
		super(x, y, size_x, size_y, GameObject.COLOR_PLANKS, 0.8, 0.965);
		
	}
	
	@Override
	public void draw(Graphics g, Player player, double cam_x, double cam_y, double size) {
		super.draw(g, player, cam_x, cam_y, size);
		
		int brushSize = 5*((int)(Main.SIZE/size));
		
		int drawX = (int) ( (this.x + brushSize/2 - (this.size_x)/2 - (cam_x - size/2)) * (Main.SIZE/size));
		int drawY = (int) ( (size - (this.y - brushSize/2 + (this.size_y)/2) + (cam_y - size/2)) * (Main.SIZE/size));
		
		g.setColor(GameObject.COLOR_WOOD);
		((Graphics2D) g).setStroke(new BasicStroke(brushSize));
		g.drawRoundRect(drawX, drawY, (int) ((this.size_x-brushSize/2) * Main.SIZE/size), (int) ((this.size_y-brushSize/2) * Main.SIZE/size), 
		(int)(5*(Main.SIZE/size)), (int)(5*(Main.SIZE/size)));
	}

}
