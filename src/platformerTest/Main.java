package platformerTest;

import java.util.ArrayList;
import java.util.HashMap;

import platformerTest.appdata.DataManager;
import platformerTest.appdata.FileLoader;
import platformerTest.appdata.discord.DiscordRPC;
import platformerTest.menu.ApplicationFrame;

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
