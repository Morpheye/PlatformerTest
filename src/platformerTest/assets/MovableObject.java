package platformerTest.assets;

import java.awt.Color;

import platformerTest.Main;
import platformerTest.game.GameObject;
import platformerTest.game.MainFrame;

public class MovableObject extends GameObject {

	public MovableObject(double x, double y, double size_x, double size_y, Color color) {
		super(x, y, size_x, size_y, color);
		
		this.movable = true;
	}
	
	@Override
	public void move() {
		//speed threshold
		if (Math.abs(vx) < 0.2) vx = 0;
		if (Math.abs(vy) < 0.2) vy = 0;
		
		//y
		
		vy -= Main.GRAVITY;

		
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

				double prev_v = obj.vy;
				
				if (obj.movable) obj.vy += this.vy * (this.getArea()/obj.getArea());
				this.vy = 0;
				this.vy += prev_v * (obj.getArea()/this.getArea());
				
			}
		}
		this.vy *= Main.DRAG;

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
