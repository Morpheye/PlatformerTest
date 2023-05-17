package skycubedPlatformer.assets.creature.ai.allDirectional;

import java.util.ArrayList;

import skycubedPlatformer.assets.creature.CreatureAi;
import skycubedPlatformer.assets.creature.creatures.Creature;
import skycubedPlatformer.game.GameObject;
import skycubedPlatformer.game.LivingObject;
import skycubedPlatformer.menu.GamePanel;
import skycubedPlatformer.weapons.starterWeapons.SharpSword;
import skycubedPlatformer.weapons.weaponsT5.SpiritScythe;

public class GuardAi extends CreatureAi {

	public GameObject target;
	public ArrayList<GameObject> targets = new ArrayList<GameObject>();
	public double postX;
	public double postY;
	public double xBoundMin;
	public double xBoundMax;
	public double yBoundMin;
	public double yBoundMax;
	
	public GuardAi(int x, int y, int xBound, LivingObject target) {
		this(x, y, 0, xBound, 0, Integer.MAX_VALUE, target);
	}
	
	public GuardAi(double initX, double initY, double minRangeX, double maxRangeX, double minRangeY, double maxRangeY, LivingObject target) {
		this.target = target;
		this.postX = initX;
		this.postY = initY;
		this.xBoundMin = minRangeX;
		this.xBoundMax = maxRangeX;
		this.yBoundMin = minRangeY;
		this.yBoundMax = maxRangeY;
		this.targets.clear();
		this.targets.add(this.target);
	}
	
	public void run(Creature c) {
		//change targets
		if (this.target != null && !this.target.exists) {
			this.targets.remove(this.target);
			this.target = null;
		}
				
		if (this.target == null) {
			if (this.targets.size() > 0) this.target = this.targets.get(0);
		}
				
		if (c.x - this.postX > xBoundMax) { //too far right
			c.movingRight = false;
			c.movingLeft = true;
			c.lastDirection = -1;
		} else if (this.postX - c.x > xBoundMax) { //too far left
			c.movingLeft = false;
			c.movingRight = true;
			c.lastDirection = 1;
		} else if (target != null && Math.abs(target.x - this.postX) < xBoundMax) { //target within guard range
			if (c.x - target.x > 0 && Math.abs(target.y - this.postY) < yBoundMax) {
				c.movingLeft = true;
				c.movingRight = false;
				c.lastDirection = -1;
			} else if (c.x - target.x < 0 && Math.abs(target.y - this.postY) < yBoundMax) {
				c.movingRight = true;
				c.movingLeft = false;
				c.lastDirection = 1;
			} else {
				c.movingRight = false;
				c.movingLeft = false;
			}
		} else if (Math.abs(c.x - this.postX) <= xBoundMin) {
			c.movingLeft = false;
			c.movingRight = false;
		} else if (c.x > this.postX) {
			c.movingLeft = true;
			c.movingRight = false;
			c.lastDirection = -1;
		} else if (c.x < this.postX) {
			c.movingLeft = false;
			c.movingRight = true;
			c.lastDirection = 1;
		} else {
			c.movingLeft = false;
			c.movingRight = false;
		}
		
		if (c.y - this.postY > yBoundMax) { //too far up
			c.movingUp = false;
			c.movingDown = true;
		} else if (this.postY - c.y > yBoundMax) { //too far down
			c.movingDown = false;
			c.movingUp = true;
		} else if (target != null && Math.abs(target.y - this.postY) < yBoundMax && Math.abs(target.y - this.postY) > yBoundMin
				&& c.y - target.y > 0 && Math.abs(target.x - this.postX) < xBoundMax) { //target within guard range
				c.movingDown = true;
				c.movingUp = false;
		} else if (target != null && Math.abs(target.y - this.postY) < yBoundMax && Math.abs(target.y - this.postY) > yBoundMin
				&& c.y - target.y < 0 && Math.abs(target.x - this.postX) < xBoundMax) {
				c.movingUp = true;
				c.movingDown = false;
		} else if (Math.abs(c.y - this.postY) <= yBoundMin) {
			c.movingUp = false;
			c.movingDown = false;
		} else if (c.y > this.postY) {
			c.movingUp = false;
			c.movingDown = true;
		} else if (c.y < this.postY) { 
			c.movingUp = true;
			c.movingDown = false;
		}
		
		
		
		
	}
	
	@Override
	public void onDamage(Creature creature, LivingObject source) {
		if (source instanceof SpiritScythe.ScytheSpirit || source.equals(GamePanel.player)) {
			if (!this.targets.contains(source)) this.targets.add(source);
			this.target = source;
		}
	}
	
}
