package skycubedPlatformer.assets.creature.creatures.goblin;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.sound.sampled.AudioSystem;

import skycubedPlatformer.Main;
import skycubedPlatformer.assets.creature.CreatureAi;
import skycubedPlatformer.assets.creature.ai.allDirectional.GuardAi;
import skycubedPlatformer.assets.creature.ai.attack.NormalMovementAttackAi;
import skycubedPlatformer.assets.creature.creatures.Creature;
import skycubedPlatformer.assets.decoration.particles.CoinParticle;
import skycubedPlatformer.assets.decoration.particles.ItemParticle;
import skycubedPlatformer.game.GameObject;
import skycubedPlatformer.game.Player;
import skycubedPlatformer.items.consumables.shopConsumables.OverhealFruit;
import skycubedPlatformer.items.weapons.Weapon;
import skycubedPlatformer.menu.GamePanel;
import skycubedPlatformer.util.SoundHelper;

public class CreatureGoblinGuard extends CreatureGoblin {

	public int projectileRange;
	
	/**
	* Create a dart goblin following an x point with unlimited range and set projectile range
	*/
	public CreatureGoblinGuard(double initX, double initY, double size, int projectileRange, Weapon weapon) {
		this(initX, initY, size, Double.MAX_VALUE, 0, projectileRange, weapon);
	}
	
	/**
	* Create a dart goblin following an x point with limited range and set projectile range
	*/
	public CreatureGoblinGuard(double initX, double initY, double size, double rangeX, 
			double minRangeY, double maxRangeY, Weapon weapon) {
		super(initX, initY, size, 0, 0);
		
		this.maxHealth = (int) size;
		this.health = this.maxHealth;
		this.gemChance = 0.0025;
		this.minCoins = 1;
		this.maxCoins = 2;
		this.coinWeight = 1;
		
		this.attackDamage = 5;
		this.maxAttackCooldown = 40;
		this.attackKnockback = 2;
		
		if (weapon != null) {
			this.weapon = weapon;
			this.weapon.init(this);
			try {
				this.hitSound.close();
				this.hitSound = AudioSystem.getClip();
				SoundHelper.loadSound(this, this.hitSound, this.weapon.hitSound);
				this.attackSound.close();
				this.attackSound = AudioSystem.getClip();
				SoundHelper.loadSound(this, this.attackSound, this.weapon.attackSound);
			} catch (Exception e) {e.printStackTrace();}
		}
		
		this.aiList = new ArrayList<CreatureAi>();
		this.aiList.add(new GuardAi(initX, initY, 20, rangeX, minRangeY, maxRangeY, GamePanel.player));
		this.aiList.add(new NormalMovementAttackAi(this.attackRange/2, GamePanel.player));
		

	}
	
	@Override
	public void draw(Graphics g, Player player, double x, double y, double size) {
		super.draw(g, player, x, y, size);
		int drawX = (int) ( (this.x - (this.size_x)/2 - (x - size/2)) * (Main.SIZE/size));
		int drawY = (int) ( (size - (this.y + (this.size_y)/2) + (y - size/2)) * (Main.SIZE/size));
		
		//mask
		g.setColor(GameObject.COLOR_WOOD);
		g.fillRoundRect(drawX-(int)(2*Main.SIZE/size), drawY-(int)(2*Main.SIZE/size), (int) (this.size_x * Main.SIZE/size)+(int)(4*Main.SIZE/size),
				(int) (this.size_y*5/6 * Main.SIZE/size), (int)(5*(Main.SIZE/size)), (int)(5*(Main.SIZE/size)));
		
		//redraw eyes and draw mouth
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke((float)(4*(Main.SIZE/size))));
		g2d.setColor(this.eyeColor);
		int x1, x2, x3;
		if (this.lastDirection == 1) {
			x1 = (int) (drawX+(this.size_x*(Main.SIZE/size)*2/5));
			x2 = (int) (drawX+(this.size_x*(Main.SIZE/size)*3/4));
			
		} else {
			x1 = (int) (drawX+(this.size_x*(Main.SIZE/size)*1/4));
			x2 = (int) (drawX+(this.size_x*(Main.SIZE/size)*3/5));
			
		}
		int y1 = (int) (drawY+(this.size_y*(Main.SIZE/size)*1/4));
		int y2 = (int) (drawY+(this.size_y*(Main.SIZE/size)*2/5));
		g2d.drawLine(x1, y1, x1, y2);
		g2d.drawLine(x2, y1, x2, y2);
		
	}
	
	@Override
	public void dropLoot() { //2-3 drops totalling 6-7 coins, guaranteed 1 silver coin drop
		GamePanel.particles.add(new CoinParticle(this.x, this.y, 5));
		Creature.loot(this);
	}
	

}
