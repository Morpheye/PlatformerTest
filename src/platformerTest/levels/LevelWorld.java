package platformerTest.levels;

import java.awt.Color;
import java.util.ArrayList;

import platformerTest.levels.world1.World1;

public class LevelWorld {
	
	public LevelWorld() {
		
	}
	
	public Color menuBGColor = Color.BLACK;
	public ArrayList<Level> levels;
	
	public static ArrayList<LevelWorld> levelWorlds = new ArrayList<LevelWorld>();
	
	public static void init() {
		levelWorlds.add(new World1());
	}
	
}
