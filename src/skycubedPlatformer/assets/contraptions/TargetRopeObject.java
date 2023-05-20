package skycubedPlatformer.assets.contraptions;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;

import skycubedPlatformer.Main;
import skycubedPlatformer.assets.PushableObject;
import skycubedPlatformer.game.GameObject;
import skycubedPlatformer.game.Player;
import skycubedPlatformer.menu.GamePanel;

public class TargetRopeObject extends PushableObject {

	public PushableObject obj;
	public PushableObject target;
	public double ropeLength;
	public double tension;
	public boolean snapped;
	
	public TargetRopeObject(PushableObject obj, PushableObject target, double ropeLength, double tension) {
		super(obj.x, obj.y, obj.size_x, obj.size_y, obj.color, obj.density, obj.slipperiness);
		
		this.obj = obj; //purely for drawing purposes
		this.target = target;
		this.ropeLength = ropeLength;
		this.tension = tension;
		
		this.snapped = false;
		
	}
	
	@Override
	public void draw(Graphics g, Player player, double x, double y, double size) {
		Graphics2D g2d = ((Graphics2D) g);
		
		if (!this.snapped) { 
			int drawX = (int) ( (this.x - (x - size/2)) * (Main.SIZE/size));
			int drawY = (int) ( (size - (this.y) + (y - size/2)) * (Main.SIZE/size));
		
			int chainX = (int) ( (this.target.x - (x - size/2)) * (Main.SIZE/size));
			int chainY = (int) ( (size - (this.target.y) + (y - size/2)) * (Main.SIZE/size));
		
			g2d.setStroke(new BasicStroke((int) (5 * (Main.SIZE/size))));
			g2d.setColor(GameObject.COLOR_PLANKS);
			g2d.drawLine(drawX, drawY, chainX, chainY);
		
			//redraw target
			target.draw(g, player, x, y, size);
		}
		
		obj.x = this.x;
		obj.y = this.y;
		obj.draw(g, player, x, y, size); //draw the actual object
	}
	
	@Override //for chains, this should fully override the super.move() and not reference it
	public void move() {
		if (!this.exists) this.snapped = true;
		if (!this.target.exists) this.snapped = true; 
		
		if (!this.snapped) { 
			double chainLengthY = this.y - this.target.y;
			double chainLengthX = this.x - this.target.x;
			double chainLength = Math.hypot(chainLengthX, chainLengthY);
	
			if (chainLength > this.ropeLength) {
				double diffX = (chainLengthX * (chainLength / this.ropeLength) - chainLengthX) * (this.tension / 100);
				double diffY = (chainLengthY * (chainLength / this.ropeLength) - chainLengthY) * (this.tension / 100);
				
				double weightTotal = this.getWeight() + this.target.getWeight();
				
				this.vx -= diffX / (this.getWeight() / weightTotal);
				this.vy -= diffY / (this.getWeight() / weightTotal);
				
				this.target.vx += diffX / (this.target.getWeight() / weightTotal);
				this.target.vy += diffY / (this.target.getWeight() / weightTotal);
			}
		}
		
		super.move();
	}
	
	@Override
	public boolean hasCollided(GameObject obj) {
		if (obj.equals(GamePanel.MainFrameObj)) return true;
		else return super.hasCollided(obj);
	}


}
