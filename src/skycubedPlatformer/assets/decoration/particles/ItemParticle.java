package skycubedPlatformer.assets.decoration.particles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.sound.sampled.AudioSystem;

import skycubedPlatformer.Main;
import skycubedPlatformer.assets.decoration.Particle;
import skycubedPlatformer.game.Player;
import skycubedPlatformer.items.Item;
import skycubedPlatformer.menu.ApplicationFrame;
import skycubedPlatformer.menu.GamePanel;
import skycubedPlatformer.util.SoundHelper;
import skycubedPlatformer.util.appdata.DataManager;

public class ItemParticle extends Particle {

	BufferedImage img;
	
	public ItemParticle(double x, double y, Item item, int amount) {
		super(x, y, 40, 40, Color.WHITE);
		this.lifetime = 90;
		this.gravity = false;
		
		this.vx = (0.5 - Math.random()) * 5;
		this.vy = 2+(Math.random() * 1);
		
		//add
		DataManager.addItem(item.name, (long) amount);
		this.img = item.image;
		
		try {
			this.spawnSound = AudioSystem.getClip();
			SoundHelper.loadSound(this, this.spawnSound, "/sounds/inventory/consume.wav");
			SoundHelper.playFinalSound(this.spawnSound);
		} catch (Exception e) {}
	}
	
	int lastNormalDrawX, lastNormalDrawY = 0;
	int target_x = Main.SIZE*3/5;
	int target_y = 10;
	
	@Override
	public void draw(Graphics g, Player player, double cam_x, double cam_y, double size) {
		if (this.lifetime > 30) {
			int drawX = (int) ( (this.x - (this.size_x)/2 - (cam_x - size/2)) * (Main.SIZE/size));
			int drawY = (int) ( (size - (this.y + (this.size_y)/2) + (cam_y - size/2)) * (Main.SIZE/size));
			
			lastNormalDrawX = drawX;
			lastNormalDrawY = drawY;
			
			((Graphics2D) g).drawImage(this.img, drawX, drawY, 
					(int) (this.size_x * Main.SIZE/size), (int) (this.size_x * Main.SIZE/size), null);
		} else {
			Graphics2D g2d = (Graphics2D) g;
			
			this.x = GamePanel.getPanel().camera_x;
			this.y = GamePanel.getPanel().camera_y;
			
			int drawX = (int) (lastNormalDrawX + (target_x-lastNormalDrawX)*((30-this.lifetime)/30.0));
			int drawY = (int) (lastNormalDrawY + (target_y-lastNormalDrawY)*((30-this.lifetime)/30.0));
			
			((Graphics2D) g).drawImage(this.img, drawX, drawY, 
					(int) (this.size_x * Main.SIZE/size), (int) (this.size_x * Main.SIZE/size), null);
			
		}
		
	}
	
	public static void spawnItem(double x, double y, Item item, int count) {

		GamePanel.getPanel().particles.add(new ItemParticle(x, y, item, count));
		return;
		
	}
	
	@Override
	public void destroy() {
		img.flush();
		super.destroy();
	}

}
