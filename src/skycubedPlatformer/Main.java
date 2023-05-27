package skycubedPlatformer;

import java.awt.Toolkit;

import skycubedPlatformer.menu.ApplicationFrame;
import skycubedPlatformer.util.appdata.DataManager;
import skycubedPlatformer.util.appdata.FileLoader;

public class Main {
	
	public static ApplicationFrame jframe;
	
	public static final int SIZE = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()-125);
	public static final double SIZE_RATIO = SIZE/800.0;
	public static final float VOLUME = 1.0f;

	public static boolean testMode = true;
	
	public static void main(String[] args) {
		try {//DiscordRPC.init();
		}catch (Exception e) {}
		FileLoader.run();
		DataManager.onStart();
		
		//start the app up
		jframe = new ApplicationFrame();
		System.out.println("Created game window with size " + SIZE + " pixels.");
	}
	
}