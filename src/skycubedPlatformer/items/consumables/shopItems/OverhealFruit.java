package skycubedPlatformer.items.consumables.shopItems;

import javax.imageio.ImageIO;

import skycubedPlatformer.game.Player;
import skycubedPlatformer.items.consumables.Consumable;

public class OverhealFruit extends Consumable {
	
	public OverhealFruit() {
		try {
			this.coinCost = 100;
			this.inShop = 1;
			
			this.name = "Overheal Fruit";
			this.tier = 0;
			
			this.stats = new String[]{"On level start:", "+50 overheal", "Single Use"};
			this.statMap = new int[] {1, 1, 0};
			
			this.lore = "Grown in a well-kept garden free of the use of pesticides and GMOs, "
					+ "this fruit heavily boosts the consumer's health on consumption.";
			
			this.image = ImageIO.read(this.getClass().getResource("/items/shopItems/OverhealFruit.png"));

		} catch (Exception e) {}
	}
	
	@Override
	public void onLevelStart(Player p) {
		p.overheal += 50;
	}
	
}
