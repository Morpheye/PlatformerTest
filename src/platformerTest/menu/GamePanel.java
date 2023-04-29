package platformerTest.menu;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Arc2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import platformerTest.Main;
import platformerTest.game.Creature;
import platformerTest.game.GameObject;
import platformerTest.game.ObjType;
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
	public static double camera_size;
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
		
		camera_x = (int) player.x;
		camera_y = (int) player.y;
		camera_size = Main.SIZE;
		target_camera_size = Main.SIZE;
	
		airDrag = level.airDrag;
		gravity = level.gravity;
		
		level.drawPlatforms();
		level.drawForeground();
		
		level.onStart();
		
		createFlash(Color.white,100);

	}
	
	/**Order: Level tick -> Player move -> Camera move -> Background -> Level Paint -> draw attacks -> Draw ambience
	-> Create flash effects -> Fade out if falling into void -> draw HUD -> display text -> win fading
	**/
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
		
		//draw attacks
		if (!isPaused) drawAttacks(g);
		
		if (!isPaused) level.drawAmbience(g);
		
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
		
		//draw hud
		drawHUD(g);
		
		//display text
		if (textDuration != 0) {
			int alpha = 255;
			if (textDuration <= 120) alpha = 255*textDuration/120;
			Graphics2D g2d = (Graphics2D) g;
			GradientPaint gp2 = new GradientPaint(0, Main.SIZE-100, new Color(255,255,255,0), 0, Main.SIZE-75, new Color(255,255,255,alpha), false);
			g2d.setPaint(gp2);
			g2d.fillRect(-50, Main.SIZE-125, Main.SIZE+50, Main.SIZE);
			Font font = new Font(Font.MONOSPACED, Font.BOLD, 25);
			g.setFont(font);
			g.setColor(new Color(100,100,100,alpha));
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
		g2d.fillRoundRect(Main.SIZE/2-buttonSizeX/2, Main.SIZE/4-buttonSizeY/2, buttonSizeX, buttonSizeY, 5, 5);
		
		g2d.setColor(Color.GRAY);
		g2d.fillRoundRect(Main.SIZE/2-buttonSizeX/2, Main.SIZE/2-buttonSizeY/2, buttonSizeX, buttonSizeY, 5, 5);
		
		g2d.setColor(Color.GRAY);
		g2d.fillRoundRect(Main.SIZE/2-buttonSizeX/2, Main.SIZE*3/4-buttonSizeY/2, buttonSizeX, buttonSizeY, 5, 5);
		
		if (this.getMousePosition() != null) {
			int mouseX = this.getMousePosition().x;
			int mouseY = this.getMousePosition().y;
			
			if (Math.abs(mouseX - Main.SIZE/2) < buttonSizeX/2 && Math.abs(mouseY - Main.SIZE/4) < buttonSizeY/2) {
				g2d.setColor(new Color(255, 255, 255, 100));
				g2d.fillRect(Main.SIZE/2-buttonSizeX/2, Main.SIZE/4-buttonSizeY/2, buttonSizeX, buttonSizeY);
			}
			
			if (Math.abs(mouseX - Main.SIZE/2) < buttonSizeX/2 && Math.abs(mouseY - Main.SIZE/2) < buttonSizeY/2) {
				g2d.setColor(new Color(255, 255, 255, 100));
				g2d.fillRect(Main.SIZE/2-buttonSizeX/2, Main.SIZE/2-buttonSizeY/2, buttonSizeX, buttonSizeY);
			}
			
			if (Math.abs(mouseX - Main.SIZE/2) < buttonSizeX/2 && Math.abs(mouseY - Main.SIZE*3/4) < buttonSizeY/2) {
				g2d.setColor(new Color(255, 255, 255, 100));
				g2d.fillRect(Main.SIZE/2-buttonSizeX/2, Main.SIZE*3/4-buttonSizeY/2, buttonSizeX, buttonSizeY);
			}
			
			
		}
		
		g2d.setStroke(new BasicStroke(5));
		g2d.setColor(Color.WHITE);
		g.drawRoundRect(Main.SIZE/2-buttonSizeX/2, Main.SIZE/4-buttonSizeY/2, buttonSizeX, buttonSizeY, 5, 5);
		
		int StringWidth = g.getFontMetrics(font).stringWidth("Unpause Game");
		int StringHeight = g.getFontMetrics(font).getHeight();
		g2d.drawString("Unpause Game", Main.SIZE/2-StringWidth/2, Main.SIZE/4+10);
		
		g2d.setStroke(new BasicStroke(5));
		g2d.setColor(Color.WHITE);
		g.drawRoundRect(Main.SIZE/2-buttonSizeX/2, Main.SIZE/2-buttonSizeY/2, buttonSizeX, buttonSizeY, 5, 5);
		
		StringWidth = g.getFontMetrics(font).stringWidth("Restart Level");
		g2d.drawString("Restart Level", Main.SIZE/2-StringWidth/2, Main.SIZE/2+10);
		
		g2d.setStroke(new BasicStroke(5));
		g2d.setColor(Color.WHITE);
		g.drawRoundRect(Main.SIZE/2-buttonSizeX/2, Main.SIZE*3/4-buttonSizeY/2, buttonSizeX, buttonSizeY, 5, 5);
		
		StringWidth = g.getFontMetrics(font).stringWidth("Exit to Menu");
		g2d.drawString("Exit to Menu", Main.SIZE/2-StringWidth/2, Main.SIZE*3/4+10);
		
		
		
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
	
	public void drawHUD(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		//top
		GradientPaint gp1 = new GradientPaint(0, 50, Color.white, 0, 75, new Color(255,255,255,0), false);
		g2d.setPaint(gp1);
		g2d.fillRect(-50, 0, Main.SIZE+50, 75);
		//render healthbar
		if (player.health > 100) player.health = 100;
		if (player.health < 0) player.health = 0;
		try {
			BufferedImage image = ImageIO.read(this.getClass().getResource("/gui/health.png"));
			g2d.drawImage(image, Main.SIZE*3/4+25, 10, 30, 30, null);
			g2d.setColor(Color.black);
			g2d.fillRoundRect(Main.SIZE*3/4 + 70, 10, 100, 30, 5, 5);
			g2d.setColor(Color.red);
			g2d.fillRoundRect(Main.SIZE*3/4 + 70, 10, player.health, 30, 5, 5);
			
			if (player.overheal != 0) {
				int overHeal = player.overheal;
				if (player.overheal > 100) overHeal = 100;
				
				g2d.setColor(GameObject.COLOR_GOLD);
				g2d.fillRoundRect(Main.SIZE*3/4 + 70, 10, overHeal, 30, 5, 5);
				
			}
			
			g2d.setColor(Color.black);
			g2d.setStroke(new BasicStroke(3));
			g2d.drawRoundRect(Main.SIZE*3/4 + 70, 10, 100, 30, 5, 5);
		} catch (IOException e) {}

	}
	
	public static HashMap<Color,Integer> flashes = new HashMap<Color,Integer>();
	
	public static void createFlash(Color color, int duration) {
		if (flashes.containsKey(color) && flashes.get(color) < duration) flashes.replace(color, duration);
		else flashes.put(color, duration);
	}
	
	public void drawAttacks(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		for (GameObject obj : objects) {
			if (obj.type.equals(ObjType.Creature) && obj.hasCollided(MainFrameObj)) {
				Creature c = (Creature) obj;
				
				if (c.attackCooldown == 0) continue;
				if (c.maxAttackCooldown - c.attackCooldown > 20) continue;
				int alpha = (20-(c.maxAttackCooldown - c.attackCooldown))*255/20;
				
				int lastAttackX = (int) (c.x + c.lastAttackRange * Math.cos(c.lastAttackAngle * Math.PI/180));
				int lastAttackY = (int) (c.y + c.lastAttackRange * Math.sin(c.lastAttackAngle * Math.PI/180));
				
				int drawX = (int) ((lastAttackX - (c.size_x)/2 - (camera_x - camera_size/2)) * (Main.SIZE/camera_size));
				int drawY = (int) ((camera_size - (lastAttackY + (c.size_y)/2) + (camera_y - camera_size/2)) * (Main.SIZE/camera_size));
				int sizeX = (int) ((c.size_x) * Main.SIZE/camera_size);
				int sizeY = (int) ((c.size_y) * Main.SIZE/camera_size);
				
				Arc2D arc = new Arc2D.Double(drawX, drawY, sizeX, sizeY, c.lastAttackAngle-45, 90, Arc2D.OPEN);
				g2d.setColor(new Color(c.color.getRed(),c.color.getGreen(),c.color.getBlue(),alpha));
				g2d.setStroke(new BasicStroke((float) (5*Main.SIZE/camera_size)));
				g2d.draw(arc);
		}}
		
		if (player.attackCooldown == 0) return;
		if (player.maxAttackCooldown - player.attackCooldown > 20) return;
		
		int alpha = (20-(player.maxAttackCooldown - player.attackCooldown))*255/20;
		
		int lastAttackX = (int) (player.x + player.lastAttackRange * Math.cos(player.lastAttackAngle * Math.PI/180));
		int lastAttackY = (int) (player.y + player.lastAttackRange * Math.sin(player.lastAttackAngle * Math.PI/180));
		
		int drawX = (int) ((lastAttackX - (player.size_x)/2 - (camera_x - camera_size/2)) * (Main.SIZE/camera_size));
		int drawY = (int) ((camera_size - (lastAttackY + (player.size_y)/2) + (camera_y - camera_size/2)) * (Main.SIZE/camera_size));
		int sizeX = (int) ((player.size_x) * Main.SIZE/camera_size);
		int sizeY = (int) ((player.size_y) * Main.SIZE/camera_size);
		
		Arc2D arc = new Arc2D.Double(drawX, drawY, sizeX, sizeY, player.lastAttackAngle-45, 90, Arc2D.OPEN);
		g2d.setColor(new Color(player.color.getRed(),player.color.getGreen(),player.color.getBlue(),alpha));
		g2d.setStroke(new BasicStroke((float) (5*Main.SIZE/camera_size)));
		g2d.draw(arc);

		
		
	}
	
	public class Keyboard extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE && levelWon == 0 && player.isAlive) isPaused = isPaused? false : true;
			if (isPaused) return;
			if (e.getKeyCode() == KeyEvent.VK_SPACE) player.isAttacking = true; //SPACE
			if (e.getKeyCode() == KeyEvent.VK_W) player.movingUp = true; //W
			if (e.getKeyCode() == KeyEvent.VK_A) {
				player.movingLeft = true; //A
				player.lastDirection = -1;
			}
			if (e.getKeyCode() == KeyEvent.VK_S) player.movingDown = true; //S
			if (e.getKeyCode() == KeyEvent.VK_D) {
				player.movingRight = true; //D
				player.lastDirection = 1;
			}
			
			if (e.getKeyCode() == KeyEvent.VK_R && levelWon == 0) GamePanel.restartLevel(level); 
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_SPACE) player.isAttacking = false;
			if (e.getKeyCode() == KeyEvent.VK_W) player.movingUp = false; //W
			if (e.getKeyCode() == KeyEvent.VK_A) player.movingLeft = false;
			if (e.getKeyCode() == KeyEvent.VK_S) player.movingDown = false; //S
			if (e.getKeyCode() == KeyEvent.VK_D) player.movingRight = false;
	

		}
	}
	
	public class PauseMenuMouse extends MouseAdapter {
		
		@Override
		public void mouseClicked(MouseEvent e) {
			if (!isPaused) return;
			int mouseX = e.getX();
			int mouseY = e.getY();
			
			if (Math.abs(mouseX - Main.SIZE/2) < buttonSizeX/2 && Math.abs(mouseY - Main.SIZE/4) < buttonSizeY/2) {
				isPaused = false;
			}
			
			if (Math.abs(mouseX - Main.SIZE/2) < buttonSizeX/2 && Math.abs(mouseY - Main.SIZE/2) < buttonSizeY/2) {
				isPaused = false;
				GamePanel.restartLevel(level);
			}
			
			if (Math.abs(mouseX - Main.SIZE/2) < buttonSizeX/2 && Math.abs(mouseY - Main.SIZE*3/4) < buttonSizeY/2) {
				timer.stop();
				Main.jframe.exitGame(level);
			}
			
		}
	}
	
}
