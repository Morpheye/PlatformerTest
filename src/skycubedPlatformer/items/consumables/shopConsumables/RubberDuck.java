package skycubedPlatformer.items.consumables.shopConsumables;

import javax.imageio.ImageIO;

import skycubedPlatformer.game.Player;
import skycubedPlatformer.items.consumables.Consumable;

public class RubberDuck extends Consumable {
	
	public RubberDuck() {
		super();
		try {
			this.coinCost = 20;
			this.inShop = 1;
			
			this.name = "Rubber Duck";
			
			this.stats = new String[]{"On level start:", "-10% density", "Single Use"};
			this.statMap = new int[] {1, 1, -1};
			
			this.lore = "Made of rubber and very cute. Also reduces the risk of drowning while swimming.";
			
			this.image = ImageIO.read(this.getClass().getResource("/items/shopConsumables/RubberDuck.png"));

		} catch (Exception e) {}
	}
	
	@Override
	public void onLevelStart(Player p) {
		p.density -= 0.1;
	}
	
}
