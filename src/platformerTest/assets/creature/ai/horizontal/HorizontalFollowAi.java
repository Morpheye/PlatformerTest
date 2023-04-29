package platformerTest.assets.creature.ai.horizontal;

import platformerTest.assets.creature.ai.HorizontalMovementAi;
import platformerTest.game.Creature;
import platformerTest.game.GameObject;
import platformerTest.menu.GamePanel;

/**
 * AI for horizontal movement following an object. Very stupid, can be lured off cliffs.
 */
public class HorizontalFollowAi extends HorizontalMovementAi {

	public GameObject target;
	
	public HorizontalFollowAi(double minRange, double maxRange, GameObject target) {
		this(minRange, maxRange, 0, Double.MAX_VALUE, target);
	}
	
	public HorizontalFollowAi(double minRangeX, double maxRangeX, double minRangeY, double maxRangeY, GameObject target) {
		super(GamePanel.player.x, GamePanel.player.y, minRangeX, maxRangeX, minRangeY, maxRangeY);
		this.target = target;
	}
	
	@Override
	public void run(Creature c) {
		this.targetX = this.target.x;
		this.targetY = this.target.y;
		super.run(c);
		
	}

}
