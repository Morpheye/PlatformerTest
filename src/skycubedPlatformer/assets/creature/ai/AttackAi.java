package skycubedPlatformer.assets.creature.ai;

import java.util.ArrayList;

import skycubedPlatformer.assets.creature.CreatureAi;
import skycubedPlatformer.assets.creature.creatures.Creature;
import skycubedPlatformer.game.GameObject;
import skycubedPlatformer.game.LivingObject;
import skycubedPlatformer.items.weapons.weaponsT5.SpiritScythe;
import skycubedPlatformer.menu.GamePanel;

/**
 * General attack AI
 */
public class AttackAi extends CreatureAi {

	public double meleeRange;
	public ArrayList<LivingObject> targets;
	
	public AttackAi(double meleeRange, LivingObject target) {
		this.meleeRange = meleeRange;
		this.targets = new ArrayList<LivingObject>();
		this.targets.add(target);
	}
	
	public AttackAi(double meleeRange, ArrayList<LivingObject> targets) {
		this.meleeRange = meleeRange;
		this.targets = targets;
	}
	
	@Override
	public void run(Creature c) {
		c.isAttacking = false;
		for (LivingObject i : targets) {
			if (!i.isAlive) {
				continue;
			}
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
	
	@Override
	public void onDamage(Creature creature, GameObject source) {
		if (source instanceof SpiritScythe.ScytheSpirit || source.equals(GamePanel.player)) {
			if (!this.targets.contains(source)) this.targets.add((LivingObject) source);
		}
	}
	
}
