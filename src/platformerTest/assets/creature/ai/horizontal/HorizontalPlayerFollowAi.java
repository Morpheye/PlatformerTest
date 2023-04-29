package platformerTest.assets.creature.ai.horizontal;

import platformerTest.assets.creature.ai.HorizontalMovementAi;
import platformerTest.game.Creature;
import platformerTest.menu.GamePanel;

public class HorizontalPlayerFollowAi extends HorizontalMovementAi {

	public HorizontalPlayerFollowAi(double minRange, double maxRange) {
		super(GamePanel.player.x, minRange, maxRange);
	}
	
	@Override
	public void run(Creature c) {
		this.targetX = GamePanel.player.x;
		super.run(c);
		
	}

}
