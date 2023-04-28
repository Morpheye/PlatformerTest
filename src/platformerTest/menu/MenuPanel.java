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
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import platformerTest.Main;
import platformerTest.game.GameObject;
import platformerTest.levels.LevelWorld;
import platformerTest.levels.world1.World1;

@SuppressWarnings("serial")
public class MenuPanel extends JPanel {
	
	public static ArrayList<LevelWorld> worlds;
	
	public static LevelWorld levelWorld;
	public static Timer timer;
	
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
	int[] levelSlotsY = new int[] {Main.SIZE/4, Main.SIZE/4, Main.SIZE/4,
									Main.SIZE/2, Main.SIZE/2, Main.SIZE/2,
									Main.SIZE*3/4, Main.SIZE*3/4, Main.SIZE*3/4};
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		//paint bg
		
		Graphics2D g2d = (Graphics2D) g;
		
		Font font = new Font(Font.MONOSPACED, Font.BOLD, 50);
		g2d.setFont(font);
		g2d.setColor(Color.WHITE);
		int lvlSelectStringWidth = g2d.getFontMetrics(font).stringWidth("Level Select");
		g2d.drawString("Level Select", Main.SIZE/2 - lvlSelectStringWidth/2, 75);
		
		for (int i=0; i<9; i++) {
			try {
				g2d.setStroke(new BasicStroke(5));
				g2d.drawRoundRect(levelSlotsX[i]-imgR, levelSlotsY[i]-imgR, 2*imgR, 2*imgR, 5, 5);
				
				if (levelWorld.levels.size()-1 < i) continue;
				String lvlName = levelWorld.levels.get(i).getClass().getSimpleName().substring(6);
				BufferedImage image = ImageIO.read(this.getClass().getResource("/levels/"+lvlName+".png"));
				g2d.drawImage(image, levelSlotsX[i]-imgR, levelSlotsY[i]-imgR, 2*imgR, 2*imgR, null);
				
				if (Main.completedLevels.containsKey(levelWorld.levels.get(i).getClass().getSimpleName())) {
					int completions = Main.completedLevels.get(levelWorld.levels.get(i).getClass().getSimpleName());
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
				
				g2d.setColor(Color.WHITE);
				int lvlTitleWidth = g2d.getFontMetrics(font).stringWidth(lvlName);
				int lvlTitleHeight = g2d.getFontMetrics(font).getHeight();
				g2d.drawString(lvlName, levelSlotsX[i]-(lvlTitleWidth/2), levelSlotsY[i]);
			} catch (Exception e) {
				
			}
		}
		
		//now check mouse
		if (this.getMousePosition() != null) {
			int mouseX = this.getMousePosition().x;
			int mouseY = this.getMousePosition().y;
			
			for (int i=0; i<9; i++) {
				if (Math.abs(mouseX - levelSlotsX[i]) < imgR && Math.abs(mouseY - levelSlotsY[i]) < imgR) {
					if (levelWorld.levels.size()-1 < i) break;
					g2d.setColor(new Color(255, 255, 255, 100));
					g2d.fillRect(levelSlotsX[i]-imgR, levelSlotsY[i]-imgR, 2*imgR, 2*imgR);
					
					g2d.setColor(Color.WHITE);
					
					g2d.setStroke(new BasicStroke(5));
					g2d.drawRoundRect(levelSlotsX[i]-imgR, levelSlotsY[i]-imgR, 2*imgR, 2*imgR, 5, 5);
					break;
				}
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
				if (Math.abs(mouseX - levelSlotsX[i]) < imgR && Math.abs(mouseY - levelSlotsY[i]) < imgR) {
					if (levelWorld.levels.size()-1 < i) break;
					
					timer.stop();
					Main.jframe.startGame(levelWorld.levels.get(i));
					
					break;
				}
			}
			
		}
	}
	
}
