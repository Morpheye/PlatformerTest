package platformerTest.assets.creature.creatures;

import java.awt.Color;

import platformerTest.assets.creature.ai.AttackAi;
import platformerTest.assets.creature.ai.attack.NormalMovementAttackAi;
import platformerTest.assets.creature.ai.horizontal.HorizontalFollowAi;
import platformerTest.assets.creature.ai.vertical.VerticalFollowAi;
import platformerTest.game.Creature;
import platformerTest.menu.GamePanel;

public class CreatureGoblin extends Creature {

	public static final Color COLOR_GOBLIN = new Color(65,176,45);
	
	/**
	* Create a goblin with unlimited range
	*/
	public CreatureGoblin(double initX, double initY, double size) {
		this(initX, initY, size, 0, Double.MAX_VALUE);
	}
	
	/**
	* Create a goblin with limited X range disregarding Y range
	*/
	public CreatureGoblin(double initX, double initY, double size, double minRange, double maxRange) {
		super(initX, initY, size, COLOR_GOBLIN, Color.RED, 1, 20, 0.15, 12, 3, 7, 30, 1);
		this.friendlyFire = false;
		
		this.aiList.add(new HorizontalFollowAi(minRange, maxRange, GamePanel.player));
		this.aiList.add(new VerticalFollowAi(0, 200, GamePanel.player));
		this.aiList.add(new NormalMovementAttackAi(this.attackRange/2, GamePanel.player));
	}
	
	/** 
	* Create a goblin with limited X and Y range
	*/
	public CreatureGoblin(double initX, double initY, double size, double minRangeX, double maxRangeX, double minRangeY, double maxRangeY) {
		super(initX, initY, size, COLOR_GOBLIN, Color.RED, 1, 20, 0.15, 12, 3, 7, 30, 1);
		this.friendlyFire = false;
		
		this.aiList.add(new HorizontalFollowAi(minRangeX, maxRangeX, minRangeY, maxRangeY, GamePanel.player));
		this.aiList.add(new VerticalFollowAi(6, 100, 75, 200, minRangeX, maxRangeX, GamePanel.player));
		this.aiList.add(new NormalMovementAttackAi(this.attackRange/2, GamePanel.player));
		//add projectilemovement
	}

}
