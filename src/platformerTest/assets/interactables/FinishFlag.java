package platformerTest.assets.interactables;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

import platformerTest.Main;
import platformerTest.assets.DecorationObject;
import platformerTest.game.GameObject;
import platformerTest.game.ObjType;
import platformerTest.game.Player;

public class FinishFlag extends DecorationObject {

	DecorationObject flag;
	DecorationObject pole;
	
	public FinishFlag(double x, double y, double size_x, double size_y) {
		super(x, y, size_x, size_y, null);
		
		if (!(size_y > size_x)) return;
		
		this.flag = new DecorationObject(x, (y+size_y/2-(size_x*0.375)), size_x, size_x*0.75, Color.RED);
		this.pole = new DecorationObject((x - this.flag.size_x/2), y, 5, size_y, GameObject.COLOR_WOOD);
		
		this.type = ObjType.FinishFlag;
		
	}
	
	@Override
	public void draw(Graphics g, Player player, double cam_x, double cam_y, double size) {
		
		//flag
		int drawX = (int) ( (this.flag.x - (this.flag.size_x)/2 - (cam_x - size/2)) * (Main.SIZE/size));
		int drawY = (int) ( (size - (this.flag.y + (this.flag.size_y)/2) + (cam_y - size/2)) * (Main.SIZE/size));
		
		g.setColor(this.flag.color);
		g.fillRect(drawX, drawY, (int) (this.flag.size_x * Main.SIZE/size), (int) (this.flag.size_y * Main.SIZE/size));

		
		//pole
		drawX = (int) ( (this.pole.x - (this.pole.size_x)/2 - (cam_x - size/2)) * (Main.SIZE/size));
		drawY = (int) ( (size - (this.pole.y + (this.pole.size_y)/2) + (cam_y - size/2)) * (Main.SIZE/size));
		
		g.setColor(this.pole.color);
		g.fillRect(drawX, drawY, (int) (this.pole.size_x * Main.SIZE/size), (int) (this.pole.size_y * Main.SIZE/size));
	}
	
	@Override
	public boolean hasCollided(GameObject obj) {
		return (this.flag.hasCollided(obj) || this.pole.hasCollided(obj));
	}
	
}
