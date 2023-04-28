package platformerTest.assets;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import platformerTest.Main;
import platformerTest.game.GameObject;
import platformerTest.game.ObjType;
import platformerTest.game.Player;
import platformerTest.menu.GamePanel;

public class MovableObject extends GameObject {
	
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
		
		this.type = ObjType.MovableObject;
		
		this.density = density;
		this.movable = true;
		this.solid = true;
		this.slipperiness = 1;
	}
	
	@Override
	public void move() {
		
		//speed threshold
		this.last_slip = 1;
		
		this.inLiquid = false;
		this.liquidSlip = 1;
		
		//CHECK FOR LIQUIDS
		for (GameObject obj : GamePanel.objects) {
			if (!this.exists) continue;
			if (obj.equals(this)) continue;
			if (this.hasCollided(obj) && obj.exists && obj.type.equals(ObjType.LiquidPlatform)) {
				liquidSlip = ((LiquidPlatform) obj).slipperiness;
				inLiquid = true;
				this.inAir = true;
				liquidDensity = ((LiquidPlatform) obj).density;
		}}
		
		this.diff = this.density - liquidDensity; //0 = same
		this.dragMultiplier = 1;
		this.lift = 0;
		
		this.vy += GamePanel.gravity;

		//YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY
		
		this.attemptMoveY(this.vy, true);
		
		//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
		
		this.attemptMoveX(this.vx, true);
		
		//LIQUID DRAG
		
		if (this.inLiquid) {
			
			this.vx *= Math.pow(liquidSlip, dragMultiplier);
			this.vy *= Math.pow(liquidSlip, dragMultiplier);
			
			//-1 as -approaches infinity, 0 if 0, 1 as approaches infinity
			
		}
		
		//MULTIPLICATION
		
		if (this.inAir) {
			this.vy *= GamePanel.airDrag;
			this.vx *= GamePanel.airDrag;
			
		}
		if (!this.inAir) {
			this.vx *= last_slip *= this.slipperiness;
		}

		//CHECK BOUNDS

		if (this.y > GamePanel.level.topLimit && !this.equals(GamePanel.player)) GamePanel.deletedObjects.add(this);
		if (this.y < GamePanel.level.bottomLimit && !this.equals(GamePanel.player)) GamePanel.deletedObjects.add(this);
		
		//INERTIA THRESHOLD
		if (Math.abs(vx) < 0.2) vx = 0;
		if (Math.abs(vy) < 0.2) vy = 0;

		this.liquidDensity = 1;
	
	}
	
	public void attemptMoveX(double vx, Boolean isFinal) {
		ArrayList<GameObject> collisions = new ArrayList<GameObject>();
		boolean collidedx = false;
		
		this.x += vx;
		//check collisions
		for (GameObject obj : GamePanel.objects) {
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
				
				for (GameObject obj2 : GamePanel.objects) {
					if (obj2.equals(this) || resistors.contains(obj2)) continue;
					if (this.hasCollided(obj2) && obj2.solid) {
						if (obj2.type.equals(ObjType.SolidPlatform)) this.crush();
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
	
	public void attemptMoveY(double vy, boolean isFinal) {
		ArrayList<GameObject> collisions = new ArrayList<GameObject>();
		boolean collidedy = false;
		
		this.y += vy;
		
		//check collisions
		for (GameObject obj : GamePanel.objects) {
			if (!this.exists) continue;
			if (obj.equals(this)) continue;
			if (this.hasCollided(obj) && obj.exists) {
				if (obj.solid) {
					
					if (vy < 0) this.inAir = false;
					
					if (obj.type.equals(ObjType.SolidPlatform)) {
						last_slip = ((SolidPlatform) obj).slipperiness;
					} if (obj.type.equals(ObjType.MovableObject)) {
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
				
				
				for (GameObject obj2 : GamePanel.objects) { //check for crush
					if (obj2.equals(this) || resistors.contains(obj2)) continue;
					if (this.hasCollided(obj2) && obj2.solid) {
						if (obj2.type.equals(ObjType.SolidPlatform)) this.crush();
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
		
		if (pusher.type.equals(ObjType.SolidPlatform)) weightedV = v;
		else weightedV = v * (pusher.getWeight()/this.getWeight());
		
		this.vx += weightedV;
		this.x += v;

		for (GameObject obj : GamePanel.objects) {
			if (obj.equals(this)) continue;
			if (this.hasCollided(obj) && obj.solid && obj.exists) {
				
				if (v == 0) { //ERROR NEEDS FIX
					resistors.add(this);
				} else if (obj.type.equals(ObjType.SolidPlatform)) { //imediately pushes into wall
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
		
		if (pusher.type.equals(ObjType.SolidPlatform)) weightedV = v;
		else weightedV = v * (pusher.getWeight()/this.getWeight());
		
		this.vy += weightedV;
		this.y += v;

		for (GameObject obj : GamePanel.objects) {
			if (obj.equals(this)) continue;
			if (this.hasCollided(obj) && obj.solid && obj.exists) {
				
				if (v == 0) { //ERROR NEEDS FIX
					resistors.add(this);
				} else if (obj.type.equals(ObjType.SolidPlatform)) { //imediately pushes into wall
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
	public void draw(Graphics g, Player player, double x, double y, double size) {
		super.draw(g, player, x, y, size);
	}
	
	@Override
	public void crush() {
		this.die();
	}
	
	public void die() {
		this.exists = false;
	}

}
