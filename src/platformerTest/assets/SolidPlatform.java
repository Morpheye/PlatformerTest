package platformerTest.assets;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import platformerTest.Main;
import platformerTest.game.GameObject;
import platformerTest.game.MainFrame;
import platformerTest.game.MovableObject;
import platformerTest.game.ObjType;
import platformerTest.game.Player;

public class SolidPlatform extends GameObject {

	public SolidPlatform(double x, double y, double size_x, double size_y, Color color) {
		super(x, y, size_x, size_y, color);
		
		this.type = ObjType.SolidPlatform;
		
		this.movable = false;
		this.solid = true;
		this.slipperiness = 1;
	}
	
	@Override
	public void move() {
		
		//YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY
		
		ArrayList<GameObject> collisions = new ArrayList<GameObject>();
		boolean collided = false;
		
		this.y += this.vy;
		
		for (GameObject obj : MainFrame.objects) {
			if (obj.equals(this)) continue;
			if (this.hasCollided(obj) && obj.type.equals(ObjType.MovableObject)) {
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
			if (this.hasCollided(obj) && obj.type.equals(ObjType.MovableObject)) {
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
	public void draw(Graphics g, Player player, double x, double y, double size) {
		int drawX = (int) (this.x - this.size_x/2 - (x - Main.SIZE_X/2));
		int drawY = (int) (Main.SIZE_Y - (this.y + this.size_y/2) + (y - Main.SIZE_Y/2));
		
		g.setColor(this.color);
		g.fillRoundRect(drawX, drawY, (int) this.size_x, (int) this.size_y, 5, 5);
	}
	
	@Override
	public void crush() {
		return;
	}
	
}