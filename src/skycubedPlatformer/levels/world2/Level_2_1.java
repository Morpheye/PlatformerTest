package skycubedPlatformer.levels.world2;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import skycubedPlatformer.Main;
import skycubedPlatformer.assets.SolidPlatform;
import skycubedPlatformer.assets.solidPlatforms.StonePlatform;
import skycubedPlatformer.game.GameObject;
import skycubedPlatformer.levels.Level;
import skycubedPlatformer.levels.world1.World1;
import skycubedPlatformer.menu.GamePanel;

public class Level_2_1 extends Level {
	
	public Level_2_1() {
		this.backgroundColor = COLOR_DAYSKY;
		
		this.spawnX = 0; //0
		this.spawnY = 200; //200
		
		this.name = "Low Friction";
		
		this.reqs = (new World1()).levelList();
		this.reward = 70;

	}
	
	int[] Fx1 = {0, 100, 600, 800, 1100};
	int[] Fy1 = {700, 500, 700, 1000, 400};
	int[] Fsize1 = {250, 250, 400, 250, 500};
	
	int[] Fx2 = {-100, 200, 300, 500, 700, 800, 900, 1200, 1400, 1500, 1800, 1900, 2300};
	int[] Fy2 = {400, 350, 500, 400, 250, 550, 400, 550, 300, 500, 550, 350, 400};
	int[] Fsize2 = {175, 250, 150, 175, 150, 150, 200, 175, 150, 225, 150, 250, 200};
	
	@Override
	public void fill(Graphics2D g) {
		Color gc1 = new Color(160, 215, 215);
		Color gc2 = new Color(110, 185, 215);
		g.setPaint(new GradientPaint(0,0,gc1,Main.SIZE,Main.SIZE,gc2));
		g.fillRect(-50,-50,Main.SIZE+50,Main.SIZE+50);

		drawRoundScenery(g, GameObject.COLOR_STONE.brighter().brighter(), Fx1, Fy1, Fsize1, 7);
		drawFloorScenery(g, GameObject.COLOR_STONE.brighter().brighter(), 150, 7);
		drawRoundScenery(g, Color.white.darker(), Fx2, Fy2, Fsize2, 4);

	}
	
	@Override
	public void moveCamera() { //loosely locked
		double diffX = GamePanel.player.x - GamePanel.camera_x;
		double diffY = GamePanel.player.y - GamePanel.camera_y;
		
		int higherLimitX = 0;
		int lowerLimitX = -100;
		int higherLimitY = 100;
		int lowerLimitY = -100;
		
		if (diffX > higherLimitX) GamePanel.camera_x = GamePanel.player.x - higherLimitX;
		if (diffX < lowerLimitX) GamePanel.camera_x = GamePanel.player.x - lowerLimitX;
		if (diffY > higherLimitY) GamePanel.camera_y = GamePanel.player.y - higherLimitY;
		if (diffY < lowerLimitY) GamePanel.camera_y = GamePanel.player.y - lowerLimitY;
		
		if (GamePanel.camera_y < 400) GamePanel.camera_y = 400;
	}
	
	@Override
	public void onStart(GamePanel panel) {
	}
	
	@Override
	public void drawBackground() {
		List<GameObject> objects = GamePanel.objects;
	}
	
	@Override
	public void drawPlatforms() {
		List<GameObject> objects = GamePanel.objects;
		
		//spawn
		objects.add(new StonePlatform(0, 50, 400, 100));
		
	}
	
	@Override
	public void onTick() {
		super.onTick();
		
	}
	
	
}
