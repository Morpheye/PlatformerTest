package platformerTest.levels.world5;

import java.awt.Color;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import platformerTest.levels.Level;
import platformerTest.levels.LevelWorld;

public class World5 extends LevelWorld {

	public World5() {
		this.name = "Phantom Skyrise";
		this.menuTint = new Color(0, 0, 0, 200);
		ArrayList<Level> levels = new ArrayList<Level>();
		try {
			this.bg = ImageIO.read(this.getClass().getResource("/worlds/world5.png"));
		} catch (Exception e) {}
		
		
		this.levels = levels;
	}
	
}
