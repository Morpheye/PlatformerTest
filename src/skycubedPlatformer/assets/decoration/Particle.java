package skycubedPlatformer.assets.decoration;

import java.awt.Color;
import java.awt.Graphics;

import javax.sound.sampled.Clip;

import skycubedPlatformer.assets.DecorationObject;
import skycubedPlatformer.game.ObjType;
import skycubedPlatformer.game.Player;
import skycubedPlatformer.menu.ApplicationFrame;
import skycubedPlatformer.menu.GamePanel;

public class Particle extends DecorationObject {

	volatile public Clip spawnSound;
	volatile public Clip despawnSound;
	
	public boolean gravity;
	public int lifetime;
	
	public Particle(double x, double y, double size_x, double size_y, Color color) {
		super(x, y, size_x, size_y, color);

		this.solid = false;
		this.gravity = true;
		this.lifetime = 0;
		
		this.drawLayer = 10;
		this.type = ObjType.PARTICLE;
		
	}
	
	@Override
	public void move() {
		if (this.gravity) this.vy += GamePanel.getPanel().gravity;
		
		this.x += this.vx;
		this.y += this.vy;
		
		this.vx *= GamePanel.getPanel().airDrag;
		this.vy *= GamePanel.getPanel().airDrag;
		
		this.lifetime--;
		if (this.lifetime < 0) {
			this.destroy();
			GamePanel.getPanel().deletedObjects.add(this);
		}
		
	}
	
	@Override
	public void draw(Graphics g, Player player, double cam_x, double cam_y, double size) {}

}
