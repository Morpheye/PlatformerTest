package skycubedPlatformer.util.appdata;

import java.util.ArrayList;
import java.util.HashMap;

public class SaveData {
	public HashMap<String,Integer> completedLevels = new HashMap<String,Integer>();
	public long coins = 0;
	public long gems = 0;
	public HashMap<String, Long> inventory = new HashMap<String, Long>();
	public String selectedWeapon = null;
	public ArrayList<String> activeItems = new ArrayList<String>();
	
	public SaveData() {

	}
	
}
