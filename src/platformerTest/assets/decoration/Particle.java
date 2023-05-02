package platformerTest.assets.decoration;

import java.awt.Color;
import java.awt.Graphics;

import platformerTest.assets.DecorationObject;
import platformerTest.game.Player;
import platformerTest.menu.GamePanel;

public class Particle extends DecorationObject{

	public boolean gravity;
	public int lifetime;
	
	public Particle(double x, double y, double size_x, double size_y, Color color) {
		super(x, y, size_x, size_y, color);

		this.solid = false;
		this.gravity = true;
		this.lifetime = 0;
		
		
		
	}
	
	@Override
	public void move() {
		if (this.gravity) this.vy += GamePanel.gravity;
		
		this.x += this.vx;
		this.y += this.vy;
		
		this.vx *= GamePanel.airDrag;
		this.vy *= GamePanel.airDrag;
		
		this.lifetime--;
		if (this.lifetime < 0) GamePanel.deletedObjects.add(this);
		
	}
	
	@Override
	public void draw(Graphics g, Player player, double cam_x, double cam_y, double size) {}

}
