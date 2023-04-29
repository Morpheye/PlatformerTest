package platformerTest.assets.creature.creatures;

import java.awt.Color;

import platformerTest.assets.creature.ai.attack.NoVerticalMovementAttackAi;
import platformerTest.assets.creature.ai.horizontal.HorizontalFollowAi;
import platformerTest.game.Creature;
import platformerTest.menu.GamePanel;

public class CreatureZombie extends Creature {

	public static final Color COLOR_ZOMBIE = new Color(70,106,74);
	
	public CreatureZombie(double initX, double initY, double size) {
		this(initX, initY, size, 0, Double.MAX_VALUE);
	}
	
	public CreatureZombie(double initX, double initY, double size, double minRange, double maxRange) {
		super(initX, initY, size, COLOR_ZOMBIE, Color.GRAY, 1, 20, 0.05, 0, 5, 10, 45, 1);
		this.friendlyFire = true;
		
		this.aiList.add(new HorizontalFollowAi(minRange, maxRange, GamePanel.player));
		this.aiList.add(new NoVerticalMovementAttackAi(this.attackRange/2, GamePanel.player));
	}
	
	public CreatureZombie(double initX, double initY, double size, double minRangeX, double maxRangeX, double minRangeY, double maxRangeY) {
		super(initX, initY, size, COLOR_ZOMBIE, Color.GRAY, 1, 20, 0.05, 0, 5, 10, 45, 1);
		this.friendlyFire = true;
		
		this.aiList.add(new HorizontalFollowAi(minRangeX, maxRangeX, minRangeY, maxRangeY, GamePanel.player));
		this.aiList.add(new NoVerticalMovementAttackAi(this.attackRange/2, GamePanel.player));
	}

}
