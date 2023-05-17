package skycubedPlatformer.assets.creature.ai;

import skycubedPlatformer.assets.creature.CreatureAi;
import skycubedPlatformer.assets.creature.creatures.Creature;
import skycubedPlatformer.game.Player;
import skycubedPlatformer.menu.GamePanel;

/**
 * Base AI for only horizontal movement following a point. Do not use.
 */
public class HorizontalMovementAi extends CreatureAi {

	public double targetX;
	public double targetY;
	public double maxRangeX;
	public double minRangeX;
	public double maxRangeY;
	public double minRangeY;
	
	public HorizontalMovementAi(double targetX, double minRangeX, double maxRangeX) {
		this(targetX, 0, minRangeX, maxRangeX, 0, Double.MAX_VALUE);
	}
	
	public HorizontalMovementAi(double targetX, double targetY, double minRangeX, double maxRangeX, double minRangeY, double maxRangeY) {
		this.targetX = targetX;
		this.maxRangeX = maxRangeX;
		this.minRangeX = minRangeX;
		this.maxRangeY = maxRangeY;
		this.minRangeY = minRangeY;
	}
	
	@Override
	public void run(Creature c) {
		if (Math.abs(c.y-this.targetY)>this.maxRangeY) { //out of range on y
			c.movingRight = false;
			c.movingLeft = false;
			return;
		}
		if (Math.abs(c.y-this.targetY)<this.minRangeY) { //too close on y
			c.movingRight = false;
			c.movingLeft = false;
			return;
		}
		
		if (Math.abs(c.x-this.targetX)>this.maxRangeX) { //out of range on x
			c.movingRight = false;
			c.movingLeft = false;
			return;
		}
		if (Math.abs(c.x-this.targetX)<this.minRangeX) { //too close on x
			c.movingRight = false;
			c.movingLeft = false;
			return;
		}
		
		if (c.x < this.targetX) {
			c.movingRight = true;
			c.lastDirection = 1;
			c.movingLeft = false;
		}
		if (c.x > this.targetX) {
			c.movingLeft = true;
			c.movingRight = false;
			c.lastDirection = -1;
		}
		
	}
	
}
