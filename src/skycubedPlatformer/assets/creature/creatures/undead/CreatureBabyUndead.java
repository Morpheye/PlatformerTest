package skycubedPlatformer.assets.creature.creatures.undead;

import java.awt.Color;

import skycubedPlatformer.assets.creature.ai.attack.NoVerticalMovementAttackAi;
import skycubedPlatformer.assets.creature.ai.horizontal.HorizontalFollowAi;
import skycubedPlatformer.assets.creature.creatures.Creature;
import skycubedPlatformer.assets.decoration.particles.CoinParticle;
import skycubedPlatformer.assets.decoration.particles.GemParticle;
import skycubedPlatformer.menu.GamePanel;

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
		this.minCoins = 1;
		this.maxCoins = 9;
		this.coinWeight = 3;
		
		this.aiList.add(new HorizontalFollowAi(minRange, maxRange, GamePanel.getPanel().player));
		this.aiList.add(new NoVerticalMovementAttackAi(this.attackRange/2, GamePanel.getPanel().player));
	}
	
	/**
	* Create a zombie with limited X and Y range
	*/
	public CreatureBabyUndead(double initX, double initY, int maxHealth, double minRangeX, double maxRangeX, double minRangeY, double maxRangeY) {
		super(initX, initY, 30, COLOR_ZOMBIE, Color.GRAY, 1, maxHealth, 0.135, 0, 5, 10, 45, 1);
		this.friendlyFire = true;
		this.gemChance = 0.0015;
		this.minCoins = 1;
		this.maxCoins = 9;
		this.coinWeight = 3;
		
		this.aiList.add(new HorizontalFollowAi(minRangeX, maxRangeX, minRangeY, maxRangeY, GamePanel.getPanel().player));
		this.aiList.add(new NoVerticalMovementAttackAi(this.attackRange/2, GamePanel.getPanel().player));
	}
	
	@Override
	public void dropLoot() {  //1-3 drops totalling 1-9 coins
		Creature.loot(this);
	}

}
