package platformerTest.game;

import java.awt.Color;
import java.util.ArrayList;

import platformerTest.assets.LiquidPlatform;
import platformerTest.assets.PushableObject;
import platformerTest.assets.SolidPlatform;

public class MovableObject extends GameObject {
	
	public double slipperiness = 0.97;
	
	
	public boolean inAir = true;
	
	public double last_slip = 1;
	
	public boolean inLiquid = false;
	public double liquidSlip = 1;
	public double liquidDensity = 1;
	
	public double diff = 0;
	public double dragMultiplier = 1;
	public double lift = 0;

	public MovableObject(double x, double y, double size_x, double size_y, Color color, double density) {
		super(x, y, size_x, size_y, color);
		
		this.density = density;
		this.movable = true;
		this.solid = true;
	}
	
	@Override
	public void move() {
		
		//speed threshold
		this.last_slip = 1;
		
		this.inLiquid = false;
		this.liquidSlip = 1;
		
		//CHECK FOR LIQUIDS
		for (GameObject obj : MainFrame.objects) {
			if (!this.exists) continue;
			if (obj.equals(this)) continue;
			if (this.hasCollided(obj) && obj.exists && obj instanceof LiquidPlatform) {
				liquidSlip = ((LiquidPlatform) obj).slipperiness;
				inLiquid = true;
				this.inAir = true;
				liquidDensity = ((LiquidPlatform) obj).density;
		}}
		
		this.diff = this.density - liquidDensity; //0 = same
		this.dragMultiplier = 1;
		this.lift = 0;
		
		if (this.inLiquid) {
			if (diff > 1) {
				dragMultiplier = 1/(diff+0.5); //starts at 1, approaches 0
			}
			lift = MainFrame.gravity * Math.atan(2*diff) / (Math.PI / 2) - MainFrame.gravity;
		}
		
		//YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY
		
		//if (this instanceof Player) this.vy += lift;
		
		this.vy += MainFrame.gravity;

		this.attemptMoveY(this.vy);
		
		//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
		
		this.attemptMoveX(this.vx);
		
		if (this.inLiquid) {
			
			this.vx *= Math.pow(liquidSlip, dragMultiplier);
			this.vy *= Math.pow(liquidSlip, dragMultiplier);
			
			//-1 as -approaches infinity, 0 if 0, 1 as approaches infinity
			
		}
		
		//MULTIPLICATION
		
		if (this.inAir) {
			this.vy *= MainFrame.airDrag;
			this.vx *= MainFrame.airDrag;
			
		}
		if (!this.inAir) {
			this.vx *= last_slip;
		}

		//CHECK BOUNDS

		if (this.y > MainFrame.level.topLimit) this.die();
		if (this.y < MainFrame.level.bottomLimit) this.die();
		
		//INERTIA THRESHOLD
		if (Math.abs(vx) < 0.2) vx = 0;
		if (Math.abs(vy) < 0.2) vy = 0;

		this.liquidDensity = 1;
	
	}
	
	public void attemptMoveX(double vx) {
		ArrayList<GameObject> collisions = new ArrayList<GameObject>();
		boolean collidedx = false;
		
		this.x += vx;
		//check collisions
		for (GameObject obj : MainFrame.objects) {
			if (!this.exists) continue;
			if (obj.equals(this)) continue;
			if (this.hasCollided(obj) && obj.solid && obj.exists) {
				
				collidedx = true;
				collisions.add(obj);
			
		}}
		
		//BONK
		ArrayList<GameObject> list = new ArrayList<GameObject>();
		list.add(this);
		ArrayList<GameObject> resistors = new ArrayList<GameObject>();
		
		for (GameObject obj : collisions) {
			if (vx == 0) continue;
			ArrayList<GameObject> pushing = obj.pushx(vx, this, list, false);
			resistors.addAll(pushing);
		}
		
		if (resistors.size() != 0) {
			
			boolean stillColliding = false;
			
			do {
				
				if (vx > 0) this.x -= 0.01;
				else if (vx < 0) this.x += 0.01;
				
				for (GameObject obj2 : MainFrame.objects) {
					if (obj2.equals(this) || resistors.contains(obj2)) continue;
					if (this.hasCollided(obj2) && obj2.solid) {
						if (obj2 instanceof SolidPlatform) this.crush();
					}
				}
				
				stillColliding = false;
				
				for (GameObject obj : collisions) {
					stillColliding = stillColliding || this.hasCollided(obj);
				}
				
			} while (stillColliding);
			
		}
		
		if (collidedx) this.vx = 0;
	}
	
	public void attemptMoveY(double vy) {
		ArrayList<GameObject> collisions = new ArrayList<GameObject>();
		boolean collidedy = false;
		
		this.y += vy;
		
		//check collisions
		for (GameObject obj : MainFrame.objects) {
			if (!this.exists) continue;
			if (obj.equals(this)) continue;
			if (this.hasCollided(obj) && obj.exists) {
				if (obj.solid) {
					
					if (vy < 0) this.inAir = false;
					
					if (obj instanceof SolidPlatform) {
						last_slip = ((SolidPlatform) obj).slipperiness;
					} if (obj instanceof MovableObject) {
						last_slip = ((MovableObject) obj).slipperiness;
					}
					
					collidedy = true;
					collisions.add(obj);
					
				}
		}}	
		
		//BONK
		ArrayList<GameObject> list = new ArrayList<GameObject>();
		list.add(this);
		ArrayList<GameObject> resistors = new ArrayList<GameObject>();
		
		for (GameObject obj : collisions) {
			ArrayList<GameObject> pushing = obj.pushy(vy, this, list, false);
			
			resistors.addAll(pushing);
		}
		
		if (resistors.size() != 0) {
			
			boolean stillColliding = false;
			
			do {
				
				if (vy > 0) this.y -= 0.01;
				else if (vy < 0) this.y += 0.01;
				
				
				for (GameObject obj2 : MainFrame.objects) { //check for crush
					if (obj2.equals(this) || resistors.contains(obj2)) continue;
					if (this.hasCollided(obj2) && obj2.solid) {
						if (obj2 instanceof SolidPlatform) this.crush();
					}
				}
				
				stillColliding = false;
				
				for (GameObject obj : collisions) {
					stillColliding = stillColliding || this.hasCollided(obj);
				}
				
				if (vy == 0) stillColliding = true;
				
			} while (stillColliding);
			
		}
		
		if (collidedy) this.vy = 0;
		
		if (this.vy < 0) {
			inAir = true;
		}
	}
	
	@Override
	public ArrayList<GameObject> pushx(double v, GameObject pusher, ArrayList<GameObject> pushers, boolean wall) {
		ArrayList<GameObject> resistors = new ArrayList<GameObject>();
		
		double weightedV;
		
		if (pusher instanceof SolidPlatform) weightedV = v;
		else weightedV = v * (pusher.getWeight()/this.getWeight());
		
		this.vx += weightedV;
		this.x += v;

		for (GameObject obj : MainFrame.objects) {
			if (obj.equals(this)) continue;
			if (this.hasCollided(obj) && obj.solid && obj.exists) {
				
				if (v == 0) { //ERROR NEEDS FIX
					resistors.add(this);
				} else if (obj instanceof SolidPlatform) { //imediately pushes into wall
					resistors.add(this);
				} else { //hit new object
					if (obj.equals(pusher)) { //ERROR NEEDS FIX
						resistors.add(this);
						continue;
					}
					pushers.add(this);
					ArrayList<GameObject> pushing = obj.pushx(v, this, pushers, false);
					resistors.addAll(pushing);
				}
				
				
			}}
		
		if (resistors.size() != 0) {
			this.x -= v;
			this.vx -= weightedV;
		}
		
		return resistors;

	}
	
	@Override
	public ArrayList<GameObject> pushy(double v, GameObject pusher, ArrayList<GameObject> pushers, boolean wall) {
		
		if (v>0) this.inAir = false;
		ArrayList<GameObject> resistors = new ArrayList<GameObject>();
		
		double weightedV;
		
		if (pusher instanceof SolidPlatform) weightedV = v;
		else weightedV = v * (pusher.getWeight()/this.getWeight());
		
		this.vy += weightedV;
		this.y += v;

		for (GameObject obj : MainFrame.objects) {
			if (obj.equals(this)) continue;
			if (this.hasCollided(obj) && obj.solid && obj.exists) {
				
				if (v == 0) { //ERROR NEEDS FIX
					resistors.add(this);
				} else if (obj instanceof SolidPlatform) { //imediately pushes into wall
					resistors.add(this);
					
				} else { //hit new object
					if (obj.equals(pusher)) { //ERROR NEEDS FIX
						System.out.println("Error at " + this);
						resistors.add(this);
						continue;
					}
					ArrayList<GameObject> newPushers = new ArrayList<GameObject>();
					newPushers.addAll(pushers);
					newPushers.add(this);
					ArrayList<GameObject> pushing = obj.pushy(v, this, newPushers, false);
					resistors.addAll(pushing);
				}
				
				
			}}
		
		if (resistors.size() != 0) {
			this.y -= v;
			this.vy -= weightedV;
		}
		
		
		
		return resistors;

	}
	
	@Override
	public void crush() {
		this.die();
	}
	
	public void die() {
		this.exists = false;
	}

}
