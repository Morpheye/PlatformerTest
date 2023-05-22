package skycubedPlatformer.levels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

import skycubedPlatformer.Main;
import skycubedPlatformer.menu.ApplicationFrame;
import skycubedPlatformer.menu.GamePanel;

public class Level {

	/**
	 * Only for if generation fails
	 */
	public Color backgroundColor;
	public double airDrag = 0.97;
	public double gravity = -0.5;
	
	public double topLimit = 50000;
	public double bottomLimit = -1000;
	
	public double spawnX = 0;
	public double spawnY = 0;
	
	public String name;
	public int reward = 50;
	public String[] reqs;
	
	public boolean isRaining = false;
	public boolean isStorming = false;
	
	public Level() {
		
	}
	
	
	public void drawPlatforms() {}
	
	public void onTick() {}
	
	public void fill(Graphics2D g) {
		g.setColor(this.backgroundColor);
		g.fillRect(-50, -50, Main.SIZE+50, Main.SIZE+50);
	}
	
	public void drawForeground() {}
	
	public void drawBackground() {}
	
	public void drawAmbience(Graphics g) {}
	
	public void onStart(GamePanel panel) {}
	
	public void moveCamera() { //DEFAULT CAMERA MOVEMENT (fully locked)
		GamePanel.camera_x = GamePanel.player.x;
		GamePanel.camera_y = GamePanel.player.y;
		
	}
	
	public void destroy() {
		
	}
	
	public static final Color COLOR_DAYSKY = new Color(150, 225, 255);
	public static final Color COLOR_DARKSKY = new Color(112, 146, 190);
	public static final Color COLOR_NIGHTSKY = new Color(0, 20, 70);
	
	public static void drawRoundScenery(Graphics g, Color color, int[] x, int[] y, int[] size, int scale) {
		g.setColor(color);
		for (int i=0; i<x.length; i++) {
			int drawX = x[i] - size[i]/2 - (int) ((GamePanel.camera_x + ((GamePanel) ApplicationFrame.current).shake_x)/scale);
			int drawY = Main.SIZE - (int)(y[i] - (GamePanel.camera_y + ((GamePanel) ApplicationFrame.current).shake_y)/scale);
			g.fillRoundRect(drawX, drawY, size[i], Main.SIZE*10, 150, 150);
		}
	}
	
	
	public static void drawFloorScenery(Graphics g, Color color, int y, int scale) {
		g.setColor(color);
		g.fillRect(0, Main.SIZE-(y - (int)((GamePanel.camera_y + ((GamePanel) ApplicationFrame.current).shake_y) / scale)), Main.SIZE, Main.SIZE*10);
	}

}
