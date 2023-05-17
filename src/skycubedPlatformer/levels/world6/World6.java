package skycubedPlatformer.levels.world6;

import java.awt.Color;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import skycubedPlatformer.levels.Level;
import skycubedPlatformer.levels.LevelWorld;

public class World6 extends LevelWorld {

	public World6() {
		this.name = "Nightshade Abyss";
		this.menuTint = new Color(0, 0, 0, 150);
		ArrayList<Level> levels = new ArrayList<Level>();
		try {
			this.bg = ImageIO.read(this.getClass().getResource("/worlds/world6.png"));
		} catch (Exception e) {}
		
		
		this.levels = levels;
	}
	
}