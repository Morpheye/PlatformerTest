package platformerTest.assets;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.sound.sampled.Clip;

import platformerTest.assets.creature.creatures.Creature;
import platformerTest.game.GameObject;
import platformerTest.game.LivingObject;
import platformerTest.game.MovableObject;
import platformerTest.game.ObjType;
import platformerTest.game.Player;
import platformerTest.menu.GamePanel;
import platformerTest.weapons.weaponsT5.SpiritScythe;

public class Projectile extends MovableObject {

	public Clip hitSound;
	public Clip attackSound;
	
	public int lifetime;
	
	public boolean waterResistant;
	public boolean hitTarget;
	public boolean rotating;
	
	public double pushStrength;
	public int damage;
	public LivingObject firer;

	
	public Projectile(double x, double y, double size_x, double size_y, double vx, double vy, LivingObject firer, int damage) {
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
		
		boolean hasCollided = false;
		for (GameObject obj : GamePanel.objects) {
			if (!this.exists || this.hitTarget) continue;
			if (obj.equals(this) || obj.equals(this.firer)) continue;
			if (this.hasCollided(obj) && obj.exists) {
				
				if (obj.solid) hasCollided = true;
				
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
						if (((Creature) firer).friendlyFire || obj instanceof SpiritScythe.ScytheSpirit) {
							((Creature) obj).damage(this.damage, this.firer);
						}
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
					
		}
			
		if (hasCollided) this.playHitSound();
			
		}
		
		this.lifetime--;
		if (this.lifetime <= 0) {
			this.exists = false;
		}
	}
	
	public void playHitSound() {
		if (this.hitSound != null) {
			this.hitSound.setMicrosecondPosition(0);
			this.hitSound.start();
		}
	}
	
	public void playAttackSound() {
		if (this.attackSound != null) {
			this.attackSound.setMicrosecondPosition(0);
			this.attackSound.start();
		}
	}
	
	@Override
	public void draw(Graphics g, Player player, double cam_x, double cam_y, double size) {
		
	}
	
}
