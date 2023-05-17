package skycubedPlatformer.assets.creature.creatures.undead;

import java.awt.Color;

import skycubedPlatformer.assets.creature.ai.attack.NoVerticalMovementAttackAi;
import skycubedPlatformer.assets.creature.ai.horizontal.HorizontalFollowAi;
import skycubedPlatformer.assets.creature.creatures.Creature;
import skycubedPlatformer.assets.decoration.particles.CoinParticle;
import skycubedPlatformer.assets.decoration.particles.GemParticle;
import skycubedPlatformer.menu.GamePanel;

public class CreatureUndead extends Creature {

	public static final Color COLOR_ZOMBIE = new Color(70,106,74);
	
	/**
	* Create a zombie with infinite range
	*/
	public CreatureUndead(double initX, double initY, double size) {
		this(initX, initY, size, 30, 20, Double.MAX_VALUE);
	}
	
	/**
	* Create a zombie with limited X range disregarding Y range
	*/
	public CreatureUndead(double initX, double initY, double size, int maxHealth, double minRange, double maxRange) {
		super(initX, initY, size, COLOR_ZOMBIE, Color.GRAY, 1, maxHealth, 0.04, 0, 5, 10, 45, 1);
		this.friendlyFire = true;
		this.gemChance = 0.001;
		
		this.aiList.add(new HorizontalFollowAi(minRange, maxRange, GamePanel.player));
		this.aiList.add(new NoVerticalMovementAttackAi(this.attackRange/2, GamePanel.player));
	}
	
	/**
	* Create a zombie with limited X and Y range
	*/
	public CreatureUndead(double initX, double initY, double size, int maxHealth, double minRangeX, double maxRangeX, double minRangeY, double maxRangeY) {
		super(initX, initY, size, COLOR_ZOMBIE, Color.GRAY, 1, maxHealth, 0.04, 0, 5, 10, 45, 1);
		this.friendlyFire = true;
		this.gemChance = 0.001;
		
		this.aiList.add(new HorizontalFollowAi(minRangeX, maxRangeX, minRangeY, maxRangeY, GamePanel.player));
		this.aiList.add(new NoVerticalMovementAttackAi(this.attackRange/2, GamePanel.player));
	}
	
	@Override
	public void dropLoot() { //1-3 drops totalling 1-6 coins
		int amount = 1 + (int) (Math.random()*3);
		CoinParticle.spawnCoins(this.x, this.y, amount, amount+(int)(Math.random()*(amount+1)));
		
		if (Math.random() > (1 - this.gemChance)) GemParticle.spawnGem(this.x, this.y, 1); //0.1% chance of a gem
	}

}
