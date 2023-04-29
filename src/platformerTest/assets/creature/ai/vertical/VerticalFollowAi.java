package platformerTest.assets.creature.ai.vertical;

import platformerTest.assets.creature.ai.VerticalMovementAi;
import platformerTest.game.Creature;
import platformerTest.game.GameObject;
import platformerTest.menu.GamePanel;

/**
 * AI for vertical following of a target. Should not be used to substitute four directional.
 */
public class VerticalFollowAi extends VerticalMovementAi {

	public GameObject target;
	
	public VerticalFollowAi(double minRange, double maxRange, GameObject target) {
		this(0, Double.MAX_VALUE, minRange, maxRange, minRange, maxRange, target);
	}
	
	public VerticalFollowAi(double minRangeX, double maxRangeX, double minRangeYUp, double maxRangeYUp, double minRangeYDown, double maxRangeYDown, GameObject target) {
		super(GamePanel.player.x, GamePanel.player.y, minRangeYUp, maxRangeYUp, minRangeYDown, maxRangeYDown, minRangeX, maxRangeX);
		this.target = target;
	}
	
	@Override
	public void run(Creature c) {
		this.targetX = this.target.x;
		this.targetY = this.target.y;
		super.run(c);
		
	}

}
