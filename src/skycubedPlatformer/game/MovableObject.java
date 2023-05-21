package skycubedPlatformer.game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import skycubedPlatformer.assets.LiquidPlatform;
import skycubedPlatformer.assets.SolidPlatform;
import skycubedPlatformer.menu.GamePanel;

public class MovableObject extends GameObject {
	
	public boolean inAir = true;
	
	public double last_slip = 1;
	
	public boolean inLiquid = false;
	public double liquidSlip = 1;
	public double liquidDensity = 1;
	public ArrayList<Class<? extends LiquidPlatform>> liquids;
	
	public double diff = 0;
	public double dragMultiplier = 1;
	public double lift = 0;

	public MovableObject(double x, double y, double size_x, double size_y, Color color, double density) {
		super(x, y, size_x, size_y, color);
		
		this.drawLayer = -5;
		
		this.density = density;
		this.movable = true;
		this.solid = true;
		this.slipperiness = 1;
		this.liquids = new ArrayList<Class<? extends LiquidPlatform>>();
	}
	
	@Override
	public void move() {
		
		//speed threshold
		this.last_slip = 1;

		this.inLiquid = false;
		this.liquidSlip = 1;
		this.liquids.clear();
		
		//CHECK FOR LIQUIDS
		for (GameObject obj : GamePanel.objects) {
			if (!this.exists) continue;
			if (obj.equals(this)) continue;
			if (this.hasCollided(obj) && obj.exists && obj.type.equals(ObjType.LiquidPlatform)) {
				liquidSlip = ((LiquidPlatform) obj).slipperiness;
				inLiquid = true;
				this.inAir = true;
				liquidDensity = ((LiquidPlatform) obj).density;
				this.liquids.add(((LiquidPlatform) obj).getClass());
				((LiquidPlatform) obj).onTick(this);
		}}
		
		this.diff = this.density - liquidDensity; //0 = same
		this.dragMultiplier = 1;
		this.lift = 0;
		
		if (this.inLiquid) {
			double diff = this.density - liquidDensity;
			double lift = GamePanel.gravity * Math.atan(2*diff) / (Math.PI / 2) - GamePanel.gravity;
			
			this.vy += lift;
		}
		
		this.vy += GamePanel.gravity;

		//YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY
		
		this.inAir = true;
		this.attemptMoveY(this.vy, true);
		
		//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
		
		this.attemptMoveX(this.vx, true);
		
		//LIQUID DRAG
		if (this.inLiquid) {
			this.vx *= Math.pow(liquidSlip, dragMultiplier);
			this.vy *= Math.pow(liquidSlip, dragMultiplier);
			//-1 as -approaches infinity, 0 if 0, 1 as approaches infinity
		}
		
		//AIR DRAG
		if (this.inAir) {
			this.vy *= GamePanel.airDrag;
			this.vx *= GamePanel.airDrag;
		}
		
		//GROUND DRAG
		if (!this.inAir) {
			this.vx *= last_slip *= this.slipperiness;
		}

		//CHECK BOUNDS
		if (this.y > GamePanel.level.topLimit && !this.equals(GamePanel.player)) GamePanel.deletedObjects.add(this);
		if (this.y < GamePanel.level.bottomLimit && !this.equals(GamePanel.player)) GamePanel.deletedObjects.add(this);
		
		//INERTIA THRESHOLD
		if (Math.abs(vx) < 0.01) vx = 0;
		if (Math.abs(vy) < 0.01) vy = 0;
		this.liquidDensity = 1;
	
	}
	
	final double threshold = 0.0001;
	
	public void attemptMoveX(double vx, Boolean isFinal) {
		ArrayList<GameObject> collisions = new ArrayList<GameObject>();
		ArrayList<GameObject> ignoreObjs = new ArrayList<GameObject>();
		boolean collidedx = false;
		
		for (GameObject obj : GamePanel.objects) {
			if (!this.exists || obj.equals(this)) continue;
			if (this.hasCollided(obj) && obj.solid && obj.exists) ignoreObjs.add(obj); 
		}
		
		this.x += vx; //move the object
		
		if (!this.solid) return;

		for (GameObject obj : GamePanel.objects) { //check for all collisions after moving
			if (!this.exists) continue;
			if (obj.equals(this) || ignoreObjs.contains(obj)) continue;
			if (this.hasCollided(obj) && obj.solid && obj.exists) {
				collidedx = true;
				collisions.add(obj);
		}}
		
		ArrayList<GameObject> list = new ArrayList<GameObject>(); //start pushing the objects.
		list.add(this);
		ArrayList<GameObject> resistors = new ArrayList<GameObject>(); //total objects resisting the push
		for (GameObject obj : collisions) {
			ArrayList<GameObject> pushing;
			if (this.type.equals(ObjType.Player) && obj.type.equals(ObjType.Creature)) { //push by amount you moved
				pushing = obj.pushx(vx, this, list, false, false);
			} else if (this.type.equals(ObjType.Creature) && obj.type.equals(ObjType.Player)) { 
				pushing = obj.pushx(vx, this, list, false, false);
			} else {
				pushing = obj.pushx(vx, this, list, false, isFinal);
			}
			resistors.addAll(pushing);
		}
		
		if (resistors.size() != 0) { //something is resisting
			//of the resistors, find the lowest bounding box to snap to
			double closestBound = (vx > 0) ? Double.MAX_VALUE : -Double.MAX_VALUE;
			GameObject closestObj = resistors.get(0);
			for (GameObject i : resistors) { //loop through
				if (vx > 0) if (i.getLowerBoundX() < closestBound) {
					closestBound = i.getLowerBoundX() - threshold;
					closestObj = i;
				}
				if (vx < 0) if (i.getHigherBoundX() > closestBound) {
					closestBound = i.getHigherBoundX() + threshold;
					closestObj = i;
				}
				
				
			}
			
			//now attempt to move object back!
			double oldX = this.x;
			if (vx > 0) this.x = closestBound - this.size_x/2;
			if (vx < 0) this.x = closestBound + this.size_x/2;
			double snapback = this.x - oldX; //will be positive if originally moving left, negative if originally moving right
			
			//now check if the snapback caused any collisions (SOMETHING IS GETTING CRUSHED)
			for (GameObject obj : GamePanel.objects) {
				if (!this.exists) continue;
				if (obj.equals(this) || ignoreObjs.contains(obj)) continue;
				if (this.hasCollided(obj) && obj.solid && obj.exists) {
					if (obj.type.equals(ObjType.SolidPlatform)) this.crush();
					else System.out.println("x " + this.getClass().getSimpleName() + " " + obj.getClass().getSimpleName() + " " + snapback);
					
			}}
			
		}
		
		if (collidedx && isFinal) {
			if (Math.abs(this.vx) > 12 && (this.type.equals(ObjType.Player) || this.hasCollided(GamePanel.MainFrameObj))) {
				GamePanel.createShake(3, this.getWeight()/500*(12-Math.abs(this.vx)));
			}
			this.vx = 0; //if anything was hit, stop moving.
		}
	}
	
	public void attemptMoveY(double vy, boolean isFinal) {
		ArrayList<GameObject> collisions = new ArrayList<GameObject>();
		ArrayList<GameObject> ignoreObjs = new ArrayList<GameObject>();
		boolean collidedy = false;
		
		for (GameObject obj : GamePanel.objects) {
			if (!this.exists || obj.equals(this)) continue;
			if (this.hasCollided(obj) && obj.solid && obj.exists) ignoreObjs.add(obj); 
		}
		this.y += vy; //move the object
		
		if (!this.solid) return;

		for (GameObject obj : GamePanel.objects) { //check for all collisions after moving
			if (!this.exists) continue;
			if (obj.equals(this) || ignoreObjs.contains(obj)) continue;
			if (this.hasCollided(obj) && obj.solid && obj.exists) {
				if (vy < 0) this.inAir = false;
				
				if (obj.type.equals(ObjType.SolidPlatform)) {
					last_slip = ((SolidPlatform) obj).slipperiness;
				} else if (obj.type.equals(ObjType.MovableObject)) {
					last_slip = ((MovableObject) obj).slipperiness;
				} else if (obj.type.equals(ObjType.Creature)) {
					last_slip = GamePanel.level.airDrag;
				}
				
				collidedy = true;
				collisions.add(obj);
		}}
		
		ArrayList<GameObject> list = new ArrayList<GameObject>(); //start pushing the objects.
		list.add(this);
		ArrayList<GameObject> resistors = new ArrayList<GameObject>(); //total objects resisting the push
		for (GameObject obj : collisions) {
			ArrayList<GameObject> pushing;
			if (this.type.equals(ObjType.Player) && obj.type.equals(ObjType.Creature)) { //push by amount you moved
				pushing = obj.pushy(vy, this, list, false, false);
			} else if (this.type.equals(ObjType.Creature) && obj.type.equals(ObjType.Player)) { 
				pushing = obj.pushy(vy, this, list, false, false);
			} else {
				pushing = obj.pushy(vy, this, list, false, isFinal);
			}
			resistors.addAll(pushing);
		}
		
		if (resistors.size() != 0) { //something is resisting
			//of the resistors, find the lowest bounding box to snap to
			double closestBound = (this.vy > 0) ? Double.MAX_VALUE : -Double.MAX_VALUE;
			GameObject closestObj = resistors.get(0);
			for (GameObject i : resistors) { //loop through
				if (vy > 0) if (i.getLowerBoundY() < closestBound) {
					closestBound = i.getLowerBoundY() - threshold;
					closestObj = i;
				}
				if (vy < 0) if (i.getHigherBoundY() > closestBound) {
					closestBound = i.getHigherBoundY() + threshold;
					closestObj = i;
				}
				
				
			}
			
			//now attempt to move object back!
			double oldY = this.y;
			if (vy > 0) this.y = closestBound - this.size_y/2;
			if (vy < 0) this.y = closestBound + this.size_y/2;
			double snapback = this.y - oldY; //will be positive if originally moving left, negative if originally moving right
			
			//now check if the snapback caused any collisions (SOMETHING IS GETTING CRUSHED)
			for (GameObject obj : GamePanel.objects) {
				if (!this.exists) continue;
				if (obj.equals(this) || ignoreObjs.contains(obj)) continue;
				if (this.hasCollided(obj) && obj.solid && obj.exists) {
					if (obj.type.equals(ObjType.SolidPlatform)) this.crush();
					else System.out.println("y " + this.getClass().getSimpleName() + " " + obj.getClass().getSimpleName() + " " + snapback + " " + this.vy);
					
			}}
			
			if (closestObj.vx != 0 && closestObj.type.equals(ObjType.SolidPlatform) && isFinal) {
				this.attemptMoveX(closestObj.vx, false);
			}
			
		}
		
		if (collidedy && isFinal) {
			if (Math.abs(this.vy) > 12 && (this.type.equals(ObjType.Player) || this.hasCollided(GamePanel.MainFrameObj))) {
				GamePanel.createShake(3, this.getWeight()/500*(12-Math.abs(this.vy)));
			}
			this.vy = 0;
		}
		
		if (this.vy < 0) {
			//inAir = true;
		}
	}
	
	@Override
	public ArrayList<GameObject> pushx(double v, GameObject pusher, ArrayList<GameObject> pushers, boolean wall, boolean keepV) {
		ArrayList<GameObject> resistors = new ArrayList<GameObject>();
		ArrayList<GameObject> ignoredObjs = new ArrayList<GameObject>();
		double weightedV;
		
		if (pusher.type.equals(ObjType.SolidPlatform)) weightedV = v; //calculations for density & force & physics shit
		else weightedV = v * (pusher.getWeight()/this.getWeight());
		if (!keepV) weightedV = 0;
		
		for (GameObject obj : GamePanel.objects) {
			if (obj.equals(this)) continue;
			if (this.hasCollided(obj) && obj.solid && obj.exists) ignoredObjs.add(obj);
		}
		
		this.vx += weightedV; //change density
		this.x += v; //move the object

		for (GameObject obj : GamePanel.objects) {
			if (obj.equals(this)) continue;
			if (this.hasCollided(obj) && obj.solid && obj.exists) {
				if (v == 0 || ignoredObjs.contains(obj)) { //ERROR NEEDS FIX
					//System.out.println("Error at " + this.getClass().getSimpleName() + ": Physics error");
					resistors.add(this);
				} else if (obj.type.equals(ObjType.SolidPlatform)) { //imediately pushes into wall
					resistors.add(this);
				} else { //hit new object
					if (obj.equals(pusher)) { //ERROR NEEDS FIX
						//System.out.println("Error at " + this.getClass().getSimpleName() + ": Physics error on x");
						resistors.add(this);
						continue;
					}
					pushers.add(this);
					ArrayList<GameObject> pushing = obj.pushx(v, this, pushers, false, false);
					resistors.addAll(pushing);
					if (pushing.size() != 0 ) resistors.add(this);
				}
				
				
			}}
		
		if (resistors.size() != 0) { //I cannot move so I'm not moving.
			this.x -= v;
			this.vx -= weightedV;
			for (GameObject obj : GamePanel.objects) {
				if (obj.equals(this) || pushers.contains(obj)) continue;
				if (!this.solid || !obj.solid) continue; 
			}
		}
		
		return resistors;

	}
	
	@Override
	public ArrayList<GameObject> pushy(double v, GameObject pusher, ArrayList<GameObject> pushers, boolean wall, boolean keepV) {
		if (v<0 && (pusher.type.equals(ObjType.Creature) || pusher.type.equals(ObjType.Player))) {
			((LivingObject) pusher).inAir = false;
		}
		
		ArrayList<GameObject> resistors = new ArrayList<GameObject>();
		ArrayList<GameObject> ignoredObjs = new ArrayList<GameObject>();
		
		double weightedV;
		
		if (pusher.type.equals(ObjType.SolidPlatform)) weightedV = v;
		else weightedV = v * (pusher.getWeight()/this.getWeight());
		if (!keepV) weightedV = 0;
		
		for (GameObject obj : GamePanel.objects) {
			if (obj.equals(this)) continue;
			if (this.hasCollided(obj) && obj.solid && obj.exists) ignoredObjs.add(obj);
		}
		
		this.vy += weightedV;
		this.y += v;

		for (GameObject obj : GamePanel.objects) {
			if (obj.equals(this) || ignoredObjs.contains(obj)) continue;
			if (this.hasCollided(obj) && obj.solid && obj.exists) {
				
				if (v == 0 || ignoredObjs.contains(obj)) { //ERROR NEEDS FIX
					//System.out.println("Error at " + this.getClass().getSimpleName() + ": Physics error");
					resistors.add(this);
				} else if (obj.type.equals(ObjType.SolidPlatform)) { //imediately pushes into wall
					resistors.add(this);
					
				} else { //hit new object
					if (obj.equals(pusher)) { //ERROR NEEDS FIX
						//System.out.println("Error at " + this.getClass().getSimpleName() + ": Physics error on y");
						resistors.add(this);
						continue;
					}
					pushers.add(this);
					ArrayList<GameObject> pushing = obj.pushx(v, this, pushers, false, false);
					resistors.addAll(pushing);
					if (pushing.size() != 0 ) resistors.add(this);
				}
				
				
			}}
		
		if (resistors.size() != 0) {
			this.y -= v;
			this.vy -= weightedV;
			for (GameObject obj : GamePanel.objects) {
				if (obj.equals(this) || pushers.contains(obj)) continue;
				if (!this.solid || !obj.solid) continue; 
			}
				
		}
		
		if (weightedV > 0 && !pusher.type.equals(ObjType.Player) && !pusher.type.equals(ObjType.Creature)) {
			this.inAir = false;
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
