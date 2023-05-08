package platformerTest.game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import platformerTest.assets.LiquidPlatform;
import platformerTest.assets.SolidPlatform;
import platformerTest.menu.GamePanel;

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
		
		this.type = ObjType.MovableObject;
		
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
	
	public void attemptMoveX(double vx, Boolean isFinal) {
		ArrayList<GameObject> collisions = new ArrayList<GameObject>();
		boolean collidedx = false;
		
		for (GameObject obj : GamePanel.objects) { //check for all collisions after moving
			if (!this.exists) continue;
			if (obj.equals(this)) continue;
			if (this.hasCollided(obj) && obj.solid && obj.exists) {
		}}
		
		this.x += vx; //move the object

		for (GameObject obj : GamePanel.objects) { //check for all collisions after moving
			if (!this.exists) continue;
			if (obj.equals(this)) continue;
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
				pushing = obj.pushx(this.vx, this, list, false, false);
			} else if (this.type.equals(ObjType.Creature) && obj.type.equals(ObjType.Player)) { 
				pushing = obj.pushx(this.vx, this, list, false, false);
			} else {
				pushing = obj.pushx(this.vx, this, list, false, isFinal);
			}
			resistors.addAll(pushing);
		}
		
		if (resistors.size() != 0) { //something is resisting
			//of the resistors, find the lowest bounding box to snap to
			double closestBound = (this.vx > 0) ? Double.MAX_VALUE : -Double.MAX_VALUE;
			GameObject closestObj = resistors.get(0);
			for (GameObject i : resistors) { //loop through
				if (this.vx > 0) if (i.getLowerBoundX() < closestBound) {
					closestBound = i.getLowerBoundX();
					closestObj = i;
				}
				if (this.vx < 0) if (i.getHigherBoundX() > closestBound) {
					closestBound = i.getHigherBoundX();
					closestObj = i;
				}
				
				
			}
			
			//now attempt to move object back!
			double oldX = this.x;
			if (this.vx > 0) this.x = closestBound - this.size_x/2;
			if (this.vx < 0) this.x = closestBound + this.size_x/2;
			double snapback = this.x - oldX; //will be positive if originally moving left, negative if originally moving right
			
			//now check if the snapback caused any collisions (SOMETHING IS GETTING CRUSHED)
			for (GameObject obj : GamePanel.objects) {
				if (!this.exists) continue;
				if (obj.equals(this)) continue;
				if (this.hasCollided(obj) && obj.solid && obj.exists) {
					if (obj.type.equals(ObjType.SolidPlatform)) this.crush();
					else System.out.println(this.getClass().getSimpleName() + " " + obj.getClass().getSimpleName() + " " + snapback);
					
			}}
			
		}
		
		
		
		if (collidedx) this.vx = 0; //if anything was hit, stop moving.
	}
	
	public void attemptMoveY(double vy, boolean isFinal) {
		ArrayList<GameObject> collisions = new ArrayList<GameObject>();
		boolean collidedy = false;
		
		this.y += vy; //move the object

		for (GameObject obj : GamePanel.objects) { //check for all collisions after moving
			if (!this.exists) continue;
			if (obj.equals(this)) continue;
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
				pushing = obj.pushy(this.vy, this, list, false, false);
			} else if (this.type.equals(ObjType.Creature) && obj.type.equals(ObjType.Player)) { 
				pushing = obj.pushy(this.vy, this, list, false, false);
			} else {
				pushing = obj.pushy(this.vy, this, list, false, isFinal);
			}
			resistors.addAll(pushing);
		}
		
		if (resistors.size() != 0) { //something is resisting
			//of the resistors, find the lowest bounding box to snap to
			double closestBound = (this.vy > 0) ? Double.MAX_VALUE : -Double.MAX_VALUE;
			GameObject closestObj = resistors.get(0);
			for (GameObject i : resistors) { //loop through
				if (this.vy > 0) if (i.getLowerBoundY() < closestBound) {
					closestBound = i.getLowerBoundY();
					closestObj = i;
				}
				if (this.vy < 0) if (i.getHigherBoundY() > closestBound) {
					closestBound = i.getHigherBoundY();
					closestObj = i;
				}
				
				
			}
			
			//now attempt to move object back!
			double oldY = this.y;
			if (this.vy > 0) this.y = closestBound - this.size_y/2;
			if (this.vy < 0) this.y = closestBound + this.size_y/2;
			double snapback = this.y - oldY; //will be positive if originally moving left, negative if originally moving right
			
			//now check if the snapback caused any collisions (SOMETHING IS GETTING CRUSHED)
			for (GameObject obj : GamePanel.objects) {
				if (!this.exists) continue;
				if (obj.equals(this)) continue;
				if (this.hasCollided(obj) && obj.solid && obj.exists) {
					if (obj.type.equals(ObjType.SolidPlatform)) this.crush();
					else System.out.println(this.getClass().getSimpleName() + " " + obj.getClass().getSimpleName() + " " + snapback);
					
			}}
			
		}
		
		if (collidedy) this.vy = 0;
		
		if (this.vy < 0) {
			inAir = true;
		}
	}
	
	@Override
	public ArrayList<GameObject> pushx(double v, GameObject pusher, ArrayList<GameObject> pushers, boolean wall, boolean keepV) {
		ArrayList<GameObject> resistors = new ArrayList<GameObject>();
		double weightedV;
		
		if (pusher.type.equals(ObjType.SolidPlatform)) weightedV = v; //calculations for density & force & physics shit
		else weightedV = v * (pusher.getWeight()/this.getWeight());
		if (!keepV) weightedV = 0;
		
		this.vx += weightedV; //change density
		this.x += v; //move the object

		for (GameObject obj : GamePanel.objects) {
			if (obj.equals(this)) continue;
			if (this.hasCollided(obj) && obj.solid && obj.exists) {
				if (v == 0) { //ERROR NEEDS FIX
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
		}
		
		return resistors;

	}
	
	@Override
	public ArrayList<GameObject> pushy(double v, GameObject pusher, ArrayList<GameObject> pushers, boolean wall, boolean keepV) {
		
		if (v>0 && !pusher.type.equals(ObjType.Creature)) this.inAir = false;
		
		ArrayList<GameObject> resistors = new ArrayList<GameObject>();
		
		double weightedV;
		
		if (pusher.type.equals(ObjType.SolidPlatform)) weightedV = v;
		else weightedV = v * (pusher.getWeight()/this.getWeight());
		if (!keepV) weightedV = 0;
		
		this.vy += weightedV;
		this.y += v;

		for (GameObject obj : GamePanel.objects) {
			if (obj.equals(this)) continue;
			if (this.hasCollided(obj) && obj.solid && obj.exists) {
				
				if (v == 0) { //ERROR NEEDS FIX
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
