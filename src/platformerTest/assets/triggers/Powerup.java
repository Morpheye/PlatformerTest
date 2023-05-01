package platformerTest.assets.triggers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import platformerTest.Main;
import platformerTest.assets.Trigger;
import platformerTest.game.ObjType;
import platformerTest.game.Player;

public class Powerup extends Trigger {

	public Image image;
	
	public Powerup(double x, double y, double size, Code code) {
		super(x, y, size, size, code);
		this.type = ObjType.Powerup;
		this.color = null;
		this.visible = true;
	}
	
	@Override
	public void run() {
		
	}
	
	@Override
	public void draw(Graphics g, Player player, double cam_x, double cam_y, double size) {
		Graphics2D g2d = (Graphics2D) g;

		int alpha = 100;
		int a = 2;
		if (this.color.getAlpha() > 250) a = -5;
		else if (this.color.getAlpha() < 20) a = 5;
		
		g2d.setColor(new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), alpha));
		int drawX = (int) ( (this.x - (this.size_x)/2 - (cam_x - size/2)) * (Main.SIZE/size));
		int drawY = (int) ( (size - (this.y + (this.size_y)/2) + (cam_y - size/2)) * (Main.SIZE/size));

		g.fillOval(drawX, drawY, (int) (this.size_x * Main.SIZE/size), (int) (this.size_y * Main.SIZE/size));
		
	}

	public static final Color COLOR_POWERUP_MARKSMAN = new Color(255,50,255,100); //pink
	public static final Color COLOR_POWERUP_PUNCH = new Color(170,0,170,100); //purple
	public static final Color COLOR_POWERUP_SWIFTNESS = new Color(0,255,255,100); //cyan
	public static final Color COLOR_POWERUP_CAMERASIZE = new Color(0,255,0,100); //light green
	public static final Color COLOR_POWERUP_JUMPBOOST = new Color(255,255,0,100); //yellow
	public static final Color COLOR_POWERUP_OVERHEAL = new Color(255,215,0,100); //gold
	public static final Color COLOR_POWERUP_FIRERESISTANCE = new Color(255,100,0,100); //orange
	public static final Color COLOR_POWERUP_HEAL = new Color(255,0,0,100); //red
	public static final Color COLOR_POWERUP_STRENGTH = new Color(160,0,0,100); //crimson
	public static final Color COLOR_POWERUP_ATTACKSPEED = new Color(110,50,0,100); //brown
	public static final Color COLOR_POWERUP_DENSITY = new Color(125,125,125,100); //gray

}
