package skycubedPlatformer.assets.contraptions;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import skycubedPlatformer.Main;
import skycubedPlatformer.assets.SolidPlatform;
import skycubedPlatformer.game.GameObject;
import skycubedPlatformer.game.ObjType;
import skycubedPlatformer.game.Player;
import skycubedPlatformer.menu.GamePanel;

public class Generator extends SolidPlatform {

	GameObject obj;
	ArrayList<GameObject> generated;
	public int generationDelay;
	
	public Generator(double x, double y, double size, GameObject obj, int delay) {
		super(x, y, size, size, GameObject.COLOR_IRON.darker());
		
		this.obj = obj;
		this.generationDelay = delay;
		this.delay = 0;
		this.generated = new ArrayList<GameObject>();
		this.slipperiness = 0.97;
		
	}
	
	public int delay = 0;
	@Override
	public void move() {
		if (this.delay < this.generationDelay) this.delay++;
		boolean occupied = false;
		for (GameObject obj : generated) if (this.hasCollided(obj)) occupied = true;
		if (!occupied && this.delay >= this.generationDelay) {
			this.generate();
			this.delay -= this.generationDelay;
		}
		
		generated.removeIf(obj -> !obj.hasCollided(this));
		
		oldMove(); //do the movements
	}
	
	public void generate() {
		try {
			GameObject newObj = obj.clone();
			newObj.x = this.x;
			newObj.y = this.y;
			this.generated.add(newObj);
			GamePanel.addedObjects.add(newObj);
		} catch (CloneNotSupportedException e) {}
	}
	
	@Override
	public void draw(Graphics g, Player player, double cam_x, double cam_y, double size) {
		super.draw(g, player, cam_x, cam_y, size);
		
		//shine
		int drawX2 = (int) ( (this.x - (this.size_x)/2 - (cam_x - size/2)) * (Main.SIZE/size));
		int drawY2 = (int) ( (size - (this.y + (this.size_y)/2) + (cam_y - size/2)) * (Main.SIZE/size));
		Graphics2D g2d = (Graphics2D) g;
		g2d.setPaint(new GradientPaint(drawX2, drawY2, new Color(255, 255, 255, 200),
			drawX2+(int)(this.size_x* Main.SIZE/size), drawY2+(int)(this.size_y* Main.SIZE/size), new Color(255, 255, 255, 0)));
		g2d.fillRect(drawX2, drawY2, (int)(this.size_x* Main.SIZE/size), (int)(this.size_y* Main.SIZE/size));
		
		//progress bar
		g.setColor(new Color(255,255,255,50));
		int progress = (int) (this.size_y * ((double) this.delay / this.generationDelay));
		int drawY3 = (int) ( (size - (this.y + (this.size_y)/2) + (cam_y - size/2) + (this.size_y - progress)) * (Main.SIZE/size));
		g.fillRect(drawX2, drawY3, (int)(this.size_x* Main.SIZE/size), (int) (progress * (Main.SIZE/size)));
		
		//draw outline
		int brushSize = (int)(5*(Main.SIZE/size));
		int drawX = (int) ( (this.x + brushSize/2 - (this.size_x)/2 - (cam_x - size/2)) * (Main.SIZE/size));
		int drawY = (int) ( (size - (this.y - brushSize/2 + (this.size_y)/2) + (cam_y - size/2)) * (Main.SIZE/size));
		g.setColor(Color.BLACK);
		((Graphics2D) g).setStroke(new BasicStroke(brushSize));
		g.drawRoundRect(drawX-1, drawY-1, (int) ((this.size_x-brushSize+2) * Main.SIZE/size), (int) ((this.size_y-brushSize+2) * Main.SIZE/size), 
		(int)(4*(Main.SIZE/size)), (int)(4*(Main.SIZE/size)));
	}

	
	private void oldMove() {
		ArrayList<GameObject> collisions = new ArrayList<GameObject>();
		boolean collided = false;
		this.y += this.vy;
		
		for (GameObject obj : GamePanel.objects) {	
			if (obj.equals(this)) continue;
			if (this.generated.contains(obj)) continue;
			if (this.hasCollided(obj) && (obj.type.equals(ObjType.MovableObject) || obj.type.equals(ObjType.Player))) {
				collisions.add(obj);
				collided = true;
		}}
		
		ArrayList<GameObject> list = new ArrayList<GameObject>();
		list.add(this);
		ArrayList<GameObject> resistors = new ArrayList<GameObject>();
		
		for (GameObject obj : collisions) {
			ArrayList<GameObject> pushing = obj.pushy(this.vy, this, list, false, false);
			resistors.addAll(pushing);
		}
		
		if (resistors.size() != 0) {
			for (GameObject i : resistors) {
				i.crush();
			}
			
			for (GameObject obj : collisions) {
				obj.pushy(this.vy, this, list, false, false);
			}
			
		}
		
		collisions = new ArrayList<GameObject>();
		collided = false;
		this.x += this.vx;

		for (GameObject obj : GamePanel.objects) {
			if (obj.equals(this)) continue;
			if (this.generated.contains(obj)) continue;
			if (this.hasCollided(obj) && (obj.type.equals(ObjType.MovableObject) || obj.type.equals(ObjType.Player))) {
				collisions.add(obj);
				collided = true;
		}}
		
		list = new ArrayList<GameObject>();
		list.add(this);
		resistors = new ArrayList<GameObject>();
		
		for (GameObject obj : collisions) {
			ArrayList<GameObject> pushing = obj.pushx(this.vx, this, list, false, false);
			resistors.addAll(pushing);
		}
		
		if (resistors.size() != 0) {
			for (GameObject i : resistors) {
				i.crush();
			}
			
			for (GameObject obj : collisions) {
				obj.pushx(this.vx, this, list, false, false);
			}
			
		}
	}

}
