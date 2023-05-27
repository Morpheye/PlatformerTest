package skycubedPlatformer.levels.world1;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import skycubedPlatformer.Main;
import skycubedPlatformer.assets.SolidPlatform;
import skycubedPlatformer.assets.solidPlatforms.SandPlatform;
import skycubedPlatformer.game.GameObject;
import skycubedPlatformer.levels.Level;
import skycubedPlatformer.levels.world1.Level_1_8.Raindrop;
import skycubedPlatformer.menu.GamePanel;

public class Level_1_9 extends Level {
	
	public boolean opened;
	
	public Level_1_9() {
		this.backgroundColor = COLOR_DAYSKY;
		
		this.spawnX = -100; //-100
		this.spawnY = 500; //250
		
		this.name = "Goblin Titan's Lair";
		
		this.reqs = new String[] {"Level_1_8"};
		this.reward = 100;
		
		this.isRaining = true;
		this.isStorming = true;

		this.raindrops = new ArrayList<Raindrop>();
		for (int i=0; i<120; i++) { //recycles the same raindrops for lag prevention
			raindrops.add(new Raindrop((int)((Math.random()*1.5*Main.SIZE)-(Main.SIZE*0.5)), (int) (Math.random()*Main.SIZE),
			(byte)3, (byte)-50));
		}
		
		this.opened = false;
		
	}
	
	@Override
	public void onStart(GamePanel panel) {
		if (!this.opened) {
			//panel.inControl = false;
			
			
		}
		
		panel.inControl = true;
		this.opened = false;
		panel.displayText("Find a way into the fortress.", 240);
	}

	
	int[] Fx1 = {0, 100, 600, 800, 1100};
	int[] Fy1 = {700, 500, 400, 600, 300};
	int[] Fsize1 = {250, 250, 400, 250, 500};
	
	int[] Fx2 = {-100, 200, 300, 500, 700, 800, 900, 1200, 1400, 1500, 1800, 1900, 2300};
	int[] Fy2 = {400, 350, 500, 400, 250, 550, 400, 550, 300, 500, 550, 350, 400};
	int[] Fsize2 = {175, 250, 150, 175, 150, 150, 200, 175, 150, 225, 150, 250, 200};
	
	@Override
	public void fill(Graphics2D g) {
		Color gc1 = new Color(12, 0, 51);
		Color gc2 = new Color(0, 20, 70);
		g.setPaint(new GradientPaint(Main.SIZE/2,0,gc1,Main.SIZE/2,Main.SIZE,gc2));
		g.fillRect(-50,-50,Main.SIZE+50,Main.SIZE+50);
		
		drawRoundScenery(g, Color.GRAY.darker().darker(), Fx1, Fy1, Fsize1, 10);
		drawFloorScenery(g, Color.GRAY.darker().darker(), 150, 10);
		drawRoundScenery(g, Color.GRAY.darker(), Fx2, Fy2, Fsize2, 7);
	}
	
	@Override
	public void moveCamera() { //loosely locked
		double diffX = GamePanel.getPanel().player.x - GamePanel.getPanel().camera_x;
		double diffY = GamePanel.getPanel().player.y - GamePanel.getPanel().camera_y;
		
		int higherLimitX = 0;
		int lowerLimitX = -100;
		int higherLimitY = 100;
		int lowerLimitY = -100;
		
		if (diffX > higherLimitX) GamePanel.getPanel().camera_x = GamePanel.getPanel().player.x - higherLimitX;
		if (diffX < lowerLimitX) GamePanel.getPanel().camera_x = GamePanel.getPanel().player.x - lowerLimitX;
		if (diffY > higherLimitY) GamePanel.getPanel().camera_y = GamePanel.getPanel().player.y - higherLimitY;
		if (diffY < lowerLimitY) GamePanel.getPanel().camera_y = GamePanel.getPanel().player.y - lowerLimitY;
		
		if (GamePanel.getPanel().camera_y < 400) GamePanel.getPanel().camera_y = 400;
	}
	
	@Override
	public void drawPlatforms(GamePanel panel) {
		List<GameObject> objects = panel.objects;
		
		//spawn
		objects.add(new SandPlatform(0, 300, 500, 200));
		
	}
	
	ArrayList<Raindrop> raindrops;
	ArrayList<Raindrop> removeRaindrops;
	int weatherAlpha = 100;
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
		if (Math.random() > 0.975) WAV *= -1;
		if (weatherAlpha > 125) WAV = -Math.abs(WAV);
		else if (weatherAlpha < 75) WAV = Math.abs(WAV);
	
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
