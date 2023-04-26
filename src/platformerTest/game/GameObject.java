package platformerTest.game;

import java.awt.Color;
import java.util.ArrayList;

public class GameObject {

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
	
	public GameObject(double x, double y, double size_x, double size_y, Color color) {
		this.x = x;
		this.y = y;
		this.size_x = size_x;
		this.size_y = size_y;
		
		this.color = color;
		
	}
	
	public void move() {
		
		
	}
	
	public boolean hasCollided(GameObject obj) {
		boolean xCollided, yCollided;
		
		if (!this.exists || !obj.exists) return false; 
		
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
	

	public ArrayList<GameObject> pushx(double v, GameObject pusher, ArrayList<GameObject> pushers, boolean wall) {
		return new ArrayList<GameObject>();
	}
	
	public ArrayList<GameObject> pushy(double v, GameObject pusher, ArrayList<GameObject> pushers, boolean wall) {
		return new ArrayList<GameObject>();
	}
	
	public void crush() {
		
	}
	
	//COLORS
	
	public static final Color COLOR_GRASS = new Color(20, 200, 20);
	public static final Color COLOR_WOOD = new Color(133, 94, 30);
	public static final Color COLOR_STONE = Color.GRAY;
	
	public static final Color COLOR_WATER = new Color(0, 50, 255, 100);
	
}
