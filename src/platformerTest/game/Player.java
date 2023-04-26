package platformerTest.game;

import java.awt.Color;
import java.util.ArrayList;

import platformerTest.assets.LiquidPlatform;

public class Player extends MovableObject {

	double movementSpeed = 0.5;
	double jumpStrength = 25;
	
	boolean movingInLiquid = false;
	
	public Player(double initX, double initY, double size) {
		super(initX, initY, size, size, Color.WHITE, 1.0);
		
		this.movable = true;
		this.x = initX;
		this.y = initY;
		this.size_x = size;
		this.size_y = size;
		
	}
	
	boolean movingUp = false;
	boolean movingDown = false;
	boolean movingLeft = false;
	boolean movingRight = false;
	
	@Override
	public void move() {
		
		this.movingInLiquid = false;
		this.liquidDensity = 1;
		
		for (GameObject obj : MainFrame.objects) { //check for water
			if (obj.equals(this)) continue;
			if (this.hasCollided(obj) && obj instanceof LiquidPlatform && obj.exists) {
				this.movingInLiquid = true;
				this.inAir = true;
				this.liquidDensity = ((LiquidPlatform) obj).density;
			}
		}
		
		if (this.inLiquid) {
			double diff = this.density - liquidDensity;
			double lift = MainFrame.gravity * Math.atan(2*diff) / (Math.PI / 2) - MainFrame.gravity;
			
			this.vy += lift;

		}
		
		if (movingInLiquid) {
			double slowdown = 1;
			double diff = this.density - liquidDensity;
			if (diff > 1) {
				slowdown = 1/(diff+0.5); //starts at 1, approaches 0
			}
			
			if (this.movingUp) this.vy += Math.pow(1, slowdown)*this.movementSpeed;
			if (this.movingDown)this.vy -= Math.pow(1, slowdown)*this.movementSpeed;
			if (this.movingRight) this.vx += Math.pow(1, slowdown)*this.movementSpeed;
			if (this.movingLeft) this.vx -= Math.pow(1, slowdown)*this.movementSpeed;
			
		} else {
			if (this.movingUp & !this.inAir) { //jump
				this.vy += this.jumpStrength;
				this.inAir = true;
			}
			if (this.movingRight) this.vx += this.movementSpeed;
			if (this.movingLeft) this.vx -= this.movementSpeed;
		}
		
		//then apply movableobject physics
		super.move();
		
	}
	
	@Override
	public void crush() {
		super.crush();
		this.die();
	}
	
	@Override
	public void die() {
		MainFrame.restartLevel(MainFrame.level);
	}
	

	
}
