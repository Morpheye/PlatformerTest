package platformerTest.assets;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import platformerTest.Main;
import platformerTest.game.GameObject;
import platformerTest.game.ObjType;
import platformerTest.game.Player;
import platformerTest.menu.GamePanel;

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
		
		for (GameObject obj : GamePanel.objects) {	
			if (obj.equals(this)) continue;
			if (this.hasCollided(obj) && (obj.type.equals(ObjType.MovableObject) || obj.type.equals(ObjType.Player))) {
				collisions.add(obj);
				collided = true;
				obj.vx *= this.slipperiness *= obj.slipperiness;
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

		for (GameObject obj : GamePanel.objects) {
			if (obj.equals(this)) continue;
			if (this.hasCollided(obj) && (obj.type.equals(ObjType.MovableObject) || obj.type.equals(ObjType.Player))) {
				collisions.add(obj);
				collided = true;
		}}
		
		list = new ArrayList<GameObject>();
		list.add(this);
		resistors = new ArrayList<GameObject>();
		
		for (GameObject obj : collisions) {
			System.out.println(obj + " " + this.vx);
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
		super.draw(g, player, x, y, size);
	}
	
	@Override
	public void crush() {
		return;
	}
	
}