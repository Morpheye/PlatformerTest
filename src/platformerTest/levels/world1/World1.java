package platformerTest.levels.world1;

import java.awt.Color;
import java.util.ArrayList;

import platformerTest.levels.Level;
import platformerTest.levels.LevelWorld;

public class World1 extends LevelWorld {

	public World1() {
		this.menuBGColor = Color.BLACK;
		ArrayList<Level> levels = new ArrayList<Level>();
		
		levels.add(new Level_1_1());
		levels.add(new Level_1_2());
		levels.add(new Level_1_3());
		levels.add(new Level_1_4());
		levels.add(new Level_1_5());
		levels.add(new Level_1_6());
		
		
		this.levels = levels;
	}
	
}
