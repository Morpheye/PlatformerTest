package platformerTest.weapons;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import platformerTest.game.LivingObject;
import platformerTest.weapons.starterWeapons.*;

public class Weapon {

	public BufferedImage image;
	public int size;
	public String name;
	public String[] stats;
	public int[] statMap;
	public String lore;
	public int coinCost = 0;
	public int gemCost = 0;
	public boolean inShop = false;
	
	public Weapon() {
		
	}

	public static void weaponListInit() {
		addWeapon(new SharpSword());
		addWeapon(new SharpAxe());
		addWeapon(new PointedSpear());
		addWeapon(new WoodenClub());
		addWeapon(new SwiftDagger());
		
	}
	
	public void init(LivingObject creature) {}
	
	public static HashMap<String,Weapon> weapons = new HashMap<String,Weapon>();
	public static ArrayList<Weapon> weaponList = new ArrayList<Weapon>();
	public static ArrayList<String> weaponNames = new ArrayList<String>();
	public static Weapon getWeapon(String name) {
		if (weapons.containsKey(name)) return weapons.get(name);
		else return null;
	}
	private static void addWeapon(Weapon weapon) {
		weapons.put(weapon.name, weapon);
		weaponList.add(weapon);
		weaponNames.add(weapon.name);
	}
	
}
