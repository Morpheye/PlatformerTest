package skycubedPlatformer.assets.pushableObjects.special;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import skycubedPlatformer.Main;
import skycubedPlatformer.assets.PushableObject;
import skycubedPlatformer.assets.decoration.particles.Explosion;
import skycubedPlatformer.game.GameObject;
import skycubedPlatformer.game.LivingObject;
import skycubedPlatformer.game.ObjType;
import skycubedPlatformer.game.Player;
import skycubedPlatformer.menu.GamePanel;

public class PushableExplosive extends PushableObject {

	public int health;
	public int maxHealth;
	public double blastStrength;
	public LivingObject lastDamager;
	
	public PushableExplosive(double x, double y, double size, double blastStrength, int health) {
		super(x, y, size, size, new Color(166, 107, 43), 1.0, 0.965);
		this.maxHealth = health;
		this.health = this.maxHealth;
		this.blastStrength = blastStrength;
		this.dmgTime = 0;
		this.lastDamager = null;
	}
	
	@Override
	public void move() {
		if (this.health <= 0) this.health = 0;
		if (this.dmgTime > 0 && this.health > 0) this.dmgTime -= 5;
		else if (this.health <= 0 && this.dmgTime < 510) this.dmgTime += 5;
		if (this.dmgTime >= 510) this.explode();
	}
	
	public void explode() {
		this.exists = false;
		GamePanel.deletedObjects.add(this);
		
		double lowerBoundX = getLowerBoundX(), lowerBoundY = getLowerBoundY(),
				higherBoundX = getHigherBoundX(), higherBoundY = getHigherBoundY();
		
		GamePanel.particles.add(new Explosion(this.x, this.y, 2*this.blastStrength, this.blastStrength, this.blastStrength, this.lastDamager) {
			@Override
			public double getLowerBoundX() {
				return lowerBoundX;
			}
			@Override
			public double getLowerBoundY() {
				return lowerBoundY;
			}
			@Override
			public double getHigherBoundX() {
				return higherBoundX;
			}
			@Override
			public double getHigherBoundY() {
				return higherBoundY;
			}
		});

	}
	
	@Override
	public void damage(int damage, GameObject source) {
		if (damage > 20 & this.dmgTime < 255) this.dmgTime = 255;
		else if (damage > 5 & this.dmgTime < 175) this.dmgTime = 175;
		else if (this.dmgTime < 100) this.dmgTime = 100;
		
		this.health -= damage;
		
		if (source.type.equals(ObjType.Creature) || source.type.equals(ObjType.Player)) this.lastDamager = (LivingObject) source;
	}
	
	@Override
	public void die() {
		this.explode();
	}
	
	public int dmgTime;
	@Override
	public void draw(Graphics g, Player player, double x, double y, double size) {
		int shakeX = (int) (3 * Math.random() * (Main.SIZE/size) * (1 - (double) this.health / this.maxHealth));
		int shakeY = (int) (3 * Math.random() * (Main.SIZE/size) * (1 - (double) this.health / this.maxHealth));
		
		double cam_x = x + shakeX;
		double cam_y = y + shakeY;
		
		super.draw(g, player, cam_x+shakeX, cam_y+shakeY, size);
		//dmg
		int drawX = (int) ( (this.x - (this.size_x)/2 - (cam_x - size/2)) * (Main.SIZE/size));
		int drawY = (int) ( (size - (this.y + (this.size_y)/2) + (cam_y - size/2)) * (Main.SIZE/size));
		g.setColor(new Color(255, 0, 0, (dmgTime/2 > 255) ? 255 : dmgTime/2));
		g.fillRect(drawX, drawY, (int) (this.size_x * Main.SIZE/size), (int) (this.size_y * Main.SIZE/size));
		
		int brushSize = (int)(5*(Main.SIZE/size));
		
		drawX = (int) ( (this.x + brushSize/2 - (this.size_x)/2 - (cam_x - size/2)) * (Main.SIZE/size));
		drawY = (int) ( (size - (this.y - brushSize/2 + (this.size_y)/2) + (cam_y - size/2)) * (Main.SIZE/size));
		
		//border
		g.setColor(GameObject.COLOR_WOOD.darker().darker());
		((Graphics2D) g).setStroke(new BasicStroke(brushSize));
		g.drawRoundRect(drawX-1, drawY-1, (int) ((this.size_x-brushSize+2) * Main.SIZE/size), (int) ((this.size_y-brushSize+2) * Main.SIZE/size), 
		(int)(4*(Main.SIZE/size)), (int)(4*(Main.SIZE/size)));
		
		//plates
		drawX = (int) ( (this.x - (this.size_x)/2 - (cam_x - size/2)) * (Main.SIZE/size));
		drawY = (int) ( (size - (this.y + (this.size_y)/2) + (cam_y - size/2)) * (Main.SIZE/size));
		g.setColor(GameObject.COLOR_IRON.darker());
		g.fillRect(drawX, drawY + (int) (this.size_y*2/10 * Main.SIZE/size)
				,(int) (this.size_x * Main.SIZE/size), (int) (this.size_y/10 * Main.SIZE/size));
		g.fillRect(drawX, drawY + (int) (this.size_y*7/10 * Main.SIZE/size)
				,(int) (this.size_x * Main.SIZE/size), (int) (this.size_y/10 * Main.SIZE/size));
		
		//display
		g.setColor(Color.red.darker());
		drawX = (int) ( (this.x - (this.size_x)/4 - (cam_x - size/2)) * (Main.SIZE/size));
		drawY = (int) ( (size - (this.y + (this.size_y)/8) + (cam_y - size/2)) * (Main.SIZE/size));
		g.fillRect(drawX, drawY, (int) (this.size_x/2 * Main.SIZE/size), (int) (this.size_y/4 * Main.SIZE/size));
		
		g.setColor(new Color(255,0,0,150));
		g.fillRect(drawX, drawY, (int) ((1 - (double) this.health / this.maxHealth) * this.size_x/2 * Main.SIZE/size), 
				(int) (this.size_y/4 * Main.SIZE/size));
		
		brushSize = (int)(3*(Main.SIZE/size));
		drawX = (int) ( (this.x + brushSize/2 - (this.size_x)/4 - (cam_x - size/2)) * (Main.SIZE/size));
		drawY = (int) ( (size - (this.y - brushSize/2 + (this.size_y)/8) + (cam_y - size/2)) * (Main.SIZE/size));
		g.setColor(Color.RED.darker().darker());
		((Graphics2D) g).setStroke(new BasicStroke(brushSize));
		g.drawRoundRect(drawX-1, drawY-1, (int) ((this.size_x/2-brushSize+2) * Main.SIZE/size), (int) ((this.size_y/4-brushSize+2) * Main.SIZE/size), 
		(int)(3*(Main.SIZE/size)), (int)(3*(Main.SIZE/size)));
		
	}

}
