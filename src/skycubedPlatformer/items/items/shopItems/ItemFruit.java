package skycubedPlatformer.items.items.shopItems;

import javax.imageio.ImageIO;

import skycubedPlatformer.items.Item;

public class ItemFruit extends Item {

	public ItemFruit() {
		try {
			this.coinCost = 10;
			this.inShop = 1;
			
			this.name = "Fruit";
			
			this.stats = new String[]{};
			this.statMap = new int[] {};
			
			this.lore = "Grown in pristine conditions and immune to spoiling. Very tasty, but otherwise does nothing.";
			
			this.image = ImageIO.read(this.getClass().getResource("/items/shopItems/Fruit.png"));

		} catch (Exception e) {}
	}
	
}
