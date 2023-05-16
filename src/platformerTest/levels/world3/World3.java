package platformerTest.levels.world3;

import java.awt.Color;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import platformerTest.levels.Level;
import platformerTest.levels.LevelWorld;

public class World3 extends LevelWorld {

	public World3() {
		this.name = "Weaver Depths";
		this.menuTint = new Color(0, 0, 0, 200);
		ArrayList<Level> levels = new ArrayList<Level>();
		try {
			this.bg = ImageIO.read(this.getClass().getResource("/worlds/world3.png"));
		} catch (Exception e) {}
		
		
		this.levels = levels;
	}
	
}
