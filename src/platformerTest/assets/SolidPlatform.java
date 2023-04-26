package platformerTest.assets;

import java.awt.Color;
import java.util.ArrayList;

import platformerTest.game.GameObject;
import platformerTest.game.MainFrame;
import platformerTest.game.MovableObject;
import platformerTest.game.Player;

public class SolidPlatform extends GameObject {
	
	public double slipperiness = 1;

	public SolidPlatform(double x, double y, double size_x, double size_y, Color color) {
		super(x, y, size_x, size_y, color);
		this.movable = false;
		this.solid = true;
	}
	
	@Override
	public void move() {
		
		//YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY
		
		ArrayList<GameObject> collisions = new ArrayList<GameObject>();
		boolean collided = false;
		
		this.y += this.vy;
		
		for (GameObject obj : MainFrame.objects) {
			if (obj.equals(this)) continue;
			if (this.hasCollided(obj) && obj instanceof MovableObject) {
				collisions.add(obj);
				collided = true;
		}}
		
		ArrayList<GameObject> list = new ArrayList<GameObject>();
		list.add(this);
		ArrayList<GameObject> resistors = new ArrayList<GameObject>();
		
		for (GameObject obj : collisions) {
			ArrayList<GameObject> pushing = obj.pushy(this.vy, this, list, false);
			resistors.addAll(pushing);
		}
		
		if (resistors.size() != 0) {
			for (GameObject i : resistors) {
				i.crush();
			}
			
			for (GameObject obj : collisions) {
				obj.pushy(this.vy, this, list, false);
			}
			
		}
		
		//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
		
		collisions = new ArrayList<GameObject>();
		collided = false;
		
		this.x += this.vx;

		for (GameObject obj : MainFrame.objects) {
			if (obj.equals(this)) continue;
			if (this.hasCollided(obj) && obj instanceof MovableObject) {
				collisions.add(obj);
				collided = true;
		}}
		
		list = new ArrayList<GameObject>();
		list.add(this);
		resistors = new ArrayList<GameObject>();
		
		for (GameObject obj : collisions) {
			ArrayList<GameObject> pushing = obj.pushx(this.vx, this, list, false);
			resistors.addAll(pushing);
		}
		
		if (resistors.size() != 0) {
			for (GameObject i : resistors) {
				i.crush();
			}
			
			for (GameObject obj : collisions) {
				obj.pushx(this.vx, this, list, false);
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