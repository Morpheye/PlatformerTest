package platformerTest.assets.interactables;

import java.awt.Color;
import java.awt.Graphics;

import platformerTest.Main;
import platformerTest.assets.DecorationObject;
import platformerTest.game.ObjType;
import platformerTest.game.Player;

public class NullZone extends DecorationObject {

	static Color color1 = new Color(255, 0, 0, 50);
	static Color color2 = new Color(255, 0, 0, 100);
	
	public NullZone(double x, double y, double size_x, double size_y) {
		super(x, y, size_x, size_y, color1);
		this.type = ObjType.NullZone;
		// TODO Auto-generated constructor stub
	}
	
	public void draw(Graphics g, Player player, double cam_x, double cam_y, double size) { //move object center to top left edge
		if (player.hasCollided(this)) this.color = color2;
		else this.color = color1;

		
		super.draw(g, player, cam_x, cam_y, size);
	}

}
