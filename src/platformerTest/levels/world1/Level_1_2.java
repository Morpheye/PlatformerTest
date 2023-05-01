package platformerTest.levels.world1;

import java.util.List;

import platformerTest.Main;
import platformerTest.assets.SolidPlatform;
import platformerTest.assets.decoration.walls.SolidBackgroundObject;
import platformerTest.assets.interactables.FinishFlag;
import platformerTest.assets.liquidPlatforms.WaterPlatform;
import platformerTest.assets.powerups.CameraSizePowerup;
import platformerTest.assets.powerups.JumpBoostPowerup;
import platformerTest.assets.pushableObjects.PushableBox;
import platformerTest.assets.pushableObjects.PushableStone;
import platformerTest.assets.solidPlatforms.GrassPlatform;
import platformerTest.assets.solidPlatforms.StonePlatform;
import platformerTest.assets.triggers.Code;
import platformerTest.assets.triggers.TextDisplayTrigger;
import platformerTest.game.GameObject;
import platformerTest.levels.Level;
import platformerTest.menu.GamePanel;

public class Level_1_2 extends Level {

	public Level_1_2() {
		this.backgroundColor = COLOR_DAYSKY;
		
		this.spawnX = 0; //0
		this.spawnY = 200; //200
		
		this.reqs = new String[] {"Level_1_1"};

		this.name = "Mountain Climbing";
		
	}
	
	@Override
	public void onStart() {
		GamePanel.displayText("Use boxes wisely, don't softlock yourself.", 300);
		
	}
	
	@Override
	public void drawBackground() {
		List<GameObject> objects = GamePanel.objects;
		
		objects.add(new SolidBackgroundObject(700,200,275,275,GameObject.COLOR_DIRT));
		objects.add(new SolidBackgroundObject(600,150,275,150,GameObject.COLOR_DIRT));
		
		objects.add(new SolidBackgroundObject(2100,625,250,175,GameObject.COLOR_STONE.brighter()));
		
		//in mountain
		objects.add(new SolidBackgroundObject(5800,400,820,395,GameObject.COLOR_STONE.brighter()));
		objects.add(new SolidBackgroundObject(6125,750,790,620,GameObject.COLOR_STONE.brighter()));
		
		objects.add(new FinishFlag(6125,1575,50,100));
		
	}
	
	SolidPlatform crusherPlatform;
	
	SolidPlatform mountainCrusher;
	
	SolidPlatform mountainPlatform;
	
	@Override
	public void drawPlatforms() {
		List<GameObject> objects = GamePanel.objects;
		
		//spawn platform
		objects.add(new GrassPlatform(0, 50, 400, 100));
		
		//box platform
		objects.add(new PushableBox(700, 180, 60, 60));
		objects.add(new GrassPlatform(700,325,300,50));
		objects.add(new GrassPlatform(825,200,50,300));
		objects.add(new GrassPlatform(625,100,450,100));
		
		objects.add(new GrassPlatform(1100,400,200,50));
		
		objects.add(new GrassPlatform(1500,500,200,50));
		
		//crusher platform
		objects.add(new TextDisplayTrigger(1850,550,200,200,"Caution: Crushing results in instant death.",300));
		objects.add(new StonePlatform(2100,500,600,100));
		objects.add(new StonePlatform(2100,750,300,150));
		objects.add(new PushableBox(1900,625,60,60));
		crusherPlatform = new StonePlatform(2100,625,150,150);
		objects.add(crusherPlatform);
		
		//widen camera powerup
		objects.add(new TextDisplayTrigger(2700,665,50,50,"Various power-ups give different buffs.",300));
		objects.add(new CameraSizePowerup(2700, 665, 50, new Code() {
			@Override
			public void run() {
				GamePanel.target_camera_size = (int) (Main.SIZE*1.6);
			}}));
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
		objects.add(new WaterPlatform(5800,400,800,395));
		objects.add(new StonePlatform(5400,400,150,400));
		objects.add(new StonePlatform(5800,275,800,150));
		objects.add(new StonePlatform(6350,400,400,400));
		
		mountainCrusher = new StonePlatform(5800,550,100,200);
		objects.add(mountainCrusher);
		
		objects.add(new StonePlatform(5800,750,150,600));
		objects.add(new StonePlatform(6125,1075,800,150));
		objects.add(new StonePlatform(5850,750,150,100));
		
		objects.add(new PushableStone(6200,650,60,60));
		objects.add(new PushableStone(6500,650,60,60));
		objects.add(new PushableBox(6350,650,60,60));
		
		objects.add(new StonePlatform(6400,800,500,100));
		
		objects.add(new StonePlatform(6125,1275,200,500));
		objects.add(new StonePlatform(6125,1200,400,300));
		
		mountainPlatform = new StonePlatform(6800,830,150,40);
		objects.add(mountainPlatform);
		
		//extra pk
		objects.add( new StonePlatform(7200,1050,300,100));
		objects.add( new StonePlatform(7700,1150,300,100));
		objects.add( new StonePlatform(8200,1250,300,100));
		
		//jumpboost
		objects.add(new TextDisplayTrigger(8200,1350,50,50,"Jump boost power-ups grant a higher jump.",300));
		objects.add(new JumpBoostPowerup(8200, 1350, 50, 4));
		
	}
	
	@Override
	public void onTick() {
		if (crusherPlatform.y >= 750) crusherPlatform.vy = -2.5;
		else if (crusherPlatform.y <= 625) crusherPlatform.vy = 2.5;
		
		if (mountainCrusher.y >= 550) mountainCrusher.vy = -1;
		else if (mountainCrusher.y <= 450) mountainCrusher.vy = 1;
		
		if (mountainPlatform.y >= 1130) mountainPlatform.vy = -1;
		else if (mountainPlatform.y <= 830) mountainPlatform.vy = 2;
	}
	
}
