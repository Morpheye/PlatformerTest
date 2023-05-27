package skycubedPlatformer.assets.contraptions;

import java.awt.Graphics;

import skycubedPlatformer.Main;
import skycubedPlatformer.assets.DecorationObject;
import skycubedPlatformer.game.GameObject;
import skycubedPlatformer.game.ObjType;
import skycubedPlatformer.game.Player;

public class Ladder extends DecorationObject {

	public Ladder(double x, double y, double size_y) {
		super(x, y, 40, size_y, GameObject.COLOR_WOOD);
		
		this.type = ObjType.Ladder;
		this.drawLayer = -6;
	}
	
	@Override
	public void draw(Graphics g, Player player, double cam_x, double cam_y, double size) {
		int drawX1 = (int) ( (this.x - (this.size_x)/2 - (cam_x - size/2)) * (Main.SIZE/size));
		int drawY = (int) ( (size - (this.y + (this.size_y)/2) + (cam_y - size/2)) * (Main.SIZE/size));
		int drawX2 = drawX1 + (int) ((this.size_x - 8) * (Main.SIZE/size));
		
		//draw rungs
		g.setColor(GameObject.COLOR_PLANKS);
		int drawX3 = drawX1 + (int) (2 * (Main.SIZE/size));
		for (int i=0; i<this.size_y/20-1; i++) {
			g.fillRect(drawX3, drawY+(int)((i*20+10)*(Main.SIZE/size)), (int) ((this.size_x-4) * Main.SIZE/size), (int) (8 * Main.SIZE/size));
		}
		
		//draw stems
		g.setColor(this.color);
		g.fillRoundRect(drawX1, drawY, (int) (8 * Main.SIZE/size), (int) (this.size_y * Main.SIZE/size), 
		(int)(5*(Main.SIZE/size)), (int)(5*(Main.SIZE/size)));

		g.fillRoundRect(drawX2, drawY, (int) (8 * Main.SIZE/size), (int) (this.size_y * Main.SIZE/size), 
		(int)(5*(Main.SIZE/size)), (int)(5*(Main.SIZE/size)));
		

	}

}
