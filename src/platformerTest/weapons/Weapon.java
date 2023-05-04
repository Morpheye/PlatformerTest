package platformerTest.weapons;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import platformerTest.game.LivingObject;
import platformerTest.weapons.starterWeapons.*;

public class Weapon {

	public BufferedImage image;
	public int size = 0;
	public int tier = 0;
	public String name;
	public String[] stats;
	public int[] statMap;
	public String lore;
	public int coinCost = 0;
	public int gemCost = 0;
	public boolean inShop = false;
	
	public Weapon() {
		
	}

	public void init(LivingObject creature) {}
	
	/**
	 * Triggers when wielder hits something. Use this for modifying damage
	 */
	public void onAttackStart(LivingObject wielder, LivingObject victim) {}
	
	/**
	 * Triggers after damage dealt. Use this to undo effects of onAttackStart()
	 */
	public void onAttackEnd(LivingObject wielder, LivingObject victim) {}
	
	/**
	 * Triggers when victim takes damage, if it dies.
	 */
	public void onKill(LivingObject wielder, LivingObject victim) {}
	
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
	public static void weaponListInit() {
		//beginner weapons (tier 1: Bronze)
		addWeapon(new SharpSword());
		addWeapon(new SharpAxe());
		addWeapon(new PointedSpear());
		addWeapon(new WoodenClub());
		addWeapon(new SwiftDagger());
		//moderate weapons (tier 2: Silver)
		
		//high-class weapons (tier 3: Gold)
		
		//exotic weapons (tier 4: diamond)
		
		//ultra-exotic weapons (tier 5: crimsonade)
	}
	
}
