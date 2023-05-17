package skycubedPlatformer.assets;

import java.awt.Graphics;

import skycubedPlatformer.assets.triggers.Code;
import skycubedPlatformer.game.ObjType;
import skycubedPlatformer.game.Player;

public class Trigger extends DecorationObject {

	public Code code;
	public boolean visible;
	
	public Trigger(double x, double y, double size_x, double size_y, Code code) {
		super(x, y, size_x, size_y, null);
		this.type = ObjType.Trigger;
		this.code = code;
		this.visible = false;
		
	}
	
	public void run() {
		if (this.code != null) code.run();
		
	}
	
	@Override
	public void draw(Graphics g, Player player, double cam_x, double cam_y, double size) {
		if (!this.visible) return; 
	}
	
}
