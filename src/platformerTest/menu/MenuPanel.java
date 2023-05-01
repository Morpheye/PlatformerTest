package platformerTest.menu;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import platformerTest.Main;
import platformerTest.appdata.DataManager;
import platformerTest.game.GameObject;
import platformerTest.levels.Level;
import platformerTest.levels.LevelWorld;
import platformerTest.levels.world1.World1;

@SuppressWarnings("serial")
public class MenuPanel extends JPanel {
	
	public static ArrayList<LevelWorld> worlds;
	
	public static LevelWorld levelWorld;
	public static Timer timer;
	public Image lockImage;
	
	public MenuPanel(LevelWorld levelworld) {
		worlds = new ArrayList<LevelWorld>();
		worlds.add(new World1());
		
		this.setName("Menu");
		levelWorld = levelworld;
		this.setBackground(levelWorld.menuBGColor);
		this.setSize(Main.SIZE, Main.SIZE);
		this.setVisible(true);
		this.setFocusable(true);
		this.addMouseListener(new MenuMouse());
		try {
			this.lockImage = ImageIO.read(this.getClass().getResource("/gui/lock.png"));
		} catch (Exception e) {}
		
		timer = new Timer(1000/30, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
		}});
		
		timer.start();
		
	}
	
	int imgR = 75;
	int[] levelSlotsX = new int[] {Main.SIZE/4, Main.SIZE/2, Main.SIZE*3/4,
									Main.SIZE/4, Main.SIZE/2, Main.SIZE*3/4,
									Main.SIZE/4, Main.SIZE/2, Main.SIZE*3/4};
	int[] levelSlotsY = new int[] {Main.SIZE/4-20, Main.SIZE/4-20, Main.SIZE/4-20,
									Main.SIZE/2-20, Main.SIZE/2-20, Main.SIZE/2-20,
									Main.SIZE*3/4-20, Main.SIZE*3/4-20, Main.SIZE*3/4-20};
	
	int buttonSizeX=200;
	int buttonSizeY=50;
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		//paint bg
		
		Graphics2D g2d = (Graphics2D) g;
		
		Font font = new Font(Font.MONOSPACED, Font.BOLD, 50);
		g2d.setFont(font);
		g2d.setColor(Color.WHITE);
		int lvlSelectStringWidth = g2d.getFontMetrics(font).stringWidth("Level Select");
		g2d.drawString("Level Select", Main.SIZE/2 - lvlSelectStringWidth/2, 60);
		
		for (int i=0; i<9; i++) {
			try {
				if (levelWorld.levels.size()-1 < i) break;
				Level level = levelWorld.levels.get(i);
				String lvlName = level.getClass().getSimpleName().substring(6);
				BufferedImage image = ImageIO.read(this.getClass().getResource("/levels/"+lvlName+".png"));
				g2d.drawImage(image, levelSlotsX[i]-imgR, levelSlotsY[i]-imgR, 2*imgR, 2*imgR, null);
				
				if (DataManager.saveData.completedLevels.containsKey(level.getClass().getSimpleName())) {
					int completions = DataManager.saveData.completedLevels.get(level.getClass().getSimpleName());
					if (completions < 5) {
						g2d.setColor(GameObject.COLOR_COPPER);
					} else if (completions < 10) {
						g2d.setColor(GameObject.COLOR_SILVER);
					} else if (completions < 20) {
						g2d.setColor(GameObject.COLOR_GOLD);
					} else {
						g2d.setColor(Color.CYAN);
					}
				} else {
					g2d.setColor(Color.WHITE);
				}
				
				g2d.setStroke(new BasicStroke(5));
				g2d.drawRoundRect(levelSlotsX[i]-imgR, levelSlotsY[i]-imgR, 2*imgR, 2*imgR, 5, 5);
				
				if (!DataManager.saveData.completedLevels.keySet().containsAll(Arrays.asList(level.reqs))) {
					g2d.setColor(new Color(100, 100, 100, 200));
					g2d.fillRect(levelSlotsX[i]-imgR, levelSlotsY[i]-imgR, 2*imgR, 2*imgR);
					g2d.setColor(Color.WHITE);
					g2d.setStroke(new BasicStroke(5));
					g2d.drawRoundRect(levelSlotsX[i]-imgR, levelSlotsY[i]-imgR, 2*imgR, 2*imgR, 5, 5);
					
					g2d.drawImage(lockImage, levelSlotsX[i]-50, levelSlotsY[i]-50, 100, 100, null);
					continue;
				}
				
				font = new Font(Font.MONOSPACED, Font.BOLD, 50);
				g2d.setFont(font);
				g2d.setColor(Color.WHITE);
				int lvlTitleWidth = g2d.getFontMetrics(font).stringWidth(lvlName);
				int lvlTitleHeight = g2d.getFontMetrics(font).getHeight();
				g2d.drawString(lvlName, levelSlotsX[i]-(lvlTitleWidth/2), levelSlotsY[i]);
				
				List<String> lvlTextA = Arrays.asList(level.name.split(" "));
				font = new Font(Font.MONOSPACED, Font.BOLD, 10);
				g2d.setFont(font);
				lvlTitleWidth = g2d.getFontMetrics(font).stringWidth(String.join(" ", lvlTextA));
				lvlTitleHeight = g2d.getFontMetrics(font).getHeight();
				
				if (lvlTitleWidth > (2*imgR-10)) {
					List<String> text1 = lvlTextA.subList(0, lvlTextA.size()/2);
					List<String> text2 = lvlTextA.subList(lvlTextA.size()/2, lvlTextA.size());
					
					lvlTitleWidth = g2d.getFontMetrics(font).stringWidth(String.join(" ", text1));
					g2d.drawString(String.join(" ", text1), levelSlotsX[i]-(lvlTitleWidth/2), levelSlotsY[i]+50);
					lvlTitleWidth = g2d.getFontMetrics(font).stringWidth(String.join(" ", text2));
					g2d.drawString(String.join(" ", text2), levelSlotsX[i]-(lvlTitleWidth/2), levelSlotsY[i]+50+lvlTitleHeight);
					
				} else {
					g2d.drawString(String.join(" ", lvlTextA), levelSlotsX[i]-(lvlTitleWidth/2), levelSlotsY[i]+50);
					
				}
				
			} catch (Exception e) { 
			}
		}
		
		g2d.setColor(Color.gray);
		g2d.fillRoundRect(Main.SIZE/2-buttonSizeX/2, Main.SIZE*8/9-buttonSizeY/2, buttonSizeX, buttonSizeY, 5, 5);
		
		//return to main menu
		font = new Font(Font.MONOSPACED, Font.BOLD, 40);
		g2d.setFont(font);
		g2d.setColor(Color.WHITE);
		int lvlTitleWidth = g2d.getFontMetrics(font).stringWidth("Back");
		int lvlTitleHeight = g2d.getFontMetrics(font).getHeight();
		g2d.drawString("Back", Main.SIZE/2-(lvlTitleWidth/2), Main.SIZE*8/9+10);

		g2d.drawRoundRect(Main.SIZE/2-buttonSizeX/2, Main.SIZE*8/9-buttonSizeY/2, buttonSizeX, buttonSizeY, 5, 5);
		
		
		
		//now check mouse
		if (this.getMousePosition() != null) {
			int mouseX = this.getMousePosition().x;
			int mouseY = this.getMousePosition().y;
			
			for (int i=0; i<9; i++) {
				if (Math.abs(mouseX - levelSlotsX[i]) < imgR && Math.abs(mouseY - levelSlotsY[i]) < imgR) {
					if (levelWorld.levels.size()-1 < i) break;
					
					Level level = levelWorld.levels.get(i);
					if (!DataManager.saveData.completedLevels.keySet().containsAll(Arrays.asList(level.reqs))) continue;
					
					g2d.setColor(new Color(255, 255, 255, 100));
					g2d.fillRect(levelSlotsX[i]-imgR, levelSlotsY[i]-imgR, 2*imgR, 2*imgR);
					
					if (DataManager.saveData.completedLevels.containsKey(level.getClass().getSimpleName())) {
						int completions = DataManager.saveData.completedLevels.get(level.getClass().getSimpleName());
						if (completions < 5) {
							g2d.setColor(GameObject.COLOR_COPPER);
						} else if (completions < 10) {
							g2d.setColor(GameObject.COLOR_SILVER);
						} else if (completions < 20) {
							g2d.setColor(GameObject.COLOR_GOLD);
						} else {
							g2d.setColor(Color.CYAN);
						}
					} else {
						g2d.setColor(Color.WHITE);
					}
					
					g2d.setStroke(new BasicStroke(5));
					g2d.drawRoundRect(levelSlotsX[i]-imgR, levelSlotsY[i]-imgR, 2*imgR, 2*imgR, 5, 5);
					break;
				}
			}
			
			if (Math.abs(mouseX - Main.SIZE/2) < buttonSizeX/2 && Math.abs(mouseY - Main.SIZE*8/9) < buttonSizeY/2) {
				g2d.setColor(new Color(255, 255, 255, 100));
				g2d.fillRect(Main.SIZE/2-buttonSizeX/2, Main.SIZE*8/9-buttonSizeY/2, buttonSizeX, buttonSizeY);
				
				g2d.setColor(Color.WHITE);
				g2d.setStroke(new BasicStroke(5));
				g2d.drawRoundRect(Main.SIZE/2-buttonSizeX/2, Main.SIZE*8/9-buttonSizeY/2, buttonSizeX, buttonSizeY, 5, 5);
			}
			
		}
		
	}
	
	public class MenuMouse extends MouseAdapter {
		
		
		@Override
		public void mouseClicked(MouseEvent e) {
			int mouseX = e.getX();
			int mouseY = e.getY();
			for (int i=0; i<9; i++) {
				if (levelWorld.levels.size()-1 < i) break;
				
				Level level = levelWorld.levels.get(i);
				if (!DataManager.saveData.completedLevels.keySet().containsAll(Arrays.asList(level.reqs))) continue;
				
				if (Math.abs(mouseX - levelSlotsX[i]) < imgR && Math.abs(mouseY - levelSlotsY[i]) < imgR) {
					if (levelWorld.levels.size()-1 < i) break;
					
					timer.stop();
					Main.jframe.startGame(levelWorld.levels.get(i));
					
					break;
				}
			}
			
			if (Math.abs(mouseX - Main.SIZE/2) < buttonSizeX/2 && Math.abs(mouseY - Main.SIZE*8/9) < buttonSizeY/2) {
				timer.stop();
				Main.jframe.openMainMenu();
			}
			
		}
	}
	
}
