package platformerTest.levels;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import platformerTest.levels.world1.World1;
import platformerTest.levels.world2.World2;
import platformerTest.levels.world3.World3;
import platformerTest.levels.world4.World4;
import platformerTest.levels.world5.World5;
import platformerTest.levels.world6.World6;
import platformerTest.levels.world7.World7;

public class LevelWorld {
	
	public LevelWorld() {
		
	}
	
	public Color menuTint = new Color(0, 0, 0, 200);
	public ArrayList<Level> levels;
	public String name;
	public BufferedImage bg;
	
	public static ArrayList<LevelWorld> levelWorlds = new ArrayList<LevelWorld>();
	
	public static void init() {
		levelWorlds.add(new World1());
		levelWorlds.add(new World2());
		levelWorlds.add(new World3());
		levelWorlds.add(new World4());
		levelWorlds.add(new World5());
		levelWorlds.add(new World6());
		levelWorlds.add(new World7());
	}
	
}
