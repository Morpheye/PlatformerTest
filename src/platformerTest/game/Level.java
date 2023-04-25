package platformerTest.game;

import java.awt.Color;

public class Level {
	
	public Color backgroundColor;
	public double drag;
	public double gravity;
	
	public double topLimit = 50000;
	public double bottomLimit = -1000;
	
	public double spawnX = 0;
	public double spawnY = 0;
	
	public Level() {
		
	}
	
	/**
	 * Called to draw all platforms and sprites in level.
	 */
	public void drawPlatforms() {}
	
	/**
	 * Called each frame to update level components
	 */
	public void onTick() {}

}
