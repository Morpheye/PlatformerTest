package skycubedPlatformer.game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import skycubedPlatformer.Main;
import skycubedPlatformer.assets.Projectile;
import skycubedPlatformer.assets.decoration.Particle;
import skycubedPlatformer.assets.effects.Effect;
import skycubedPlatformer.assets.triggers.Powerup;

public class GameObject implements Cloneable {

	public double slipperiness = 1;
	
	public double size_x;
	public double size_y;
	public double x;
	public double y;
	public double vx = 0;
	public double vy = 0;
	
	public boolean movable = false;
	public double density = 1;
	
	public Color color;
	public boolean solid;
	public boolean exists = true;
	
	public ObjType type;
	public byte drawLayer = 0;
	
	public GameObject(double x, double y, double size_x, double size_y, Color color) {
		this.x = x;
		this.y = y;
		this.size_x = size_x;
		this.size_y = size_y;
		
		this.color = color;
		
	}
	
	public void move() {}
	
	public void draw(Graphics g, Player player, double cam_x, double cam_y, double size) { //move object center to top left edge
		int drawX = (int) ( (this.x - (this.size_x)/2 - (cam_x - size/2)) * (Main.SIZE/size));
		int drawY = (int) ( (size - (this.y + (this.size_y)/2) + (cam_y - size/2)) * (Main.SIZE/size));
		
		g.setColor(this.color);
		g.fillRoundRect(drawX, drawY, (int) (this.size_x * Main.SIZE/size), (int) (this.size_y * Main.SIZE/size), 
		(int)(5*(Main.SIZE/size)), (int)(5*(Main.SIZE/size)));
	}
	
	
	public boolean hasCollided(GameObject obj) {
		boolean xCollided, yCollided;
		if (!this.exists || (!obj.exists)) return false; 
		
		if (obj.getLowerBoundX() < this.getHigherBoundX() && obj.getHigherBoundX() > this.getLowerBoundX()) xCollided = true;
		else xCollided = false;
		if (obj.getLowerBoundY() < this.getHigherBoundY() && obj.getHigherBoundY() > this.getLowerBoundY()) yCollided = true;
		else yCollided = false;
		
		return xCollided && yCollided;
	}
	
	public double distanceTo(GameObject obj) {
		return Math.hypot(this.x-obj.x, this.y-obj.y);
	}
	
	public double getLowerBoundX() {
		return this.x - (this.size_x / 2);
	}
	
	public double getLowerBoundY() {
		return this.y - (this.size_y / 2);
	}

	public double getHigherBoundX() {
		return this.x + (this.size_x / 2);
	}
	
	public double getHigherBoundY() {
		return this.y + (this.size_y / 2);
	}
	
	public double getArea() {
		return this.size_x * this.size_y;
	}
	
	public double getWeight() {
		return this.getArea() * this.density;
	}
	

	public ArrayList<GameObject> pushx(double v, GameObject pusher, ArrayList<GameObject> pushers, boolean wall, boolean keepV) {
		return new ArrayList<GameObject>();
	}
	
	public ArrayList<GameObject> pushy(double v, GameObject pusher, ArrayList<GameObject> pushers, boolean wall, boolean keepV) {
		return new ArrayList<GameObject>();
	}
	
	public void crush() {}

	public void destroy() { //on restart or end
		if (this instanceof LivingObject) {
			LivingObject livingObj = (LivingObject) this;
			if (livingObj.attackSound != null) if (livingObj.attackSound.isOpen()) livingObj.attackSound.close();
			if (livingObj.hitSound != null) if (livingObj.hitSound.isOpen()) livingObj.hitSound.close();
			
			livingObj.effects = new ArrayList<Effect>(); //wipe effects
		}

		if (this instanceof Projectile) {
			Projectile projectile = (Projectile) this;
			if (projectile.attackSound != null) if (projectile.attackSound.isOpen()) projectile.attackSound.close();
			if (projectile.hitSound != null) if (projectile.hitSound.isOpen()) projectile.hitSound.close();
		}
		
		if (this instanceof Particle) {
			Particle particle = (Particle) this;
			if (particle.spawnSound != null) if (particle.spawnSound.isOpen()) particle.spawnSound.close();
			if (particle.despawnSound != null) if (particle.despawnSound.isOpen()) particle.despawnSound.close();
		}
		
		if (this instanceof Powerup) {
			Powerup powerup = (Powerup) this;
			if (powerup.sound != null) if (powerup.sound.isOpen()) powerup.sound.close();
		}
		
	}
	
	@Override
	public GameObject clone() throws CloneNotSupportedException {
		return (GameObject) super.clone();
	}
	
	//COLORS
	
	public static final Color COLOR_GRASS = new Color(20, 200, 20);
	public static final Color COLOR_DIRT = new Color(155, 118, 83);
	public static final Color COLOR_SAND = new Color(255, 255, 150);
	public static final Color COLOR_WOOD = new Color(133, 94, 30);
	public static final Color COLOR_PLANKS = new Color(226, 187, 123);
	public static final Color COLOR_STONE = new Color(100, 100, 100);
	public static final Color COLOR_IRON = new Color(220,220,220);
	
	public static final Color COLOR_COPPER = new Color(184, 115, 51);
	public static final Color COLOR_SILVER = new Color(180, 180, 200);
	public static final Color COLOR_GOLD = new Color(255, 215, 0);
	public static final Color COLOR_DIAMOND = new Color(0,255,255);
	public static final Color COLOR_CRIMSONADE = new Color(180, 0, 0);
	
	public static final Color COLOR_WATER = new Color(0, 50, 255, 100);
	
}
