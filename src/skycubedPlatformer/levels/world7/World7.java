package skycubedPlatformer.levels.world7;

import java.awt.Color;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import skycubedPlatformer.levels.Level;
import skycubedPlatformer.levels.LevelWorld;

public class World7 extends LevelWorld {

	public World7() {
		this.name = "Stalker Hellscape";
		this.menuTint = new Color(0, 0, 0, 150);
		ArrayList<Level> levels = new ArrayList<Level>();
		try {
			this.bg = ImageIO.read(this.getClass().getResource("/worlds/world7.png"));
		} catch (Exception e) {}
		
		
		this.levels = levels;
	}
	
}
