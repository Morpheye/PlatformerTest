package skycubedPlatformer.appdata;

import java.util.ArrayList;
import java.util.HashMap;

import skycubedPlatformer.weapons.Weapon;

public class SaveData {
	public HashMap<String,Integer> completedLevels = new HashMap<String,Integer>();
	public long coins = 0;
	public long gems = 0;
	public ArrayList<String> ownedWeapons = new ArrayList<String>();
	public String selectedWeapon = null;
	
	public SaveData() {

	}
	
}
