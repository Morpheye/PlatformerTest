package platformerTest.assets.triggers;

import java.awt.Graphics;

import platformerTest.game.Player;
import platformerTest.menu.GamePanel;

public class CameraSizeTrigger extends Trigger {

	public boolean visible;
	public int cameraSize;
	
	public CameraSizeTrigger(double x, double y, double size_x, double size_y, int cameraSize, boolean visible) {
		super(x, y, size_x, size_y);
		
		this.cameraSize = cameraSize;
		this.visible = visible;
		
	}
	
	@Override
	public void run() {
		GamePanel.target_camera_size = this.cameraSize;
	}
	
	@Override
	public void draw(Graphics g, Player player, double cam_x, double cam_y, double size) {
		if (!this.visible) return; 
	}

}
