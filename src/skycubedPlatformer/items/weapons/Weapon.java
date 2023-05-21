package skycubedPlatformer.items.weapons;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import skycubedPlatformer.game.GameObject;
import skycubedPlatformer.game.LivingObject;
import skycubedPlatformer.items.Item;
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
import skycubedPlatformer.items.weapons.shopWeaponsT4.VampiricSabre;
import skycubedPlatformer.items.weapons.starterWeapons.PointedSpear;
import skycubedPlatformer.items.weapons.starterWeapons.SharpAxe;
import skycubedPlatformer.items.weapons.starterWeapons.SharpSword;
import skycubedPlatformer.items.weapons.starterWeapons.SwiftDagger;
import skycubedPlatformer.items.weapons.starterWeapons.WoodenClub;
import skycubedPlatformer.items.weapons.weaponsT5.SpiritScythe;

public class Weapon extends Item {
	public int size = 0;
	public boolean isRanged = false;
	
	public String attackSound = "/sounds/attack/default/attack.wav";
	public String hitSound = "/sounds/attack/default/hit.wav";
	
	public Weapon() {
		this.isWeapon = true;
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
		itemList.forEach(c -> {
			if (c.isWeapon) addWeapon((Weapon) c);
		});
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
