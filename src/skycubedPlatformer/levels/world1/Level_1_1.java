package skycubedPlatformer.levels.world1;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.util.List;

import skycubedPlatformer.Main;
import skycubedPlatformer.assets.SolidPlatform;
import skycubedPlatformer.assets.interactables.FinishFlag;
import skycubedPlatformer.assets.liquidPlatforms.WaterPlatform;
import skycubedPlatformer.assets.pushableObjects.PushableBox;
import skycubedPlatformer.assets.pushableObjects.PushableStone;
import skycubedPlatformer.assets.solidPlatforms.GrassPlatform;
import skycubedPlatformer.assets.triggers.TextDisplayTrigger;
import skycubedPlatformer.game.GameObject;
import skycubedPlatformer.levels.Level;
import skycubedPlatformer.menu.GamePanel;

public class Level_1_1 extends Level {
	
	public Level_1_1() {
		this.backgroundColor = COLOR_DAYSKY;
		
		this.spawnX = -100; //-100
		this.spawnY = 250; //250
		
		this.name = "Islands";
		
		this.reqs = new String[] {};
		this.reward = 50;
		
		//this.spawnX = 7650;
		//this.spawnY = 700;

	}
	
	int[] Fx1 = {0, 100, 600, 800, 1100};
	int[] Fy1 = {700, 500, 400, 600, 300};
	int[] Fsize1 = {250, 250, 400, 250, 500};
	
	int[] Fx2 = {-100, 200, 300, 500, 700, 800, 900, 1200, 1400, 1500, 1800, 1900, 2300};
	int[] Fy2 = {400, 350, 500, 400, 250, 550, 400, 550, 300, 500, 550, 350, 400};
	int[] Fsize2 = {175, 250, 150, 175, 150, 150, 200, 175, 150, 225, 150, 250, 200};
	
	@Override
	public void fill(Graphics2D g) {
		Color gc1 = new Color(180, 235, 235);
		Color gc2 = new Color(130, 205, 235);
		g.setPaint(new GradientPaint(0,0,gc1,Main.SIZE,Main.SIZE,gc2));
		g.fillRect(-50,-50,Main.SIZE+50,Main.SIZE+50);

		drawRoundScenery(g, GameObject.COLOR_GRASS.darker().darker().darker(), Fx1, Fy1, Fsize1, 10);
		drawFloorScenery(g, GameObject.COLOR_GRASS.darker().darker().darker(), 150, 10);
		drawRoundScenery(g, GameObject.COLOR_GRASS.darker().darker(), Fx2, Fy2, Fsize2, 5);

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
	public void onStart() {
		GamePanel.displayText("Use WASD to move around.", 240);
	}
	
	SolidPlatform movingPlatform;
	SolidPlatform movingPlatform2;
	
	@Override
	public void drawBackground() {
		List<GameObject> objects = GamePanel.objects;
		
		objects.add(new FinishFlag(7750, 700, 50, 100));
	}
	
	@Override
	public void drawPlatforms() {
		List<GameObject> objects = GamePanel.objects;
		
		//spawn
		objects.add(new GrassPlatform(0, 100, 500, 200));
		objects.add(new GrassPlatform(400, 125, 500, 250));
		objects.add(new GrassPlatform(450, 0, 400, 200));
		
		//first island
		objects.add(new GrassPlatform(1000, 250, 200, 50));
		objects.add(new GrassPlatform(1100, 300, 200, 150));
		objects.add(new GrassPlatform(800, 500, 150, 50));
		
		//water
		objects.add(new TextDisplayTrigger(1500, 450, 100, 100, "Most liquids can be swum in.", 240));
		objects.add(new WaterPlatform(1875, 200, 800, 395));
		objects.add(new GrassPlatform(1500, 200, 200, 400));
		objects.add(new GrassPlatform(2250, 200, 200, 400));
		objects.add(new GrassPlatform(1875, 400, 200, 350));
		objects.add(new GrassPlatform(1875, 50, 925, 100));
		
		//moving platform
		movingPlatform = new GrassPlatform(2650, 300, 200, 50);
		movingPlatform.vy = 1;
		objects.add(movingPlatform);
		
		objects.add(new GrassPlatform(3050, 500, 200, 200));
		
		//moving platform2
		movingPlatform2 = new GrassPlatform(3350, 500, 200, 50);
		movingPlatform2.vx = 1.5;
		objects.add(movingPlatform2);
		
		objects.add(new GrassPlatform(4000, 500, 200, 200));
		
		//movable object platform
		objects.add(new TextDisplayTrigger(4450, 525, 100, 100, "Some objects can be pushed.", 240));
		objects.add(new GrassPlatform(4900, 450, 200, 400));
		objects.add(new GrassPlatform(4700, 350, 600, 200));
		objects.add(new GrassPlatform(4450, 375, 100, 200));
		objects.add(new PushableStone(4550, 600, 60, 60));
		
		//floating objet platform
		objects.add(new TextDisplayTrigger(5300, 500, 100, 100, "Different objects have different densities.", 240));
		objects.add(new PushableBox(5750, 500, 60, 60));
		objects.add(new PushableStone(5300, 500, 60, 60));
		objects.add(new WaterPlatform(5800, 250, 1025, 395));
		objects.add(new GrassPlatform(5650, 325, 300, 250));
		objects.add(new GrassPlatform(5550, 340, 100, 280));
		objects.add(new GrassPlatform(5300, 150, 100, 600));
		objects.add(new GrassPlatform(6250, 230, 200, 800));
		objects.add(new GrassPlatform(5775, -50, 925, 400));

		
		//islands
		objects.add(new TextDisplayTrigger(6700, 600, 100, 100, "Reach the finish flag to complete the level.", 240));
		objects.add(new GrassPlatform(6700, 500, 300, 100));
		
		objects.add(new GrassPlatform(7200, 550, 300, 100));

		//finish platform
		objects.add(new GrassPlatform(7750, 550, 500, 200));
		
	}
	
	@Override
	public void onTick() {
		if (movingPlatform.y >= 600) movingPlatform.vy = -1;
		else if (movingPlatform.y <= 300) movingPlatform.vy = 1;
		
		if (movingPlatform2.x >= 3700) movingPlatform2.vx = -1.5;
		else if (movingPlatform2.x <= 3350) movingPlatform2.vx = 1.5;
		
		super.onTick();
		
	}
	
	
}
