package platformerTest.levels.world1;

import java.util.List;

import platformerTest.assets.SolidPlatform;
import platformerTest.assets.creature.creatures.Creature;
import platformerTest.assets.creature.creatures.goblin.CreatureDartGoblin;
import platformerTest.assets.creature.creatures.goblin.CreatureGoblin;
import platformerTest.assets.interactables.FinishFlag;
import platformerTest.assets.powerups.HealPowerup;
import platformerTest.assets.powerups.PunchPowerup;
import platformerTest.assets.solidPlatforms.GrassPlatform;
import platformerTest.assets.solidPlatforms.SandPlatform;
import platformerTest.assets.solidPlatforms.WoodPlatform;
import platformerTest.assets.triggers.TextDisplayTrigger;
import platformerTest.game.GameObject;
import platformerTest.levels.Level;
import platformerTest.menu.GamePanel;

public class Level_1_5 extends Level {

	public Level_1_5() {
		this.backgroundColor = COLOR_DARKSKY;
		
		this.spawnX = 0; //0
		this.spawnY = 200; //200

		this.reqs = new String[] {"Level_1_4"};
		this.reward = 70;
		this.name = "Goblin Sands";
		
	}
	
	@Override
	public void onStart() {
		
		GamePanel.camera_x = GamePanel.player.x;
		GamePanel.camera_y = GamePanel.player.y;
		GamePanel.displayText("Don't fall behind!", 240);
	}
	
	@Override
	public void drawBackground() {
		List<GameObject> objects = GamePanel.objects;
		
		finishFlag = new FinishFlag(12100, 1400, 50, 100);
		objects.add(finishFlag);

	}
	
	@Override
	public void drawForeground() {
		List<GameObject> objects = GamePanel.objects;

	}
	
	SolidPlatform movingPlatform;
	SolidPlatform finishPlatform;
	FinishFlag finishFlag;
	Creature finalGoblin;
	
	@Override
	public void drawPlatforms() {
		List<GameObject> objects = GamePanel.objects;
		
		//spawn platform
		objects.add(new GrassPlatform(0, 50, 400, 100));
		
		//parkour
		objects.add(new GrassPlatform(500, 50, 200, 200));
		objects.add(new GrassPlatform(850, 150, 100, 100));
		objects.add(new GrassPlatform(1150, 200, 100, 100));
		objects.add(new GrassPlatform(1500, 200, 200, 200));
		
		objects.add(new GrassPlatform(2000, 300, 400, 100));
		objects.add(new CreatureGoblin(1950, 400, 30, 20, 300, 0, 300));
		objects.add(new CreatureGoblin(2000, 400, 30, 20, 300, 0, 300));
		
		//sand
		objects.add(new SandPlatform(2650, 350, 400, 100));
		objects.add(new SandPlatform(2800, 400, 200, 200));
		objects.add(new CreatureDartGoblin(2800, 550, 30, 300));
		
		objects.add(new GrassPlatform(3200, 450, 200, 200));
		objects.add(new HealPowerup(3200, 600, 50));
		objects.add(new TextDisplayTrigger(3200, 600, 50, 50, "Caution: Don't run ahead too fast!", 300));
		
		//dart goblin madness
		objects.add(new GrassPlatform(3600, 500, 200, 200));
		objects.add(new GrassPlatform(4000, 500, 200, 200));
		objects.add(new CreatureDartGoblin(4000, 650, 30, 400));
		
		objects.add(new GrassPlatform(4350, 500, 200, 50));
		objects.add(new GrassPlatform(4350, 700, 200, 50));
		objects.add(new CreatureDartGoblin(4350, 525, 30, 500));
		
		objects.add(new GrassPlatform(4700, 500, 200, 200));
		
		//sand
		objects.add(new TextDisplayTrigger(5000, 700, 100, 100, "Beware of ambushes.", 300));
		objects.add(new SandPlatform(5200, 600, 400, 100));
		objects.add(new SandPlatform(5800, 650, 400, 100));
		
		//ambush
		objects.add(new SandPlatform(5600, 1500, 300, 100));
		objects.add(new SandPlatform(5400, 1500, 50, 150));
		objects.add(new CreatureGoblin(5650, 1600, 30));
		objects.add(new CreatureGoblin(5600, 1600, 30));
		
		objects.add(new GrassPlatform(6300, 650, 200, 200));
		objects.add(new HealPowerup(6300, 800, 50));
		
		//moving platform
		objects.add(new GrassPlatform(6750, 1100, 400, 200));
		movingPlatform = new WoodPlatform(6850, 750, 400, 50);
		objects.add(movingPlatform);
		
		objects.add(new GrassPlatform(7400, 550, 400, 200));
		objects.add(new CreatureDartGoblin(7350, 700, 30, 500));
		objects.add(new PunchPowerup(7400, 685, 50, 5));
		objects.add(new TextDisplayTrigger(7400, 685, 50, 50, "Extra knockback is useful against swarms.", 300));
		
		//ambush 2
		objects.add(new SandPlatform(8200, 1400, 400, 100));
		objects.add(new CreatureGoblin(8150, 1500, 30, 20, 400));
		objects.add(new CreatureGoblin(8250, 1500, 30, 20, 400));
		objects.add(new CreatureGoblin(8200, 1500, 30, 20, 400));
		
		objects.add(new GrassPlatform(9000, 890, 400, 200));
		
		objects.add(new GrassPlatform(9000, 500, 350, 50));
		objects.add(new TextDisplayTrigger(9000, 600, 200, 200, "Keep up with the moving platform.", 300));
		
		objects.add(new GrassPlatform(9400, 550, 200, 50));
		objects.add(new GrassPlatform(9800, 600, 200, 50));
		objects.add(new GrassPlatform(10200, 650, 200, 50));
		
		objects.add(new GrassPlatform(11500, 650, 200, 200));
		objects.add(new GrassPlatform(12100, 700, 600, 200));
		finalGoblin = new CreatureDartGoblin(12400, 850, 30, 100, 500, 50, 150, 300);
		finalGoblin.required = true;
		objects.add(finalGoblin);
		
		finishPlatform = new SandPlatform(12100, 1325, 200, 50);
		objects.add(finishPlatform);
		
	}
	
	@Override
	public void onTick() {
		//Autoscroll
		if ((GamePanel.camera_x - GamePanel.player.x > GamePanel.camera_size/2 + 100) && GamePanel.levelWon == 0) {
			if (GamePanel.player.isAlive) {
				GamePanel.player.health = 0;
				GamePanel.player.die();
			}
		}
		
		if (GamePanel.camera_x >= 9450 && GamePanel.camera_x <= 9800) movingPlatform.vy = 0.6;
		else if (GamePanel.camera_x >= 10200 && GamePanel.camera_x <= 10700) movingPlatform.vy = -0.6;
		else movingPlatform.vy = 0;
		
		if (GamePanel.camera_x >= 6850 && GamePanel.camera_x <= 11000) movingPlatform.vx = 1;
		else movingPlatform.vx = 0;
		
		if (!GamePanel.objects.contains(finalGoblin) && finishPlatform.y > 825) {
			finishPlatform.vy = -1;
			finishFlag.vy = -1;
		} else {
			finishPlatform.vy = 0;
			finishFlag.vy = 0;
		}
		
	}
	
	@Override
	public void moveCamera() {
		if (GamePanel.camera_x <= 12100) GamePanel.camera_x += 1;
		else {
			if (GamePanel.player.x <= 12100) {}
			else {
				if (GamePanel.player.x - GamePanel.camera_x > 10 && GamePanel.player.x < 12800) GamePanel.camera_x += 10;
				else GamePanel.camera_x = GamePanel.player.x;
			
		}}
		
		double diffY = GamePanel.player.y - GamePanel.camera_y;
		
		int higherLimitY = 100;
		int lowerLimitY = -100;

		if (diffY > higherLimitY) GamePanel.camera_y = GamePanel.player.y - higherLimitY;
		if (diffY < lowerLimitY) GamePanel.camera_y = GamePanel.player.y - lowerLimitY;
		
	}
	
}
