package platformerTest.assets.creature.ai.attack;

import java.util.ArrayList;

import platformerTest.assets.creature.ai.AttackAi;
import platformerTest.assets.creature.creatures.Creature;
import platformerTest.game.GameObject;

/**
 * AI for creatures which do not follow their targets vertically.
 */
public class NoVerticalMovementAttackAi extends AttackAi {

	public NoVerticalMovementAttackAi(double meleeRange, GameObject target) {
		super(meleeRange, target);
	}
	
	public NoVerticalMovementAttackAi(double meleeRange, ArrayList<GameObject> targets) {
		super(meleeRange, targets);
	}
	
	@Override
	public void run(Creature c) {
		c.isAttacking = false;
		for (GameObject i : targets) {
			double xDist = Math.abs(c.x - i.x) - (0.5 * Math.abs(c.size_x + i.size_x));
			double yDist = Math.abs(c.y - i.y) - (0.5 * Math.abs(c.size_y + i.size_y));
			
			if (xDist < 0) xDist = 0;
			
			double distance = Math.sqrt(Math.pow(xDist, 2) + Math.pow(((yDist<0)?0:yDist), 2));
			if (distance <= meleeRange) {
				c.isAttacking = true;
				if (yDist >= 0) {
					if (c.y > i.y) {
						c.movingUp = false;
						c.movingDown= true;
					}
					else {
						c.movingUp = true;
						c.movingDown = false;
					}
				} else {
					c.movingUp = false;
					c.movingDown = false;
				}
			} else {
				c.movingUp = false;
				c.movingDown = false;
			}
			
		}
	}

}
