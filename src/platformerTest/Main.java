package platformerTest;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

import platformerTest.game.MainFrame;

public class Main {
	
	public static final int SIZE_X = 800;
	public static final int SIZE_Y = 800;
	public static JFrame jframe;
	public static JPanel game;

	public static void main(String[] args) {
		
		jframe = new JFrame("Platformer Test");
		jframe.setTitle("Platformer Test");
		jframe.setSize(SIZE_X,SIZE_Y);
		jframe.setResizable(false);
		jframe.setLocationRelativeTo(null);
		jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		Container panel = jframe.getContentPane();

		game = new MainFrame();
		
		panel.add(game);

		jframe.setVisible(true);

	}

}
