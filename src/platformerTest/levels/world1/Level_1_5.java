package platformerTest.levels.world1;

import java.util.ArrayList;
import java.util.List;

import platformerTest.Main;
import platformerTest.assets.creature.CreatureAi;
import platformerTest.assets.creature.ai.HorizontalMovementAi;
import platformerTest.assets.creature.ai.vertical.VerticalFollowAi;
import platformerTest.assets.creature.creatures.CreatureDartGoblin;
import platformerTest.assets.creature.creatures.CreatureGoblin;
import platformerTest.assets.creature.creatures.CreatureZombie;
import platformerTest.assets.decoration.walls.FadingWallObject;
import platformerTest.assets.decoration.walls.SolidBackgroundObject;
import platformerTest.assets.interactables.FinishFlag;
import platformerTest.assets.powerups.CameraSizePowerup;
import platformerTest.assets.powerups.HealPowerup;
import platformerTest.assets.powerups.OverhealPowerup;
import platformerTest.assets.pushableObjects.PushableBox;
import platformerTest.assets.pushableObjects.PushableStone;
import platformerTest.assets.solidPlatforms.GrassPlatform;
import platformerTest.assets.solidPlatforms.StonePlatform;
import platformerTest.assets.solidPlatforms.WoodPlatform;
import platformerTest.assets.triggers.Code;
import platformerTest.assets.triggers.TextDisplayTrigger;
import platformerTest.game.Creature;
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
		this.name = "Goblin Lands";
		
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

	}
	
	@Override
	public void drawForeground() {
		List<GameObject> objects = GamePanel.objects;

	}
	
	@Override
	public void drawPlatforms() {
		List<GameObject> objects = GamePanel.objects;
		
		//spawn platform
		objects.add(new GrassPlatform(0, 50, 400, 100));
		
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
		
		
	}
	
	@Override
	public void moveCamera() {
		GamePanel.camera_x++;
		
		double diffY = GamePanel.player.y - GamePanel.camera_y;
		
		int higherLimitY = 100;
		int lowerLimitY = -100;

		if (diffY > higherLimitY) GamePanel.camera_y = GamePanel.player.y - higherLimitY;
		if (diffY < lowerLimitY) GamePanel.camera_y = GamePanel.player.y - lowerLimitY;
		
	}
	
}
