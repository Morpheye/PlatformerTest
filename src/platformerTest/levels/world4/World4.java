package platformerTest.levels.world4;

import java.awt.Color;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import platformerTest.levels.Level;
import platformerTest.levels.LevelWorld;

public class World4 extends LevelWorld {

	public World4() {
		this.name = "Sludge Swamp";
		this.menuTint = new Color(0, 0, 0, 200);
		ArrayList<Level> levels = new ArrayList<Level>();
		try {
			this.bg = ImageIO.read(this.getClass().getResource("/worlds/world4.png"));
		} catch (Exception e) {}
		
		
		this.levels = levels;
	}
	
}
