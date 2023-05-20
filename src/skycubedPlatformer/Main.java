package skycubedPlatformer;

import java.util.ArrayList;
import java.util.HashMap;

import skycubedPlatformer.menu.ApplicationFrame;
import skycubedPlatformer.util.appdata.DataManager;
import skycubedPlatformer.util.appdata.FileLoader;
import skycubedPlatformer.util.discord.DiscordRPC;

public class Main {
	
	public static final int SIZE = 800; //DOES NOT CHANGE
	public static ApplicationFrame jframe;
	public static boolean testMode = false;
	
	public static void main(String[] args) {
		try {DiscordRPC.init();
		}catch (Exception e) {}
		FileLoader.run();
		DataManager.onStart();
		
		//start the app up
		jframe = new ApplicationFrame();
	}
	
}
