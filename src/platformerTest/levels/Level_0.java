package platformerTest.levels;

import java.awt.Color;
import java.util.List;

import platformerTest.assets.LiquidPlatform;
import platformerTest.assets.MovableObject;
import platformerTest.assets.SolidPlatform;
import platformerTest.assets.decoration.TextObject;
import platformerTest.game.GameObject;
import platformerTest.game.Level;
import platformerTest.game.MainFrame;

public class Level_0 extends Level {
	
	public Level_0() {
		this.backgroundColor = Color.black;
		this.gravity = -1.5;
		this.drag = 0.97;
		
		this.spawnX = 4000; //-100
		this.spawnY = 700; //250

	}
	
	SolidPlatform movingPlatform;
	SolidPlatform movingPlatform2;
	SolidPlatform movingPlatform3;
	
	@Override
	public void drawPlatforms() {
		List<GameObject> objects = MainFrame.objects;
		
		//spawn
		objects.add(new TextObject(-250, 350, Color.LIGHT_GRAY, "Use the WASD keys to move."));
		objects.add(new SolidPlatform(0, 100, 500, 200, Color.GREEN));
		objects.add(new SolidPlatform(400, 125, 500, 250, Color.GREEN));
		objects.add(new SolidPlatform(450, 0, 400, 200, Color.GREEN));
		
		//first island
		objects.add(new SolidPlatform(1000, 250, 200, 50, Color.GREEN));
		objects.add(new SolidPlatform(1100, 300, 200, 150, Color.GREEN));
		objects.add(new SolidPlatform(800, 500, 150, 35, Color.GREEN));
		
		//water
		objects.add(new LiquidPlatform(1875, 200, 800, 390, Color.CYAN));
		objects.add(new SolidPlatform(1500, 200, 200, 400, Color.GREEN));
		objects.add(new SolidPlatform(2250, 200, 200, 400, Color.GREEN));
		objects.add(new SolidPlatform(1875, 400, 200, 350, Color.GREEN));
		objects.add(new SolidPlatform(1875, 50, 925, 100, Color.GREEN));
		
		//moving platform
		movingPlatform = new SolidPlatform(2650, 300, 200, 50, Color.GREEN);
		movingPlatform.vy = 2;
		objects.add(movingPlatform);
		
		objects.add(new SolidPlatform(3050, 500, 200, 200, Color.GREEN));
		
		//moving platform2
		movingPlatform2 = new SolidPlatform(3350, 500, 200, 50, Color.GREEN);
		movingPlatform2.vx = 3;
		objects.add(movingPlatform2);
		
		objects.add(new SolidPlatform(4000, 500, 200, 200, Color.GREEN));
		
		//crusherplatform
		objects.add(new SolidPlatform(4400, 450, 200, 400, Color.GREEN));
		objects.add(new SolidPlatform(4700, 350, 600, 200, Color.GREEN));
		
		objects.add(new MovableObject(4400, 800, 60, 60, Color.RED));
		objects.add(new MovableObject(4600, 800, 35, 35, Color.ORANGE));
		
		//moving platform3
		movingPlatform3 = new SolidPlatform(4600, 475, 200, 50, Color.GREEN);
		movingPlatform3.vx = 3;
		objects.add(movingPlatform3);
		
		
	}
	
	@Override
	public void onTick() {
		if (movingPlatform.y >= 600) movingPlatform.vy = -2;
		else if (movingPlatform.y <= 300) movingPlatform.vy = 2;
		
		if (movingPlatform2.x >= 3700) movingPlatform2.vx = -3;
		else if (movingPlatform2.x <= 3350) movingPlatform2.vx = 3;
		
		if (movingPlatform3.x >= 5200) movingPlatform3.vx = -3;
		else if (movingPlatform3.x <= 4600) movingPlatform3.vx = 3;
	}
	
	
}
