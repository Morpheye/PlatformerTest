package skycubedPlatformer.assets.decoration.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import skycubedPlatformer.Main;
import skycubedPlatformer.assets.DecorationObject;
import skycubedPlatformer.assets.contraptions.Generator;
import skycubedPlatformer.assets.creature.creatures.undead.CreatureBabyUndead;
import skycubedPlatformer.assets.creature.creatures.undead.CreatureUndead;
import skycubedPlatformer.game.GameObject;
import skycubedPlatformer.game.Player;
import skycubedPlatformer.menu.GamePanel;

public class NecromancyGravestone extends Gravestone implements Generator {

	DecorationObject stone;
	DecorationObject cross;
	
	ArrayList<GameObject> generated;
	int lifetimeGenerated;
	
	int max;
	int lifetimeMax;
	
	double range;
	int maxDelay = 100;

	
	public NecromancyGravestone(double x, double y, double size_x, double size_y, double range, int max, int lifetimeMax) {
		super(x, y, size_x, size_y);
		this.drawLayer = -7;
		
		if (size_x > size_y) { 
			this.stone = new DecorationObject(x, y, size_x, size_y, Color.DARK_GRAY);
			
		} else {
			this.stone = new DecorationObject(x, y-size_y/4, size_x, size_y/2, Color.DARK_GRAY);
			this.cross = new DecorationObject(x, y+size_y/4, size_x/5, size_y/2, Color.GRAY);
		}
		
		this.max = max;
		this.lifetimeMax = lifetimeMax;
		this.range = range;
		this.generated = new ArrayList<GameObject>();
		
		this.lifetimeGenerated = 0;
		
	}
	
	public int delay = 0;
	@Override
	public void move() {
		if (this.lifetimeGenerated < this.lifetimeMax) { 
			generated.removeIf(obj -> !GamePanel.getPanel().objects.contains(obj));
			if (this.delay < maxDelay && generated.size() < max) this.delay++;
			boolean occupied = false;
			for (GameObject obj : generated) if (this.hasCollided(obj)) occupied = true;
			if (!occupied && this.delay >= maxDelay && generated.size() < max
					&& distanceTo(GamePanel.getPanel().player) < range) {
				this.generate();
				this.delay -= maxDelay;
			}
		}
		
		super.move();
	}
	
	
	@Override
	public void generate() {
		GameObject newObj;
		
		if (Math.random() > 0.8) newObj = new CreatureBabyUndead(this.stone.x, this.stone.y, 30, 0, 400, 0, 400);
		else newObj = new CreatureUndead(this.x, this.y, 40, 30, 0, 400, 0, 400);

		for (GameObject obj : GamePanel.getPanel().objects) {
			if (!this.exists) continue;
			if (obj.equals(this)) continue;
			if (this.hasCollided(obj) && obj.exists && obj.solid) {
				newObj.destroy();
				return;
			}
		}
		
		this.lifetimeGenerated++;
		this.generated.add(newObj);
		GamePanel.getPanel().addedObjects.add(newObj);
	}
	
	@Override
	public void draw(Graphics g, Player player, double cam_x, double cam_y, double size) {
		Graphics2D g2d = (Graphics2D) g;
		if (size_x > size_y) { 
			int drawX = (int) ( (this.stone.x - (this.stone.size_x)/2 - (cam_x - size/2)) * (Main.SIZE/size));
			int drawY = (int) ( (size - (this.stone.y + (this.stone.size_y)/2) + (cam_y - size/2)) * (Main.SIZE/size));
			
			g.setColor(this.stone.color);
			g.fillRoundRect(drawX, drawY, (int) (this.stone.size_x * Main.SIZE/size), (int) (this.stone.size_y * Main.SIZE/size), 
			(int)(this.size_y/2*(Main.SIZE/size)), (int)(this.size_y/2*(Main.SIZE/size)));
			g.fillRect(drawX, drawY+(int)(this.stone.size_y/2*(Main.SIZE/size)), (int) (this.stone.size_x * Main.SIZE/size),
					(int) (this.stone.size_y * Main.SIZE/size - (int)(this.stone.size_y/2*(Main.SIZE/size))));
			
		} else {
			//cross stem
			int drawX = (int) ( (this.cross.x - (this.cross.size_x)/2 - (cam_x - size/2)) * (Main.SIZE/size));
			int drawY = (int) ( (size - (this.cross.y + (this.cross.size_y)/2) + (cam_y - size/2)) * (Main.SIZE/size));
			g.setColor(this.cross.color);
			g.fillRect(drawX, drawY, (int) (this.cross.size_x * Main.SIZE/size), (int) (this.cross.size_y*1.5* Main.SIZE/size));
			
			//cross horizontal part
			drawX = (int) ( (this.stone.x - (this.stone.size_x)/3 - (cam_x - size/2)) * (Main.SIZE/size));
			drawY = (int) ( (size - (this.cross.y + (this.cross.size_y)/4) + (cam_y - size/2)) * (Main.SIZE/size));
			g.fillRect(drawX, drawY, (int) (this.stone.size_x*2/3 * Main.SIZE/size), (int) (this.cross.size_x* Main.SIZE/size));
			
			//then stone
			drawX = (int) ( (this.stone.x - (this.stone.size_x)/2 - (cam_x - size/2)) * (Main.SIZE/size));
			drawY = (int) ( (size - (this.stone.y + (this.stone.size_y)/2) + (cam_y - size/2)) * (Main.SIZE/size));
			
			g.setColor(this.stone.color);
			g.fillRoundRect(drawX, drawY, (int) (this.stone.size_x * Main.SIZE/size), (int) (this.stone.size_y * Main.SIZE/size), 
			(int)(this.size_y/2*(Main.SIZE/size)), (int)(this.size_y/2*(Main.SIZE/size)));
			g.fillRect(drawX, drawY+(int)(this.stone.size_y/2*(Main.SIZE/size)), (int) (this.stone.size_x * Main.SIZE/size),
					(int) (this.stone.size_y * Main.SIZE/size - (int)(this.stone.size_y/2*(Main.SIZE/size))));

			
		}
	}

}
