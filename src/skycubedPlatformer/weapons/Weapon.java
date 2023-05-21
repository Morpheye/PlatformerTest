package skycubedPlatformer.weapons;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import skycubedPlatformer.game.GameObject;
import skycubedPlatformer.game.LivingObject;
import skycubedPlatformer.weapons.shopWeaponsT2.AerialStaff;
import skycubedPlatformer.weapons.shopWeaponsT2.AquaforgedTrident;
import skycubedPlatformer.weapons.shopWeaponsT2.DuelingFoil;
import skycubedPlatformer.weapons.shopWeaponsT2.GoldenKnife;
import skycubedPlatformer.weapons.shopWeaponsT2.HeavyMace;
import skycubedPlatformer.weapons.shopWeaponsT3.BejeweledMoonstaff;
import skycubedPlatformer.weapons.shopWeaponsT3.BladeOfImmolation;
import skycubedPlatformer.weapons.shopWeaponsT3.DrainingSceptre;
import skycubedPlatformer.weapons.shopWeaponsT3.ExecutionerAxe;
import skycubedPlatformer.weapons.shopWeaponsT3.PoisonEdgeKatana;
import skycubedPlatformer.weapons.shopWeaponsT4.GildedChimeraBlade;
import skycubedPlatformer.weapons.shopWeaponsT4.VampiricSabre;
import skycubedPlatformer.weapons.starterWeapons.PointedSpear;
import skycubedPlatformer.weapons.starterWeapons.SharpAxe;
import skycubedPlatformer.weapons.starterWeapons.SharpSword;
import skycubedPlatformer.weapons.starterWeapons.SwiftDagger;
import skycubedPlatformer.weapons.starterWeapons.WoodenClub;
import skycubedPlatformer.weapons.weaponsT5.SpiritScythe;

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
	public boolean isRanged = false;
	public int inShop = 0;
	
	public String attackSound = "/sounds/attack/default/attack.wav";
	public String hitSound = "/sounds/attack/default/hit.wav";
	
	public Weapon() {
		
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
	public static void weaponListInit() {
		//beginner weapons (tier 1: Bronze)
		addWeapon(new SharpSword());
		addWeapon(new SharpAxe());
		addWeapon(new PointedSpear());
		addWeapon(new WoodenClub());
		addWeapon(new SwiftDagger());
		//moderate weapons (tier 2: Silver)
		addWeapon(new DuelingFoil());
		addWeapon(new HeavyMace());
		addWeapon(new AquaforgedTrident());
		addWeapon(new AerialStaff());
		addWeapon(new GoldenKnife());
		//high-class weapons (tier 3: Gold)
		addWeapon(new PoisonEdgeKatana());
		addWeapon(new ExecutionerAxe());
		addWeapon(new BejeweledMoonstaff());
		addWeapon(new DrainingSceptre());
		addWeapon(new BladeOfImmolation());
		//exotic weapons (tier 4: diamond)
		addWeapon(new GildedChimeraBlade());
		addWeapon(new VampiricSabre());
		//ultra-exotic weapons (tier 5: crimsonade)
		addWeapon(new SpiritScythe());
	}
	
	public void onTick(LivingObject wielder) {}
	
	/**
	 * Triggers when wielder hits something. Use this for modifying damage
	 */
	public void onAttackStart(LivingObject wielder, GameObject victim) {}
	
	/**
	 * Triggers after damage dealt. Use this to undo effects of onAttackStart()
	 */
	public void onAttackEnd(LivingObject wielder, GameObject victim) {}
	
	/**
	 * Triggers when victim takes damage, if it dies.
	 */
	public void onKill(LivingObject wielder, LivingObject victim) {}
	
	/**
	 * 
	 */
	public void onUserHit(LivingObject wielder, GameObject attacker) {}
	
	/**
	 * What the weapon does when used if it is ranged
	 */
	public void rangedAttack(LivingObject wielder, int angle) {}
	
	
}
