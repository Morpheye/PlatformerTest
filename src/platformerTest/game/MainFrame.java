package platformerTest.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

import platformerTest.Main;
import platformerTest.assets.MovableObject;
import platformerTest.assets.SolidPlatform;

@SuppressWarnings("serial")
public class MainFrame extends JPanel {
	
	static Player player;
	public static List<GameObject> objects = new ArrayList<GameObject>();
	GameObject platform;
	
	public MainFrame() {
		
		this.setBackground(Color.BLACK);
		this.setSize(Main.SIZE_X, Main.SIZE_Y);
		this.setVisible(true);
		this.setFocusable(true);
		this.addKeyListener(new Keyboard());
		

		
		objects.add(new SolidPlatform(400, 100, 210, 200, Color.WHITE));
		objects.add(new SolidPlatform(600, 100, 210, 280, Color.WHITE));
		objects.add(new SolidPlatform(800, 100, 200, 400, Color.WHITE));
		
		objects.add(new MovableObject(550, 400, 80, 80, Color.RED));
		
		player = new Player(400, 400, 40);
		objects.add(player);

		Timer timer = new Timer(1000/60, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
		}});
		
		timer.start();
		
		
	}
	
	public void paint (Graphics g) {
		super.paint(g);
		
		for (GameObject obj : objects) {
			obj.move();
			
			this.paintObj(g, obj);
		}
		
	}
	
	public void paintObj(Graphics g, GameObject obj) {
		
		int drawX = (int) (obj.x - obj.size_x/2);
		int drawY = (int) (Main.SIZE_Y - (obj.y + obj.size_y/2));
		
		g.setColor(obj.color);
		g.fillRoundRect(drawX, drawY, (int) obj.size_x, (int) obj.size_y, 3, 3);
	}
	
	public class Keyboard extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == 87) player.movingUp = true; //W
			if (e.getKeyCode() == 65) player.movingLeft = true; //A
			if (e.getKeyCode() == 83) player.movingDown = true; //S
			if (e.getKeyCode() == 68) player.movingRight = true; //D
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == 87) player.movingUp = false; //W
			if (e.getKeyCode() == 65) player.movingLeft = false; //A
			if (e.getKeyCode() == 83) player.movingDown = false; //S
			if (e.getKeyCode() == 68) player.movingRight = false; //D
			
		}
	}


	
	
}
