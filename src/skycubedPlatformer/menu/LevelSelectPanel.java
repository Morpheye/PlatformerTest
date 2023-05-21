package skycubedPlatformer.menu;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import skycubedPlatformer.Main;
import skycubedPlatformer.game.GameObject;
import skycubedPlatformer.levels.Level;
import skycubedPlatformer.levels.LevelWorld;
import skycubedPlatformer.util.ImageHelper;
import skycubedPlatformer.util.Screenshot;
import skycubedPlatformer.util.appdata.DataManager;

@SuppressWarnings("serial")
public class LevelSelectPanel extends JPanel {
	public LevelWorld levelWorld;
	public Timer timer;
	
	public static int scroll = 0;
	
	public LevelSelectPanel() {
		if (scroll < 0) scroll = 0;
		if (scroll > LevelWorld.levelWorlds.size()-1) scroll = LevelWorld.levelWorlds.size()-1;
		
		this.setName("Menu");
		this.levelWorld = LevelWorld.levelWorlds.get(scroll);
		this.setBackground(Color.black);
		this.setSize(Main.SIZE, Main.SIZE);
		this.setVisible(true);
		this.setFocusable(true);
		this.addMouseListener(new MenuMouse());
		this.addKeyListener(new MenuKeyboard());
		
		try {
			this.lockImage = ImageIO.read(this.getClass().getResource("/gui/lock.png"));
			this.coinImage = ImageIO.read(this.getClass().getResource("/gui/goldcoin.png"));
			this.gemImage = ImageIO.read(this.getClass().getResource("/gui/gem.png"));
		} catch (Exception e) {}
		
		loadLevelImages();
		screenshotTime = 0;
		
		timer = new Timer(1000/30, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
		}});
		
		timer.start();
		
	}
	
	final int imgR = (int) (75*(Main.SIZE/800.0));
	final int[] levelSlotsX = new int[] {Main.SIZE/4, Main.SIZE/2, Main.SIZE*3/4,
									Main.SIZE/4, Main.SIZE/2, Main.SIZE*3/4,
									Main.SIZE/4, Main.SIZE/2, Main.SIZE*3/4};
	final int[] levelSlotsY = new int[] {Main.SIZE/4-20, Main.SIZE/4-20, Main.SIZE/4-20,
									Main.SIZE/2-20, Main.SIZE/2-20, Main.SIZE/2-20,
									Main.SIZE*3/4-20, Main.SIZE*3/4-20, Main.SIZE*3/4-20};
	
	BufferedImage[] lvlImages = new BufferedImage[9];
	
	int buttonSizeX=(int) (200*(Main.SIZE/800.0));
	int buttonSizeY=(int) (50*(Main.SIZE/800.0));
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		//first paint background
		if (levelWorld.bg != null) {
			g.drawImage(levelWorld.bg, 0, 0, Main.SIZE, Main.SIZE, null);
			g.setColor(levelWorld.menuTint);
			g.fillRect(0, 0, Main.SIZE, Main.SIZE);
		}
		
		Graphics2D g2d = (Graphics2D) g;
		
		Font font = new Font(Font.MONOSPACED, Font.BOLD, (int) (50*(Main.SIZE/800.0)));
		g2d.setFont(font);
		g2d.setColor(Color.WHITE);
		int lvlSelectStringWidth = g2d.getFontMetrics(font).stringWidth("Level Select");
		g2d.drawString("Level Select", Main.SIZE/2 - lvlSelectStringWidth/2, (int) (45*(Main.SIZE/800.0)));
		
		font = new Font(Font.MONOSPACED, Font.BOLD, (int) (25*(Main.SIZE/800.0)));
		g2d.setFont(font);
		lvlSelectStringWidth = g2d.getFontMetrics(font).stringWidth(levelWorld.name);
		g2d.drawString(levelWorld.name, Main.SIZE/2 - lvlSelectStringWidth/2, (int) (75*(Main.SIZE/800.0)));
		
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
					
					g2d.drawImage(lockImage, levelSlotsX[i]-(int)(50*(Main.SIZE/800.0)), levelSlotsY[i]-(int)(50*(Main.SIZE/800.0)), 
							(int) (100*(Main.SIZE/800.0)), (int) (100*(Main.SIZE/800.0)), null);
					continue;
				}
				
				font = new Font(Font.MONOSPACED, Font.BOLD, (int) (50*(Main.SIZE/800.0)));
				g2d.setFont(font);
				g2d.setColor(Color.WHITE);
				int lvlTitleWidth = g2d.getFontMetrics(font).stringWidth(lvlName);
				int lvlTitleHeight = g2d.getFontMetrics(font).getHeight();
				g2d.drawString(lvlName, levelSlotsX[i]-(lvlTitleWidth/2), levelSlotsY[i]);
				
				List<String> lvlTextA = Arrays.asList(level.name.split(" "));
				font = new Font(Font.MONOSPACED, Font.BOLD, (int) (10*(Main.SIZE/800.0)));
				g2d.setFont(font);
				lvlTitleWidth = g2d.getFontMetrics(font).stringWidth(String.join(" ", lvlTextA));
				lvlTitleHeight = g2d.getFontMetrics(font).getHeight();
				
				if (lvlTitleWidth > (2*imgR-10)) {
					List<String> text1 = lvlTextA.subList(0, lvlTextA.size()/2);
					List<String> text2 = lvlTextA.subList(lvlTextA.size()/2, lvlTextA.size());
					
					lvlTitleWidth = g2d.getFontMetrics(font).stringWidth(String.join(" ", text1));
					g2d.drawString(String.join(" ", text1), levelSlotsX[i]-(lvlTitleWidth/2),
							(int) (levelSlotsY[i]+50*(Main.SIZE/800.0)));
					lvlTitleWidth = g2d.getFontMetrics(font).stringWidth(String.join(" ", text2));
					g2d.drawString(String.join(" ", text2), levelSlotsX[i]-(lvlTitleWidth/2),
							levelSlotsY[i]+(int)(50*(Main.SIZE/800.0))+lvlTitleHeight);
					
				} else {
					g2d.drawString(String.join(" ", lvlTextA), levelSlotsX[i]-(lvlTitleWidth/2),
							levelSlotsY[i]+(int) (50*(Main.SIZE/800.0)));
					
				}
				
			} catch (Exception e) { 
			}
		}
		
		//return to main menu
		g2d.setStroke(new BasicStroke(5));
		
		g2d.setColor(Color.gray);
		g2d.fillRoundRect(Main.SIZE*1/6-buttonSizeX/2, Main.SIZE*8/9-buttonSizeY/2, buttonSizeX, buttonSizeY, 5, 5);
		font = new Font(Font.MONOSPACED, Font.BOLD, (int) (40*(Main.SIZE/800.0)));
		g2d.setFont(font);
		g2d.setColor(Color.WHITE);
		int lvlTitleWidth = g2d.getFontMetrics(font).stringWidth("Back");
		int lvlTitleHeight = g2d.getFontMetrics(font).getHeight();
		g2d.drawString("Back", Main.SIZE*1/6-(lvlTitleWidth/2), Main.SIZE*8/9+10);

		g2d.drawRoundRect(Main.SIZE*1/6-buttonSizeX/2, Main.SIZE*8/9-buttonSizeY/2, buttonSizeX, buttonSizeY, 5, 5);
		
		//weapons
		g2d.setColor(Color.gray);
		g2d.fillRoundRect(Main.SIZE*5/6-buttonSizeX/2, Main.SIZE*8/9-buttonSizeY/2, buttonSizeX, buttonSizeY, 5, 5);
		font = new Font(Font.MONOSPACED, Font.BOLD, (int) (30*(Main.SIZE/800.0)));
		g2d.setFont(font);
		g2d.setColor(Color.WHITE);
		lvlTitleWidth = g2d.getFontMetrics(font).stringWidth("Inventory");
		lvlTitleHeight = g2d.getFontMetrics(font).getHeight();
		g2d.drawString("Inventory", Main.SIZE*5/6-(lvlTitleWidth/2), Main.SIZE*8/9+8);

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
		
		drawScreenshotEffect(g);
		if (screenshotTime > 0) screenshotTime--;
		
	}
	
	void drawCurrency(Graphics2D g2d) {
		String coinText = ImageHelper.formatCurrency(DataManager.saveData.coins);
		String gemText = ImageHelper.formatCurrency(DataManager.saveData.gems);
		
		int imgWidth = (int) (15*(Main.SIZE/800.0));
		int barHeight = (int) (34*(Main.SIZE/800.0));
		
		g2d.setColor(GameObject.COLOR_GOLD);
		g2d.fillRoundRect((int) (13*(Main.SIZE/800.0)), (int) (13*(Main.SIZE/800.0)), (int) (20*(Main.SIZE/800.0)), barHeight,5,5);
		g2d.fillRect((int) (19*(Main.SIZE/800.0)), (int) (13*(Main.SIZE/800.0)), (int) (28*(Main.SIZE/800.0)), barHeight);
		g2d.setColor(new Color(30,30,30));
		g2d.fillRect((int) (47*(Main.SIZE/800.0)),(int) (13*(Main.SIZE/800.0)), (int) (70*(Main.SIZE/800.0)),barHeight);
		g2d.fillRoundRect((int) (65*(Main.SIZE/800.0)),(int) (13*(Main.SIZE/800.0)),(int) (80*(Main.SIZE/800.0)), barHeight,5,5);
		g2d.drawImage(coinImage, imgWidth, imgWidth, 2*imgWidth, 2*imgWidth, null);
		
		int diff = Main.SIZE*11/14;
		g2d.setColor(GameObject.COLOR_DIAMOND);
		g2d.fillRoundRect((int)(13*(Main.SIZE/800.0))+diff, (int) (13*(Main.SIZE/800.0)), (int) (20*(Main.SIZE/800.0)), barHeight,5,5);
		g2d.fillRect((int)(19*(Main.SIZE/800.0))+diff, (int) (13*(Main.SIZE/800.0)), (int) (28*(Main.SIZE/800.0)), barHeight);
		g2d.setColor(new Color(30,30,30));
		g2d.fillRect((int)(47*(Main.SIZE/800.0))+diff,(int) (13*(Main.SIZE/800.0)),(int) (70*(Main.SIZE/800.0)),barHeight);
		g2d.fillRoundRect((int)(65*(Main.SIZE/800.0))+diff,(int) (13*(Main.SIZE/800.0)),(int) (80*(Main.SIZE/800.0)),barHeight,5,5);
		g2d.drawImage(gemImage, imgWidth+diff, imgWidth, 2*imgWidth, 2*imgWidth, null);
		
		g2d.setColor(Color.WHITE);
		Font font = new Font(Font.MONOSPACED, Font.BOLD, (int) (15*(Main.SIZE/800.0)));
		g2d.setFont(font);
		g2d.setColor(Color.WHITE);
		
		int coinTextWidth = g2d.getFontMetrics(font).stringWidth(coinText);
		int coinTextHeight = g2d.getFontMetrics(font).getHeight();
		g2d.drawString(coinText, (int)(97*(Main.SIZE/800.0))-(coinTextWidth/2), (int) (35*(Main.SIZE/800.0)));
		
		int gemTextWidth = g2d.getFontMetrics(font).stringWidth(gemText);
		int gemTextHeight = g2d.getFontMetrics(font).getHeight();
		g2d.drawString(gemText, (int)(97*(Main.SIZE/800.0))+diff-(gemTextWidth/2), (int) (35*(Main.SIZE/800.0)));
	}
	
	void drawScrollButtons(Graphics2D g2d, Point mousePosition) {
		int x1 = Main.SIZE/15, x2 = Main.SIZE*14/15, y = Main.SIZE/2-(int)(10*(Main.SIZE/800.0)), w = (int) (50*(Main.SIZE/800.0));
		
		if (scroll > 0 || (scroll > -1 && false)) { //left button
			g2d.setColor(Color.gray);
			g2d.fillRoundRect(x1-w/2, y-w/2, w, w, 5, 5);
			g2d.setColor(Color.WHITE);
			g2d.setFont(new Font(Font.MONOSPACED, Font.BOLD, (int) (40*(Main.SIZE/800.0))));
			g2d.drawString("<", x1-w/2+(int)(13*(Main.SIZE/800.0)), y+(int)(10*(Main.SIZE/800.0)));
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
			g2d.setFont(new Font(Font.MONOSPACED, Font.BOLD, (int) (40*(Main.SIZE/800.0))));
			g2d.drawString(">", x2-w/2+(int)(13*(Main.SIZE/800.0)), y+(int)(10*(Main.SIZE/800.0)));
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
	
	int screenshotTime = 0;
	public void drawScreenshotEffect(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,(float) (screenshotTime/25.0));
		g2d.setComposite(ac);
		g2d.drawImage(ImageHelper.screenshotImage, Main.SIZE/4, Main.SIZE/4, Main.SIZE/2, Main.SIZE/2, null);
	}
	
	class MenuKeyboard extends KeyAdapter { 
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_F2) {
				new Screenshot();
				screenshotTime = 20;
			}
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
			int x1 = Main.SIZE/15, x2 = Main.SIZE*14/15, y = Main.SIZE/2-(int)(10*(Main.SIZE/800.0)), w = (int) (50*(Main.SIZE/800.0));
			if (scroll > -1 || (scroll > -1 && false)) { //left button
				if (Math.abs(mouseX - x1) < w/2 && Math.abs(mouseY - y) < w/2) {
					scroll--;
					if (scroll == -1) {
						
					} else {
						levelWorld = LevelWorld.levelWorlds.get(scroll);
					}
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
		for (BufferedImage i : lvlImages) if (i != null) i.flush();
		lvlImages = new BufferedImage[9];
		
		try {
			for (int i=0; i<9; i++) {
				if (levelWorld.levels.size()-1 < i) break;
				
				Level level = levelWorld.levels.get(i);
				String worldName = levelWorld.getClass().getSimpleName();
				String lvlName = level.getClass().getSimpleName().substring(6);
				lvlImages[i] = ImageIO.read(this.getClass().getResource("/levels/"+worldName+"/"+lvlName+".png"));
				
			}
		} catch (Exception e) {}
	}
	
	void destroy() {
		
	}
	
}
