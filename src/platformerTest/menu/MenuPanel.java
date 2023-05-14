package platformerTest.menu;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.text.DecimalFormat;
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
import platformerTest.weapons.Weapon;

@SuppressWarnings("serial")
public class MenuPanel extends JPanel {
	public LevelWorld levelWorld;
	public Timer timer;
	int scroll = 0;
	
	public MenuPanel(LevelWorld levelworld) {
		
		this.setName("Menu");
		this.scroll = 0;
		this.levelWorld = LevelWorld.levelWorlds.get(this.scroll);
		this.setBackground(levelWorld.menuBGColor);
		this.setSize(Main.SIZE, Main.SIZE);
		this.setVisible(true);
		this.setFocusable(true);
		this.addMouseListener(new MenuMouse());
		try {
			this.lockImage = ImageIO.read(this.getClass().getResource("/gui/lock.png"));
			this.coinImage = ImageIO.read(this.getClass().getResource("/gui/goldcoin.png"));
			this.gemImage = ImageIO.read(this.getClass().getResource("/gui/gem.png"));
		} catch (Exception e) {}
		
		loadLevelImages();
		
		timer = new Timer(1000/30, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
		}});
		
		timer.start();
		
	}
	
	final int imgR = 75;
	final int[] levelSlotsX = new int[] {Main.SIZE/4, Main.SIZE/2, Main.SIZE*3/4,
									Main.SIZE/4, Main.SIZE/2, Main.SIZE*3/4,
									Main.SIZE/4, Main.SIZE/2, Main.SIZE*3/4};
	final int[] levelSlotsY = new int[] {Main.SIZE/4-20, Main.SIZE/4-20, Main.SIZE/4-20,
									Main.SIZE/2-20, Main.SIZE/2-20, Main.SIZE/2-20,
									Main.SIZE*3/4-20, Main.SIZE*3/4-20, Main.SIZE*3/4-20};
	
	BufferedImage[] lvlImages = new BufferedImage[9];
	
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
				g2d.drawImage(lvlImages[i], levelSlotsX[i]-imgR, levelSlotsY[i]-imgR, 2*imgR, 2*imgR, null);
				
				if (DataManager.saveData.completedLevels.containsKey(level.getClass().getSimpleName())) {
					int completions = DataManager.saveData.completedLevels.get(level.getClass().getSimpleName());
					if (completions < 5) {
						g2d.setColor(GameObject.COLOR_COPPER);
					} else if (completions < 10) {
						g2d.setColor(GameObject.COLOR_SILVER);
					} else if (completions < 20) {
						g2d.setColor(GameObject.COLOR_GOLD);
					} else if (completions < 50) {
						g2d.setColor(GameObject.COLOR_DIAMOND);
					} else {
						g2d.setColor(GameObject.COLOR_CRIMSONADE);
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
		
		//return to main menu
		g2d.setColor(Color.gray);
		g2d.fillRoundRect(Main.SIZE*1/6-buttonSizeX/2, Main.SIZE*8/9-buttonSizeY/2, buttonSizeX, buttonSizeY, 5, 5);
		font = new Font(Font.MONOSPACED, Font.BOLD, 40);
		g2d.setFont(font);
		g2d.setColor(Color.WHITE);
		int lvlTitleWidth = g2d.getFontMetrics(font).stringWidth("Back");
		int lvlTitleHeight = g2d.getFontMetrics(font).getHeight();
		g2d.drawString("Back", Main.SIZE*1/6-(lvlTitleWidth/2), Main.SIZE*8/9+10);

		g2d.drawRoundRect(Main.SIZE*1/6-buttonSizeX/2, Main.SIZE*8/9-buttonSizeY/2, buttonSizeX, buttonSizeY, 5, 5);
		
		//weapons
		g2d.setColor(Color.gray);
		g2d.fillRoundRect(Main.SIZE*5/6-buttonSizeX/2, Main.SIZE*8/9-buttonSizeY/2, buttonSizeX, buttonSizeY, 5, 5);
		font = new Font(Font.MONOSPACED, Font.BOLD, 40);
		g2d.setFont(font);
		g2d.setColor(Color.WHITE);
		lvlTitleWidth = g2d.getFontMetrics(font).stringWidth("Weapons");
		lvlTitleHeight = g2d.getFontMetrics(font).getHeight();
		g2d.drawString("Weapons", Main.SIZE*5/6-(lvlTitleWidth/2), Main.SIZE*8/9+10);

		g2d.drawRoundRect(Main.SIZE*5/6-buttonSizeX/2, Main.SIZE*8/9-buttonSizeY/2, buttonSizeX, buttonSizeY, 5, 5);
		
		
		//now check mouse
		Point mousePosition = this.getMousePosition();
		if (mousePosition != null) {
			int mouseX = mousePosition.x;
			int mouseY = mousePosition.y;
			
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
						} else if (completions < 50) {
							g2d.setColor(GameObject.COLOR_DIAMOND);
						} else {
							g2d.setColor(GameObject.COLOR_CRIMSONADE);
						}
					} else {
						g2d.setColor(Color.WHITE);
					}
					
					g2d.setStroke(new BasicStroke(5));
					g2d.drawRoundRect(levelSlotsX[i]-imgR, levelSlotsY[i]-imgR, 2*imgR, 2*imgR, 5, 5);
					break;
				}
			}
			
			if (Math.abs(mouseX - Main.SIZE*1/6) < buttonSizeX/2 && Math.abs(mouseY - Main.SIZE*8/9) < buttonSizeY/2) {
				g2d.setColor(new Color(255, 255, 255, 100));
				g2d.fillRect(Main.SIZE*1/6-buttonSizeX/2, Main.SIZE*8/9-buttonSizeY/2, buttonSizeX, buttonSizeY);
				
				g2d.setColor(Color.WHITE);
				g2d.setStroke(new BasicStroke(5));
				g2d.drawRoundRect(Main.SIZE*1/6-buttonSizeX/2, Main.SIZE*8/9-buttonSizeY/2, buttonSizeX, buttonSizeY, 5, 5);
			}
			
			if (Math.abs(mouseX - Main.SIZE*5/6) < buttonSizeX/2 && Math.abs(mouseY - Main.SIZE*8/9) < buttonSizeY/2) {
				g2d.setColor(new Color(255, 255, 255, 100));
				g2d.fillRect(Main.SIZE*5/6-buttonSizeX/2, Main.SIZE*8/9-buttonSizeY/2, buttonSizeX, buttonSizeY);
				
				g2d.setColor(Color.WHITE);
				g2d.setStroke(new BasicStroke(5));
				g2d.drawRoundRect(Main.SIZE*5/6-buttonSizeX/2, Main.SIZE*8/9-buttonSizeY/2, buttonSizeX, buttonSizeY, 5, 5);
			}
			
		}

		drawCurrency(g2d);
		
		drawScrollButtons(g2d, mousePosition);
		
	}
	
	void drawCurrency(Graphics2D g2d) {
		DecimalFormat df = new DecimalFormat("#");
		df.setMaximumFractionDigits(2);
		
		BigDecimal coins = BigDecimal.valueOf(DataManager.saveData.coins);
		String coinText;
		if (coins.compareTo(BigDecimal.valueOf(1_000_000_000_000_000_000L)) == 1) {
			coinText = df.format(coins.divide(BigDecimal.valueOf(1_000_000_000_000_000_000L))) + "♚";} //Quintillion
		else if (coins.compareTo(BigDecimal.valueOf(1_000_000_000_000_000L)) == 1) {
			coinText = df.format(coins.divide(BigDecimal.valueOf(1_000_000_000_000_000L))) + "Q";} //Quadrillion
		else if (coins.compareTo(BigDecimal.valueOf(1_000_000_000_000L)) == 1) {
			coinText = df.format(coins.divide(BigDecimal.valueOf(1_000_000_000_000L))) + "T";} //Trillion
		else if (coins.compareTo(BigDecimal.valueOf(1_000_000_000L)) == 1) {
			coinText = df.format(coins.divide(BigDecimal.valueOf(1_000_000_000L))) + "B";} //Billion
		else if (coins.compareTo(BigDecimal.valueOf(1_000_000L)) == 1) {
			coinText = df.format(coins.divide(BigDecimal.valueOf(1_000_000L))) + "M";} //Million
		else if (coins.compareTo(BigDecimal.valueOf(1_000L)) == 1) {
			coinText = df.format(coins.divide(BigDecimal.valueOf(1_000L))) + "k";} //Thousand
		else coinText = df.format(coins);
		
		BigDecimal gems = BigDecimal.valueOf(DataManager.saveData.gems);
		String gemText;
		if (gems.compareTo(BigDecimal.valueOf(1_000_000_000_000_000_000L)) == 1) {
			gemText = df.format(gems.divide(BigDecimal.valueOf(1_000_000_000_000_000_000L))) + "♚";} //Quintillion
		else if (gems.compareTo(BigDecimal.valueOf(1_000_000_000_000_000L)) == 1) {
			gemText = df.format(gems.divide(BigDecimal.valueOf(1_000_000_000_000_000L))) + "Q";} //Quadrillion
		else if (gems.compareTo(BigDecimal.valueOf(1_000_000_000_000L)) == 1) {
			gemText = df.format(gems.divide(BigDecimal.valueOf(1_000_000_000_000L))) + "T";} //Trillion
		else if (gems.compareTo(BigDecimal.valueOf(1_000_000_000L)) == 1) {
			gemText = df.format(gems.divide(BigDecimal.valueOf(1_000_000_000L))) + "B";} //Billion
		else if (gems.compareTo(BigDecimal.valueOf(1_000_000L)) == 1) {
			gemText = df.format(gems.divide(BigDecimal.valueOf(1_000_000L))) + "M";} //Million
		else if (gems.compareTo(BigDecimal.valueOf(1_000L)) == 1) {
			gemText = df.format(gems.divide(BigDecimal.valueOf(1_000L))) + "k";} //Thousand
		else gemText = df.format(gems);
		
		g2d.setColor(GameObject.COLOR_GOLD);
		g2d.fillRoundRect(13, 13, 20, 34, 5, 5);
		g2d.fillRect(19, 13, 28, 34);
		g2d.setColor(new Color(30,30,30));
		g2d.fillRect(47,13,70,34);
		g2d.fillRoundRect(65,13,80,34,5,5);
		g2d.drawImage(coinImage, 15, 15, 30, 30, null);
		
		int diff = Main.SIZE*11/14;
		g2d.setColor(GameObject.COLOR_DIAMOND);
		g2d.fillRoundRect(13+diff, 13, 20, 34, 5, 5);
		g2d.fillRect(19+diff, 13, 28, 34);
		g2d.setColor(new Color(30,30,30));
		g2d.fillRect(47+diff,13,70,34);
		g2d.fillRoundRect(65+diff,13,80,34,5,5);
		g2d.drawImage(gemImage, 15+diff, 15, 30, 30, null);
		
		g2d.setColor(Color.WHITE);
		Font font = new Font(Font.MONOSPACED, Font.BOLD, 15);
		g2d.setFont(font);
		g2d.setColor(Color.WHITE);
		
		int coinTextWidth = g2d.getFontMetrics(font).stringWidth(coinText);
		int coinTextHeight = g2d.getFontMetrics(font).getHeight();
		g2d.drawString(coinText, 97-(coinTextWidth/2), 35);
		
		int gemTextWidth = g2d.getFontMetrics(font).stringWidth(gemText);
		int gemTextHeight = g2d.getFontMetrics(font).getHeight();
		g2d.drawString(gemText, 97+diff-(gemTextWidth/2), 35);
	}
	
	void drawScrollButtons(Graphics2D g2d, Point mousePosition) {
		int x1 = Main.SIZE/15, x2 = Main.SIZE*14/15, y = Main.SIZE/2-10, w = 50;
		
		if (scroll > 0) { //left button
			g2d.setColor(Color.gray);
			g2d.fillRoundRect(x1-w/2, y-w/2, w, w, 5, 5);
			g2d.setColor(Color.WHITE);
			g2d.setFont(new Font(Font.MONOSPACED, Font.BOLD, 40));
			g2d.drawString("<", x1-w/2+13, y+10);
			if (mousePosition != null) {
				int mouseX = mousePosition.x;
				int mouseY= mousePosition.y;
				if (Math.abs(mouseX - x1) < w/2 && Math.abs(mouseY - y) < w/2) {
					g2d.setColor(new Color(255,255,255,100));
					g2d.fillRoundRect(x1-w/2, y-w/2, w, w, 5, 5);
				}
			}
			g2d.setColor(Color.white);
			g2d.drawRoundRect(x1-w/2, y-w/2, w, w, 5, 5);
		}
		
		if (scroll < LevelWorld.levelWorlds.size()-1) {
			g2d.setColor(Color.gray);
			g2d.fillRoundRect(x2-w/2, y-w/2, w, w, 5, 5);
			g2d.setColor(Color.WHITE);
			g2d.setFont(new Font(Font.MONOSPACED, Font.BOLD, 40));
			g2d.drawString(">", x2-w/2+13, y+10);
			if (mousePosition != null) {
				int mouseX = mousePosition.x;
				int mouseY= mousePosition.y;
				if (Math.abs(mouseX - x2) < w/2 && Math.abs(mouseY - y) < w/2) {
					g2d.setColor(new Color(255,255,255,100));
					g2d.fillRoundRect(x2-w/2, y-w/2, w, w, 5, 5);
				}
			}
			g2d.setColor(Color.white);
			g2d.drawRoundRect(x2-w/2, y-w/2, w, w, 5, 5);
		}
		
	}
	
	class MenuMouse extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) { //level select
			int mouseX = e.getX();
			int mouseY = e.getY();
			for (int i=0; i<9; i++) {
				if (levelWorld.levels.size()-1 < i) break;
				
				Level level = levelWorld.levels.get(i);
				if (!DataManager.saveData.completedLevels.keySet().containsAll(Arrays.asList(level.reqs))) continue;
				if (Math.abs(mouseX - levelSlotsX[i]) < imgR && Math.abs(mouseY - levelSlotsY[i]) < imgR) {
					if (levelWorld.levels.size()-1 < i) break;
					
					timer.stop();
					destroy();
					Main.jframe.startGame(levelWorld.levels.get(i));
					
					break;
			}}
			//main menu button
			if (Math.abs(mouseX - Main.SIZE*1/6) < buttonSizeX/2 && Math.abs(mouseY - Main.SIZE*8/9) < buttonSizeY/2) {
				timer.stop();
				destroy();
				Main.jframe.openMainMenu();
			}
			//weapon menu button
			if (Math.abs(mouseX - Main.SIZE*5/6) < buttonSizeX/2 && Math.abs(mouseY - Main.SIZE*8/9) < buttonSizeY/2) {
				timer.stop();
				destroy();
				Main.jframe.openWeaponsMenu();
			}
			
			//scroll buttons
			int x1 = Main.SIZE/15, x2 = Main.SIZE*14/15, y = Main.SIZE/2-10, w = 50;
			if (scroll > 0) { //left button
				if (Math.abs(mouseX - x1) < w/2 && Math.abs(mouseY - y) < w/2) {
					scroll--;
					levelWorld = LevelWorld.levelWorlds.get(scroll);
					loadLevelImages();
				}
			}
			
			if (scroll < LevelWorld.levelWorlds.size()-1) {
				if (Math.abs(mouseX - x2) < w/2 && Math.abs(mouseY - y) < w/2) {
					scroll++;
					levelWorld = LevelWorld.levelWorlds.get(scroll);
					loadLevelImages();
				}
			}
			
			
		}
	}
	
	Image lockImage, coinImage, gemImage;
	
	void loadLevelImages() {
		try {
			for (int i=0; i<9; i++) {
				if (levelWorld.levels.size()-1 < i) break;
				
				Level level = levelWorld.levels.get(i);
				String lvlName = level.getClass().getSimpleName().substring(6);
				lvlImages[i] = ImageIO.read(this.getClass().getResource("/levels/"+lvlName+".png"));
				
			}
		} catch (Exception e) {}
	}
	
	void destroy() {
		
	}
	
}
