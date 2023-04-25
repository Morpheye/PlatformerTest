package platformerTest.assets;

import java.awt.Color;
import java.util.ArrayList;

import platformerTest.game.GameObject;
import platformerTest.game.MainFrame;
import platformerTest.game.Player;

public class SolidPlatform extends GameObject {
	
	public double slipperiness = 0.98;

	public SolidPlatform(double x, double y, double size_x, double size_y, Color color) {
		super(x, y, size_x, size_y, color);
		this.movable = false;
		this.solid = true;
	}
	
	@Override
	public void move() {
		this.y += this.vy;
		
		for (GameObject obj : MainFrame.objects) { //check for water
			if (obj.equals(this)) continue;
			if (this.hasCollided(obj) && obj instanceof MovableObject) {
				ArrayList<GameObject> list = new ArrayList<GameObject>();
				list.add(this);
				ArrayList<GameObject> pushing = ((MovableObject) obj).pushy(this.vy, this, list, false);
				if (pushing.size() != 0) {
					for (GameObject i : pushing) {
						i.crush();
					}
				}
			}
		}
		
		this.x += this.vx;

		for (GameObject obj : MainFrame.objects) { //check for water
			if (obj.equals(this)) continue;
			if (this.hasCollided(obj) && obj instanceof MovableObject) {
				ArrayList<GameObject> list = new ArrayList<GameObject>();
				list.add(this);
				ArrayList<GameObject> pushing = ((MovableObject) obj).pushx(this.vx, this, list, false);
				if (pushing.size() != 0) {
					for (GameObject i : pushing) {
						i.crush();
					}
				}
			}
		}
		
		
	}
	

	@Override
	public ArrayList<GameObject> pushx(double v, GameObject pusher, ArrayList<GameObject> pushers, boolean wall) {
		ArrayList<GameObject> list = new ArrayList<GameObject>();
		list.add(this);
		return list;
	}
	
	@Override
	public ArrayList<GameObject> pushy(double v, GameObject pusher, ArrayList<GameObject> pushers, boolean wall) {
		ArrayList<GameObject> list = new ArrayList<GameObject>();
		list.add(this);
		return list;
	}
	
	@Override
	public void crush() {
		return;
	}
	
}