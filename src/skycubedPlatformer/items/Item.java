package skycubedPlatformer.items;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import skycubedPlatformer.items.consumables.shopConsumables.LuckyClover;
import skycubedPlatformer.items.consumables.shopConsumables.MegahealFruit;
import skycubedPlatformer.items.consumables.shopConsumables.OverhealFruit;
import skycubedPlatformer.items.consumables.shopConsumables.RubberDuck;
import skycubedPlatformer.items.items.shopItems.ItemFruit;
import skycubedPlatformer.items.weapons.Weapon;
import skycubedPlatformer.items.weapons.shopWeaponsT2.AerialStaff;
import skycubedPlatformer.items.weapons.shopWeaponsT2.AquaforgedTrident;
import skycubedPlatformer.items.weapons.shopWeaponsT2.DuelingFoil;
import skycubedPlatformer.items.weapons.shopWeaponsT2.GoldenKnife;
import skycubedPlatformer.items.weapons.shopWeaponsT2.HeavyMace;
import skycubedPlatformer.items.weapons.shopWeaponsT3.BejeweledMoonstaff;
import skycubedPlatformer.items.weapons.shopWeaponsT3.BladeOfImmolation;
import skycubedPlatformer.items.weapons.shopWeaponsT3.DrainingSceptre;
import skycubedPlatformer.items.weapons.shopWeaponsT3.ExecutionerAxe;
import skycubedPlatformer.items.weapons.shopWeaponsT3.PoisonEdgeKatana;
import skycubedPlatformer.items.weapons.shopWeaponsT4.GildedChimeraBlade;
import skycubedPlatformer.items.weapons.shopWeaponsT4.JadedHalberd;
import skycubedPlatformer.items.weapons.shopWeaponsT4.VampiricSabre;
import skycubedPlatformer.items.weapons.starterWeapons.PointedSpear;
import skycubedPlatformer.items.weapons.starterWeapons.SharpAxe;
import skycubedPlatformer.items.weapons.starterWeapons.SharpSword;
import skycubedPlatformer.items.weapons.starterWeapons.SwiftDagger;
import skycubedPlatformer.items.weapons.starterWeapons.WoodenClub;
import skycubedPlatformer.items.weapons.weaponsT5.SpiritScythe;

public class Item {
	public BufferedImage image;
	public int tier = 0;
	public String name;
	public String[] stats;
	public int[] statMap;
	public String lore;
	public int coinCost = 0;
	public int gemCost = 0;

	public int inShop = 0;
	public boolean isWeapon = false;
	public boolean isConsumable = false;
	
	public static HashMap<String,Item> items = new HashMap<String,Item>();
	public static ArrayList<Item> itemList = new ArrayList<Item>();
	public static ArrayList<String> itemNames = new ArrayList<String>();
	
	public static Item getItem(String name) {
		if (items.containsKey(name)) return items.get(name);
		else return null;
	}
	private static void addItem(Item item) {
		items.put(item.name, item);
		itemList.add(item);
		itemNames.add(item.name);
	}
	public static void itemListInit() {
		//ITEMS
		addItem(new ItemFruit());
		addItem(new RubberDuck());
		addItem(new LuckyClover());
		addItem(new OverhealFruit());
		addItem(new MegahealFruit());
		
		//WEAPONS (tier 1: Bronze)
		addItem(new SharpSword());
		addItem(new SharpAxe());
		addItem(new PointedSpear());
		addItem(new WoodenClub());
		addItem(new SwiftDagger());
		//moderate weapons (tier 2: Silver)
		addItem(new DuelingFoil());
		addItem(new HeavyMace());
		addItem(new AquaforgedTrident());
		addItem(new AerialStaff());
		addItem(new GoldenKnife());
		//high-class weapons (tier 3: Gold)
		addItem(new PoisonEdgeKatana());
		addItem(new ExecutionerAxe());
		addItem(new BejeweledMoonstaff());
		addItem(new DrainingSceptre());
		addItem(new BladeOfImmolation());
		//exotic weapons (tier 4: diamond)
		addItem(new GildedChimeraBlade());
		addItem(new JadedHalberd());
		addItem(new VampiricSabre());
		//ultra-exotic weapons (tier 5: crimsonade)
		addItem(new SpiritScythe());
		
		Weapon.weaponListInit();
	}
	
}
