package platformerTest.levels.world1;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.util.List;

import platformerTest.Main;
import platformerTest.assets.creature.creatures.goblin.CreatureDartGoblin;
import platformerTest.assets.creature.creatures.goblin.CreatureGoblin;
import platformerTest.assets.creature.creatures.goblin.CreatureGoblinGuard;
import platformerTest.assets.decoration.walls.FadingWallObject;
import platformerTest.assets.decoration.walls.SolidBackgroundObject;
import platformerTest.assets.powerups.HealPowerup;
import platformerTest.assets.pushableObjects.PushableStone;
import platformerTest.assets.solidPlatforms.GrassPlatform;
import platformerTest.assets.solidPlatforms.SandPlatform;
import platformerTest.assets.triggers.TextDisplayTrigger;
import platformerTest.game.GameObject;
import platformerTest.levels.Level;
import platformerTest.menu.GamePanel;
import platformerTest.weapons.starterWeapons.WoodenClub;

public class Level_1_6 extends Level {

	public Level_1_6() {
		this.backgroundColor = COLOR_DAYSKY;
		
		this.spawnX = 1600; //0
		this.spawnY = 1100; //200

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

		objects.add(new SolidBackgroundObject(1400, 300, 1790, 990, GameObject.COLOR_DIRT));
	}
	
	@Override
	public void drawForeground() {
		List<GameObject> objects = GamePanel.objects;

		objects.add(new FadingWallObject(1400, 300, 1790, 990, GameObject.COLOR_GRASS));
	}
	
	@Override
	public void drawPlatforms() {
		List<GameObject> objects = GamePanel.objects;
		
		//spawn platform
		objects.add(new GrassPlatform(0, 50, 400, 100));
		
		//large cavern
		objects.add(new GrassPlatform(700, -100, 400, 200));
		objects.add(new GrassPlatform(600, 500, 200, 600));
		objects.add(new GrassPlatform(2200, 300, 200, 1000));
		objects.add(new GrassPlatform(1600, 700, 1400, 200));
		objects.add(new GrassPlatform(2100, 200, 400, 200));
		
		objects.add(new SandPlatform(1200, 0, 200, 200));
		objects.add(new CreatureGoblinGuard(1200, 115, 30, 100, 100, 200, new WoodenClub()));
		objects.add(new SandPlatform(1600, 100, 200, 200));
		objects.add(new CreatureGoblinGuard(1600, 215, 30, 100, 100, 200, new WoodenClub()));
		objects.add(new PushableStone(2000,375,80,80));
		
		objects.add(new TextDisplayTrigger(1800, 550, 100, 50, "Equip your own weapon from the Weapons menu.", 300));
		objects.add(new CreatureGoblin(1600, 550, 30, 20, 400, 0, 50));
		objects.add(new GrassPlatform(1600, 500, 600, 50));
		objects.add(new GrassPlatform(1050, 450, 200, 50));
		objects.add(new GrassPlatform(700, 400, 200, 50));
		objects.add(new GrassPlatform(650, 620, 200, 50));
		objects.add(new PushableStone(750,520,80,80));
		
		objects.add(new CreatureDartGoblin(600, 816, 30, 400));
		objects.add(new GrassPlatform(1600, 800, 200, 200));
		objects.add(new GrassPlatform(1950, 800, 700, 400));
		objects.add(new HealPowerup(1700, 1050, 50));
		
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
		
		if (GamePanel.camera_y < 200) GamePanel.camera_y = 200;
		
	}
	
}
