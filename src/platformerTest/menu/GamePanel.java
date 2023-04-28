package platformerTest.menu;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

import platformerTest.Main;
import platformerTest.game.GameObject;
import platformerTest.game.Player;
import platformerTest.levels.Level;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {
	
	public static Player player;
	public static Level level;
	public static double airDrag;
	public static double gravity;
	public static List<GameObject> objects;
	public static List<GameObject> deletedObjects;
	
	public static double camera_x;
	public static double camera_y;
	public static int camera_size;
	public static int target_camera_size;
	
	public static boolean isPaused;
	public static int levelWon;
	
	public static double checkpointX;
	public static double checkpointY;

	public static final GameObject MainFrameObj = new GameObject(0, 0, Main.SIZE+50, Main.SIZE+50, null);
	public static Timer timer;
	
	public GamePanel(Level level) {
		
		this.setBackground(Color.BLACK);
		this.setSize(Main.SIZE, Main.SIZE);
		this.setVisible(true);
		this.setFocusable(true);
		this.addKeyListener(new Keyboard());
		this.addMouseListener(new PauseMenuMouse());
		
		checkpointX = level.spawnX;
		checkpointY = level.spawnY;
		
		restartLevel(level);
		
		timer = new Timer(1000/120, new ActionListener() {
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
		deletedObjects = new ArrayList<GameObject>();
		flashes = new HashMap<Color,Integer>();
		
		level = newLevel;
		
		level.drawBackground();
		
		player = new Player(checkpointX, checkpointY, 40);
		objects.add(player);
		player.health = 10;
		
		camera_x = (int) player.x;
		camera_y = (int) player.y;
		camera_size = Main.SIZE;
		target_camera_size = Main.SIZE;
		
		level.drawForeground();
	
		airDrag = level.airDrag;
		gravity = level.gravity;
		
		level.drawPlatforms();
		
		level.onStart();
		
		createFlash(Color.BLACK,100);

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
		
		for (GameObject obj : deletedObjects) {
			objects.remove(obj);
		}
		
		deletedObjects.clear();
		
		level.drawAmbience(g);
		
		//flash effects
		ArrayList<Color> removedFlashes = new ArrayList<Color>();
		flashes.forEach((Color c, Integer i) -> {
			int alpha = (i > 255) ? 255 : i;
			Color newColor = new Color(c.getRed(), c.getGreen(), c.getBlue(), alpha);
			g.setColor(newColor);
			g.fillRect(-50, -50, Main.SIZE+50, Main.SIZE + 50);
			if (i<2) removedFlashes.add(c);
			else flashes.replace(c, i, i-1);
			
		});
		for (Color c : removedFlashes) flashes.remove(c);
		
		//fade out upon reaching bottom of level
		if (player.y < (level.bottomLimit + 1000) && levelWon == 0) {
			int alpha = (int)(player.y - level.bottomLimit)* 255/1000;
			if (alpha < 0) alpha = 0;
			g.setColor(new Color(0,0,0,255-alpha));
			g.fillRect(-50, -50, Main.SIZE+50, Main.SIZE + 50);
		} else if (player.y > (level.topLimit - 1000) && levelWon == 0) {
			int alpha = -(int)(player.y - level.topLimit)* 255/1000;
			if (alpha < 0) alpha = 0;
			g.setColor(new Color(0,0,0,255-alpha));
			g.fillRect(-50, -50, Main.SIZE+50, Main.SIZE + 50);
		}
		
		//display text
		if (textDuration != 0) {
			int alpha = 255;
			if (textDuration <= 120) alpha = 255*textDuration/120;
			Font font = new Font(Font.MONOSPACED, Font.BOLD, 25);
			g.setFont(font);
			g.setColor(new Color(255,255,255,alpha));
			int lvlSelectStringWidth = g.getFontMetrics(font).stringWidth(displayText);
			g.drawString(displayText, Main.SIZE/2 - lvlSelectStringWidth/2, Main.SIZE-50);
			
			textDuration--;
			
		}
		
		//pause menu
		if (isPaused) {
			drawPauseMenu(g);
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
				if (!Main.completedLevels.containsKey(level.getClass().getSimpleName())) {
					Main.completedLevels.put(level.getClass().getSimpleName(), 1);
				} else {
					Main.completedLevels.replace(level.getClass().getSimpleName(),
					Main.completedLevels.get(level.getClass().getSimpleName())+1);
				}
				timer.stop();
				Main.jframe.exitGame(level);
				
			}
			
		}
	}
	
	int buttonSizeX=400;
	int buttonSizeY=100;
	
	public void drawPauseMenu(Graphics g) {
		g.setColor(new Color(0,0,0,200));
		g.fillRect(-50, -50, Main.SIZE+50, Main.SIZE + 50);
		
		Font font = new Font(Font.MONOSPACED, Font.BOLD, 50);
		g.setFont(font);
		g.setColor(Color.WHITE);
		int lvlSelectStringWidth = g.getFontMetrics(font).stringWidth("Game Paused");
		g.drawString("Game Paused", Main.SIZE/2 - lvlSelectStringWidth/2, 75);
		
		Graphics2D g2d = (Graphics2D) g;
		font = new Font(Font.MONOSPACED, Font.BOLD, 40);
		g.setFont(font);
		
		g2d.setColor(Color.GRAY);
		g2d.fillRoundRect(Main.SIZE/2-buttonSizeX/2, Main.SIZE/3-buttonSizeY/2, buttonSizeX, buttonSizeY, 5, 5);
		
		g2d.setColor(Color.GRAY);
		g2d.fillRoundRect(Main.SIZE/2-buttonSizeX/2, Main.SIZE*2/3-buttonSizeY/2, buttonSizeX, buttonSizeY, 5, 5);
		
		if (this.getMousePosition() != null) {
			int mouseX = this.getMousePosition().x;
			int mouseY = this.getMousePosition().y;
			
			if (Math.abs(mouseX - Main.SIZE/2) < buttonSizeX/2 && Math.abs(mouseY - Main.SIZE/3) < buttonSizeY/2) {
				g2d.setColor(new Color(255, 255, 255, 100));
				g2d.fillRect(Main.SIZE/2-buttonSizeX/2, Main.SIZE/3-buttonSizeY/2, buttonSizeX, buttonSizeY);
			}
			
			if (Math.abs(mouseX - Main.SIZE/2) < buttonSizeX/2 && Math.abs(mouseY - Main.SIZE*2/3) < buttonSizeY/2) {
				g2d.setColor(new Color(255, 255, 255, 100));
				g2d.fillRect(Main.SIZE/2-buttonSizeX/2, Main.SIZE*2/3-buttonSizeY/2, buttonSizeX, buttonSizeY);
			}
			
			
		}
		
		g2d.setStroke(new BasicStroke(5));
		g2d.setColor(Color.WHITE);
		g.drawRoundRect(Main.SIZE/2-buttonSizeX/2, Main.SIZE/3-buttonSizeY/2, buttonSizeX, buttonSizeY, 5, 5);
		
		int StringWidth = g.getFontMetrics(font).stringWidth("Unpause Game");
		int StringHeight = g.getFontMetrics(font).getHeight();
		g2d.drawString("Unpause Game", Main.SIZE/2-StringWidth/2, Main.SIZE/3+10);
		
		g2d.setStroke(new BasicStroke(5));
		g2d.setColor(Color.WHITE);
		g.drawRoundRect(Main.SIZE/2-buttonSizeX/2, Main.SIZE*2/3-buttonSizeY/2, buttonSizeX, buttonSizeY, 5, 5);
		
		StringWidth = g.getFontMetrics(font).stringWidth("Exit to Menu");
		g2d.drawString("Exit to Menu", Main.SIZE/2-StringWidth/2, Main.SIZE*2/3+10);
		
		
		
	}
	
	public void moveCamera() {
		level.moveCamera();
		
		MainFrameObj.x = camera_x;
		MainFrameObj.y = camera_y;
		MainFrameObj.size_x = camera_size + 50;
		MainFrameObj.size_y = camera_size + 50;
		
		//smooth movement
		if (Math.abs(camera_size - target_camera_size) < 10) camera_size = target_camera_size;
		if (camera_size > target_camera_size + 100) camera_size = camera_size - 10;
		else if (camera_size > target_camera_size) camera_size = camera_size - 5;
		else if (camera_size < target_camera_size - 100) camera_size = camera_size + 10;
		else if (camera_size < target_camera_size) camera_size = camera_size + 5;
		
	}

	public static String displayText = null;
	public static int textDuration = 0;
	
	public static void displayText(String newText, int newDuration) {
		displayText = newText;
		textDuration = newDuration;
	}
	
	public static HashMap<Color,Integer> flashes = new HashMap<Color,Integer>();
	
	public static void createFlash(Color color, int duration) {
		if (flashes.containsKey(color) && flashes.get(color) < duration) flashes.replace(color, duration);
		else flashes.put(color, duration);
	}
	
	public class Keyboard extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_W) player.movingUp = true; //W
			if (e.getKeyCode() == KeyEvent.VK_A) player.movingLeft = true; //A
			if (e.getKeyCode() == KeyEvent.VK_S) player.movingDown = true; //S
			if (e.getKeyCode() == KeyEvent.VK_D) player.movingRight = true; //D
			
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE && levelWon == 0 && player.isAlive) isPaused = isPaused? false : true;
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_W) player.movingUp = false; //W
			if (e.getKeyCode() == KeyEvent.VK_A) player.movingLeft = false; //A
			if (e.getKeyCode() == KeyEvent.VK_S) player.movingDown = false; //S
			if (e.getKeyCode() == KeyEvent.VK_D) player.movingRight = false; //D
			
			
		}
	}
	
	public class PauseMenuMouse extends MouseAdapter {
		
		@Override
		public void mouseClicked(MouseEvent e) {
			if (!isPaused) return;
			int mouseX = e.getX();
			int mouseY = e.getY();
			
			if (Math.abs(mouseX - Main.SIZE/2) < buttonSizeX/2 && Math.abs(mouseY - Main.SIZE/3) < buttonSizeY/2) {
				isPaused = false;
			}
			
			if (Math.abs(mouseX - Main.SIZE/2) < buttonSizeX/2 && Math.abs(mouseY - Main.SIZE*2/3) < buttonSizeY/2) {
				timer.stop();
				Main.jframe.exitGame(level);
			}
			
		}
	}
	
}
