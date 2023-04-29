package platformerTest.levels.world1;

import java.awt.Color;
import java.util.List;

import platformerTest.assets.creature.ai.horizontal.HorizontalPlayerFollowAi;
import platformerTest.assets.creature.creatures.CreatureZombie;
import platformerTest.assets.solidPlatforms.StonePlatform;
import platformerTest.game.Creature;
import platformerTest.game.GameObject;
import platformerTest.levels.Level;
import platformerTest.menu.GamePanel;

public class Level_1_3 extends Level {

	public Level_1_3() {
		this.backgroundColor = COLOR_DARKSKY;
		
		this.spawnX = 0; //0
		this.spawnY = 200; //200

		this.name = "Entering Battle";
		
	}
	
	@Override
	public void onStart() {
		GamePanel.displayText("Use space to melee attack.", 240);
	}
	
	@Override
	public void drawBackground() {
		List<GameObject> objects = GamePanel.objects;
		
		//spawn platform
		objects.add(new StonePlatform(0, 50, 400, 100));

	}

	@Override
	public void drawPlatforms() {
		List<GameObject> objects = GamePanel.objects;
		
		//spawn platform
		objects.add(new StonePlatform(0, 50, 400, 100));
		
		//battle 1 platform
		objects.add(new StonePlatform(700, 100, 600, 100));
		objects.add(new CreatureZombie(700, 200, 40, 20, 400));
		
	}
	
	@Override
	public void onTick() {
		
	}
	
}
