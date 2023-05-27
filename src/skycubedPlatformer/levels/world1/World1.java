package skycubedPlatformer.levels.world1;

import java.awt.Color;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import skycubedPlatformer.levels.Level;
import skycubedPlatformer.levels.LevelWorld;

public class World1 extends LevelWorld {

	public World1() {
		this.name = "Goblin Oasis";
		this.menuTint = new Color(0, 0, 0, 200);
		ArrayList<Level> levels = new ArrayList<Level>();
		try {
			this.bg = ImageIO.read(this.getClass().getResource("/worlds/world1.png"));
		} catch (Exception e) {}
		
		levels.add(new Level_1_1());
		levels.add(new Level_1_2());
		levels.add(new Level_1_3());
		levels.add(new Level_1_4());
		levels.add(new Level_1_5());
		levels.add(new Level_1_6());
		levels.add(new Level_1_7());
		levels.add(new Level_1_8());
		levels.add(new Level_1_9());
		
		this.levels = levels;
	}
	
}
