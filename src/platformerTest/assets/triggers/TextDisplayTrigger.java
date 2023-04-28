package platformerTest.assets.triggers;

import java.awt.Graphics;

import platformerTest.assets.DecorationObject;
import platformerTest.assets.Trigger;
import platformerTest.game.ObjType;
import platformerTest.game.Player;
import platformerTest.menu.GamePanel;

public class TextDisplayTrigger extends Trigger {

	public String text;
	public int displayTime;
	
	public TextDisplayTrigger(double x, double y, double size_x, double size_y, String text, int displayTime) {
		super(x, y, size_x, size_y, null);
		
		this.text = text;
		this.displayTime = displayTime;
	}
	
	@Override
	public void run() {
		super.run();
		GamePanel.displayText(this.text, this.displayTime);
	}
	
	@Override
	public void draw(Graphics g, Player player, double cam_x, double cam_y, double size) {
		//don't draw
	}

}
