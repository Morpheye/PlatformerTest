package skycubedPlatformer.items.consumables;

import java.util.ArrayList;

import skycubedPlatformer.game.Player;
import skycubedPlatformer.items.Item;
import skycubedPlatformer.util.appdata.DataManager;

public class Consumable extends Item {
	
	public Consumable() {
		this.isConsumable = true;
	}
	
	public void onLevelStart(Player p) {
		
	}
	
	public static void activateItems(Player p, ArrayList<Consumable> list) {
		DataManager.saveData.activeItems.forEach(c -> {
			if (DataManager.saveData.inventory.containsKey(c)) {
				long amount = DataManager.saveData.inventory.get(c);
				DataManager.saveData.inventory.put(c, amount - 1);
				if (amount - 1 == 0) {
					DataManager.saveData.inventory.remove(c);
				}
				
				((Consumable) getItem(c)).onLevelStart(p);
				list.add((Consumable) getItem(c));
			}
		});
		
		DataManager.saveData.activeItems.removeIf(c -> {
			return !DataManager.saveData.inventory.containsKey(c);
		});
	}
	
}
