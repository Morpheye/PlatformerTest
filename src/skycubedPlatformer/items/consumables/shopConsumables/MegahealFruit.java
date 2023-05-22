package skycubedPlatformer.items.consumables.shopConsumables;

import javax.imageio.ImageIO;

import skycubedPlatformer.game.Player;
import skycubedPlatformer.items.consumables.Consumable;

public class MegahealFruit extends Consumable {
	
	public MegahealFruit() {
		super();
		try {
			this.coinCost = 300;
			this.inShop = 1;
			
			this.name = "Megaheal Fruit";
			
			this.stats = new String[]{"On level start:", "+150 overheal", "Single Use"};
			this.statMap = new int[] {1, 1, -1};
			
			this.lore = "The typical golden Overheal Apple petrifies into diamond after years of "
					+ "selective plant breeding, providing an extreme amount of overheal to its consumer.";
			
			this.image = ImageIO.read(this.getClass().getResource("/items/shopConsumables/MegahealFruit.png"));

		} catch (Exception e) {}
	}
	
	@Override
	public void onLevelStart(Player p) {
		p.overheal += 150;
	}
	
}
