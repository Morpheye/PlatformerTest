package platformerTest.menu;

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
import platformerTest.game.GameObject;
import platformerTest.game.Level;
import platformerTest.game.Player;
import platformerTest.levels.world1.Level_1_1;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {
	
	public static Player player;
	public static Level level;
	public static double airDrag;
	public static double gravity;
	public static List<GameObject> objects;
	
	public static double camera_x;
	public static double camera_y;
	public static int camera_size;
	
	public static boolean isPaused;
	public static int levelWon;

	public static final GameObject MainFrameObj = new GameObject(0, 0, Main.SIZE+50, Main.SIZE+50, null);
	
	public GamePanel(Level level) {
		
		this.setBackground(Color.BLACK);
		this.setSize(Main.SIZE, Main.SIZE);
		this.setVisible(true);
		this.setFocusable(true);
		this.addKeyListener(new Keyboard());
		
		restartLevel(level);
		
		Timer timer = new Timer(1000/120, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
		}});
		
		timer.start();
		
		levelWon = 0;
		isPaused = false;
		
	}
	
	public static void restartLevel(Level newLevel) {
		
		objects = new ArrayList<GameObject>();
		
		level = newLevel;
		
		level.drawBackground();
		
		player = new Player(level.spawnX, level.spawnY, 40);
		objects.add(player);
		
		camera_x = (int) player.x;
		camera_y = (int) player.y;
		camera_size = Main.SIZE;
		
		level.drawForeground();
	
		airDrag = level.airDrag;
		gravity = level.gravity;
		
		level.drawPlatforms();
		


	}
	
	public void paint(Graphics g) {
		super.paint(g);

		if (!isPaused) {
			level.onTick();
			player.move();
			moveCamera();
		}
		
		Color bgColor = level.backgroundColor;
		
		g.setColor(bgColor);
		g.fillRect(-50, -50, Main.SIZE+50, Main.SIZE + 50);
		
		for (GameObject obj : objects) {
			if (!obj.equals(player) && !isPaused) {
				obj.move();
			}
			
			if (obj.hasCollided(MainFrameObj)) obj.draw(g, player, camera_x, camera_y, camera_size);
			
		}
		
		level.drawAmbience(g);
		
		if (player.y < (level.bottomLimit + 1000) && levelWon == 0) {
			int alpha = (int)(player.y - level.bottomLimit)* 255/1000;
			g.setColor(new Color(0,0,0,255-alpha));
			g.fillRect(-50, -50, Main.SIZE+50, Main.SIZE + 50);
		} else if (player.y > (level.topLimit - 1000) && levelWon == 0) {
			int alpha = -(int)(player.y - level.topLimit)* 255/1000;
			g.setColor(new Color(0,0,0,255-alpha));
			g.fillRect(-50, -50, Main.SIZE+50, Main.SIZE + 50);
		}
		
		//pause menu
		if (isPaused) {
			g.setColor(new Color(0,0,0,200));
			g.fillRect(-50, -50, Main.SIZE+50, Main.SIZE + 50);
			
			Font font = new Font(Font.MONOSPACED, Font.BOLD, 50);
			g.setFont(font);
			g.setColor(Color.WHITE);
			int lvlSelectStringWidth = g.getFontMetrics(font).stringWidth("Game Paused");
			g.drawString("Game Paused", Main.SIZE/2 - lvlSelectStringWidth/2, 75);
			
		}
		
		//check if won
		if (!(levelWon == 0)) {
			levelWon++;
			if (levelWon < 240) {
				g.setColor(new Color(255,255,255,255*(240-levelWon)/240));
				g.fillRect(-50, -50, Main.SIZE+50, Main.SIZE + 50);
				}
			if (levelWon > 240) {
				g.setColor(new Color(0,0,0,255*(levelWon-241)/120));
				g.fillRect(-50, -50, Main.SIZE+50, Main.SIZE + 50);
			}
			if (levelWon>360) {
				if (!Main.completedLevels.contains(level)) Main.completedLevels.add(level);
				Main.jframe.exitGame(level);
				
			}
			
		}
		

		
		
	}
	
	public void moveCamera() {
		camera_x = player.x;
		camera_y = player.y;
		
		//camera_size = Main.SIZE*1;
		
		MainFrameObj.x = camera_x;
		MainFrameObj.y = camera_y;
		MainFrameObj.size_x = camera_size + 50;
		MainFrameObj.size_y = camera_size + 50;
		
		//temp
		
	}

	
	public class Keyboard extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_W) player.movingUp = true; //W
			if (e.getKeyCode() == KeyEvent.VK_A) player.movingLeft = true; //A
			if (e.getKeyCode() == KeyEvent.VK_S) player.movingDown = true; //S
			if (e.getKeyCode() == KeyEvent.VK_D) player.movingRight = true; //D
			
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE && levelWon == 0) isPaused = isPaused? false : true;
			
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
