package skycubedPlatformer.levels.world1;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.util.List;

import skycubedPlatformer.Main;
import skycubedPlatformer.assets.SolidPlatform;
import skycubedPlatformer.assets.decoration.walls.SolidBackgroundObject;
import skycubedPlatformer.assets.interactables.FinishFlag;
import skycubedPlatformer.assets.liquidPlatforms.WaterPlatform;
import skycubedPlatformer.assets.powerups.CameraSizePowerup;
import skycubedPlatformer.assets.powerups.JumpBoostPowerup;
import skycubedPlatformer.assets.pushableObjects.PushableBox;
import skycubedPlatformer.assets.pushableObjects.PushableStone;
import skycubedPlatformer.assets.solidPlatforms.GrassPlatform;
import skycubedPlatformer.assets.solidPlatforms.StonePlatform;
import skycubedPlatformer.assets.triggers.Code;
import skycubedPlatformer.assets.triggers.TextDisplayTrigger;
import skycubedPlatformer.game.GameObject;
import skycubedPlatformer.levels.Level;
import skycubedPlatformer.menu.ApplicationFrame;
import skycubedPlatformer.menu.GamePanel;

public class Level_1_2 extends Level {

	public Level_1_2() {
		this.backgroundColor = COLOR_DAYSKY;
		
		this.spawnX = 0; //0
		this.spawnY = 200; //200
		
		this.reqs = new String[] {"Level_1_1"};
		this.reward = 50;
		this.name = "Mountain Climbing";
		
	}
	
	int[] Fx1 = {0, 100, 600, 800, 1100};
	int[] Fy1 = {700, 500, 700, 1000, 400};
	int[] Fsize1 = {250, 250, 400, 250, 500};
	
	int[] Fx2 = {-100, 200, 300, 500, 700, 800, 900, 1200, 1400, 1500, 1800, 1900, 2300};
	int[] Fy2 = {400, 350, 500, 400, 250, 550, 400, 550, 300, 500, 550, 350, 400};
	int[] Fsize2 = {175, 250, 150, 175, 150, 150, 200, 175, 150, 225, 150, 250, 200};
	
	@Override
	public void fill(Graphics2D g) {
		Color gc1 = new Color(180, 235, 235);
		Color gc2 = new Color(130, 205, 235);
		g.setPaint(new GradientPaint(Main.SIZE,0,gc1,0,Main.SIZE,gc2));
		g.fillRect(-50,-50,Main.SIZE+50,Main.SIZE+50);
		
		drawRoundScenery(g, GameObject.COLOR_STONE.brighter().brighter(), Fx1, Fy1, Fsize1, 10);
		drawFloorScenery(g, GameObject.COLOR_STONE.brighter().brighter(), 150, 10);
		drawRoundScenery(g, Color.white.darker(), Fx2, Fy2, Fsize2, 5);
	}
	
	@Override
	public void onStart(GamePanel panel) {
		panel.displayText("Use boxes wisely, don't softlock yourself.", 300);
		
	}
	
	SolidPlatform crusherPlatform;
	
	SolidPlatform mountainCrusher;
	
	SolidPlatform mountainPlatform;
	
	@Override
	public void drawPlatforms(GamePanel panel) {
		List<GameObject> objects = panel.objects;

		//spawn platform
		objects.add(new GrassPlatform(0, 50, 400, 100));
		
		//box platform
		objects.add(new PushableBox(700, 180, 60, 60));
		objects.add(new GrassPlatform(700,325,300,50));
		objects.add(new GrassPlatform(825,200,50,300));
		objects.add(new GrassPlatform(625,100,450,100));
		objects.add(new SolidBackgroundObject(700,200,275,275,GameObject.COLOR_DIRT));
		objects.add(new SolidBackgroundObject(600,150,275,150,GameObject.COLOR_DIRT));
		
		objects.add(new GrassPlatform(1100,400,200,50));
		
		objects.add(new GrassPlatform(1500,500,200,50));
		
		//crusher platform
		objects.add(new TextDisplayTrigger(1850,550,200,200,"Caution: Crushing results in instant death.",300));
		objects.add(new StonePlatform(2100,500,600,100));
		objects.add(new StonePlatform(2100,750,300,150));
		objects.add(new SolidBackgroundObject(2100,625,250,175,GameObject.COLOR_STONE.brighter()));
		objects.add(new PushableBox(1900,625,60,60));
		crusherPlatform = new StonePlatform(2100,625,150,150);
		objects.add(crusherPlatform);
		
		//widen camera powerup
		objects.add(new TextDisplayTrigger(2700,665,50,50,"Various power-ups give different buffs.",300));
		objects.add(new CameraSizePowerup(2700, 665, 50, 1.6));
		objects.add(new StonePlatform(2700, 600, 200, 50));
		objects.add(new StonePlatform(3100,650,200,700));
		objects.add(new StonePlatform(3100,100,250,50));
		objects.add(new StonePlatform(3500,200,200,50));
		objects.add(new StonePlatform(3900,300,200,50));
		
		//midway
		objects.add(new StonePlatform(4450,300,400,150));
		objects.add(new StonePlatform(4600,375,150,300));
		
		objects.add(new StonePlatform(5000,550,200,50));
		
		//mountain
		objects.add(new PushableStone(6200,650,60,60));
		objects.add(new PushableStone(6500,650,60,60));
		objects.add(new PushableBox(6350,650,60,60));
		
		objects.add(new WaterPlatform(5800,400,800,399));
		objects.add(new TextDisplayTrigger(5400, 700, 200, 200, "Tip: Use Escape to pause and R to restart.", 300));
		objects.add(new StonePlatform(5400,0,150,1200));
		objects.add(new StonePlatform(5800,-50,800,800));
		objects.add(new StonePlatform(6350,0,400,1200));
		
		mountainCrusher = new StonePlatform(5800,550,100,200);
		objects.add(mountainCrusher);
		
		objects.add(new StonePlatform(5800,750,150,600));
		objects.add(new StonePlatform(6125,1075,800,150));
		objects.add(new StonePlatform(5850,725,150,100));
		
		objects.add(new StonePlatform(6400,800,500,100));
		
		objects.add(new StonePlatform(6125,1275,200,500));
		objects.add(new StonePlatform(6125,1200,400,300));
		
		mountainPlatform = new StonePlatform(6800,830,150,40);
		objects.add(mountainPlatform);
		
		objects.add(new SolidBackgroundObject(5800,400,820,395,GameObject.COLOR_STONE.brighter()));
		objects.add(new SolidBackgroundObject(6125,750,790,620,GameObject.COLOR_STONE.brighter()));
		
		objects.add(new FinishFlag(6125,1575,50,100));
		
		//extra pk
		objects.add( new StonePlatform(7200,1050,300,100));
		objects.add( new StonePlatform(7700,1150,300,100));
		objects.add( new StonePlatform(8200,1250,300,100));
		
		//jumpboost
		objects.add(new TextDisplayTrigger(8200,1350,50,50,"Jump boost grants a higher jump. Now go climb the mountain.",300));
		objects.add(new JumpBoostPowerup(8200, 1350, 50, 4));
		
	}
	
	@Override
	public void onTick() {
		if (crusherPlatform.y >= 750) crusherPlatform.vy = -1.5;
		else if (crusherPlatform.y <= 625) crusherPlatform.vy = 1.5;
		
		if (mountainCrusher.y >= 550) mountainCrusher.vy = -1;
		else if (mountainCrusher.y <= 450) mountainCrusher.vy = 1;
		
		if (mountainPlatform.y >= 1130) mountainPlatform.vy = -1.5;
		else if (mountainPlatform.y <= 830) mountainPlatform.vy = 1.5;
	}
	
	@Override
	public void moveCamera() {
		super.moveCamera();
		if (GamePanel.getPanel().camera_y < 300) GamePanel.getPanel().camera_y = 300;
	}
	
}
