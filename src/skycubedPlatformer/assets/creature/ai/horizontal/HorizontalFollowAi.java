package skycubedPlatformer.assets.creature.ai.horizontal;

import java.util.ArrayList;

import skycubedPlatformer.assets.creature.ai.HorizontalMovementAi;
import skycubedPlatformer.assets.creature.creatures.Creature;
import skycubedPlatformer.game.GameObject;
import skycubedPlatformer.game.LivingObject;
import skycubedPlatformer.menu.GamePanel;
import skycubedPlatformer.weapons.weaponsT5.SpiritScythe;

/**
 * AI for horizontal movement following an object. Very stupid, can be lured off cliffs.
 */
public class HorizontalFollowAi extends HorizontalMovementAi{

	public GameObject target;
	public ArrayList<GameObject> targets = new ArrayList<GameObject>();
	
	public HorizontalFollowAi(double minRange, double maxRange, LivingObject target) {
		this(minRange, maxRange, 0, Double.MAX_VALUE, target);
	}
	
	public HorizontalFollowAi(double minRangeX, double maxRangeX, double minRangeY, double maxRangeY, LivingObject target) {
		super(GamePanel.player.x, GamePanel.player.y, minRangeX, maxRangeX, minRangeY, maxRangeY);
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
	public void onDamage(Creature creature, LivingObject source) {
		if (source instanceof SpiritScythe.ScytheSpirit || source.equals(GamePanel.player)) {
			if (!this.targets.contains(source)) this.targets.add(source);
			this.target = source;
		}
	}

}
