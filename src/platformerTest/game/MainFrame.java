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
		
		player = new Player(level.spawnX, level.spawnY, 40);
		objects.add(player);
	
		airDrag = level.airDrag;
		gravity = level.gravity;
		
		level.drawPlatforms();
		


	}
	
	
	public void paint(Graphics g) {
		super.paint(g);
		
		level.onTick();
		
		Color bgColor = level.backgroundColor;

		
		g.setColor(bgColor);
		g.fillRect(-50, -50, Main.SIZE_X+50, Main.SIZE_Y + 50);
		
		for (GameObject obj : objects) {
			obj.move();
			
			if (obj.hasCollided(MainFrameObj)) this.paintObj(g, obj);
			
			if (obj.equals(player)) { 
				MainFrameObj.x = player.x;
				MainFrameObj.y = player.y;
			}
			
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
			g.setFont(((TextObject) obj).font);
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
