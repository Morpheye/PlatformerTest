package platformerTest.assets.creature.ai;

import platformerTest.assets.creature.CreatureAi;
import platformerTest.game.Creature;
import platformerTest.game.Player;
import platformerTest.menu.GamePanel;

public class HorizontalMovementAi extends CreatureAi {

	public double targetX;
	public double maxRange;
	public double minRange;
	
	public HorizontalMovementAi(double targetX, double minRange, double maxRange) {
		this.targetX = targetX;
		this.maxRange = maxRange;
		this.minRange = minRange;
		
	}
	
	@Override
	public void run(Creature c) {
		if (Math.abs(c.x-this.targetX)>this.maxRange) {
			c.movingRight = false;
			c.movingLeft = false;
			return;
		}
		if (Math.abs(c.x-this.targetX)<this.minRange) {
			c.movingRight = false;
			c.movingLeft = false;
			return;
		}
		
		if (c.x < this.targetX) {
			c.movingRight = true;
			c.movingLeft = false;
		}
		if (c.x > this.targetX) {
			c.movingLeft = true;
			c.movingRight = false;
		}
		
	}
	
}
