package skycubedPlatformer.menu;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
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

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import skycubedPlatformer.Main;
import skycubedPlatformer.levels.Level;
import skycubedPlatformer.levels.world1.Level_1_1;
import skycubedPlatformer.util.ImageHelper;
import skycubedPlatformer.util.Screenshot;
import skycubedPlatformer.util.appdata.DataManager;
import skycubedPlatformer.util.appdata.FileLoader;

@SuppressWarnings("serial")
public class MainPanel extends JPanel {

	public Timer timer;
	public BufferedImage bgImage;
	
	public MainPanel() {
		this.setName("Menu");
		this.setBackground(Level.COLOR_DAYSKY);
		this.setSize(Main.SIZE, Main.SIZE);
		this.setVisible(true);
		this.setFocusable(true);
		this.addMouseListener(new MainMouse());
		this.addKeyListener(new MainKeyboard());
		
		screenshotTime = 0;
		
		try {
			this.bgImage = ImageIO.read(this.getClass().getResource("/title.png"));
		} catch (Exception e) {}
		
		this.timer = new Timer(1000/90, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
				scroll++;
				if (scroll == 7200) scroll -= 7200;
		}});
		
		timer.start();
		
	}
	
	int scroll = (int) (Math.random() * 4000);
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		//paint bg
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(this.bgImage, -scroll, 0, bgImage.getWidth(), bgImage.getHeight(), null);
		g2d.setColor(new Color(0,0,0,200));
		g2d.fillRect(-50, -50, Main.SIZE+100, Main.SIZE+100);
		
		Font font = new Font(Font.MONOSPACED, Font.BOLD, 60*Main.SIZE/800);
		g2d.setFont(font);
		g2d.setColor(Color.WHITE);
		int lvlSelectStringWidth = g2d.getFontMetrics(font).stringWidth("SkyCubed Platformer");
		g2d.drawString("SkyCubed Plaformer", Main.SIZE/2 - lvlSelectStringWidth/2, Main.SIZE/6);

		drawButtons(g);
		
		drawScreenshotEffect(g);
		if (screenshotTime > 0) screenshotTime--;
		
	}
	
	int buttonSizeX=(int) (400*(Main.SIZE/800.0));
	int buttonSizeY=(int) (100*(Main.SIZE/800.0));
	private void drawButtons(Graphics g) {
		//Buttons
		Graphics2D g2d = (Graphics2D) g;
		Font font = new Font(Font.MONOSPACED, Font.BOLD, (int) (40*(Main.SIZE/800.0)));
		g.setFont(font);
		
		g2d.setColor(Color.GRAY);
		g2d.fillRoundRect(Main.SIZE/2-buttonSizeX/2, Main.SIZE/3-buttonSizeY/2, buttonSizeX, buttonSizeY, 5, 5);
		
		g2d.setColor(Color.GRAY);
		g2d.fillRoundRect(Main.SIZE/2-buttonSizeX/2, Main.SIZE*2/3-buttonSizeY/2, buttonSizeX, buttonSizeY, 5, 5);
		
		//Mouse
		Point mousePosition = this.getMousePosition();
		if (mousePosition != null) {
			int mouseX = mousePosition.x;
			int mouseY = mousePosition.y;
			
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
		g2d.drawRoundRect(Main.SIZE/2-buttonSizeX/2, Main.SIZE/3-buttonSizeY/2, buttonSizeX, buttonSizeY, 5, 5);
		int StringWidth = g.getFontMetrics(font).stringWidth("Level Select");
		int StringHeight = g.getFontMetrics(font).getHeight();
		g2d.drawString("Level Select", Main.SIZE/2-StringWidth/2, Main.SIZE/3+10);
		
		g2d.setColor(Color.WHITE);
		g2d.drawRoundRect(Main.SIZE/2-buttonSizeX/2, Main.SIZE*2/3-buttonSizeY/2, buttonSizeX, buttonSizeY, 5, 5);
		StringWidth = g.getFontMetrics(font).stringWidth("Quit Game");
		StringHeight = g.getFontMetrics(font).getHeight();
		g2d.drawString("Quit Game", Main.SIZE/2-StringWidth/2, Main.SIZE*2/3+10);
		
		if (FileLoader.rootDirectory == null) {
			g2d.setColor(Color.RED);
			font = new Font(Font.SANS_SERIF, Font.BOLD, (int) (20*(Main.SIZE/800.0)));
			g.setFont(font);
			String text = "WARNING: OS NOT SUPPORTED, YOUR DATA WILL NOT SAVE";
			StringWidth = g.getFontMetrics(font).stringWidth(text);
			StringHeight = g.getFontMetrics(font).getHeight();
			g2d.drawString(text, Main.SIZE/2-StringWidth/2, Main.SIZE*9/10);
		}
	}
	
	int screenshotTime = 0;
	public void drawScreenshotEffect(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,(float) (screenshotTime/25.0));
		g2d.setComposite(ac);
		g2d.drawImage(ImageHelper.screenshotImage, Main.SIZE/4, Main.SIZE/4, Main.SIZE/2, Main.SIZE/2, null);
	}
	
	class MainKeyboard extends KeyAdapter { 
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_F2) {
				new Screenshot();
				screenshotTime = 20;
			}
		}
	}
	
	class MainMouse extends MouseAdapter {
		
		@Override
		public void mouseClicked(MouseEvent e) {
			int mouseX = e.getX();
			int mouseY = e.getY();
			if (Math.abs(mouseX - Main.SIZE/2) < buttonSizeX/2 && Math.abs(mouseY - Main.SIZE/3) < buttonSizeY/2) {
				timer.stop();
				destroy();
				Main.jframe.openLevelSelect(new Level_1_1());
			}
			
			if (Math.abs(mouseX - Main.SIZE/2) < buttonSizeX/2 && Math.abs(mouseY - Main.SIZE*2/3) < buttonSizeY/2) {
				timer.stop();
				destroy();
				DataManager.save();
				System.exit(0);
			}
			
		}
	}
	
	void destroy() {
		System.gc();
	}
	
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Main.SIZE, Main.SIZE);
    }
	
}
