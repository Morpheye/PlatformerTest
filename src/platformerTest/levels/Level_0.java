package platformerTest.levels;

import java.awt.Color;
import java.util.List;

import platformerTest.assets.PushableObject;
import platformerTest.assets.SolidPlatform;
import platformerTest.assets.decorations.TextObject;
import platformerTest.assets.liquidPlatforms.WaterPlatform;
import platformerTest.assets.solidPlatforms.GrassPlatform;
import platformerTest.game.GameObject;
import platformerTest.game.Level;
import platformerTest.game.MainFrame;

public class Level_0 extends Level {
	
	public Level_0() {
		this.backgroundColor = COLOR_DAYSKY;
		
		this.spawnX = -100; //-100
		this.spawnY = 250; //250

	}
	
	SolidPlatform movingPlatform;
	SolidPlatform movingPlatform2;
	
	@Override
	public void drawPlatforms() {
		List<GameObject> objects = MainFrame.objects;
		
		//spawn
		objects.add(new TextObject(-185, -50, Color.WHITE, "Use the WASD keys to move."));
		objects.add(new GrassPlatform(0, 100, 500, 200));
		objects.add(new GrassPlatform(400, 125, 500, 250));
		objects.add(new GrassPlatform(450, 0, 400, 200));
		
		//first island
		objects.add(new GrassPlatform(1000, 250, 200, 50));
		objects.add(new GrassPlatform(1100, 300, 200, 150));
		objects.add(new GrassPlatform(800, 500, 150, 35));
		
		//water
		objects.add(new WaterPlatform(1875, 200, 800, 390));
		objects.add(new GrassPlatform(1500, 200, 200, 400));
		objects.add(new GrassPlatform(2250, 200, 200, 400));
		objects.add(new GrassPlatform(1875, 400, 200, 350));
		objects.add(new GrassPlatform(1875, 50, 925, 100));
		
		//moving platform
		movingPlatform = new GrassPlatform(2650, 300, 200, 50);
		movingPlatform.vy = 2;
		objects.add(movingPlatform);
		
		objects.add(new GrassPlatform(3050, 500, 200, 200));
		
		//moving platform2
		movingPlatform2 = new GrassPlatform(3350, 500, 200, 50);
		movingPlatform2.vx = 3;
		objects.add(movingPlatform2);
		
		objects.add(new GrassPlatform(4000, 500, 200, 200));
		
		//movable object platform
		objects.add(new GrassPlatform(4400, 450, 200, 400));
		objects.add(new GrassPlatform(4700, 350, 600, 200));
		objects.add(new PushableObject(4400, 800, 60, 60, GameObject.COLOR_STONE, 2, 0.97));
		
		objects.add(new WaterPlatform(5700, 250, 1025, 390));
		objects.add(new GrassPlatform(5550, 325, 300, 250));
		objects.add(new GrassPlatform(5450, 340, 100, 280));
		objects.add(new GrassPlatform(5200, 250, 100, 400));
		objects.add(new GrassPlatform(6150, 340, 200, 580));
		objects.add(new GrassPlatform(5675, 100, 925, 100));
		
		objects.add(new PushableObject(5650, 500, 60, 60, GameObject.COLOR_WOOD, 0.8, 0.97));
		objects.add(new PushableObject(5200, 500, 60, 60, GameObject.COLOR_STONE, 2, 0.97));

		
	}
	
	@Override
	public void onTick() {
		if (movingPlatform.y >= 600) movingPlatform.vy = -2;
		else if (movingPlatform.y <= 300) movingPlatform.vy = 2;
		
		if (movingPlatform2.x >= 3700) movingPlatform2.vx = -3;
		else if (movingPlatform2.x <= 3350) movingPlatform2.vx = 3;
		
	}
	
	
}
