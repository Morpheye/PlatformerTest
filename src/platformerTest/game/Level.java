package platformerTest.game;

import java.awt.Color;
import java.awt.Graphics;

import platformerTest.Main;
import platformerTest.levels.LevelWorld;

public class Level {

	public Color backgroundColor;
	public double airDrag = 0.97;
	public double gravity = -0.5;
	
	public double topLimit = 50000;
	public double bottomLimit = -1000;
	
	public double spawnX = 0;
	public double spawnY = 0;
	
	public Level() {
		
	}
	
	public void drawPlatforms() {}
	
	public void onTick() {}
	
	public void drawForeground() {}
	
	public void drawBackground() {}
	
	public void drawAmbience(Graphics g) {
		
	}
	
	public static final Color COLOR_DAYSKY = new Color(150, 225, 255);

}
