package platformerTest.levels.world1;

import java.util.List;

import platformerTest.Main;
import platformerTest.assets.SolidPlatform;
import platformerTest.assets.decoration.SolidBackgroundObject;
import platformerTest.assets.pushableObjects.PushableBox;
import platformerTest.assets.solidPlatforms.GrassPlatform;
import platformerTest.assets.solidPlatforms.StonePlatform;
import platformerTest.assets.triggers.CameraSizeTrigger;
import platformerTest.assets.triggers.TextDisplayTrigger;
import platformerTest.game.GameObject;
import platformerTest.levels.Level;
import platformerTest.menu.GamePanel;

public class Level_1_2 extends Level {

	public Level_1_2() {
		this.backgroundColor = COLOR_DAYSKY;
		
		this.spawnX = 0; //0
		this.spawnY = 200; //200

		this.name = "Mountain Climbing";
		
	}
	
	@Override
	public void onStart() {
		
	}
	
	@Override
	public void drawBackground() {
		List<GameObject> objects = GamePanel.objects;
		
		objects.add(new SolidBackgroundObject(700,200,275,275,GameObject.COLOR_DIRT));
		objects.add(new SolidBackgroundObject(600,150,275,150,GameObject.COLOR_DIRT));
		
		objects.add(new SolidBackgroundObject(2100,625,250,175,GameObject.COLOR_STONE.brighter()));
		
		
	}
	
	SolidPlatform crusherPlatform;
	
	@Override
	public void drawPlatforms() {
		List<GameObject> objects = GamePanel.objects;
		
		//spawn platform
		objects.add(new GrassPlatform(0, 50, 400, 100));
		
		//box platform
		objects.add(new TextDisplayTrigger(450,150,100,100,"Use the box wisely, don't softlock yourself.",300));
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
		
		//widen camera
		
		objects.add(new StonePlatform(2700, 600, 200, 50));
		objects.add(new CameraSizeTrigger(2650, 650, 100, 100, (int) (Main.SIZE*1.6), false));
		
		objects.add(new StonePlatform(3100,650,200,700));
		objects.add(new StonePlatform(3100,100,250,50));
		objects.add(new StonePlatform(3500,200, 200, 50));
		objects.add(new StonePlatform(3900,300, 200, 50));
		objects.add(new CameraSizeTrigger(3850, 350, 100, 100, Main.SIZE, false));
		
		//midway
		objects.add(new StonePlatform(4450,300,400,150));
		
		
	}
	
	@Override
	public void onTick() {
		if (crusherPlatform.y >= 750) crusherPlatform.vy = -2.5;
		else if (crusherPlatform.y <= 625) crusherPlatform.vy = 2.5;
	}
	
}
