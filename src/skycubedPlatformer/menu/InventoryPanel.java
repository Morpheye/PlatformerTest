package skycubedPlatformer.menu;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;
import javax.swing.Timer;

import skycubedPlatformer.Main;
import skycubedPlatformer.game.GameObject;
import skycubedPlatformer.items.Item;
import skycubedPlatformer.items.weapons.Weapon;
import skycubedPlatformer.levels.LevelWorld;
import skycubedPlatformer.levels.world1.Level_1_1;
import skycubedPlatformer.util.ImageHelper;
import skycubedPlatformer.util.Screenshot;
import skycubedPlatformer.util.SoundHelper;
import skycubedPlatformer.util.appdata.DataManager;

@SuppressWarnings("serial")
public class InventoryPanel extends JPanel {

	public Timer timer;
	public Clip equipSound;
	public Clip purchaseSound;
	public Clip consumeSound;
	
	public InventoryPanel() {
		if (scroll < 0) scroll = 0;
		if (scroll > LevelWorld.levelWorlds.size()-1) scroll = LevelWorld.levelWorlds.size()-1;
		
		this.setName("Weapons");
		this.setBackground(Color.BLACK);
		this.setSize(Main.SIZE, Main.SIZE);
		this.setVisible(true);
		this.setFocusable(true);
		this.addMouseListener(new ShopMouse());
		this.addKeyListener(new ShopKeyboard());
		this.scroll = 0;
		this.reloadImages();
		this.guiOpen = false;
		this.inShop = false;
		
		screenshotTime = 0;
		
		try {
			this.equipSound = AudioSystem.getClip();
			this.purchaseSound = AudioSystem.getClip();
			this.consumeSound = AudioSystem.getClip();
			SoundHelper.loadSound(this, this.equipSound, "/sounds/inventory/equip.wav");
			SoundHelper.loadSound(this, this.purchaseSound, "/sounds/inventory/purchase.wav");
			SoundHelper.loadSound(this,  this.consumeSound, "/sounds/inventory/consume.wav");
			
		} catch (Exception e) {}
		
		this.timer = new Timer(1000/30, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
				if (buttonCooldown > 0) buttonCooldown--;
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
	Item[] itemList = new Item[slotX.length];
	String[] slotNames = new String[slotX.length];
	String[] amountList = new String[slotX.length];
	
	final int imgR = 70;
	int scroll = 0;
	boolean guiOpen = false;
	int guiSelected = 0;
	boolean inShop = false;
	int buttonSizeX = 180;
	int buttonSizeY = 40;
	
	int coloring = 100;
	int vc = 5;
	
	int buttonCooldown = 10;
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		
		if (coloring > 125) vc = -5;
		if (coloring < 75) vc = 5;
		coloring += vc;
		
		//title
		Font font = new Font(Font.MONOSPACED, Font.BOLD, 50);
		g2d.setFont(font);
		g2d.setColor(Color.WHITE);
		String text = (inShop) ? "Item Shop" : "Inventory";
		int lvlSelectStringWidth = g2d.getFontMetrics(font).stringWidth(text);
		g2d.drawString(text, Main.SIZE/2 - lvlSelectStringWidth/2, 50);
		
		Point mousePosition = this.getMousePosition();
		
		//draw the slots
		for (int i=0; i<slotImg.length; i++) {
			
			g2d.setColor(Color.darkGray.darker());
			
			if (slotNames[i] != null && !inShop) {
				if (slotNames[i].equals(DataManager.saveData.selectedWeapon)
					|| DataManager.saveData.activeItems.contains(slotNames[i])) { //check for selected weapon/active item
					int tier = itemList[i].tier;
					Color borderColor = (tier == 1) ? GameObject.COLOR_COPPER :
						(tier == 2) ? GameObject.COLOR_SILVER : (tier == 3) ? GameObject.COLOR_GOLD : 
						(tier == 4) ? GameObject.COLOR_DIAMOND : (tier == 5) ? GameObject.COLOR_CRIMSONADE : Color.white;
					g2d.setColor(borderColor);
					g2d.fillRoundRect(slotX[i]-imgR-5, slotY[i]-imgR-5, 2*imgR+10, 2*imgR+10, 5, 5);
					g2d.setColor(new Color(coloring, coloring, coloring));
				}
			}
			
			g2d.fillRoundRect(slotX[i]-imgR, slotY[i]-imgR, 2*imgR, 2*imgR, 5, 5);
			int tier = 0;
			
			if (slotImg[i] != null) {
				g2d.drawImage(slotImg[i], slotX[i]-imgR, slotY[i]-imgR, 2*imgR, 2*imgR, null);
				tier = itemList[i].tier;
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
				if (DataManager.saveData.inventory.containsKey(slotNames[i]) && Weapon.weaponNames.contains(slotNames[i])) {
					g2d.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
					g2d.setColor(Color.WHITE);
					g2d.drawString("OWNED", slotX[i]-imgR+5, slotY[i]-imgR+20);
					
				} else {
					int coinCost = Item.getItem(slotNames[i]).coinCost;
					int gemCost = Item.getItem(slotNames[i]).gemCost;
				
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

			} else if (!inShop && amountList[i] != null && amountList[i].length() > 0) {
				String amount = "x"+ImageHelper.formatCurrency(Long.parseLong(amountList[i]));
				if (Weapon.weaponNames.contains(slotNames[i])) amount = "";
				font = new Font(Font.MONOSPACED, Font.BOLD, 25);
				g2d.setFont(font);
				g2d.setColor(Color.WHITE);
				int amountStringWidth = g2d.getFontMetrics(font).stringWidth(amount);
				g2d.drawString(amount, slotX[i]+imgR-amountStringWidth-5, slotY[i]+imgR-5);
				
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
		
		drawScreenshotEffect(g);
		if (screenshotTime > 0) screenshotTime--;
		
	}
	
	void drawScrollButtons(Graphics2D g2d, Point mousePosition) {
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
				(!inShop && ((scroll+1)*16 < DataManager.saveData.inventory.size()))) {
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
	
	void drawGui(Graphics2D g2d, Point mousePosition) {
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
			
			drawString(g2d, 25, slotNames[guiSelected], w*2/3, Main.SIZE/2-w/2+10, Main.SIZE/2-h/2+10);
			String[] stats = itemList[guiSelected].stats;
			int[] statMap = itemList[guiSelected].statMap;
			String lore = itemList[guiSelected].lore;
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
				int coinCost = itemList[guiSelected].coinCost;
				int gemCost = itemList[guiSelected].gemCost;
			
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
						int coinCost = itemList[guiSelected].coinCost;
						int gemCost = itemList[guiSelected].gemCost;
						if (DataManager.saveData.coins < coinCost || DataManager.saveData.gems < gemCost) { //check cost
						} else if (DataManager.saveData.inventory.containsKey(slotNames[guiSelected])
								&& Weapon.weaponNames.contains(slotNames[guiSelected])) { //check if weapon
						} else {
							g2d.setColor(new Color(255,255,255,100));
							g2d.fillRoundRect(Main.SIZE/2-100, Main.SIZE/2+h/2-80, 200, 70, 5, 5);
						}
					} else if (slotNames[guiSelected].equals(DataManager.saveData.selectedWeapon)){ //already selected
					} else if (Item.getItem(slotNames[guiSelected]).isConsumable
							|| !Weapon.weaponNames.contains(slotNames[guiSelected])){
						g2d.setColor(new Color(255,255,255,100));
						g2d.fillRoundRect(Main.SIZE/2-100, Main.SIZE/2+h/2-80, 200, 70, 5, 5);
					}
				}
			}
			
			//Purchase
			boolean enable = true;
			g2d.setColor(Color.WHITE);
			Font font = new Font(Font.MONOSPACED, Font.BOLD, 30);
			g2d.setFont(font);
			String buttonText;
			if (inShop) {
				if (DataManager.saveData.inventory.containsKey(slotNames[guiSelected]) && 
						Weapon.weaponNames.contains(slotNames[guiSelected])) {
					buttonText = "Purchased";
					enable = false;
				}
				else buttonText = "Purchase";
				int coinCost = itemList[guiSelected].coinCost;
				int gemCost = itemList[guiSelected].gemCost;
				if (DataManager.saveData.coins < coinCost || DataManager.saveData.gems < gemCost) enable = false;
			} else if (slotNames[guiSelected].equals(DataManager.saveData.selectedWeapon)) {
				buttonText = "Equipped";
				enable = false;
			} else if (DataManager.saveData.activeItems.contains(slotNames[guiSelected])) {
				buttonText = "Stop Use";
			} else if (!Item.getItem(slotNames[guiSelected]).isConsumable && !Weapon.weaponNames.contains(slotNames[guiSelected])) {
				buttonText = "Use";
				enable = false;
			} else buttonText = (Weapon.weaponNames.contains(slotNames[guiSelected])) ? "Equip" : "Start Use";
			
			int strWidth = g2d.getFontMetrics(font).stringWidth(buttonText);
			g2d.drawString(buttonText, Main.SIZE/2-strWidth/2, Main.SIZE/2+h/2-40);
			
			if (!enable) {
				g2d.setColor(new Color(0, 0, 0, 100));
				g2d.fillRoundRect(Main.SIZE/2-100, Main.SIZE/2+h/2-80, 200, 70, 5, 5);
			}
			
			g2d.setColor(Color.WHITE); //border
			g2d.drawRoundRect(Main.SIZE/2-100, Main.SIZE/2+h/2-80, 200, 70, 5, 5);
			
		}
	}
	
	void drawCurrency(Graphics2D g2d) {
		String coinText = ImageHelper.formatCurrency(DataManager.saveData.coins);
		String gemText = ImageHelper.formatCurrency(DataManager.saveData.gems);
		
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
	
	int screenshotTime = 0;
	public void drawScreenshotEffect(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,(float) (screenshotTime/25.0));
		g2d.setComposite(ac);
		g2d.drawImage(ImageHelper.screenshotImage, Main.SIZE/4, Main.SIZE/4, Main.SIZE/2, Main.SIZE/2, null);
	}
	
	class ShopKeyboard extends KeyAdapter { 
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_F2) {
				new Screenshot();
				screenshotTime = 20;
			}
		}
	}
	
	class ShopMouse extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (buttonCooldown > 0) return;
			int mouseX = e.getX();
			int mouseY = e.getY();

			if (!guiOpen) {
				//WEAPONS
				for (int i=0; i<slotImg.length; i++) {
					if (Math.abs(mouseX - slotX[i]) < imgR && Math.abs(mouseY - slotY[i]) < imgR) {
						if (slotNames[i] != null) {
							guiOpen = true;
							guiSelected = i;
							return;
						}
					}
				}
				//back
				if (Math.abs(mouseX - Main.SIZE*1/6) < buttonSizeX/2 && Math.abs(mouseY - Main.SIZE*11/12) < buttonSizeY/2) {
					if (inShop) {
						inShop = false;
						scroll = 0;
						reloadImages();
						return;
					} else {
						stop();
						timer.stop();
						Main.jframe.openLevelSelect(new Level_1_1());
						return;
					}
				}
				//shop
				if (Math.abs(mouseX - Main.SIZE*5/6) < buttonSizeX/2 && Math.abs(mouseY - Main.SIZE*11/12) < buttonSizeY/2) {
					if (!inShop) {
						inShop = true;
						scroll = 0;
						reloadImages();
						return;
					}
				}
				
				//deselect
				if (!inShop && Math.abs(mouseX - Main.SIZE/2) < buttonSizeX/2 && Math.abs(mouseY - Main.SIZE*11/12) < buttonSizeY/2) {
					if (DataManager.saveData.selectedWeapon != null) {
						equipSound.setMicrosecondPosition(0);
						equipSound.start();
					}
					
					DataManager.saveData.selectedWeapon = null;
					return;

					
				}
				
				//SIDE BUTTONS
				int x1 = Main.SIZE/15, x2 = Main.SIZE*14/15, y = Main.SIZE/2-of, w = 50;
				
				if (scroll > 0) { //Left
					if (Math.abs(mouseX - x1) < w/2 && Math.abs(mouseY - y) < w/2 && !guiOpen) {
						scroll--;
						reloadImages();
						return;

				}}
				
				if ((inShop && ((scroll+1)*16 < Weapon.weaponNames.size())) || //Right
						(!inShop && ((scroll+1)*16 < DataManager.saveData.inventory.size()))) {
					if (Math.abs(mouseX - x2) < w/2 && Math.abs(mouseY - y) < w/2 && !guiOpen) {
						scroll++;
						reloadImages();
						return;
						
				}}
			}
			
			//GUI
			if (guiOpen) {
				int w=400;
				int h=600;
				//close button
				if (Math.abs(mouseX - (Main.SIZE/2+w/3+w/12)) < w/12 && Math.abs(mouseY - (Main.SIZE/2-h/2-27)) < 35/2) {
					guiOpen = false;
					buttonCooldown = 5;
					return;
				}
				
				//PURCHASE BUTTON
				if (Math.abs(mouseX - (Main.SIZE/2)) < 100 && Math.abs(mouseY - (Main.SIZE/2+h/2-45)) < 35) {
					if (inShop) {
						int coinCost = Item.getItem(slotNames[guiSelected]).coinCost;
						int gemCost = Item.getItem(slotNames[guiSelected]).gemCost;
						if (DataManager.saveData.coins < coinCost || DataManager.saveData.gems < gemCost) { //check cost
						} else if (DataManager.saveData.inventory.containsKey(slotNames[guiSelected])
								&& Weapon.weaponNames.contains(slotNames[guiSelected])) { //check if weapon already owned
						} else { //purchase successful
							if (Weapon.weaponNames.contains(slotNames[guiSelected])) { //WEAPON
								DataManager.saveData.inventory.put(slotNames[guiSelected],1L);
							} else { //Item
								DataManager.addItem(slotNames[guiSelected], 1L);
							}
							DataManager.saveData.coins -= coinCost;
							DataManager.saveData.gems -= gemCost;
							if (Weapon.weaponNames.contains(slotNames[guiSelected])) guiOpen = false;
							buttonCooldown = 10;
							
							SoundHelper.playSound(purchaseSound);
							return;
						}
					} else if (slotNames[guiSelected].equals(DataManager.saveData.selectedWeapon)){
					} else if (Weapon.weaponNames.contains(slotNames[guiSelected])) { //EQUIP
						DataManager.saveData.selectedWeapon = slotNames[guiSelected];
						guiOpen = false;
						buttonCooldown = 5;
						
						SoundHelper.playSound(equipSound);
						return;
					} else if (!Item.getItem(slotNames[guiSelected]).isConsumable) {
					} else if (!DataManager.saveData.activeItems.contains(slotNames[guiSelected])) { //START USE
						DataManager.saveData.activeItems.add(slotNames[guiSelected]);
						buttonCooldown = 5;
						SoundHelper.playSound(consumeSound);
						return;
						
					} else if (DataManager.saveData.activeItems.contains(slotNames[guiSelected])) { //STOP USE
						DataManager.saveData.activeItems.remove(slotNames[guiSelected]);
						buttonCooldown = 5;
						SoundHelper.playSound(consumeSound);
						return;
					}
				}
				
			}
			
	}}

	BufferedImage coinImage, gemImage, lockImage;
	void reloadImages() {
		slotNames = new String[slotX.length];
		slotImg = new BufferedImage[slotX.length];
		amountList = new String[slotX.length];
		
		try {
			this.lockImage = ImageIO.read(this.getClass().getResource("/gui/lock.png"));
			this.coinImage = ImageIO.read(this.getClass().getResource("/gui/goldcoin.png"));
			this.gemImage = ImageIO.read(this.getClass().getResource("/gui/gem.png"));
			
			List<String> items;
			List<String> amount;
			
			if (!inShop) {
				items = Arrays.asList(DataManager.saveData.inventory.keySet().toArray(new String[] {}));
				items.sort(inventorySorter);
				amount = new ArrayList<String>(16);
				
			} else { //IN SHOP
				items = Item.itemNames;
				items.sort(inventorySorter);
				items.removeIf(c -> Item.getItem(c).inShop != 1);
				amount = new ArrayList<String>();
			}
			
			int finalIndex = (scroll*16+16 > items.size()) ? (items.size()) : scroll*16+16;
			List<String> filteredItems = items.subList(scroll*16, finalIndex);
			
			//load individual slot images;
			for (int i=0; i<filteredItems.size(); i++) {
				try {
					itemList[i] = Item.getItem(filteredItems.get(i));
					slotImg[i] = Item.getItem(filteredItems.get(i)).image;
					slotNames[i] = Item.getItem(filteredItems.get(i)).name;
					
					if (!inShop) {
						amountList[i] = ""+DataManager.saveData.inventory.get(filteredItems.get(i));
					}
					
				} catch (Exception e) {
					slotImg[i] = null;
					e.printStackTrace();
				}
				
			}
			
		} catch (Exception e) {e.printStackTrace();}
	}
	
	void drawString(Graphics2D g, int size, String str, int maxWidth, int drawX, int drawY) {
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
		
	void stop() {
		if (this.equipSound != null) if (this.equipSound.isOpen()) this.equipSound.close();
		if (this.purchaseSound != null) if (this.purchaseSound.isOpen()) this.purchaseSound.close();
		if (this.consumeSound != null) if (this.consumeSound.isOpen()) this.consumeSound.close();
	}
	
	static Comparator<String> inventorySorter = new Comparator<String>() {
		@Override //weapon/item -> tier -> in shop -> gem cost -> coin cost
		public int compare(String o1, String o2) {
			Item w1 = Item.getItem(o1);
			Item w2 = Item.getItem(o2);

			if (Weapon.weaponNames.contains(o1) != Weapon.weaponNames.contains(o2)) {
				if (Weapon.weaponNames.contains(o1)) return -1;
				else return 1;
				
			} else if (w1.tier != w2.tier) return w1.tier - w2.tier;
			else if (w1.inShop == 0 && w2.inShop != 0) return 1;
			else if (w1.gemCost != w2.gemCost) return w1.gemCost - w2.gemCost;
			else if (w1.coinCost != w2.coinCost) return w1.coinCost - w2.coinCost;
			else return 0;
			
	}};
	
}