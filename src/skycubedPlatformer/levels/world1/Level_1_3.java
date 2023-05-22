package skycubedPlatformer.levels.world1;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import skycubedPlatformer.Main;
import skycubedPlatformer.assets.creature.CreatureAi;
import skycubedPlatformer.assets.creature.ai.HorizontalMovementAi;
import skycubedPlatformer.assets.creature.ai.vertical.VerticalFollowAi;
import skycubedPlatformer.assets.creature.creatures.Creature;
import skycubedPlatformer.assets.creature.creatures.goblin.CreatureDartGoblin;
import skycubedPlatformer.assets.creature.creatures.goblin.CreatureGoblin;
import skycubedPlatformer.assets.creature.creatures.undead.CreatureUndead;
import skycubedPlatformer.assets.decoration.walls.FadingWallObject;
import skycubedPlatformer.assets.decoration.walls.SolidBackgroundObject;
import skycubedPlatformer.assets.interactables.FinishFlag;
import skycubedPlatformer.assets.powerups.CameraSizePowerup;
import skycubedPlatformer.assets.powerups.HealPowerup;
import skycubedPlatformer.assets.powerups.OverhealPowerup;
import skycubedPlatformer.assets.pushableObjects.PushableBox;
import skycubedPlatformer.assets.pushableObjects.PushableStone;
import skycubedPlatformer.assets.solidPlatforms.GrassPlatform;
import skycubedPlatformer.assets.solidPlatforms.StonePlatform;
import skycubedPlatformer.assets.solidPlatforms.WoodPlatform;
import skycubedPlatformer.assets.triggers.Code;
import skycubedPlatformer.assets.triggers.TextDisplayTrigger;
import skycubedPlatformer.game.GameObject;
import skycubedPlatformer.levels.Level;
import skycubedPlatformer.menu.ApplicationFrame;
import skycubedPlatformer.menu.GamePanel;

public class Level_1_3 extends Level {

	public Level_1_3() {
		this.backgroundColor = COLOR_DARKSKY;
		
		this.spawnX = 0; //0
		this.spawnY = 200; //200
		
		this.reqs = new String[] {"Level_1_2"};
		this.reward = 60;
		this.name = "Entering Battle";
		
	}
	
	int[] Fx1 = {0, 100, 600, 800, 1100};
	int[] Fy1 = {700, 500, 400, 700, 300};
	int[] Fsize1 = {250, 250, 400, 250, 500};
	
	int[] Fx2 = {-100, 200, 300, 500, 700, 800, 900, 1200, 1400, 1500};
	int[] Fy2 = {400, 350, 500, 400, 250, 550, 400, 550, 300, 500};
	int[] Fsize2 = {175, 250, 150, 175, 150, 150, 200, 175, 150, 225};
	
	@Override
	public void fill(Graphics2D g) {
		Color gc1 = new Color(102, 167, 160);
		Color gc2 = new Color(112, 146, 190);
		g.setPaint(new GradientPaint(0,0,gc1,Main.SIZE,Main.SIZE,gc2));
		g.fillRect(-50,-50,Main.SIZE+50,Main.SIZE+50);
		
		drawRoundScenery(g, GameObject.COLOR_STONE.brighter(), Fx1, Fy1, Fsize1, 10);
		drawFloorScenery(g, GameObject.COLOR_STONE.brighter(), 150, 10);
		drawRoundScenery(g, Color.white.darker().darker(), Fx2, Fy2, Fsize2, 5);
	}
	
	@Override
	public void onStart(GamePanel panel) {
		panel.displayText("Use space to melee attack. Watch your health!", 240);
	}
	
	@Override
	public void drawBackground() {
		List<GameObject> objects = GamePanel.objects;
		
		objects.add(new SolidBackgroundObject(2750, 750, 690, 990, GameObject.COLOR_DIRT));
		objects.add(new SolidBackgroundObject(3050, 2450, 190, 550, GameObject.COLOR_DIRT));
		objects.add(new SolidBackgroundObject(2450, 2450, 190, 220, GameObject.COLOR_DIRT));
		
		objects.add(new FinishFlag(3050, 2800, 50, 100));
	}
	
	@Override
	public void drawForeground() {
		List<GameObject> objects = GamePanel.objects;
		
		objects.add(new FadingWallObject(2750, 750, 690, 990, GameObject.COLOR_GRASS));
	}
	
	@Override
	public void drawPlatforms() {
		List<GameObject> objects = GamePanel.objects;
		
		//spawn platform
		objects.add(new StonePlatform(0, 50, 400, 100));
		
		//first battle
		objects.add(new StonePlatform(700, 100, 600, 100));
		objects.add(new StonePlatform(900, 150, 200, 200));
		objects.add(new CreatureUndead(700, 200, 40, 30, 20, 400));
		objects.add(new HealPowerup(900, 300, 50));
		objects.add(new TextDisplayTrigger(900, 300, 50, 50, "Health regenerates slowly, power-ups are faster.", 300));
		
		//bridge platform
		objects.add(new StonePlatform(1300, 250, 200, 200));
		objects.add(new WoodPlatform(1600, 325, 400, 50));
		objects.add(new StonePlatform(1900, 250, 200, 200));
		objects.add(new CreatureUndead(1500, 400, 40, 30, 20, 400));
		objects.add(new CreatureUndead(1700, 400, 40, 30, 20, 400));
		
		//fading mountain
		objects.add(new TextDisplayTrigger(2350, 500, 100, 100, "This wall is fake.", 300));
		objects.add(new GrassPlatform(2600, -150, 700, 1200));
		objects.add(new GrassPlatform(3000, 250, 200, 2000));
		objects.add(new GrassPlatform(2500, 950, 200, 600));
		objects.add(new PushableStone(2600, 500, 100, 100));
		
		objects.add(new GrassPlatform(2600, 675, 200, 50));
		objects.add(new GrassPlatform(2900, 825, 200, 50));
		objects.add(new GrassPlatform(2600, 975, 200, 50));
		objects.add(new PushableBox(2650, 1050, 75, 75));
		objects.add(new PushableBox(2650, 1150, 40, 40));
		objects.add(new GrassPlatform(2900, 1225, 200, 50));
		objects.add(new HealPowerup(2500, 1300, 50));
		objects.add(new CameraSizePowerup(3000, 1300, 50, 1.25));
		//start pking back
		objects.add(new StonePlatform(2100, 1200, 200, 200));
		objects.add(new StonePlatform(1500, 1300, 600, 200));
		objects.add(new CreatureUndead(1500, 1450, 40, 30, 20, 400, 0, 200));
		
		objects.add(new StonePlatform(900, 1400, 200, 200));
		objects.add(new StonePlatform(500, 1500, 200, 200));
		
		//overheal stairs
		objects.add(new StonePlatform(100, 1600, 200, 200));
		objects.add(new StonePlatform(75, 1650, 150, 200));
		objects.add(new StonePlatform(50, 1700, 100, 200));
		objects.add(new StonePlatform(25, 1750, 50, 200));
		objects.add(new OverhealPowerup(25, 1900, 50, 50));
		objects.add(new TextDisplayTrigger(25, 1900, 50, 50, "Overheals bypass the normal health limit of 100.", 300));
		
		//goblin platform
		objects.add(new StonePlatform(300, 1950, 200, 50));
		objects.add(new StonePlatform(700, 2000, 200, 200));
		objects.add(new StonePlatform(1300, 2100, 600, 200));
		objects.add(new CreatureGoblin(1500, 2400, 30, 20, 400, 0, 200));
		objects.add(new CreatureGoblin(1560, 2400, 30, 20, 400, 0, 200));
		
		objects.add(new StonePlatform(1900, 2200, 200, 200));
		
		//spear goblin platform
		objects.add(new GrassPlatform(2700, 2250, 900, 200));
		objects.add(new GrassPlatform(2900, 2725, 200, 50));
		objects.add(new GrassPlatform(3050, 2650, 200, 200));
		objects.add(new PushableBox(3050,2450,80,80));
		objects.add(new GrassPlatform(2450, 2550, 200, 50));
		objects.add(new PushableBox(2450,2650,80,80));
		objects.add(new CreatureDartGoblin(2950, 2450, 30, 40, 400, 300));
		objects.add(new TextDisplayTrigger(2300, 2400, 100, 100, "Warning: some creatures have ranged attacks.", 300));
		
	}
	
	@Override
	public void onTick() {
	}
	
	@Override
	public void moveCamera() {
		super.moveCamera();
		if (GamePanel.getPanel().camera_y < 0) GamePanel.getPanel().camera_y = 0;
	}
	
}
