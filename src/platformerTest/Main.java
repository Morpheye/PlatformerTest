package platformerTest;

import java.util.ArrayList;
import java.util.HashMap;

import platformerTest.appdata.DataManager;
import platformerTest.appdata.FileLoader;
import platformerTest.menu.ApplicationFrame;

public class Main {
	
	public static final int SIZE = 800; //DOES NOT CHANGE
	public static ApplicationFrame jframe;

	
	public static void main(String[] args) {
		
		FileLoader.run();
		DataManager.onStart();
		
		//start the app up
		jframe = new ApplicationFrame();
	}
	


	
	
}
