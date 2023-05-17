package skycubedPlatformer;

import java.util.ArrayList;
import java.util.HashMap;

import skycubedPlatformer.appdata.DataManager;
import skycubedPlatformer.appdata.FileLoader;
import skycubedPlatformer.appdata.discord.DiscordRPC;
import skycubedPlatformer.menu.ApplicationFrame;

public class Main {
	
	public static final int SIZE = 800; //DOES NOT CHANGE
	public static ApplicationFrame jframe;
	public static boolean testMode = false;
	
	public static void main(String[] args) {
		DiscordRPC.init();
		FileLoader.run();
		DataManager.onStart();
		
		//start the app up
		jframe = new ApplicationFrame();
	}
	


	
	
}
