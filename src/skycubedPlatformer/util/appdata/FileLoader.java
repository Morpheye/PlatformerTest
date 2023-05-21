package skycubedPlatformer.util.appdata;

import java.io.File;
import java.util.Scanner;

public class FileLoader {

	public static String rootDirectory;
	public static String workingDirectory;
	
	public static String saveFileDirectory;
	public static String screenshotFileDirectory;
	
	public static File saveFile;
	
	public static Long jsonKey;
	
	public static void run() {
		Scanner scanner = new Scanner(FileLoader.class.getResourceAsStream("/jsonKey"), "UTF-8");
		jsonKey = Long.parseLong(scanner.useDelimiter("\\A").next());
		scanner.close();
		
		String OS = (System.getProperty("os.name")).toUpperCase();
		if (OS.contains("WIN")) rootDirectory = System.getenv("AppData"); //windows
		else {
			rootDirectory = System.getProperty("user.home");
			if (OS.contains("MAC")) rootDirectory += "/Library/Application Support";
		}
		
		if (rootDirectory != null) { try { //it worked
			workingDirectory = rootDirectory + "/MorpheyeLabs/SkyCubedPlatformer/";
			File dir = new File(workingDirectory);
			if (!dir.exists()) dir.mkdirs();
			
			saveFileDirectory = workingDirectory + "/saves/";
			dir = new File(saveFileDirectory);
			if (!dir.exists()) dir.mkdirs();
			
			screenshotFileDirectory = workingDirectory + "/screenshots/";
			dir = new File(screenshotFileDirectory);
			if (!dir.exists()) dir.mkdirs();
			
			//make the save file
			saveFile = new File(saveFileDirectory + "save.json");
			saveFile.createNewFile();
			
		} catch (Exception e) {e.printStackTrace();}}
		
		
	}
	
}
