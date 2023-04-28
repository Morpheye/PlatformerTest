package platformerTest.assets.triggers;

import java.awt.Graphics;

import platformerTest.assets.DecorationObject;
import platformerTest.game.ObjType;
import platformerTest.game.Player;

public class Trigger extends DecorationObject {

	public Trigger(double x, double y, double size_x, double size_y) {
		super(x, y, size_x, size_y, null);
		this.type = ObjType.Trigger;
		
	}
	
	public void run() {
		
	}
	
	@Override
	public void draw(Graphics g, Player player, double cam_x, double cam_y, double size) {
		
	}
	
}
