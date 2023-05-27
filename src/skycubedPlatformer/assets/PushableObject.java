package skycubedPlatformer.assets;

import java.awt.Color;

import skycubedPlatformer.Main;
import skycubedPlatformer.game.GameObject;
import skycubedPlatformer.game.LivingObject;
import skycubedPlatformer.game.MovableObject;
import skycubedPlatformer.game.ObjType;
import skycubedPlatformer.menu.GamePanel;
import skycubedPlatformer.util.SoundHelper;

public class PushableObject extends MovableObject {

	public boolean attackable = true;
	public int max_durability;
	public int durability;
	
	public PushableObject(double x, double y, double size_x, double size_y, Color color, double density, Double slipperiness) {
		super(x, y, size_x, size_y, color, density);
		this.slipperiness = slipperiness;
		this.type = ObjType.MovableObject;
		this.max_durability = (int) this.getWeight();
		this.durability = this.max_durability;
	}
	
	@Override
	public void move() {
		super.move();
		if (this.durability <= 0) this.crush();
		if (this.durability < this.max_durability) this.durability++;
	}
	
	@Override
	public void crush() {
		if (this.exists) GamePanel.getPanel().createShake(10, 10, 2);
		super.crush();
	}
	
	public void playHitSound(LivingObject attacker) {
		double proximity = Math.hypot(this.x - GamePanel.getPanel().MainFrameObj.x, this.y - GamePanel.getPanel().MainFrameObj.y);
		double div = (proximity < GamePanel.getPanel().camera_size/2) ? 1 : 
			0.03*(Math.abs(proximity - GamePanel.getPanel().camera_size/2));
		if (div < 1) div = 1;
		SoundHelper.playSound(attacker.hitSound, Main.VOLUME/(float) div);
	}
	
	public void damage(int damage, GameObject source) {
		this.durability -= damage;
	}
	
	public void damage(int damage, GameObject source, String effect) {
		this.damage(damage, source);
	}

}
