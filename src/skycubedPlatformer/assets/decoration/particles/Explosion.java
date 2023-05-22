package skycubedPlatformer.assets.decoration.particles;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import skycubedPlatformer.Main;
import skycubedPlatformer.assets.PushableObject;
import skycubedPlatformer.assets.decoration.Particle;
import skycubedPlatformer.game.GameObject;
import skycubedPlatformer.game.LivingObject;
import skycubedPlatformer.game.ObjType;
import skycubedPlatformer.game.Player;
import skycubedPlatformer.menu.GamePanel;
import skycubedPlatformer.util.SoundHelper;

public class Explosion extends Particle {

	double damage;
	double knockback;
	double radius;
	GameObject source;
	
	public Explosion(double x, double y, double radius, double damage, double knockback, GameObject source) {
		super(x, y, radius*2, radius*2, Color.WHITE);
		this.radius = radius;
		this.damage = damage;
		this.knockback = knockback;
		this.source = source;
		
		try {
			this.spawnSound = AudioSystem.getClip();
			SoundHelper.loadSound(this, this.spawnSound, "/sounds/effects/explosion.wav");
		} catch (Exception e) {}

		this.lifetime = 20;
		this.gravity = false;

	}
	
	@Override
	public void move() {
		if (this.lifetime == 20) this.init();
		super.move();
	}
	
	private void init() {
		GamePanel.createShake(20, this.radius);
		SoundHelper.playFinalSound(this.spawnSound);
		
		//damage
		for (GameObject obj : GamePanel.objects) {
			if (!this.exists || obj.equals(this)) continue;
			if (this.distanceTo(obj) > this.radius) continue;
			if (obj.type.equals(ObjType.Creature) || obj.type.equals(ObjType.Player)) {
				double multiplier = 1 - this.distanceTo(obj) / this.radius;
				((LivingObject) obj).damage((int) Math.ceil(this.damage * multiplier), this.source, "Explosion");
				//push
				double trueDist = Math.hypot((obj.x - this.x), (obj.y - this.y));
				
				obj.vy += 5*Math.pow(knockback, 2) * multiplier * (obj.y-this.y)/trueDist / obj.getWeight();
				obj.vx += 5*Math.pow(knockback, 2) * multiplier * (obj.x-this.x)/trueDist / obj.getWeight();
				
				
			} else if (obj.type.equals(ObjType.MovableObject) && ((PushableObject) obj).attackable) {
				double multiplier = 1 - this.distanceTo(obj) / this.radius;
				((PushableObject) obj).damage((int) Math.ceil(this.damage * multiplier), this.source, "Explosion");
				//push
				double trueDist = Math.hypot((obj.x - this.x), (obj.y - this.y));
				obj.vy += 5*Math.pow(knockback, 2) * multiplier * (obj.y-this.y)/trueDist / obj.getWeight();
				obj.vx += 5*Math.pow(knockback, 2) * multiplier * (obj.x-this.x)/trueDist / obj.getWeight();
				
			}
		}
		
	}
	
	@Override
	public void draw(Graphics g, Player player, double cam_x, double cam_y, double size) {
		int drawX = (int) ( (this.x - (radius) - (cam_x - size/2)) * (Main.SIZE/size));
		int drawY = (int) ( (size - (this.y + (radius)) + (cam_y - size/2)) * (Main.SIZE/size));

		Graphics2D g2d = (Graphics2D) g.create();
		float alpha = (this.lifetime)/20.0f;
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
		g2d.setComposite(ac);
		g2d.drawImage(GamePanel.explosionImage, drawX, drawY, 
				(int) (this.size_x * Main.SIZE/size), (int) (this.size_y * Main.SIZE/size), null);
		
		
	}

}
