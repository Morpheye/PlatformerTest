package platformerTest.appdata;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import platformerTest.levels.LevelWorld;
import platformerTest.weapons.Weapon;

public class DataManager {
	public static ObjectMapper mapper = new ObjectMapper();
	public static SaveData saveData;
	
	public static void onStart() {
		if (FileLoader.saveFile == null) return;
		File save = FileLoader.saveFile;
		
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		try {
			saveData = mapper.readValue(save, SaveData.class);
			
		} catch (Exception e) {
			saveData = new SaveData();
			e.printStackTrace();
		}
		
		Weapon.weaponListInit();
		LevelWorld.init();
		
	}
	
	public static void save() {
		if (FileLoader.saveFile == null) return;
		File save = FileLoader.saveFile;
		
		try {
			if (saveData.gems < 0) saveData.gems = Long.MAX_VALUE;
			if (saveData.coins < 0) saveData.coins = Long.MAX_VALUE;
			
			String saveValue = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(saveData);
			FileWriter writer = new FileWriter(save);
			
			writer.write(saveValue);
			
			writer.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static void loadSound(Object obj, Clip clip, String resource) {
		new Thread(() -> {
			try {
				InputStream stream = new BufferedInputStream(obj.getClass().getResourceAsStream(resource));
				AudioInputStream audioStream = AudioSystem.getAudioInputStream(stream);
				clip.open(audioStream);
				
			} catch (Exception e) {e.printStackTrace();}
		}).start();
	}
	
}
