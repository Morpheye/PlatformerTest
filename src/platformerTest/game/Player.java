package platformerTest.game;

import java.awt.Color;

import platformerTest.assets.LiquidPlatform;
import platformerTest.assets.MovableObject;

public class Player extends MovableObject {

	double movementSpeed = 0.5;
	double jumpStrength = 25;
	

	
	public Player(double initX, double initY, double size) {
		super(initX, initY, size, size, Color.WHITE);
		
		this.priority = 4;
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
		
		if (this.movingUp & !this.inAir) { //jump
			this.vy += this.jumpStrength;
			this.inAir = true;
		}
		
		for (GameObject obj : MainFrame.objects) { //check for water
			if (obj.equals(this)) continue;
			if (this.hasCollided(obj) && obj instanceof LiquidPlatform) {
				if (this.movingUp) this.vy += 10*this.movementSpeed;
				if (this.movingDown)this.vy -= 5*this.movementSpeed; 
			}
		}
		
		if (this.movingRight) this.vx += this.movementSpeed;
		if (this.movingLeft) this.vx -= this.movementSpeed;
		
		//speed threshold
		super.move();
		
	}
	
	@Override
	public void crush() {
		this.die();
	}
	
	public void die() {
		MainFrame.restartLevel(MainFrame.level);
	}

	
}
