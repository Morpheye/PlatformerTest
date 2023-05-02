package platformerTest.game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import platformerTest.Main;

public class GameObject {

	public double slipperiness = 1;
	
	public double size_x;
	public double size_y;
	public double x;
	public double y;
	public double vx = 0;
	public double vy = 0;
	
	public boolean movable = false;
	public double density = 1;
	
	public Color color;
	public boolean solid;
	public boolean exists = true;
	
	public ObjType type;
	
	public GameObject(double x, double y, double size_x, double size_y, Color color) {
		this.x = x;
		this.y = y;
		this.size_x = size_x;
		this.size_y = size_y;
		
		this.color = color;
		
	}
	
	public void move() {

		
	}
	
	public void draw(Graphics g, Player player, double cam_x, double cam_y, double size) { //move object center to top left edge
		int drawX = (int) ( (this.x - (this.size_x)/2 - (cam_x - size/2)) * (Main.SIZE/size));
		int drawY = (int) ( (size - (this.y + (this.size_y)/2) + (cam_y - size/2)) * (Main.SIZE/size));
		
		g.setColor(this.color);
		g.fillRoundRect(drawX, drawY, (int) (this.size_x * Main.SIZE/size), (int) (this.size_y * Main.SIZE/size), 
		(int)(5*(Main.SIZE/size)), (int)(5*(Main.SIZE/size)));
	}
	
	
	public boolean hasCollided(GameObject obj) {
		boolean xCollided, yCollided;
		
		if (!this.exists || (!obj.exists)) return false; 
		
		if (obj.getLowerBoundX() < this.getHigherBoundX() && obj.getHigherBoundX() > this.getLowerBoundX()) xCollided = true;
		else xCollided = false;
		if (obj.getLowerBoundY() < this.getHigherBoundY() && obj.getHigherBoundY() > this.getLowerBoundY()) yCollided = true;
		else yCollided = false;
		
		return xCollided && yCollided;
		
	}
	
	
	
	public double getLowerBoundX() {
		return this.x - (this.size_x / 2);
	}
	
	public double getLowerBoundY() {
		return this.y - (this.size_y / 2);
	}

	public double getHigherBoundX() {
		return this.x + (this.size_x / 2);
	}
	
	public double getHigherBoundY() {
		return this.y + (this.size_y / 2);
	}
	
	public double getArea() {
		return this.size_x * this.size_y;
	}
	
	public double getWeight() {
		return this.getArea() * this.density;
	}
	

	public ArrayList<GameObject> pushx(double v, GameObject pusher, ArrayList<GameObject> pushers, boolean wall, boolean keepV) {
		return new ArrayList<GameObject>();
	}
	
	public ArrayList<GameObject> pushy(double v, GameObject pusher, ArrayList<GameObject> pushers, boolean wall, boolean keepV) {
		return new ArrayList<GameObject>();
	}
	
	public void crush() {
		
	}
	
	//COLORS
	
	public static final Color COLOR_GRASS = new Color(20, 200, 20);
	public static final Color COLOR_DIRT = new Color(155, 118, 83);
	public static final Color COLOR_WOOD = new Color(133, 94, 30);
	public static final Color COLOR_PLANKS = new Color(226, 187, 123);
	public static final Color COLOR_STONE = Color.GRAY;
	
	public static final Color COLOR_COPPER = new Color(184, 115, 51);
	public static final Color COLOR_SILVER = new Color(180, 180, 200);
	public static final Color COLOR_GOLD = new Color(255, 215, 0);
	
	public static final Color COLOR_WATER = new Color(0, 50, 255, 100);
	
}
