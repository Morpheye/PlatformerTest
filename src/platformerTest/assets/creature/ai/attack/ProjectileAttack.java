package platformerTest.assets.creature.ai.attack;

import java.util.ArrayList;

import platformerTest.assets.creature.ai.AttackAi;
import platformerTest.assets.creature.creatures.Creature;
import platformerTest.game.GameObject;
import platformerTest.game.LivingObject;

/**
 * AI for creatures firing projectiles as their primary attack.
 * No need for min-bounds as melees always take priority over ranged.
 * Basic, does not take into account if the creature can see its target
 */
public class ProjectileAttack extends AttackAi {

	double xBoundLeft;
	double yBoundDown;
	double xBoundRight;
	double yBoundUp;

	
	public ProjectileAttack(double xBound, double yBound, ArrayList<LivingObject> targets) {
		this(xBound, xBound, yBound, yBound, targets);
	}
	
	public ProjectileAttack(double xBound, double yBound, LivingObject target) {
		this(xBound, xBound, yBound, yBound, target);
	}
	
	public ProjectileAttack(double xBoundLeft, double xBoundRight, double yBoundDown, double yBoundUp, LivingObject target) {
		super(0, target);
		
		this.xBoundLeft = xBoundLeft;
		this.xBoundRight = xBoundRight;
		this.yBoundDown = yBoundDown;
		this.yBoundUp = yBoundUp;

	}
	
	public ProjectileAttack(double xBoundLeft, double xBoundRight, double yBoundDown, double yBoundUp, ArrayList<LivingObject> targets) {
		super(0, targets);
		
		this.xBoundLeft = xBoundLeft;
		this.xBoundRight = xBoundRight;
		this.yBoundDown = yBoundDown;
		this.yBoundUp = yBoundUp;

	}
	

	
	@Override
	public void run(Creature c) {
		for (LivingObject i : targets) {
			if (!i.isAlive) {
				continue;
			}
			c.isRangedAttacking = false;
			
			if (c.x>i.x && c.x-i.x>xBoundLeft) return;
			if (c.x<i.x && i.x-c.x>xBoundRight) return;
			if (c.y>i.y && c.y-i.y>yBoundDown) return;
			if (c.y<i.y && i.y-c.y>yBoundUp) return;
			
			if (c.x>i.x) c.lastDirection = -1;
			if (c.x<i.x) c.lastDirection = 1;
			
			//FIRE
			c.isRangedAttacking = true;
			
		}
		
	}

}
