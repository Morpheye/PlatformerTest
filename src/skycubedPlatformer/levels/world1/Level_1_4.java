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
import skycubedPlatformer.assets.creature.creatures.Creature;
import skycubedPlatformer.assets.creature.creatures.undead.CreatureBabyUndead;
import skycubedPlatformer.assets.creature.creatures.undead.CreatureUndead;
import skycubedPlatformer.assets.decoration.objects.Gravestone;
import skycubedPlatformer.assets.decoration.particles.CoinParticle;
import skycubedPlatformer.assets.decoration.particles.GemParticle;
import skycubedPlatformer.assets.decoration.walls.SolidBackgroundObject;
import skycubedPlatformer.assets.effects.Effect;
import skycubedPlatformer.assets.effects.EffectFire;
import skycubedPlatformer.assets.interactables.FinishFlag;
import skycubedPlatformer.assets.liquidPlatforms.WaterPlatform;
import skycubedPlatformer.assets.powerups.HealPowerup;
import skycubedPlatformer.assets.powerups.OverhealPowerup;
import skycubedPlatformer.assets.powerups.StrengthPowerup;
import skycubedPlatformer.assets.pushableObjects.PushableBox;
import skycubedPlatformer.assets.solidPlatforms.GrassPlatform;
import skycubedPlatformer.assets.solidPlatforms.WoodPlatform;
import skycubedPlatformer.assets.triggers.TextDisplayTrigger;
import skycubedPlatformer.game.GameObject;
import skycubedPlatformer.game.LivingObject;
import skycubedPlatformer.game.ObjType;
import skycubedPlatformer.levels.Level;
import skycubedPlatformer.menu.ApplicationFrame;
import skycubedPlatformer.menu.GamePanel;

public class Level_1_4 extends Level {
	
	public Level_1_4() {
		this.backgroundColor = COLOR_NIGHTSKY;
		
		this.spawnX = 0; //0
		this.spawnY = 200; //200
		
		this.reqs = new String[] {"Level_1_3"};
		this.reward = 60;
		this.name = "The Graveyard";
		
		this.isRaining = true;
		
		this.raindrops = new ArrayList<Raindrop>();
		for (int i=0; i<40; i++) { //recycles the same raindrops for lag prevention
			raindrops.add(new Raindrop((int)((Math.random()*1.5*Main.SIZE)-(Main.SIZE*0.5)), (int) (Math.random()*Main.SIZE),
			(byte)3, (byte)-15));
		}
		
	}
	
	@Override
	public void fill(Graphics2D g) {
		Color gc1 = new Color(52, 0, 151);
		Color gc2 = new Color(0, 20, 70);
		g.setPaint(new GradientPaint(Main.SIZE/2,0,gc1,Main.SIZE/2,Main.SIZE,gc2));
		g.fillRect(-50,-50,Main.SIZE+50,Main.SIZE+50);
	}
	
	@Override
	public void onStart(GamePanel panel) {
		panel.displayText("Undead are everywhere, conserve your health.",300);
		
	}
	
	@Override
	public void drawBackground(GamePanel panel) {
		List<GameObject> objects = panel.objects;
		
		objects.add(new Gravestone(600, 200, 50, 100));
		
		objects.add(new Gravestone(1150, 250, 50, 100));
		objects.add(new Gravestone(1300, 220, 75, 40));
		
		objects.add(new SolidBackgroundObject(2100, 300, 190, 300, GameObject.COLOR_DIRT));
		objects.add(new SolidBackgroundObject(1900, 225, 500, 100, GameObject.COLOR_DIRT));
		objects.add(new Gravestone(1900, 255, 75, 110));
		
		objects.add(new Gravestone(3350, 400, 50, 100));
		objects.add(new Gravestone(3500, 390, 150, 80));
		objects.add(new Gravestone(3650, 400, 50, 100));
		
		objects.add(new Gravestone(4300, 450, 100, 150));
		objects.add(new Gravestone(5100, 500, 200, 100));
		objects.add(new Gravestone(6000, 400, 200, 100));
		
		objects.add(new SolidBackgroundObject(7400, 375, 700, 100, GameObject.COLOR_DIRT));
		objects.add(new SolidBackgroundObject(7500, 450, 190, 300, GameObject.COLOR_DIRT));
		
		objects.add(new Gravestone(8800, 600, 50, 100));
		objects.add(new Gravestone(9000, 590, 120, 80));
		
		objects.add(new Gravestone(9900, 650, 50, 100));
		
		objects.add(new FinishFlag(12200, 850, 50, 100));
		
	}
	
	Creature gatekeeper;
	SolidPlatform gate;
	
	Creature gatekeeper2;
	Creature gatekeeper3;
	SolidPlatform gate2;
	
	Creature miniboss;
	SolidPlatform finalGate;
	
	@Override
	public void drawPlatforms(GamePanel panel) {
		List<GameObject> objects = panel.objects;
		
		//spawn platform
		objects.add(new GrassPlatform(0, 50, 400, 100));
		
		//first fight
		objects.add(new GrassPlatform(600, 100, 400, 100));
		objects.add(new CreatureUndead(600, 250, 40, 30, 30, 400));
		objects.add(new CreatureUndead(650, 250, 40, 30, 30, 400));

		objects.add(new GrassPlatform(1200, 150, 400, 100));
		objects.add(new CreatureUndead(1350, 250, 40, 30, 30, 400));
		objects.add(new CreatureBabyUndead(1300, 250, 20, 30, 400));
		
		//locked gate
		objects.add(new TextDisplayTrigger(1650,300,100,100,"Enemies requiring killing will glow.",300));
		objects.add(new GrassPlatform(1900, -400, 600, 1200));
		objects.add(new GrassPlatform(1650, 225, 100, 150));
		gate = new WoodPlatform(2100, 300, 50, 200);
		objects.add(gate);
		objects.add(new GrassPlatform(2100, 500, 200, 200));
		gatekeeper = new CreatureUndead(1800, 300, 40, 30, 30, 400);
		gatekeeper.required = true;
		objects.add(gatekeeper);
		
		objects.add(new HealPowerup(2500, 300, 50));
		objects.add(new GrassPlatform(2500, 150, 200, 200));
		
		objects.add(new GrassPlatform(2500, 150, 200, 200));
		
		objects.add(new TextDisplayTrigger(2900,400,200,200,"Caution: creatures can spawn with overheal.",300));
		objects.add(new GrassPlatform(2900, 200, 200, 200));
		
		objects.add(new GrassPlatform(3500, -650, 600, 2000));
		objects.add(new CreatureUndead(3500, 400, 40, 30, 30, 400));
		CreatureUndead overhealedZombie = new CreatureUndead(3600, 400, 40, 30, 30, 400);
		overhealedZombie.overheal = 10;
		objects.add(overhealedZombie);
		
		objects.add(new GrassPlatform(4300, -600, 600, 2000));
		objects.add(new CreatureUndead(4250, 450, 40, 30, 30, 400));
		objects.add(new CreatureUndead(4300, 450, 40, 30, 30, 400));
		objects.add(new CreatureUndead(4350, 450, 40, 30, 30, 400));
		
		objects.add(new GrassPlatform(5100, -550, 600, 2000));
		objects.add(new CreatureUndead(5100, 500, 40, 30, 30, 400));
		CreatureUndead overhealedZombie2 = new CreatureUndead(5150, 500, 40, 30, 30, 400);
		overhealedZombie2.overheal = 10;
		objects.add(overhealedZombie2);
		
		objects.add(new GrassPlatform(6000, -650, 600, 2000));
		gatekeeper2 = new CreatureUndead(6000, 400, 40, 30, 30, 400);
		gatekeeper2.overheal = 30;
		gatekeeper2.required = true;
		objects.add(gatekeeper2);
		objects.add(new CreatureBabyUndead(5950, 400, 20, 30, 400));
		
		objects.add(new GrassPlatform(6600, 300, 200, 200));
		objects.add(new HealPowerup(6600, 450, 50));
		
		//gate 2
		objects.add(new GrassPlatform(7400, -650, 800, 2000));
		objects.add(new GrassPlatform(7000, -550, 200, 2000));
		gate2 = new WoodPlatform(7500, 450, 50, 200);
		objects.add(gate2);
		objects.add(new GrassPlatform(7500, 650, 200, 200));
		gatekeeper3 = new CreatureUndead(7200, 450, 50, 50, 30, 400); //big beefy zombie
		gatekeeper3.gemChance = 0.005;
		gatekeeper3.minCoins = 3; gatekeeper3.maxCoins = 6; gatekeeper.coinWeight = 2;
		gatekeeper3.required = true;
		gatekeeper3.movementSpeed = 0.06;
		gatekeeper3.attackKnockback = 3;
		objects.add(gatekeeper3);
		objects.add(new GrassPlatform(7800, -550, 200, 2000));
		objects.add(new StrengthPowerup(7800, 500, 50, 5));
		objects.add(new TextDisplayTrigger(7800, 500, 50, 50, "Strength power-ups increase melee damage.", 300));
		
		//waterplatform
		objects.add(new CreatureUndead(8800, 600, 40, 30, 30, 400));
		objects.add(new CreatureUndead(8900, 600, 40, 30, 30, 400));
		objects.add(new WaterPlatform(8600, 450, 800, 195));
		objects.add(new GrassPlatform(8200, -450, 100, 2000));
		objects.add(new GrassPlatform(8600, -550, 800, 2000));
		objects.add(new GrassPlatform(8900, -450, 400, 2000));
		
		objects.add(new GrassPlatform(9400, 550, 200, 200));
		objects.add(new OverhealPowerup(9400,700,50,100));
		
		//final boss
		objects.add(new TextDisplayTrigger(9900,700,200,200,"Make use of the box.",300));
		objects.add(new GrassPlatform(9900, -400, 200, 2000));
		objects.add(new GrassPlatform(10400, -500, 1200, 2000));
		finalGate = new WoodPlatform(10900, 750, 50, 200);
		objects.add(finalGate);
		objects.add(new GrassPlatform(10900, -350, 200, 2000));
		objects.add(new GrassPlatform(10900, 950, 200, 200));
		miniboss = new CreatureUndead(10600, 600, 100, 150, 40, 400);
		miniboss.gemChance = 0.01;
		miniboss.minCoins = 10; miniboss.maxCoins = 20; miniboss.coinWeight = 4;
		miniboss.movementSpeed = 0.05;
		miniboss.attackKnockback = 2;
		miniboss.attackDamage = 10;
		miniboss.required = true;
		objects.add(miniboss);
		objects.add(new PushableBox(10400,600,80,80));
		
		objects.add(new GrassPlatform(11300, 600, 200, 200));
		objects.add(new GrassPlatform(11700, 650, 200, 200));
		objects.add(new GrassPlatform(12200, 700, 400, 200));
		
		
	}
	
	@Override
	public void onTick() {
		if (!GamePanel.getPanel().objects.contains(gatekeeper) && gate.y < 480) {
			gate.vy = 0.5;
		} else gate.vy = 0;
		
		if (!GamePanel.getPanel().objects.contains(gatekeeper2) && !GamePanel.getPanel().objects.contains(gatekeeper3) && gate2.y < 630) {
			gate2.vy = 0.5;
		} else gate2.vy = 0;
		
		if (!GamePanel.getPanel().objects.contains(miniboss) && finalGate.y < 930) {
			finalGate.vy = 0.5;
		} else finalGate.vy = 0;
		
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
		
		super.moveCamera();
		if (GamePanel.getPanel().camera_y < 300) GamePanel.getPanel().camera_y = 300;
	}
	
	//AMBIENT EFFECT: LIGHT RAIN
	
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
