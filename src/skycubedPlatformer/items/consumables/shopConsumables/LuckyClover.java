package skycubedPlatformer.items.consumables.shopConsumables;

import javax.imageio.ImageIO;

import skycubedPlatformer.game.Player;
import skycubedPlatformer.items.consumables.Consumable;

public class LuckyClover extends Consumable {
	
	public LuckyClover() {
		super();
		try {
			this.coinCost = 50;
			this.inShop = 1;
			
			this.name = "Lucky Clover";
			
			this.stats = new String[]{"On level start:", "+10% luck", "Single Use"};
			this.statMap = new int[] {1, 1, -1};
			
			this.lore = "The four leaves of the clover increase favorable outcome likeliness in their holder, "
					+ "granting increased coin and item drops from creatures. Not to be used for experiments.";
			
			this.image = ImageIO.read(this.getClass().getResource("/items/shopConsumables/LuckyClover.png"));

		} catch (Exception e) {}
	}
	
	@Override
	public void onLevelStart(Player p) {
		p.luck += 0.1;
	}
	
}
