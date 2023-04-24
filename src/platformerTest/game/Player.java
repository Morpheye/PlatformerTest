package platformerTest.game;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import platformerTest.Main;
import platformerTest.assets.SolidPlatform;

public class Player extends GameObject {

	double movementSpeed = 0.5;
	double jumpStrength = 15;
	
	boolean inAir = true;
	
	public Player(double initX, double initY, double size) {
		super(initX, initY, size, size, Color.CYAN);
		
		this.movable = true;
		this.x = initX;
		this.y = initY;
		this.size_x = size;
		this.size_y = size;
		this.movable = true;
		
	}
	
	boolean movingUp = false;
	boolean movingDown = false;
	boolean movingLeft = false;
	boolean movingRight = false;
	
	@Override
	public void move() {
		//speed threshold
		if (Math.abs(vx) < 0.2) vx = 0;
		if (Math.abs(vy) < 0.2) vy = 0;
		
		//y
		
		vy -= Main.GRAVITY;
		if (movingUp & !inAir) { //jump
			vy += jumpStrength;
			inAir = true;
		}
		
		//y
		
		if (movingRight) vx += movementSpeed;
		if (movingLeft) vx -= movementSpeed;
		
		this.y += this.vy;
		//check collisions
		for (GameObject obj : MainFrame.objects) {
			if (obj.equals(this)) break;
			if (this.hasCollided(obj)) {
				if (obj instanceof SolidPlatform) {
					this.vx *= ((SolidPlatform) obj).slipperiness;
				}
				do {
					if (vy > 0) this.y -= 0.1;
					else this.y += 0.1;
				} while (this.hasCollided(obj));
				if (vy < 0) this.inAir = false;
				
				double prev_v = obj.vy;
				
				if (obj.movable) obj.vy += this.vy * (this.getArea()/obj.getArea());
				this.vy = 0;
				this.vy += prev_v * (obj.getArea()/this.getArea());

				
			}
		}
		this.vy *= Main.DRAG;
		if (vy < 0) {
			this.inAir = true;
		}
		
		//x
		
		this.x += this.vx;
		//check collisions
		for (GameObject obj : MainFrame.objects) {
			if (obj.equals(this)) break;
			if (this.hasCollided(obj)) {
				do {
					if (vx > 0) this.x -= 0.1;
					else this.x += 0.1;
				} while (this.hasCollided(obj));
				
				double prev_v = obj.vx;
				
				if (obj.movable) obj.vx += this.vx * (this.getArea()/obj.getArea());
				this.vx = -this.vx;
				this.vx += prev_v * (obj.getArea()/this.getArea());

				
			}
		}
		this.vx *= Main.DRAG;

		
	}

	
}
