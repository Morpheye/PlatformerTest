package platformerTest;

import java.awt.Container;

import javax.swing.JFrame;

import platformerTest.game.MainFrame;

public class Main {
	
	public static final double DRAG = 0.98;
	public static final double GRAVITY = 1;
	
	public static final int SIZE_X = 800;
	public static final int SIZE_Y = 800;

	public static void main(String[] args) {
		
		JFrame jframe = new JFrame("Platformer Test");
		jframe.setTitle("Platformer Test");
		jframe.setSize(SIZE_X,SIZE_Y);
		jframe.setResizable(false);
		jframe.setLocationRelativeTo(null);
		jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		Container panel = jframe.getContentPane();
		panel.add(new MainFrame());

		jframe.setVisible(true);

	}

}
