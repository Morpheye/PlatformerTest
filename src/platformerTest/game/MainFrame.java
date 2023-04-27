package platformerTest.game;

import java.awt.Color;
import java.awt.Font;
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
import platformerTest.assets.decorations.TextObject;
import platformerTest.levels.Level_0;

@SuppressWarnings("serial")
public class MainFrame extends JPanel {
	
	public static Player player;
	public static Level level;
	public static double airDrag;
	public static double gravity;
	public static List<GameObject> objects;
	
	public static double camera_x;
	public static double camera_y;
	public static int camera_size;

	public static final GameObject MainFrameObj = new GameObject(0, 0, Main.SIZE_X+50, Main.SIZE_Y+50, null);
	
	public MainFrame() {
		
		this.setBackground(Color.BLACK);
		this.setSize(Main.SIZE_X, Main.SIZE_Y);
		this.setVisible(true);
		this.setFocusable(true);
		this.addKeyListener(new Keyboard());
		
		restartLevel(new Level_0());
		
		Timer timer = new Timer(1000/60, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
		}});
		
		timer.start();
		
	}
	
	public static void restartLevel(Level newLevel) {
		
		objects = new ArrayList<GameObject>();
		
		level = newLevel;
		
		level.drawBackground();
		
		player = new Player(level.spawnX, level.spawnY, 40);
		objects.add(player);
		
		camera_x = (int) player.x;
		camera_y = (int) player.y;
		camera_size = 800;
		
		level.drawForeground();
	
		airDrag = level.airDrag;
		gravity = level.gravity;
		
		level.drawPlatforms();
		


	}
	
	
	public void paint(Graphics g) {
		super.paint(g);
		
		level.onTick();
		
		player.move();
		moveCamera();
		
		Color bgColor = level.backgroundColor;
		
		g.setColor(bgColor);
		g.fillRect(-50, -50, Main.SIZE_X+50, Main.SIZE_Y + 50);
		
		for (GameObject obj : objects) {
			if (!obj.equals(player)) obj.move();
			
			if (obj.hasCollided(MainFrameObj)) obj.draw(g, player, camera_x, camera_y, camera_size);
			
		}
		
		level.drawAmbience(g);
		
		if (player.y < (level.bottomLimit + 1000)) {
			int alpha = (int)(player.y - level.bottomLimit)* 255/1000;
			g.setColor(new Color(0,0,0,255-alpha));
			g.fillRect(-50, -50, Main.SIZE_X+50, Main.SIZE_Y + 50);
		} else if (player.y > (level.topLimit - 1000)) {
			int alpha = -(int)(player.y - level.topLimit)* 255/1000;
			g.setColor(new Color(0,0,0,255-alpha));
			g.fillRect(-50, -50, Main.SIZE_X+50, Main.SIZE_Y + 50);
		}
		
	}
	
	public void moveCamera() {
		camera_x = player.x;
		camera_y = player.y;
		
		camera_size = Main.SIZE_Y;
		
		MainFrameObj.x = camera_x;
		MainFrameObj.y = camera_y;
		MainFrameObj.size_x = camera_size + 50;
		MainFrameObj.size_y = camera_size + 50;
	}

	public class Keyboard extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_W) player.movingUp = true; //W
			if (e.getKeyCode() == KeyEvent.VK_A) player.movingLeft = true; //A
			if (e.getKeyCode() == KeyEvent.VK_S) player.movingDown = true; //S
			if (e.getKeyCode() == KeyEvent.VK_D) player.movingRight = true; //D
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_W) player.movingUp = false; //W
			if (e.getKeyCode() == KeyEvent.VK_A) player.movingLeft = false; //A
			if (e.getKeyCode() == KeyEvent.VK_S) player.movingDown = false; //S
			if (e.getKeyCode() == KeyEvent.VK_D) player.movingRight = false; //D
			
		}
	}
	
}
