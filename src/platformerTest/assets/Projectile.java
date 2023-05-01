package platformerTest.assets;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import platformerTest.Main;
import platformerTest.game.Creature;
import platformerTest.game.GameObject;
import platformerTest.game.MovableObject;
import platformerTest.game.ObjType;
import platformerTest.game.Player;
import platformerTest.menu.GamePanel;

public class Projectile extends MovableObject {

	public int lifetime;
	
	public boolean waterResistant;
	public boolean hitTarget;
	public boolean rotating;
	
	public double pushStrength;
	public int damage;
	public GameObject firer;

	
	public Projectile(double x, double y, double size_x, double size_y, double vx, double vy, GameObject firer, int damage) {
		super(x, y, size_x, size_y, Color.black, 1);
		this.firer = firer;
		this.vx = vx;
		this.vy = vy;
		this.lifetime = 0;
		this.waterResistant = false;
		this.hitTarget = false;
		this.pushStrength = 1;
		this.rotating = false;
		this.damage = damage;
		this.type = ObjType.Projectile;
		this.solid = false;
	}
	
	@Override
	public void move() {
		this.y += this.vy;
		this.x += this.vx;
		
		int direction = (vx > 0) ? 1 : -1;
		
		for (GameObject obj : GamePanel.objects) {
			if (!this.exists || this.hitTarget) continue;
			if (obj.equals(this) || obj.equals(this.firer)) continue;
			if (this.hasCollided(obj) && obj.exists) {

				if (obj.type.equals(ObjType.Player))  {
					this.hitTarget = true;
					this.lifetime = 0;
					((Player) obj).damage(this.damage, this.firer);
					
					ArrayList<GameObject> list = new ArrayList<GameObject>();
					list.add(this);
					obj.pushx(direction * this.pushStrength, this, list, false, true);
					
				} else if (obj.type.equals(ObjType.Creature))  {
					this.hitTarget = true;
					this.lifetime = 0;
					if (firer.type.equals(ObjType.Creature)) {
						if (((Creature) firer).friendlyFire) ((Creature) obj).damage(this.damage, this.firer);
					} else ((Creature) obj).damage(this.damage, this.firer);
					
					ArrayList<GameObject> list = new ArrayList<GameObject>();
					list.add(this);
					obj.pushx(direction * this.pushStrength, this, list, false, true);
					
				} else if (obj.type.equals(ObjType.SolidPlatform) || obj.type.equals(ObjType.MovableObject))  {
					this.hitTarget = true;
					this.lifetime = 0;
					
					ArrayList<GameObject> list = new ArrayList<GameObject>();
					list.add(this);
					if (obj.type.equals(ObjType.MovableObject)) obj.pushx(direction * this.pushStrength, this, list, false, true);
				}
					
		}}
		
		this.lifetime--;
		if (this.lifetime <= 0) {
			this.exists = false;
		}
	}
	
	@Override
	public void draw(Graphics g, Player player, double cam_x, double cam_y, double size) {
		
	}
	
}
