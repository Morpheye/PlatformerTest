package platformerTest.assets.weapons;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import platformerTest.assets.creature.creatures.Creature;
import platformerTest.assets.weapons.axes.*;
import platformerTest.assets.weapons.swords.*;
import platformerTest.game.Player;

public class Weapon {

	public BufferedImage image;
	public int size;
	public String name;
	public String[] stats;
	public String lore;
	public int coinCost = 0;
	public int gemCost = 0;
	public boolean inShop = false;
	
	public Weapon() {
		
	}
	
	public static String getName() {return null;}
	
	public void init(Creature creature) {}
	
	public void init(Player player) {}
	
	public static HashMap<String,Weapon> weapons = new HashMap<String,Weapon>();
	public static ArrayList<Weapon> weaponList = new ArrayList<Weapon>();
	public static ArrayList<String> weaponNames = new ArrayList<String>();
	public static Weapon getWeapon(String name) {
		if (weapons.containsKey(name)) return weapons.get(name);
		else return null;
	}
	public static void weaponListInit() {
		addWeapon(new SharpSword());
		addWeapon(new SharpAxe());
		
	}
	private static void addWeapon(Weapon weapon) {
		weapons.put(weapon.name, weapon);
		weaponList.add(weapon);
		weaponNames.add(weapon.name);
	}
	
}
