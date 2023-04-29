package platformerTest.assets.creature.creatures;

import java.awt.Color;

import platformerTest.assets.creature.ai.AttackAi;
import platformerTest.assets.creature.ai.attack.NoVerticalMovementAttackAi;
import platformerTest.assets.creature.ai.horizontal.HorizontalPlayerFollowAi;
import platformerTest.game.Creature;
import platformerTest.menu.GamePanel;

public class CreatureZombie extends Creature {

	public CreatureZombie(double initX, double initY, double size) {
		this(initX, initY, size, 0, Double.MAX_VALUE);
	}
	
	public CreatureZombie(double initX, double initY, double size, double minRange, double maxRange) {
		super(initX, initY, size, Color.GREEN.darker(), 1, 20, 0.05, 0, 5, 10, 45, 1);
		this.aiList.add(new HorizontalPlayerFollowAi(minRange, maxRange));
		this.aiList.add(new NoVerticalMovementAttackAi(this.attackRange/2, GamePanel.player));
	}

}
