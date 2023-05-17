package skycubedPlatformer.assets.creature.ai;

import skycubedPlatformer.assets.creature.CreatureAi;
import skycubedPlatformer.assets.creature.creatures.Creature;

/**
 * Base AI for only vertical movement following a point. Do not use.
 */
public class VerticalMovementAi extends CreatureAi {
	
	public double targetX;
	public double targetY;
	public double maxRangeX;
	public double minRangeX;
	public double maxRangeYUp;
	public double minRangeYUp;
	public double maxRangeYDown;
	public double minRangeYDown;
	
	public VerticalMovementAi(double targetY, double minRangeY, double maxRangeY) {
		this(0, targetY, minRangeY, maxRangeY, minRangeY, maxRangeY, 0, Double.MAX_VALUE);
	}
	
	public VerticalMovementAi(double targetX, double targetY, double minRangeYUp, double maxRangeYUp, double minRangeYDown, double maxRangeYDown, double minRangeX, double maxRangeX) {
		this.targetX = targetX;
		this.targetY = targetY;
		this.maxRangeX = maxRangeX;
		this.minRangeX = minRangeX;
		this.maxRangeYUp = maxRangeYUp;
		this.minRangeYUp = minRangeYUp;
		this.maxRangeYDown = maxRangeYDown;
		this.minRangeYDown = minRangeYDown;
	}
	
	@Override
	public void run(Creature c) {
		if (Math.abs(c.x-this.targetX)>this.maxRangeX) return;
		if (Math.abs(c.x-this.targetX)<this.minRangeX) return;
		
		if ((this.targetY-c.y>this.maxRangeYUp || this.targetY-c.y<this.minRangeYUp) && c.y < this.targetY) {
			c.movingUp = false;
			c.movingDown = false;
			return;
		}
		if ((Math.abs(this.targetY-c.y)>this.maxRangeYDown || Math.abs(this.targetY-c.y)<this.minRangeYDown) && c.y > this.targetY) {
			c.movingUp = false;
			c.movingDown = false;
			return;
		}
		
		if (c.y < this.targetY) {
			c.movingUp = true;
			c.movingDown = false;
		}
		if (c.y > this.targetY) {
			c.movingDown = true;
			c.movingUp = false;
		}
		
	}
	
}
