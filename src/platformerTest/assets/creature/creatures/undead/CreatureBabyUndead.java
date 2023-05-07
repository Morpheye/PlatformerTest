package platformerTest.assets.creature.creatures.undead;

import java.awt.Color;

import platformerTest.assets.creature.ai.attack.NoVerticalMovementAttackAi;
import platformerTest.assets.creature.ai.horizontal.HorizontalFollowAi;
import platformerTest.assets.creature.creatures.Creature;
import platformerTest.assets.decoration.particles.CoinParticle;
import platformerTest.assets.decoration.particles.GemParticle;
import platformerTest.menu.GamePanel;

public class CreatureBabyUndead extends Creature {

	public static final Color COLOR_ZOMBIE = new Color(70,106,74);
	
	/**
	* Create a zombie with infinite range
	*/
	public CreatureBabyUndead(double initX, double initY) {
		this(initX, initY, 20, 0, Double.MAX_VALUE);
	}
	
	/**
	* Create a zombie with limited X range disregarding Y range
	*/
	public CreatureBabyUndead(double initX, double initY, int maxHealth, double minRange, double maxRange) {
		super(initX, initY, 30, COLOR_ZOMBIE, Color.GRAY, 1, maxHealth, 0.135, 0, 5, 10, 45, 1);
		this.friendlyFire = true;
		this.gemChance = 0.0015;
		
		this.aiList.add(new HorizontalFollowAi(minRange, maxRange, GamePanel.player));
		this.aiList.add(new NoVerticalMovementAttackAi(this.attackRange/2, GamePanel.player));
	}
	
	/**
	* Create a zombie with limited X and Y range
	*/
	public CreatureBabyUndead(double initX, double initY, int maxHealth, double minRangeX, double maxRangeX, double minRangeY, double maxRangeY) {
		super(initX, initY, 30, COLOR_ZOMBIE, Color.GRAY, 1, maxHealth, 0.135, 0, 5, 10, 45, 1);
		this.friendlyFire = true;
		this.gemChance = 0.0015;
		
		this.aiList.add(new HorizontalFollowAi(minRangeX, maxRangeX, minRangeY, maxRangeY, GamePanel.player));
		this.aiList.add(new NoVerticalMovementAttackAi(this.attackRange/2, GamePanel.player));
	}
	
	@Override
	public void dropLoot() {  //1-3 drops totalling 1-9 coins
		int amount = 1 + (int) (Math.random()*3);
		CoinParticle.spawnCoins(this.x, this.y, amount, amount+(int)(Math.random()*(2*amount+1)));
		
		if (Math.random() > (1 - this.gemChance)) GemParticle.spawnGem(this.x, this.y, 1); //0.15% chance of a gem
	}

}
