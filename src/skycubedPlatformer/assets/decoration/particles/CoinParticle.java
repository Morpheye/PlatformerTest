package skycubedPlatformer.assets.decoration.particles;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import skycubedPlatformer.Main;
import skycubedPlatformer.appdata.DataManager;
import skycubedPlatformer.assets.decoration.Particle;
import skycubedPlatformer.game.GameObject;
import skycubedPlatformer.game.Player;
import skycubedPlatformer.menu.GamePanel;

public class CoinParticle extends Particle {

	public int coinAmount;
	
	public CoinParticle(double x, double y, int coinAmount) {
		super(x, y, 20, 20, GameObject.COLOR_COPPER);

		this.lifetime = 90;
		this.gravity = true;
		
		this.vx = (0.5 - Math.random()) * 10;
		this.vy = 5 + (Math.random() * 10);
		
		this.coinAmount = coinAmount;
		
		try {
			InputStream sound = new BufferedInputStream(this.getClass().getResourceAsStream("/sounds/coin/coin.wav"));
			AudioInputStream audioStreamAttack = AudioSystem.getAudioInputStream(sound);
		
			this.despawnSound = AudioSystem.getClip();
			this.despawnSound.open(audioStreamAttack);
			
		} catch (Exception e) {}
		
	}
	
	@Override
	public void move() {
		if (this.lifetime == 0) {
			GamePanel.targetCoins += this.coinAmount;
			DataManager.saveData.coins += this.coinAmount;
			
		}
		super.move();
	}
	
	int lastNormalDrawX, lastNormalDrawY = 0;
	int target_x = Main.SIZE*3/5;
	int target_y = 10;
	
	@Override
	public void draw(Graphics g, Player player, double cam_x, double cam_y, double size) {

		BufferedImage image;
		
		if (this.coinAmount >= 20) image = GamePanel.goldCoinImage;
		else if (this.coinAmount >= 5) image = GamePanel.silverCoinImage;
		else image = GamePanel.copperCoinImage;
		
		if (this.lifetime > 30) {
			int drawX = (int) ( (this.x - (this.size_x)/2 - (cam_x - size/2)) * (Main.SIZE/size));
			int drawY = (int) ( (size - (this.y + (this.size_y)/2) + (cam_y - size/2)) * (Main.SIZE/size));
			
			lastNormalDrawX = drawX;
			lastNormalDrawY = drawY;
			
			((Graphics2D) g).drawImage(image, drawX, drawY, 
					(int) (this.size_x * Main.SIZE/size), (int) (this.size_x * Main.SIZE/size), null);
		} else {
			if (this.lifetime == 30) this.despawnSound.start();
			Graphics2D g2d = (Graphics2D) g;
			
			this.x = GamePanel.camera_x;
			this.y = GamePanel.camera_y;
			
			int drawX = (int) (lastNormalDrawX + (target_x-lastNormalDrawX)*((30-this.lifetime)/30.0));
			int drawY = (int) (lastNormalDrawY + (target_y-lastNormalDrawY)*((30-this.lifetime)/30.0));
			
			((Graphics2D) g).drawImage(image, drawX, drawY, 
					(int) (this.size_x * Main.SIZE/size), (int) (this.size_x * Main.SIZE/size), null);
			
		}
		
	}
	
	public static void spawnCoins(double x, double y, int count, int total) {
		int[] coins;
		if(count >= total) {
			coins = new int[count];
			for (int i=0; i<coins.length; i++) coins[i] = 1;
		} else {
			coins = new int[count];
			for (int i=0; i<coins.length; i++) coins[i] = 1;
			for (int i=0; i<(total-coins.length); i++) {
				coins[(int) (Math.random() * coins.length)]++;
			}
		}
		
		for (int i : coins) GamePanel.particles.add(new CoinParticle(x, y, i));
		return;
		
	}

}
