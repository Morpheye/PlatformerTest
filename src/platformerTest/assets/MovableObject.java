package platformerTest.assets;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import platformerTest.game.GameObject;
import platformerTest.game.MainFrame;

public class MovableObject extends GameObject {
	
	public boolean inAir = true;
	public double last_slip = 1;

	public MovableObject(double x, double y, double size_x, double size_y, Color color) {
		super(x, y, size_x, size_y, color);
		
		this.priority = 3;
		this.movable = true;
		this.solid = true;
	}
	
	@Override
	public void move() {
		
		//speed threshold
		this.last_slip = 1;
		
		if (Math.abs(vx) < 0.2) vx = 0;
		if (Math.abs(vy) < 0.2) vy = 0;
		
		//YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY
		
		vy += MainFrame.gravity;
		
		this.y += this.vy;
		//check collisions
		for (GameObject obj : MainFrame.objects) {
			if (!this.exists) continue;
			if (obj.equals(this)) continue;
			if (this.hasCollided(obj) && obj.exists) {
				if (obj.solid) {
					
					if (vy < 0) inAir = false;
					
					if (obj instanceof SolidPlatform) {
						this.last_slip = ((SolidPlatform) obj).slipperiness;
					}
					
					//BONK
					ArrayList<GameObject> list = new ArrayList<GameObject>();
					list.add(this);
					ArrayList<GameObject> pushing = obj.pushy(this.vy, this, list, false);
					if (pushing.size() != 0) {
						
						do {
							if (vy > 0) this.y -= 0.1;
							else this.y += 0.1;
							
							for (GameObject obj2 : MainFrame.objects) {
								if (obj2.equals(this) || obj2.equals(obj)) continue;
								if (this.hasCollided(obj2)) {
									if (obj2 instanceof SolidPlatform) this.crush();
								}
							}
							
						} while (this.hasCollided(obj));
						
					}
					
					this.vy = 0;
					
					
				} else if (obj instanceof LiquidPlatform) {
					this.last_slip = ((LiquidPlatform) obj).slipperiness;
					this.vy *= ((LiquidPlatform) obj).slipperiness;
					
				}
		}}	
		if (this.vy < 0) {
			inAir = true;
		}
		this.vy *= MainFrame.drag;
		
		//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
		
		this.x += this.vx;
		//check collisions
		for (GameObject obj : MainFrame.objects) {
			if (!this.exists) continue;
			if (obj.equals(this)) continue;
			if (this.hasCollided(obj) && obj.solid && obj.exists) {
				
				//BONK
				ArrayList<GameObject> list = new ArrayList<GameObject>();
				list.add(this);
				ArrayList<GameObject> pushing = obj.pushx(this.vx, this, list, false);
				if (pushing.size() != 0) {
					do {
						if (vx > 0) this.x -= 0.1;
						else this.x += 0.1;
						
						for (GameObject obj2 : MainFrame.objects) {
							if (obj2.equals(this) || obj2.equals(obj)) continue;
							if (this.hasCollided(obj2)) {
								if (obj2 instanceof SolidPlatform) this.crush();
							}
						}
					} while (this.hasCollided(obj));
					
				}
				
				this.vx = 0;
			
		}}
		
		this.vx *= MainFrame.drag;
		if (!this.inAir) {
			this.vx *= this.last_slip;
		}
		
	}
	
	@Override
	public ArrayList<GameObject> pushx(double v, GameObject pusher, ArrayList<GameObject> pushers, boolean wall) {
		ArrayList<GameObject> resistors = new ArrayList<GameObject>();
		this.vx += v;
		this.x += v;

		for (GameObject obj : MainFrame.objects) {
			if (obj.equals(this)) continue;
			if (this.hasCollided(obj) && obj.solid && obj.exists) {
				
				if (obj instanceof SolidPlatform) { //imediately pushes into wall
					resistors.add(this);
				} else { //hit new object
					pushers.add(this);
					ArrayList<GameObject> pushing = obj.pushx(this.vx, this, pushers, false);
					resistors.addAll(pushing);
				}
				
				
			}}
		
		if (resistors.size() != 0) {
			this.x -= v;
			this.vx -= v;
		}
		
		return resistors;

	}
	
	@Override
	public ArrayList<GameObject> pushy(double v, GameObject pusher, ArrayList<GameObject> pushers, boolean wall) {
		ArrayList<GameObject> resistors = new ArrayList<GameObject>();
		this.vy += v;
		this.y += v;

		for (GameObject obj : MainFrame.objects) {
			if (obj.equals(this)) continue;
			if (this.hasCollided(obj) && obj.solid && obj.exists) {
				
				if (obj instanceof SolidPlatform) { //imediately pushes into wall
					resistors.add(this);
				} else { //hit new object
					pushers.add(this);
					ArrayList<GameObject> pushing = obj.pushy(this.vy, this, pushers, false);
					resistors.addAll(pushing);
				}
				
				
			}}
		
		if (resistors.size() != 0) {
			this.y -= v;
			this.vy -= v;
		}
		
		return resistors;

	}
	
	@Override
	public void crush() {
		this.exists = false;
	}

}
