package platformerTest.menu;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
import platformerTest.levels.world1.Level_1_1;
import platformerTest.weapons.Weapon;

@SuppressWarnings("serial")
public class WeaponsPanel extends JPanel {

	public static Timer timer;
	
	public WeaponsPanel() {
		this.setName("Weapons");
		this.setBackground(Color.BLACK);
		this.setSize(Main.SIZE, Main.SIZE);
		this.setVisible(true);
		this.setFocusable(true);
		this.addMouseListener(new ShopMouse());
		this.scroll = 0;
		this.reloadImages();
		this.guiOpen = false;
		this.inShop = false;
		
		timer = new Timer(1000/30, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
		}});
		
		timer.start();
	}
	
	final int of = 10;
	final int[] slotX = {Main.SIZE/5, Main.SIZE*2/5, Main.SIZE*3/5, Main.SIZE*4/5,
				Main.SIZE/5, Main.SIZE*2/5, Main.SIZE*3/5, Main.SIZE*4/5,
				Main.SIZE/5, Main.SIZE*2/5, Main.SIZE*3/5, Main.SIZE*4/5,
				Main.SIZE/5, Main.SIZE*2/5, Main.SIZE*3/5, Main.SIZE*4/5};
	

	final int[] slotY = {Main.SIZE/5-of, Main.SIZE/5-of, Main.SIZE/5-of, Main.SIZE/5-of,
				Main.SIZE*2/5-of, Main.SIZE*2/5-of, Main.SIZE*2/5-of, Main.SIZE*2/5-of,
				Main.SIZE*3/5-of, Main.SIZE*3/5-of, Main.SIZE*3/5-of, Main.SIZE*3/5-of,
				Main.SIZE*4/5-of, Main.SIZE*4/5-of, Main.SIZE*4/5-of, Main.SIZE*4/5-of,};
	BufferedImage[] slotImg = new BufferedImage[slotX.length];
	Weapon[] weaponList = new Weapon[slotX.length];
	String[] slotNames = new String[slotX.length];
	
	final int imgR = 70;
	int scroll = 0;
	boolean guiOpen = false;
	int guiSelected = 0;
	boolean inShop = false;
	int buttonSizeX = 180;
	int buttonSizeY = 40;
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		
		//title
		Font font = new Font(Font.MONOSPACED, Font.BOLD, 50);
		g2d.setFont(font);
		g2d.setColor(Color.WHITE);
		String text = (inShop) ? "Weapon Shop" : "Weapons";
		int lvlSelectStringWidth = g2d.getFontMetrics(font).stringWidth(text);
		g2d.drawString(text, Main.SIZE/2 - lvlSelectStringWidth/2, 50);
		
		Point mousePosition = this.getMousePosition();
		
		//draw the slots
		for (int i=0; i<slotImg.length; i++) {
			if (slotNames[i] != null && !inShop) {
				if (slotNames[i].equals(DataManager.saveData.selectedWeapon)) {
					int tier = weaponList[i].tier;
					Color borderColor = (tier == 1) ? GameObject.COLOR_COPPER :
						(tier == 2) ? GameObject.COLOR_SILVER : (tier == 3) ? GameObject.COLOR_GOLD : 
						(tier == 4) ? GameObject.COLOR_DIAMOND : (tier == 5) ? GameObject.COLOR_CRIMSONADE : Color.white;
					g2d.setColor(borderColor);
					g2d.fillRoundRect(slotX[i]-imgR-10, slotY[i]-imgR-10, 2*imgR+20, 2*imgR+20, 5, 5);
				}
			}
			
			g2d.setColor(Color.darkGray);
			g2d.fillRoundRect(slotX[i]-imgR, slotY[i]-imgR, 2*imgR, 2*imgR, 5, 5);
			int tier = 0;
			
			if (slotImg[i] != null) {
				g2d.drawImage(slotImg[i], slotX[i]-imgR, slotY[i]-imgR, 2*imgR, 2*imgR, null);
				tier = weaponList[i].tier;
			}
			
			if (mousePosition != null && !guiOpen) { //draw highlight if mouse on position
				int mouseX = mousePosition.x;
				int mouseY = mousePosition.y;
				if (Math.abs(mouseX - slotX[i]) < imgR && Math.abs(mouseY - slotY[i]) < imgR) {
					g2d.setColor(new Color(255,255,255,100));
					g2d.fillRoundRect(slotX[i]-imgR, slotY[i]-imgR, 2*imgR, 2*imgR, 5, 5);
				}
				
			}

			//BORDER COLORS
			Color borderColor = (tier == 1) ? GameObject.COLOR_COPPER :
				(tier == 2) ? GameObject.COLOR_SILVER : (tier == 3) ? GameObject.COLOR_GOLD : 
				(tier == 4) ? GameObject.COLOR_DIAMOND : (tier == 5) ? GameObject.COLOR_CRIMSONADE : Color.white;
			g2d.setColor(borderColor);;
			g2d.setStroke(new BasicStroke(5));
			g2d.drawRoundRect(slotX[i]-imgR, slotY[i]-imgR, 2*imgR, 2*imgR, 5, 5);
			
			if (inShop && slotNames[i] != null) {
				if (DataManager.saveData.ownedWeapons.contains(slotNames[i])) {
					g2d.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
					g2d.setColor(Color.WHITE);
					g2d.drawString("OWNED", slotX[i]-imgR+5, slotY[i]-imgR+20);
					
				} else {
					int coinCost = Weapon.getWeapon(slotNames[i]).coinCost;
					int gemCost = Weapon.getWeapon(slotNames[i]).gemCost;
				
					if (coinCost != 0) {
						g2d.drawImage(coinImage, slotX[i]-imgR+5, slotY[i]-imgR+5, 20, 20, null);
						g2d.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
						g2d.setColor(Color.WHITE);
						g2d.drawString(coinCost+"", slotX[i]-imgR+30, slotY[i]-imgR+20);
					}
					if (gemCost != 0) {
						g2d.drawImage(gemImage, slotX[i]-imgR+5, slotY[i]-imgR+30, 20, 20, null);
						g2d.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
						g2d.setColor(Color.WHITE);
						g2d.drawString(gemCost+"", slotX[i]-imgR+30, slotY[i]-imgR+45);
					}
				}
			}
			
		}
		
		//return to level select
		g2d.setColor(Color.gray);
		g2d.fillRoundRect(Main.SIZE*1/6-buttonSizeX/2, Main.SIZE*11/12-buttonSizeY/2, buttonSizeX, buttonSizeY, 5, 5);
		font = new Font(Font.MONOSPACED, Font.BOLD, 35);
		g2d.setFont(font);
		g2d.setColor(Color.WHITE);
		int lvlTitleWidth = g2d.getFontMetrics(font).stringWidth("Back");
		int lvlTitleHeight = g2d.getFontMetrics(font).getHeight();
		g2d.drawString("Back", Main.SIZE*1/6-(lvlTitleWidth/2), Main.SIZE*11/12+10);
		
		if (mousePosition != null && !guiOpen) {
			int mouseX = mousePosition.x;
			int mouseY = mousePosition.y;
			if (Math.abs(mouseX - Main.SIZE*1/6) < buttonSizeX/2 && Math.abs(mouseY - Main.SIZE*11/12) < buttonSizeY/2) {
				g2d.setColor(new Color(255,255,255,100));
				g2d.fillRoundRect(Main.SIZE*1/6-buttonSizeX/2, Main.SIZE*11/12-buttonSizeY/2, buttonSizeX, buttonSizeY, 5, 5);
		}}
		g2d.setColor(Color.WHITE);
		g2d.drawRoundRect(Main.SIZE*1/6-buttonSizeX/2, Main.SIZE*11/12-buttonSizeY/2, buttonSizeX, buttonSizeY, 5, 5);
		
		//shop
		if (!inShop) {
			//open shop
			g2d.setColor(Color.gray);
			g2d.fillRoundRect(Main.SIZE*5/6-buttonSizeX/2, Main.SIZE*11/12-buttonSizeY/2, buttonSizeX, buttonSizeY, 5, 5);
			font = new Font(Font.MONOSPACED, Font.BOLD, 35);
			g2d.setFont(font);
			g2d.setColor(Color.WHITE);
			lvlTitleWidth = g2d.getFontMetrics(font).stringWidth("Shop");
			lvlTitleHeight = g2d.getFontMetrics(font).getHeight();
			g2d.drawString("Shop", Main.SIZE*5/6-(lvlTitleWidth/2), Main.SIZE*11/12+10);
			
			if (mousePosition != null && !guiOpen) {
				int mouseX = mousePosition.x;
				int mouseY = mousePosition.y;
				if (Math.abs(mouseX - Main.SIZE*5/6) < buttonSizeX/2 && Math.abs(mouseY - Main.SIZE*11/12) < buttonSizeY/2) {
					g2d.setColor(new Color(255,255,255,100));
					g2d.fillRoundRect(Main.SIZE*5/6-buttonSizeX/2, Main.SIZE*11/12-buttonSizeY/2, buttonSizeX, buttonSizeY, 5, 5);
				}}
			g2d.setColor(Color.WHITE);
			g2d.drawRoundRect(Main.SIZE*5/6-buttonSizeX/2, Main.SIZE*11/12-buttonSizeY/2, buttonSizeX, buttonSizeY, 5, 5);
			
			//unequip weapon
			g2d.setColor(Color.gray);
			g2d.fillRoundRect(Main.SIZE/2-buttonSizeX/2, Main.SIZE*11/12-buttonSizeY/2, buttonSizeX, buttonSizeY, 5, 5);
			font = new Font(Font.MONOSPACED, Font.BOLD, 35);
			g2d.setFont(font);
			g2d.setColor(Color.WHITE);
			lvlTitleWidth = g2d.getFontMetrics(font).stringWidth("Unequip");
			lvlTitleHeight = g2d.getFontMetrics(font).getHeight();
			g2d.drawString("Unequip", Main.SIZE/2-(lvlTitleWidth/2), Main.SIZE*11/12+10);
			
			if (mousePosition != null && !guiOpen) {
				int mouseX = mousePosition.x;
				int mouseY = mousePosition.y;
				if (Math.abs(mouseX - Main.SIZE/2) < buttonSizeX/2 && Math.abs(mouseY - Main.SIZE*11/12) < buttonSizeY/2) {
					g2d.setColor(new Color(255,255,255,100));
					g2d.fillRoundRect(Main.SIZE/2-buttonSizeX/2, Main.SIZE*11/12-buttonSizeY/2, buttonSizeX, buttonSizeY, 5, 5);
				}}
			g2d.setColor(Color.WHITE);
			g2d.drawRoundRect(Main.SIZE/2-buttonSizeX/2, Main.SIZE*11/12-buttonSizeY/2, buttonSizeX, buttonSizeY, 5, 5);
		}
		
		//SIDE BUTTONS
		drawScrollButtons(g2d, mousePosition);
		
		//DRAW GUI
		drawGui(g2d, mousePosition);
		
		//COINS N GEMS
		drawCurrency(g2d);
		
	}
	
	private void drawScrollButtons(Graphics2D g2d, Point mousePosition) {
		int x1 = Main.SIZE/15, x2 = Main.SIZE*14/15, y = Main.SIZE/2-of, w = 50;
		if (scroll > 0) {
			g2d.setColor(Color.gray);
			g2d.fillRoundRect(x1-w/2, y-w/2, w, w, 5, 5);
			g2d.setColor(Color.WHITE);
			g2d.setFont(new Font(Font.MONOSPACED, Font.BOLD, 40));
			g2d.drawString("<", x1-w/2+13, y+10);
			if (mousePosition != null) {
				int mouseX = mousePosition.x;
				int mouseY= mousePosition.y;
				if (Math.abs(mouseX - x1) < w/2 && Math.abs(mouseY - y) < w/2 && !guiOpen) {
					g2d.setColor(new Color(255,255,255,100));
					g2d.fillRoundRect(x1-w/2, y-w/2, w, w, 5, 5);
				}
			}
			g2d.setColor(Color.white);
			g2d.drawRoundRect(x1-w/2, y-w/2, w, w, 5, 5);
		}
		
		if ((inShop && ((scroll+1)*16 < Weapon.weaponNames.size())) ||
				(!inShop && ((scroll+1)*16 < DataManager.saveData.ownedWeapons.size()))) {
			g2d.setColor(Color.gray);
			g2d.fillRoundRect(x2-w/2, y-w/2, w, w, 5, 5);
			g2d.setColor(Color.WHITE);
			g2d.setFont(new Font(Font.MONOSPACED, Font.BOLD, 40));
			g2d.drawString(">", x2-w/2+13, y+10);
			if (mousePosition != null) {
				int mouseX = mousePosition.x;
				int mouseY= mousePosition.y;
				if (Math.abs(mouseX - x2) < w/2 && Math.abs(mouseY - y) < w/2 && !guiOpen) {
					g2d.setColor(new Color(255,255,255,100));
					g2d.fillRoundRect(x2-w/2, y-w/2, w, w, 5, 5);
				}
			}
			g2d.setColor(Color.white);
			g2d.drawRoundRect(x2-w/2, y-w/2, w, w, 5, 5);
		}
		
	}
	
	private void drawGui(Graphics2D g2d, Point mousePosition) {
		if (guiOpen) {
			g2d.setColor(new Color(0,0,0,200));
			g2d.fillRect(0, 0, Main.SIZE, Main.SIZE);
			g2d.setColor(Color.GRAY);
			int w=400;
			int h=600;
			g2d.fillRoundRect(Main.SIZE/2-w/2, Main.SIZE/2-h/2, w, h-100, 5, 5);
			g2d.setStroke(new BasicStroke(5));
			g2d.setColor(Color.WHITE);
			g2d.drawRoundRect(Main.SIZE/2-w/2, Main.SIZE/2-h/2, w, h-100, 5, 5);
			
			g2d.setColor(Color.darkGray);
			g2d.fillRoundRect(Main.SIZE/2+w/6-5, Main.SIZE/2-h/2+10, w/3-5, w/3-5, 5, 5);
			g2d.drawImage(slotImg[guiSelected], Main.SIZE/2+w/6-5, Main.SIZE/2-h/2+10, w/3-5, w/3-5, null);
			g2d.setColor(Color.white);
			g2d.drawRoundRect(Main.SIZE/2+w/6-5, Main.SIZE/2-h/2+10, w/3-5, w/3-5, 5, 5);
			
			drawString(g2d, 25, slotNames[guiSelected], w*2/3, Main.SIZE/2-w/2+5, Main.SIZE/2-h/2+10);
			String[] stats = weaponList[guiSelected].stats;
			int[] statMap = weaponList[guiSelected].statMap;
			String lore = weaponList[guiSelected].lore;
			g2d.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
			for (int i=0; i<stats.length; i++) {
				int cInt = 0;
				try {
					cInt = statMap[i];
				} catch (Exception e) {}
				g2d.setColor(cInt == 1 ? Color.green : cInt == -1 ? Color.red : cInt == 2 ? Color.cyan : Color.white);
				g2d.drawString(stats[i], Main.SIZE/2-w/2+10, Main.SIZE/2-h/2+100+(18*i));
			}
			
			drawString(g2d, 15, lore, w-20, Main.SIZE/2-w/2+10, Main.SIZE*2/5);
			
			if (inShop) {
				int coinCost = weaponList[guiSelected].coinCost;
				int gemCost = weaponList[guiSelected].gemCost;
			
				if (coinCost != 0) {
					g2d.drawImage(coinImage, Main.SIZE/2+w/6, Main.SIZE/2-h/2+w/3+10, 20, 20, null);
					g2d.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
					g2d.drawString(coinCost+"", Main.SIZE/2+w/6 + 25, Main.SIZE/2-h/2+w/3+25);
				}
				if (gemCost != 0) {
					g2d.drawImage(gemImage, Main.SIZE/2+w/6, Main.SIZE/2-h/2+w/3+35, 20, 20, null);
					g2d.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
					g2d.drawString(gemCost+"", Main.SIZE/2+w/6 + 25, Main.SIZE/2-h/2+w/3+50);
				}
			}
			
			//CLOSE BUTTON
			g2d.setColor(Color.RED.darker());
			g2d.fillRoundRect(Main.SIZE/2+w/3, Main.SIZE/2-h/2-45, w/6, 35, 5, 5);
			
			if (mousePosition != null) {
				int mouseX = mousePosition.x;
				int mouseY= mousePosition.y;
				if (Math.abs(mouseX - (Main.SIZE/2+w/3+w/12)) < w/12 && Math.abs(mouseY - (Main.SIZE/2-h/2-27)) < 35/2) {
					g2d.setColor(new Color(255,0,0,150));
					g2d.fillRoundRect(Main.SIZE/2+w/3, Main.SIZE/2-h/2-45, w/6, 35, 5, 5);
				}
			}
			
			g2d.setColor(Color.BLACK);
			g2d.setStroke(new BasicStroke(5));
			g2d.drawLine(Main.SIZE/2+w/3+w/12 - 10, Main.SIZE/2-h/2-27 - 10, Main.SIZE/2+w/3+w/12 + 10, Main.SIZE/2-h/2-27 + 10);
			g2d.drawLine(Main.SIZE/2+w/3+w/12 - 10, Main.SIZE/2-h/2-27 + 10, Main.SIZE/2+w/3+w/12 + 10, Main.SIZE/2-h/2-27 - 10);
			g2d.drawRoundRect(Main.SIZE/2+w/3, Main.SIZE/2-h/2-45, w/6, 35, 5, 5);
			
			
			//SELECT BUTTON
			g2d.setColor(Color.GRAY);
			g2d.fillRoundRect(Main.SIZE/2-100, Main.SIZE/2+h/2-80, 200, 70, 5, 5);
			if (mousePosition != null) {
				int mouseX = mousePosition.x;
				int mouseY= mousePosition.y;
				if (Math.abs(mouseX - (Main.SIZE/2)) < 100 && Math.abs(mouseY - (Main.SIZE/2+h/2-45)) < 35) {
					if (inShop) {
						int coinCost = weaponList[guiSelected].coinCost;
						int gemCost = weaponList[guiSelected].gemCost;
						if (DataManager.saveData.coins < coinCost || DataManager.saveData.gems < gemCost) {
						} else if (DataManager.saveData.ownedWeapons.contains(slotNames[guiSelected])) {
						} else {
							g2d.setColor(new Color(255,255,255,100));
							g2d.fillRoundRect(Main.SIZE/2-100, Main.SIZE/2+h/2-80, 200, 70, 5, 5);
						}
					} else if (slotNames[guiSelected].equals(DataManager.saveData.selectedWeapon)){
					} else {
						g2d.setColor(new Color(255,255,255,100));
						g2d.fillRoundRect(Main.SIZE/2-100, Main.SIZE/2+h/2-80, 200, 70, 5, 5);
					}
				}
			}
			
			//Purchase
			g2d.setColor(Color.WHITE);
			g2d.drawRoundRect(Main.SIZE/2-100, Main.SIZE/2+h/2-80, 200, 70, 5, 5);
			Font font = new Font(Font.MONOSPACED, Font.BOLD, 30);
			g2d.setFont(font);
			String buttonText;
			if (inShop) {
				if (DataManager.saveData.ownedWeapons.contains(slotNames[guiSelected])) buttonText = "Purchased";
				else buttonText = "Purchase";
			}
			else if (slotNames[guiSelected].equals(DataManager.saveData.selectedWeapon)) buttonText = "Equipped";
			else buttonText = "Equip";
			int strWidth = g2d.getFontMetrics(font).stringWidth(buttonText);
			g2d.drawString(buttonText, Main.SIZE/2-strWidth/2, Main.SIZE/2+h/2-40);
			
		}
	}
	

	private void drawCurrency(Graphics2D g2d) {
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
	
	public class ShopMouse extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			int mouseX = e.getX();
			int mouseY = e.getY();

			if (!guiOpen) {
				//WEAPONS
				for (int i=0; i<slotImg.length; i++) {
					if (Math.abs(mouseX - slotX[i]) < imgR && Math.abs(mouseY - slotY[i]) < imgR) {
						if (slotNames[i] != null) {
							guiOpen = true;
							guiSelected = i;
						}
					}
				}
				//back
				if (Math.abs(mouseX - Main.SIZE*1/6) < buttonSizeX/2 && Math.abs(mouseY - Main.SIZE*11/12) < buttonSizeY/2) {
					if (inShop) {
						inShop = false;
						scroll = 0;
						reloadImages();
					} else {
						timer.stop();
						Main.jframe.exitGame(new Level_1_1());
					}
				}
				//shop
				if (Math.abs(mouseX - Main.SIZE*5/6) < buttonSizeX/2 && Math.abs(mouseY - Main.SIZE*11/12) < buttonSizeY/2) {
					if (!inShop) {
						inShop = true;
						scroll = 0;
						reloadImages();
					}
				}
				
				//deselect
				if (!inShop && Math.abs(mouseX - Main.SIZE/2) < buttonSizeX/2 && Math.abs(mouseY - Main.SIZE*11/12) < buttonSizeY/2) {
					DataManager.saveData.selectedWeapon = null;
					
				}
				
				//SIDE BUTTONS
				int x1 = Main.SIZE/15, x2 = Main.SIZE*14/15, y = Main.SIZE/2-of, w = 50;
				
				if (scroll > 0) { //Left
					if (Math.abs(mouseX - x1) < w/2 && Math.abs(mouseY - y) < w/2 && !guiOpen) {
						scroll--;
						reloadImages();

				}}
				
				if ((inShop && ((scroll+1)*16 < Weapon.weaponNames.size())) || //Right
						(!inShop && ((scroll+1)*16 < DataManager.saveData.ownedWeapons.size()))) {
					if (Math.abs(mouseX - x2) < w/2 && Math.abs(mouseY - y) < w/2 && !guiOpen) {
						scroll++;
						reloadImages();
						
				}}
			}
			
			//GUI
			if (guiOpen) {
				int w=400;
				int h=600;
				//close button
				if (Math.abs(mouseX - (Main.SIZE/2+w/3+w/12)) < w/12 && Math.abs(mouseY - (Main.SIZE/2-h/2-27)) < 35/2) {
					guiOpen = false;
				}
				
				//PURCHASE BUTTON
				if (Math.abs(mouseX - (Main.SIZE/2)) < 100 && Math.abs(mouseY - (Main.SIZE/2+h/2-45)) < 35) {
					if (inShop) {
						int coinCost = Weapon.getWeapon(slotNames[guiSelected]).coinCost;
						int gemCost = Weapon.getWeapon(slotNames[guiSelected]).gemCost;
						if (DataManager.saveData.coins < coinCost || DataManager.saveData.gems < gemCost) {
						} else if (DataManager.saveData.ownedWeapons.contains(slotNames[guiSelected])) {
						} else {
							DataManager.saveData.ownedWeapons.add(slotNames[guiSelected]);
							DataManager.saveData.coins -= coinCost;
							DataManager.saveData.gems -= gemCost;
							guiOpen = false;
						}
					} else if (slotNames[guiSelected].equals(DataManager.saveData.selectedWeapon)){
					} else {
						DataManager.saveData.selectedWeapon = slotNames[guiSelected];
						guiOpen = false;
					}	
				}
				
			}
			
	}}

	BufferedImage coinImage, gemImage, lockImage;
	public void reloadImages() {
		slotNames = new String[slotX.length];
		slotImg = new BufferedImage[slotX.length];
		
		try {
			this.lockImage = ImageIO.read(this.getClass().getResource("/gui/lock.png"));
			this.coinImage = ImageIO.read(this.getClass().getResource("/gui/goldcoin.png"));
			this.gemImage = ImageIO.read(this.getClass().getResource("/gui/gem.png"));
			
			ArrayList<String> weapons;
			
			if (!inShop) {
				weapons = DataManager.saveData.ownedWeapons;
			} else {
				weapons = Weapon.weaponNames;
			}
			
			int finalIndex = (scroll*16+15 > weapons.size()-1) ? (weapons.size()) : scroll*16+15;
			List<String> filteredWeapons = weapons.subList(scroll*16, finalIndex);
			
			//load individual slot images;
			for (int i=0; i<filteredWeapons.size(); i++) {
				try {
					weaponList[i] = Weapon.getWeapon(filteredWeapons.get(i));
					slotImg[i] = Weapon.getWeapon(filteredWeapons.get(i)).image;
					slotNames[i] = Weapon.getWeapon(filteredWeapons.get(i)).name;
					
				} catch (Exception e) {
					slotImg[i] = null;
					e.printStackTrace();
				}
				
			}
			
		} catch (Exception e) {e.printStackTrace();}
	}
	
	public static void drawString(Graphics2D g, int size, String str, int maxWidth, int drawX, int drawY) {
		Font font = new Font(Font.MONOSPACED, Font.BOLD, size);
		g.setFont(font);
		g.setColor(Color.WHITE);
		List<String> string = Arrays.asList(str.split(" "));
		List<String> finalStrings = new ArrayList<String>();
		
		int strWidth = g.getFontMetrics(font).stringWidth(str);
		int strHeight = g.getFontMetrics(font).getHeight();
		
		List<String> subList = new ArrayList<String>();
		
		for (int i=0; i<string.size(); i++) {
			subList.add(string.get(i));
			strWidth = g.getFontMetrics(font).stringWidth(String.join(" ", subList));
			
			if (strWidth > maxWidth) {
				subList.remove(subList.size()-1);
				finalStrings.add(String.join(" ", subList));
				subList.clear();
				subList.add(string.get(i));
			}	
		}
		
		finalStrings.add(String.join(" ", subList));
		
		for (int i=0; i<finalStrings.size(); i++) {
			g.drawString(finalStrings.get(i), drawX, drawY+(int)((strHeight+1) * (i+0.5)));
		}
		
	}
		
	
}