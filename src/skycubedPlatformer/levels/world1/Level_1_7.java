package skycubedPlatformer.levels.world1;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.util.List;

import skycubedPlatformer.Main;
import skycubedPlatformer.assets.PushableObject;
import skycubedPlatformer.assets.contraptions.ConveyorBelt;
import skycubedPlatformer.assets.contraptions.Factory;
import skycubedPlatformer.assets.contraptions.RopeObject;
import skycubedPlatformer.assets.contraptions.TargetRopeObject;
import skycubedPlatformer.assets.creature.creatures.goblin.CreatureDartGoblin;
import skycubedPlatformer.assets.decoration.walls.SolidBackgroundObject;
import skycubedPlatformer.assets.interactables.FinishFlag;
import skycubedPlatformer.assets.interactables.NullZone;
import skycubedPlatformer.assets.powerups.CameraSizePowerup;
import skycubedPlatformer.assets.pushableObjects.PushableBox;
import skycubedPlatformer.assets.pushableObjects.PushableCopper;
import skycubedPlatformer.assets.pushableObjects.PushableMetal;
import skycubedPlatformer.assets.pushableObjects.PushableSilver;
import skycubedPlatformer.assets.pushableObjects.PushableStone;
import skycubedPlatformer.assets.solidPlatforms.StonePlatform;
import skycubedPlatformer.assets.solidPlatforms.WoodPlatform;
import skycubedPlatformer.assets.triggers.TextDisplayTrigger;
import skycubedPlatformer.game.GameObject;
import skycubedPlatformer.levels.Level;
import skycubedPlatformer.menu.ApplicationFrame;
import skycubedPlatformer.menu.GamePanel;

public class Level_1_7 extends Level {

	public Level_1_7() {
		this.backgroundColor = COLOR_DAYSKY;
		
		this.spawnX = 0; //0 //4000
		this.spawnY = 0; //0 //700
		this.bottomLimit = -1200;

		this.reqs = new String[] {"Level_1_6"};
		this.reward = 80;
		this.name = "Desert Quarry";
		
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
		
		drawRoundScenery(g, Color.YELLOW.darker(), Fx1, Fy1, Fsize1, 10);
		drawFloorScenery(g, Color.YELLOW.darker(), 150, 10);
		drawRoundScenery(g, Color.YELLOW, Fx2, Fy2, Fsize2, 7);
	}
	
	@Override
	public void onStart(GamePanel panel) {
		panel.displayText("Some objects have special attachments.", 240);
	}
	
	@Override
	public void drawPlatforms(GamePanel panel) {
		List<GameObject> objects = panel.objects;
		
		//spawn platform
		objects.add(new StonePlatform(0, -1100, 400, 2000));
		objects.add(new StonePlatform(500, -50, 200, 50));
		
		//rope platform
		objects.add(new StonePlatform(1400, -1000, 1000, 2000));
		objects.add(new StonePlatform(1650, -800, 500, 2000));
		objects.add(new StonePlatform(1150, 500, 200, 200));
		objects.add(new RopeObject(new PushableBox(1150, 200, 50, 50), 1150, 500, 350, 1));
		
		//swing across
		objects.add(new CameraSizePowerup(1650, 250, 50, 1.65));
		objects.add(new TextDisplayTrigger(1650, 250, 200, 200, "It's a bit unstable, hold jump the entire way across.", 300));
		objects.add(new StonePlatform(2200, 700, 200, 200));
		objects.add(new RopeObject(new PushableBox(1800, 250, 100, 100), 2200, 700, 550, 1));
		
		objects.add(new StonePlatform(2750, -750, 500, 2000));
		objects.add(new NullZone(2750, 350, 190, 300));
		objects.add(new StonePlatform(2750, 550, 300, 200));
		
		//chain platform
		objects.add(new StonePlatform(3700, -700, 1000, 2000));
		objects.add(new StonePlatform(3950, -500, 500, 2000));
		objects.add(new StonePlatform(3450, 750, 200, 200));
		objects.add(new RopeObject(new PushableStone(3450, 500, 50, 50), 3450, 750, 300, 1));
		objects.add(new PushableMetal(3800, 600, 50, 50));
		objects.add(new PushableMetal(3900, 600, 80, 80));
		
		objects.add(new SolidBackgroundObject(3900, 450, 300, 300, GameObject.COLOR_STONE.brighter()));
		objects.add(new SolidBackgroundObject(4050, 550, 200, 300, GameObject.COLOR_STONE.brighter()));
		
		objects.add(new StonePlatform(4500, 450, 200, 200));
		objects.add(new CreatureDartGoblin(4500, 565, 30, 0, 200, 200));
		
		//crusher platform
		objects.add(new TextDisplayTrigger(4900, 700, 200, 200, "Copper, silver, and gold blocks drop coins when crushed.", 300));
		objects.add(new WoodPlatform(5500, 430, 400, 60) {
			@Override
			public void move() {
				if (this.x >= 5500) this.vx = -2;
				else if (this.x <= 5200) this.vx = 2;
				super.move();
			}
		});
		objects.add(new SolidBackgroundObject(5300, -420, 700, 2000, GameObject.COLOR_STONE.brighter()));
		objects.add(new StonePlatform(4900, -600, 200, 2400));
		objects.add(new StonePlatform(5300, -600, 750, 2000));
		objects.add(new StonePlatform(5500, -600, 400, 2400));
		objects.add(new PushableCopper(5450, 650, 50, 50));
		objects.add(new PushableSilver(5550, 650, 45, 45));
		
		objects.add(new StonePlatform(6050, 600, 200, 50));
		
		//double bound objects platform
		objects.add(new TextDisplayTrigger(6400, 700, 200, 200, "Objects can also be tied to each other.", 300));
		objects.add(new StonePlatform(6900, -400, 1000, 2000));
		objects.add(new StonePlatform(7200, -250, 600, 2000));
		objects.add(new StonePlatform(7200, 850, 200, 50));
		objects.add(new StonePlatform(7250, 900, 100, 100));
		objects.add(new StonePlatform(7450, 1000, 100, 100));
		
		objects.add(new CameraSizePowerup(7450, 1100, 50, 1.5));
		
		PushableObject obj = new PushableStone(6700, 660, 50, 50);
		objects.add(obj);
		objects.add(new TargetRopeObject(new PushableBox(6600, 700, 60, 60), obj, 300, 1));
		
		//bridge puzzle
		objects.add(new StonePlatform(8800, -250, 600, 2000));
		objects.add(new StonePlatform(8000, 1100, 800, 100));
		objects.add(new StonePlatform(8450, 1250, 300, 400));
		
		PushableObject anchor1 = new PushableMetal(7700, 1200, 40, 40);
		objects.add(anchor1);
		objects.add(new TargetRopeObject(new PushableStone(7500, 850, 60, 60), anchor1, 500, 2));
		objects.add(new TextDisplayTrigger(7500, 850, 200, 200, "Jump across using the tethered objects. Hold jump!", 300));
		
		PushableObject anchor2 = new PushableMetal(7900, 1250, 60, 60);
		objects.add(anchor2);
		objects.add(new TargetRopeObject(new PushableBox(7900, 850, 80, 80), anchor2, 450, 2));
		
		//factory
		objects.add(new TextDisplayTrigger(8800, 850, 200, 200, "Conveyor belts push you at a set speed.", 300));
		objects.add(new StonePlatform(9500, 750, 400, 100));
		objects.add(new StonePlatform(9700, 850, 200, 300));
		objects.add(new ConveyorBelt(9450, 825, 300, 50, -2));
		objects.add(new PushableMetal(9750, 1050, 40, 40));
		
		//parkour
		objects.add(new StonePlatform(10700, -50, 1000, 2000));
		
		objects.add(new StonePlatform(10400, 900, 600, 100));
		objects.add(new ConveyorBelt(10300, 975, 400, 50, -3));
		
		objects.add(new StonePlatform(10800, 950, 600, 200));
		objects.add(new ConveyorBelt(10700, 1075, 400, 50, -3));
		
		objects.add(new StonePlatform(11100, 1000, 400, 300));
		objects.add(new ConveyorBelt(11100, 1175, 400, 50, -3));
		
		//spawner
		objects.add(new StonePlatform(10650, 1450, 700, 50));
		objects.add(new StonePlatform(10300, 1575, 200, 300));
		objects.add(new ConveyorBelt(10700, 1500, 600, 50, 3));
		objects.add(new Factory(10430, 1555, 60, new PushableStone(10430, 1555, 50, 50), 180, 99));
		
		objects.add(new StonePlatform(11700, 1200, 400, 100));
		
		objects.add(new StonePlatform(12350, 100, 400, 2400));
		objects.add(new FinishFlag(12350, 1349, 50, 100));
		
		

	}
	
	@Override
	public void onTick() {
		
	}
	
	@Override
	public void moveCamera() {
		double diffX = GamePanel.getPanel().player.x - GamePanel.getPanel().camera_x;
		double diffY = GamePanel.getPanel().player.y - GamePanel.getPanel().camera_y;
		
		int higherLimitX = 0;
		int lowerLimitX = -100;
		int higherLimitY = 100;
		int lowerLimitY = -100;
		
		if (diffX > higherLimitX) GamePanel.getPanel().camera_x = GamePanel.getPanel().player.x - higherLimitX;
		if (diffX < lowerLimitX) GamePanel.getPanel().camera_x = GamePanel.getPanel().player.x - lowerLimitX;
		if (diffY > higherLimitY) GamePanel.getPanel().camera_y = GamePanel.getPanel().player.y - higherLimitY;
		if (diffY < lowerLimitY) GamePanel.getPanel().camera_y = GamePanel.getPanel().player.y - lowerLimitY;
		
		if (GamePanel.getPanel().camera_y < 200) GamePanel.getPanel().camera_y = 200;
		
	}
	
}
