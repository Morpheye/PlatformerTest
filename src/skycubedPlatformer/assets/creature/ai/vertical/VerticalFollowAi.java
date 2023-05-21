package skycubedPlatformer.assets.creature.ai.vertical;

import java.util.ArrayList;

import skycubedPlatformer.assets.creature.ai.VerticalMovementAi;
import skycubedPlatformer.assets.creature.creatures.Creature;
import skycubedPlatformer.game.GameObject;
import skycubedPlatformer.game.LivingObject;
import skycubedPlatformer.menu.GamePanel;
import skycubedPlatformer.weapons.weaponsT5.SpiritScythe;

/**
 * AI for vertical following of a target. Should not be used to substitute four directional.
 */
public class VerticalFollowAi extends VerticalMovementAi {

	public GameObject target;
	public ArrayList<GameObject> targets = new ArrayList<GameObject>();
	
	public VerticalFollowAi(double minRange, double maxRange, LivingObject target) {
		this(0, Double.MAX_VALUE, minRange, maxRange, 0, maxRange, target);
	}
	
	public VerticalFollowAi(double minRangeX, double maxRangeX, double minRangeYUp, double maxRangeYUp, double minRangeYDown, double maxRangeYDown, LivingObject target) {
		super(GamePanel.player.x, GamePanel.player.y, minRangeYUp, maxRangeYUp, minRangeYDown, maxRangeYDown, minRangeX, maxRangeX);
		this.target = target;
		this.targets.clear();
		this.targets.add(this.target);
	}
	
	@Override
	public void run(Creature c) {
		//change targets
		if (this.target != null && !this.target.exists) {
			this.targets.remove(this.target);
			this.target = null;
		}
		
		if (this.target == null) {
			if (this.targets.size() > 0) this.target = this.targets.get(0);
		}
		
		if (this.target != null) {
			this.targetX = this.target.x;
			this.targetY = this.target.y;
		}
		super.run(c);
		
	}
	
	@Override
	public void onDamage(Creature creature, GameObject source) {
		if (source instanceof SpiritScythe.ScytheSpirit || source.equals(GamePanel.player)) {
			if (!this.targets.contains(source)) this.targets.add(source);
			this.target = source;
		}
	}

}
