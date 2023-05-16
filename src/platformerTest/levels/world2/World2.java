package platformerTest.levels.world2;

import java.awt.Color;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import platformerTest.levels.Level;
import platformerTest.levels.LevelWorld;

public class World2 extends LevelWorld {

	public World2() {
		this.name = "Boxer Plateau";
		this.menuTint = new Color(0, 0, 0, 200);
		ArrayList<Level> levels = new ArrayList<Level>();
		try {
			this.bg = ImageIO.read(this.getClass().getResource("/worlds/world2.png"));
		} catch (Exception e) {}
		
		
		this.levels = levels;
	}
	
}
