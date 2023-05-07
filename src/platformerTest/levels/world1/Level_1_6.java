package platformerTest.levels.world1;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.util.List;

import platformerTest.Main;
import platformerTest.assets.effects.EffectPoison;
import platformerTest.assets.solidPlatforms.SandPlatform;
import platformerTest.game.GameObject;
import platformerTest.levels.Level;
import platformerTest.menu.GamePanel;

public class Level_1_6 extends Level {

	public Level_1_6() {
		this.backgroundColor = COLOR_DAYSKY;
		
		this.spawnX = 0; //0
		this.spawnY = 200; //200

		this.reqs = new String[] {"Level_1_5"};
		this.reward = 70;
		this.name = "Sky-High Oasis";
		
	}
	
	int[] Fx1 = {0, 100, 600, 800, 1100, 1400, 1600};
	int[] Fy1 = {400, 500, 700, 400, 300, 600, 700};
	int[] Fsize1 = {250, 250, 400, 250, 500, 250, 350};
	
	int[] Fx2 = {-100, 200, 300, 500, 700, 800, 900, 1100, 1200, 1400, 1500, 1800, 1900, 2200, 2300};
	int[] Fy2 = {400, 350, 500, 400, 250, 550, 400, 300, 550, 300, 500, 550, 350, 600, 400};
	int[] Fsize2 = {200, 175, 250, 200, 125, 275, 200, 150, 275, 150, 250, 275, 175, 300, 200};
	
	@Override
	public void fill(Graphics2D g) {
		Color gc1 = new Color(180, 235, 235);
		Color gc2 = new Color(130, 205, 235);
		g.setPaint(new GradientPaint(0,0,gc1,Main.SIZE,Main.SIZE,gc2));
		g.fillRect(-50,-50,Main.SIZE+50,Main.SIZE+50);
		
		drawRoundScenery(g, GameObject.COLOR_GRASS.darker().darker().darker(), Fx1, Fy1, Fsize1, 8);
		drawFloorScenery(g, GameObject.COLOR_GRASS.darker().darker().darker(), 150, 8);
		drawRoundScenery(g, GameObject.COLOR_GRASS.darker().darker(), Fx2, Fy2, Fsize2, 5);
	}
	
	@Override
	public void onStart() {
		GamePanel.camera_x = GamePanel.player.x;
		GamePanel.camera_y = GamePanel.player.y;
		GamePanel.displayText("Don't get knocked off!", 240);
	}
	
	@Override
	public void drawBackground() {
		List<GameObject> objects = GamePanel.objects;


	}
	
	@Override
	public void drawForeground() {
		List<GameObject> objects = GamePanel.objects;

	}
	
	@Override
	public void drawPlatforms() {
		List<GameObject> objects = GamePanel.objects;
		
		//spawn platform
		objects.add(new SandPlatform(0, 50, 400, 100));
		
	}
	
	@Override
	public void onTick() {
		
		
	}
	
	@Override
	public void moveCamera() {
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
		
	}
	
}
