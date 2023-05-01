package platformerTest.levels;

import java.awt.Color;
import java.awt.Graphics;

import platformerTest.Main;
import platformerTest.menu.GamePanel;

public class Level {

	public Color backgroundColor;
	public double airDrag = 0.97;
	public double gravity = -0.5;
	
	public double topLimit = 50000;
	public double bottomLimit = -1000;
	
	public double spawnX = 0;
	public double spawnY = 0;
	
	public String name;
	
	public String[] reqs;
	
	public Level() {
		
	}
	
	public void drawPlatforms() {}
	
	public void onTick() {}
	
	public void drawForeground() {}
	
	public void drawBackground() {}
	
	public void drawAmbience(Graphics g) {}
	
	public void onStart() {}
	
	public void moveCamera() { //DEFAULT CAMERA MOVEMENT (fully locked)

		GamePanel.camera_x = GamePanel.player.x;
		GamePanel.camera_y = GamePanel.player.y;
		
	}
	
	public static final Color COLOR_DAYSKY = new Color(150, 225, 255);
	public static final Color COLOR_DARKSKY = new Color(112, 146, 190);
	public static final Color COLOR_NIGHTSKY = new Color(0, 20, 70);

}
