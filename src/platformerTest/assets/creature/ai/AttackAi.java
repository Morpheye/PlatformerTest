package platformerTest.assets.creature.ai;

import java.util.ArrayList;

import platformerTest.assets.creature.CreatureAi;
import platformerTest.game.Creature;
import platformerTest.game.GameObject;

/**
 * General attack AI
 */
public class AttackAi extends CreatureAi {

	public double meleeRange;
	public ArrayList<GameObject> targets;
	
	public AttackAi(double meleeRange, GameObject target) {
		this.meleeRange = meleeRange;
		this.targets = new ArrayList<GameObject>();
		this.targets.add(target);
	}
	
	public AttackAi(double meleeRange, ArrayList<GameObject> targets) {
		this.meleeRange = meleeRange;
		this.targets = targets;
	}
	
	@Override
	public void run(Creature c) {
		c.isAttacking = false;
		for (GameObject i : targets) {
			double xDist = Math.abs(c.x - i.x) - (0.5 * Math.abs(c.size_x + i.size_x));
			double yDist = Math.abs(c.y - i.y) - (0.5 * Math.abs(c.size_y + i.size_y));
			
			if (xDist < 0) xDist = 0;
			if (yDist < 0) yDist = 0;
			
			double distance = Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2));
			if (distance <= this.meleeRange) {
				c.isAttacking = true;
			}
		}
	}
	
}
