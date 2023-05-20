package skycubedPlatformer.levels.world1;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import skycubedPlatformer.Main;
import skycubedPlatformer.assets.interactables.NullZone;
import skycubedPlatformer.assets.solidPlatforms.SandPlatform;
import skycubedPlatformer.game.GameObject;
import skycubedPlatformer.levels.Level;
import skycubedPlatformer.menu.GamePanel;

public class Level_1_8 extends Level {

	public Level_1_8() {
		this.backgroundColor = COLOR_DAYSKY;
		
		this.spawnX = 0; //0 //4000
		this.spawnY = 200; //0 //700
		this.bottomLimit = -1200;

		this.reqs = new String[] {"Level_1_7"};
		this.reward = 90;
		this.name = "Goblin Frontlines";
		
		this.isRaining = true;
		
		this.raindrops = new ArrayList<Raindrop>();
		for (int i=0; i<40; i++) { //recycles the same raindrops for lag prevention
			raindrops.add(new Raindrop((int)((Math.random()*1.5*Main.SIZE)-(Main.SIZE*0.5)), (int) (Math.random()*Main.SIZE),
			(byte)3, (byte)-15));
		}
		
	}
	
	int[] Fx1 = {0, 100, 600, 800, 1100, 1400, 1600};
	int[] Fy1 = {400, 500, 700, 400, 300, 600, 700};
	int[] Fsize1 = {250, 250, 400, 250, 500, 250, 350};
	
	int[] Fx2 = {-100, 200, 300, 500, 700, 800, 900, 1100, 1200, 1400, 1500, 1800, 1900, 2200, 2300};
	int[] Fy2 = {400, 350, 500, 400, 250, 550, 400, 300, 550, 300, 500, 550, 350, 600, 400};
	int[] Fsize2 = {200, 175, 250, 200, 125, 275, 200, 150, 275, 150, 250, 275, 175, 300, 200};
	
	@Override
	public void fill(Graphics2D g) {
		Color gc1 = new Color(82, 147, 140);
		Color gc2 = new Color(92, 126, 170);
		g.setPaint(new GradientPaint(0,0,gc1,Main.SIZE,Main.SIZE,gc2));
		g.fillRect(-50,-50,Main.SIZE+50,Main.SIZE+50);
		
		drawRoundScenery(g, Color.GRAY.darker().darker(), Fx1, Fy1, Fsize1, 10);
		drawFloorScenery(g, Color.GRAY.darker().darker(), 150, 10);
		drawRoundScenery(g, Color.GRAY.darker(), Fx2, Fy2, Fsize2, 7);
	}
	
	@Override
	public void onStart() {
		GamePanel.camera_x = GamePanel.player.x;
		GamePanel.camera_y = GamePanel.player.y;
		GamePanel.displayText("You're getting close to the Titan.", 240);
	}
	
	@Override
	public void drawBackground() {
		List<GameObject> objects = GamePanel.objects;
		
		objects.add(new NullZone(2750, 350, 190, 300));
	}
	
	@Override
	public void drawForeground() {
		List<GameObject> objects = GamePanel.objects;
	}
	
	@Override
	public void drawPlatforms() {
		List<GameObject> objects = GamePanel.objects;
		
		//spawn platform
		objects.add(new SandPlatform(0, -900, 400, 2000));

	}
	
	@Override
	public void onTick() {
		
	}
	
	@Override
	public void moveCamera() {
		double diffX = GamePanel.player.x - GamePanel.camera_x;
		double diffY = GamePanel.player.y - GamePanel.camera_y;
		
		int higherLimitX = 0;
		int lowerLimitX = -100;
		int higherLimitY = 100;
		int lowerLimitY = -100;
		
		if (diffX > higherLimitX) GamePanel.camera_x = GamePanel.player.x - higherLimitX;
		if (diffX < lowerLimitX) GamePanel.camera_x = GamePanel.player.x - lowerLimitX;
		if (diffY > higherLimitY) GamePanel.camera_y = GamePanel.player.y - higherLimitY;
		if (diffY < lowerLimitY) GamePanel.camera_y = GamePanel.player.y - lowerLimitY;
		
		if (GamePanel.camera_y < 200) GamePanel.camera_y = 200;
		
	}
	
	ArrayList<Raindrop> raindrops;
	ArrayList<Raindrop> removeRaindrops;
	int weatherAlpha = 75;
	float WAV = 1;
	
	@Override
	public void drawAmbience(Graphics g) { //rain
		Graphics2D g2d = (Graphics2D) g;
		
		//animate rain
		for (Raindrop r : raindrops) {
			r.move();
			r.draw(g2d);
			if (r.y > Main.SIZE+50) {
				r.y = 0;
				r.x = (int)((Math.random()*1.5*Main.SIZE)-(Main.SIZE*0.5));
			}
		}
		
		//draw transparency effect
		g2d.setColor(new Color(50,50,150,weatherAlpha));
		g2d.fillRect(-50, -50, Main.SIZE+50, Main.SIZE+50);
		weatherAlpha += WAV;
		if (Math.random() > 0.95) WAV *= -1;
		if (weatherAlpha > 75) WAV = -Math.abs(WAV);
		else if (weatherAlpha < 25) WAV = Math.abs(WAV);
	
	}
	
	@Override
	public void destroy() {
		this.raindrops.clear();
		for (int i=0; i<40; i++) { //recycles the same raindrops for lag prevention
			raindrops.add(new Raindrop((int)((Math.random()*1.5*Main.SIZE)-(Main.SIZE*0.5)), (int) (Math.random()*Main.SIZE),
			(byte)3, (byte)-15));
		}
	}
	
	//raindrop particle
	public class Raindrop {
		Raindrop(int x, int y, byte vx, byte vy) {
			this.x = (int) x;
			this.vx = (byte) vx;
			this.vy = (byte) vy;
			this.y = y;
		}
		
		byte vx;
		byte vy;
		int x;
		int y;
		
		void move() {
			this.x += this.vx;
			this.y -= this.vy;
		}
		
		void draw(Graphics2D g) {
			g.setColor(new Color(50,50,150,100));
			g.setStroke(new BasicStroke(3));
			
			g.drawLine(x, y, x-this.vx, y+this.vy);
		}
		
	}
	
}
