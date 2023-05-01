package platformerTest.menu;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

import platformerTest.Main;
import platformerTest.appdata.DataManager;
import platformerTest.appdata.FileLoader;
import platformerTest.levels.Level;
import platformerTest.levels.world1.Level_1_1;

@SuppressWarnings("serial")
public class MainPanel extends JPanel {

	public static Timer timer;
	
	public MainPanel() {
		this.setName("Menu");
		this.setBackground(Level.COLOR_DAYSKY);
		this.setSize(Main.SIZE, Main.SIZE);
		this.setVisible(true);
		this.setFocusable(true);
		this.addMouseListener(new MainMouse());
		
		timer = new Timer(1000/30, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
		}});
		
		timer.start();
		
	}
	
	int buttonSizeX=400;
	int buttonSizeY=100;
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		//paint bg
		Graphics2D g2d = (Graphics2D) g;
		
		Font font = new Font(Font.MONOSPACED, Font.BOLD, 60);
		g2d.setFont(font);
		g2d.setColor(Color.WHITE);
		int lvlSelectStringWidth = g2d.getFontMetrics(font).stringWidth("SkyCubed Platformer");
		g2d.drawString("SkyCubed Plaformer", Main.SIZE/2 - lvlSelectStringWidth/2, Main.SIZE/6);

		//Buttons
		font = new Font(Font.MONOSPACED, Font.BOLD, 40);
		g.setFont(font);
		
		g2d.setColor(Color.GRAY);
		g2d.fillRoundRect(Main.SIZE/2-buttonSizeX/2, Main.SIZE/3-buttonSizeY/2, buttonSizeX, buttonSizeY, 5, 5);
		
		g2d.setColor(Color.GRAY);
		g2d.fillRoundRect(Main.SIZE/2-buttonSizeX/2, Main.SIZE*2/3-buttonSizeY/2, buttonSizeX, buttonSizeY, 5, 5);
		
		//Mouse
		if (this.getMousePosition() != null) {
			int mouseX = this.getMousePosition().x;
			int mouseY = this.getMousePosition().y;
			
			if (Math.abs(mouseX - Main.SIZE/2) < buttonSizeX/2 && Math.abs(mouseY - Main.SIZE/3) < buttonSizeY/2) {
				g2d.setColor(new Color(255, 255, 255, 100));
				g2d.fillRect(Main.SIZE/2-buttonSizeX/2, Main.SIZE/3-buttonSizeY/2, buttonSizeX, buttonSizeY);
			}
			
			if (Math.abs(mouseX - Main.SIZE/2) < buttonSizeX*2 && Math.abs(mouseY - Main.SIZE*2/3) < buttonSizeY/2) {
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
			font = new Font(Font.SANS_SERIF, Font.BOLD, 20);
			g.setFont(font);
			String text = "WARNING: OS NOT SUPPORTED, YOUR DATA WILL NOT SAVE";
			StringWidth = g.getFontMetrics(font).stringWidth(text);
			StringHeight = g.getFontMetrics(font).getHeight();
			g2d.drawString(text, Main.SIZE/2-StringWidth/2, Main.SIZE*9/10);
		}
		
	}
	
	public class MainMouse extends MouseAdapter {
		
		@Override
		public void mouseClicked(MouseEvent e) {
			int mouseX = e.getX();
			int mouseY = e.getY();
			if (Math.abs(mouseX - Main.SIZE/2) < buttonSizeX/2 && Math.abs(mouseY - Main.SIZE/3) < buttonSizeY/2) {
				timer.stop();
				Main.jframe.exitGame(new Level_1_1());
			}
			
			if (Math.abs(mouseX - Main.SIZE/2) < buttonSizeX*2 && Math.abs(mouseY - Main.SIZE*2/3) < buttonSizeY/2) {
				timer.stop();
				DataManager.save();
				System.exit(0);
			}
			
		}
	}
	
}
