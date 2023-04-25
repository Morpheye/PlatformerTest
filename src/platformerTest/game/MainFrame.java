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
import platformerTest.assets.decoration.TextObject;
import platformerTest.levels.Level_0;

@SuppressWarnings("serial")
public class MainFrame extends JPanel {
	
	public static Player player;
	public static Level level;
	public static double drag;
	public static double gravity;
	public static List<GameObject> objects;
	GameObject platform;
	
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

		drag = level.drag;
		gravity = level.gravity;
		
		level.drawPlatforms();
		
		player = new Player(level.spawnX, level.spawnY, 40);
		objects.add(player);
	}
	
	
	public void paint(Graphics g) {
		super.paint(g);
	
		
		level.onTick();
		
		if (player.y < level.bottomLimit) player.die();
		if (player.y > level.topLimit) player.die(); 
		
		Color bgColor;
		
		if (player.y < (level.bottomLimit + 1000)) {
			bgColor = new Color((int) (1000 - (player.y - level.bottomLimit))/10, 0, 0);
		} else if (player.y > (level.topLimit - 1000)) {
			bgColor = new Color((int) (1000 - (level.topLimit - player.y))/10, 0, 0);
		} else {
			bgColor = Color.BLACK;
		}
		
		g.setColor(bgColor);
		g.fillRect(-50, -50, Main.SIZE_X+50, Main.SIZE_Y + 50);
		
		for (GameObject obj : objects) {
			try {
			obj.move();
			this.paintObj(g, obj);
			} catch (Exception e) {}
		}
		
	}
	
	public void paintObj(Graphics g, GameObject obj) {
		
		if (obj instanceof Player) {
			
			int drawX = (int) (Main.SIZE_X/2 - obj.size_x/2);
			int drawY = (int) (Main.SIZE_Y - (Main.SIZE_Y/2 + obj.size_y/2));
			
			g.setColor(obj.color);
			g.fillRoundRect(drawX, drawY, (int) obj.size_x, (int) obj.size_y, 5, 5);
			
		} else if (obj instanceof TextObject) {
			
			int drawX = (int) (obj.x - obj.size_x/2 - (player.x - Main.SIZE_X/2));
			int drawY = (int) (Main.SIZE_Y - (obj.y + obj.size_y/2) + (player.y - Main.SIZE_Y/2));
			
			g.setColor(obj.color);
			g.setFont(new Font(Font.SERIF, Font.PLAIN, 25));
			g.drawString(((TextObject) obj).text, drawX, drawY);
			
		} else {
			
			int drawX = (int) (obj.x - obj.size_x/2 - (player.x - Main.SIZE_X/2));
			int drawY = (int) (Main.SIZE_Y - (obj.y + obj.size_y/2) + (player.y - Main.SIZE_Y/2));
			
			g.setColor(obj.color);
			g.fillRoundRect(drawX, drawY, (int) obj.size_x, (int) obj.size_y, 5, 5);
			
		}
		
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
