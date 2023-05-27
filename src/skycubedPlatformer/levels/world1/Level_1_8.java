package skycubedPlatformer.levels.world1;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import skycubedPlatformer.Main;
import skycubedPlatformer.assets.PushableObject;
import skycubedPlatformer.assets.SolidPlatform;
import skycubedPlatformer.assets.contraptions.ConveyorBelt;
import skycubedPlatformer.assets.contraptions.Factory;
import skycubedPlatformer.assets.contraptions.Ladder;
import skycubedPlatformer.assets.contraptions.TargetRopeObject;
import skycubedPlatformer.assets.creature.creatures.Creature;
import skycubedPlatformer.assets.creature.creatures.goblin.CreatureDartGoblin;
import skycubedPlatformer.assets.creature.creatures.goblin.CreatureGoblin;
import skycubedPlatformer.assets.creature.creatures.goblin.CreatureGoblinGuard;
import skycubedPlatformer.assets.decoration.objects.Gravestone;
import skycubedPlatformer.assets.decoration.objects.NecromancyGravestone;
import skycubedPlatformer.assets.decoration.walls.SolidBackgroundObject;
import skycubedPlatformer.assets.interactables.FinishFlag;
import skycubedPlatformer.assets.liquidPlatforms.WaterPlatform;
import skycubedPlatformer.assets.powerups.CameraSizePowerup;
import skycubedPlatformer.assets.powerups.HealPowerup;
import skycubedPlatformer.assets.powerups.OverhealPowerup;
import skycubedPlatformer.assets.pushableObjects.PushableBox;
import skycubedPlatformer.assets.pushableObjects.PushableStone;
import skycubedPlatformer.assets.pushableObjects.special.PushableExplosive;
import skycubedPlatformer.assets.solidPlatforms.SandPlatform;
import skycubedPlatformer.assets.solidPlatforms.StonePlatform;
import skycubedPlatformer.assets.solidPlatforms.WoodPlatform;
import skycubedPlatformer.assets.triggers.TextDisplayTrigger;
import skycubedPlatformer.game.GameObject;
import skycubedPlatformer.items.weapons.starterWeapons.PointedSpear;
import skycubedPlatformer.items.weapons.starterWeapons.WoodenClub;
import skycubedPlatformer.levels.Level;
import skycubedPlatformer.menu.GamePanel;

public class Level_1_8 extends Level {

	public Level_1_8() {
		this.backgroundColor = COLOR_DAYSKY;
		
		this.spawnX = 0; //0 3850
		this.spawnY = 200; //200 600
		this.bottomLimit = -1200;

		this.reqs = new String[] {"Level_1_7"};
		this.reward = 90;
		this.name = "Goblin Frontlines";
		
		this.isRaining = true;
		
		this.raindrops = new ArrayList<Raindrop>();
		for (int i=0; i<80; i++) { //recycles the same raindrops for lag prevention
			raindrops.add(new Raindrop((int)((Math.random()*1.5*Main.SIZE)-(Main.SIZE*0.5)), (int) (Math.random()*Main.SIZE),
			(byte)3, (byte)-25));
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
	public void onStart(GamePanel panel) {
		panel.displayText("Be very careful of explosives.", 240);
	}
	
	SolidPlatform finishPlatform;
	FinishFlag finishFlag;
	
	@Override
	public void drawPlatforms(GamePanel panel) {
		List<GameObject> objects = panel.objects;
		
		//spawn platform
		objects.add(new SandPlatform(0, -900, 400, 2000));
		
		//first explosive
		objects.add(new SandPlatform(700, -850, 600, 2000));
		objects.add(new PushableStone(675, 175, 50, 50));
		objects.add(new PushableExplosive(725, 175, 50, 50, 30, 255));
		objects.add(new CreatureGoblinGuard(700, 225, 40, 250, 50, 100, new PointedSpear()));
		
		objects.add(new SandPlatform(1550, -800, 600, 2000));
		objects.add(new SandPlatform(1950, -800, 600, 2400));
		objects.add(new PushableBox(1600, 250, 50, 50));
		objects.add(new Gravestone(1400, 245, 50, 100));
		objects.add(new Gravestone(1500, 225, 100, 75));
		objects.add(new SolidBackgroundObject(1750, -400, 550, 2000, GameObject.COLOR_SAND.darker()));
		
		objects.add(new TextDisplayTrigger(1650, 500, 200, 200, "Explosions can break lighter blocks.", 300));
		objects.add(new PushableBox(1800, 500, 50, 50));
		objects.add(new PushableExplosive(1850, 500, 50, 50, 30, 255));
		objects.add(new PushableBox(1825, 560, 50, 50));
		objects.add(new PushableStone(1900, 500, 50, 50));
		
		objects.add(new SandPlatform(2600, 400, 200, 50));
		
		objects.add(new SandPlatform(3200, 400, 400, 100));
		objects.add(new CreatureGoblin(3150, 500, 30, 0, 300));
		objects.add(new CreatureGoblin(3350, 600, 40, 0, 300, 0, 300));
		objects.add(new PushableStone(3350, 500, 50, 50));
		
		objects.add(new SandPlatform(3850, 450, 400, 100));
		objects.add(new CameraSizePowerup(3850, 550, 50, 1.25));
		
		//blast rope puzzle
		objects.add(new Gravestone(4750, 550, 50, 100));
		objects.add(new WaterPlatform(5300, -500, 1000, 1999));
		objects.add(new SandPlatform(4600, -500, 600, 2000));
		objects.add(new SandPlatform(5300, -700, 1000, 2000));
		objects.add(new SandPlatform(6000, -400, 600, 2000));
		
		objects.add(new Ladder(4600, 750, 500));
		objects.add(new TextDisplayTrigger(4600, 750, 40, 500, "Ladders can be ascended and descended with W and S.", 300));
		objects.add(new SandPlatform(4600, 1100, 200, 200));
		objects.add(new SandPlatform(4800, 800, 200, 50));
		objects.add(new PushableStone(4850, 850, 50, 50));
		
		objects.add(new SandPlatform(5250, 900, 400, 100));
		objects.add(new SandPlatform(5400, 1050, 100, 400));
		objects.add(new SandPlatform(5100, 910, 100, 120));
		
		objects.add(new CreatureGoblinGuard(5125, 1000, 40, 50, 30, 200, new WoodenClub()));
		
		PushableObject ropeBomb = new PushableExplosive(5250, 1000, 50, 50, 30, 255);
		objects.add(ropeBomb);
		objects.add(new TargetRopeObject(new PushableBox(5250, 800, 70, 70), ropeBomb, 200, 1));
		objects.add(new PushableBox(5500, 500, 70, 70));
		objects.add(new HealPowerup(6000, 650, 50));
		
		objects.add(new Gravestone(6075, 630, 100, 60));
		objects.add(new NecromancyGravestone(6200, 650, 50, 100, 300, 2, 3));
		
		//bigger bomb
		objects.add(new TextDisplayTrigger(6600, 700, 100, 100, "Do NOT stay on the platform when the bomb explodes.", 300));
		objects.add(new SandPlatform(6750, 600, 400, 100));
		objects.add(new PushableExplosive(6750, 700, 50, 100, 30, 500));
		objects.add(new PushableStone(6700, 700, 50, 50));
		objects.add(new PushableBox(6800, 700, 50, 50));
		objects.add(new PushableStone(6775, 750, 50, 50));
		objects.add(new CreatureGoblinGuard(6725, 750, 40, 250, 50, 100, new PointedSpear()));
		
		objects.add(new SandPlatform(7400, 650, 400, 100));
		objects.add(new NecromancyGravestone(7400, 730, 100, 60, 200, 1, 2));
	
		//climb
		objects.add(new SandPlatform(8400, 200, 1000, 1000));
		objects.add(new SandPlatform(8650, 700, 500, 1000));
		objects.add(new SolidBackgroundObject(8400, 775, 900, 800, GameObject.COLOR_SAND.darker()));
		
		objects.add(new CreatureGoblinGuard(8150, 750, 40, 350, 50, 100, new WoodenClub()));
		objects.add(new Ladder(8350, 850, 300));
		objects.add(new WoodPlatform(8100, 900, 400, 50));
		
		objects.add(new CreatureDartGoblin(8100, 950, 30, 25, 350, 350));
		objects.add(new Ladder(8000, 1050, 300));
		objects.add(new WoodPlatform(8225, 1100, 350, 50));
		objects.add(new HealPowerup(8225, 1175, 50));
		
		//crusher
		objects.add(new StonePlatform(8650, 1450, 150, 200) {
			@Override
			public void move() {
				if (this.y >= 1450) this.vy = -2;
				else if (this.y <= 1300) this.vy = 2;
				super.move();
			}
		});
		objects.add(new SandPlatform(8650, 1450, 200, 200));
		
		//conveyor belts
		objects.add(new ConveyorBelt(9350, 1200, 400, 50, -2));
		objects.add(new ConveyorBelt(9750, 1275, 400, 50, -2));
		objects.add(new ConveyorBelt(10150, 1350, 400, 50, -2));
		objects.add(new Factory(10325, 1400, 50, new PushableExplosive(10325, 1400, 40, 40, 0, 2500) {
			@Override
			public void move() {
				if (distanceTo(GamePanel.getPanel().player) < 100) this.dmgTime += 50; 
				super.move();
			}
		}, 180, 15, 3000));
		objects.add(new OverhealPowerup(10325, 1475, 50, 50));
		objects.add(new SandPlatform(10600, 1425, 400, 200));
		
		Creature gatekeeper = new CreatureGoblinGuard(10600, 1550, 40, 350, 50, 100, new WoodenClub());
		gatekeeper.required = true;
		objects.add(gatekeeper);
		
		objects.add(new WoodPlatform(11100, 1700, 50, 200) {
			@Override
			public void move() {
				if (!GamePanel.getPanel().objects.contains(gatekeeper) && this.y < 1875) {
					this.vy = 0.5;
				} else this.vy = 0;
				super.move();
			}
		});
		objects.add(new StonePlatform(11100, 1900, 200, 200));
		objects.add(new SandPlatform(11100, 1500, 200, 200));
		
		finishPlatform = new WoodPlatform(11650, 1575, 400, 50);
		finishFlag = new FinishFlag(11650, 1650, 50, 100);
		
		objects.add(finishPlatform);
		objects.add(finishFlag);
		
	}
	
	@Override
	public void onTick() {
		if (GamePanel.getPanel().levelWon > 0) {
			finishPlatform.vx = 1;
			finishFlag.vx = 1;
		} else {
			finishPlatform.vx = 0;
			finishFlag.vx = 0;
		}
	}
	
	@Override
	public void moveCamera() {
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
		
		if (GamePanel.getPanel().camera_y < 200) GamePanel.getPanel().camera_y = 200;
		
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
