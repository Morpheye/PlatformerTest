package platformerTest.assets.creature.creatures;

import java.awt.Color;

import platformerTest.assets.creature.ai.attack.NoVerticalMovementAttackAi;
import platformerTest.assets.creature.ai.horizontal.HorizontalFollowAi;
import platformerTest.game.Creature;
import platformerTest.menu.GamePanel;

public class CreatureZombie extends Creature {

	public static final Color COLOR_ZOMBIE = new Color(70,106,74);
	
	/**
	* Create a zombie with infinite range
	*/
	public CreatureZombie(double initX, double initY, double size) {
		this(initX, initY, size, 30, 0, Double.MAX_VALUE);
	}
	
	/**
	* Create a zombie with limited X range disregarding Y range
	*/
	public CreatureZombie(double initX, double initY, double size, int maxHealth, double minRange, double maxRange) {
		super(initX, initY, size, COLOR_ZOMBIE, Color.GRAY, 1, maxHealth, 0.04, 0, 5, 10, 45, 1);
		this.friendlyFire = true;
		
		this.aiList.add(new HorizontalFollowAi(minRange, maxRange, GamePanel.player));
		this.aiList.add(new NoVerticalMovementAttackAi(this.attackRange/2, GamePanel.player));
	}
	
	/**
	* Create a zombie with limited X and Y range
	*/
	public CreatureZombie(double initX, double initY, double size, int maxHealth, double minRangeX, double maxRangeX, double minRangeY, double maxRangeY) {
		super(initX, initY, size, COLOR_ZOMBIE, Color.GRAY, 1, maxHealth, 0.04, 0, 5, 10, 45, 1);
		this.friendlyFire = true;
		
		this.aiList.add(new HorizontalFollowAi(minRangeX, maxRangeX, minRangeY, maxRangeY, GamePanel.player));
		this.aiList.add(new NoVerticalMovementAttackAi(this.attackRange/2, GamePanel.player));
	}

}
