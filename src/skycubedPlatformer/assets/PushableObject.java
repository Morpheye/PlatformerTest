package skycubedPlatformer.assets;

import java.awt.Color;

import skycubedPlatformer.game.GameObject;
import skycubedPlatformer.game.LivingObject;
import skycubedPlatformer.game.MovableObject;
import skycubedPlatformer.game.ObjType;
import skycubedPlatformer.menu.ApplicationFrame;
import skycubedPlatformer.menu.GamePanel;
import skycubedPlatformer.util.SoundHelper;

public class PushableObject extends MovableObject {

	public boolean attackable = true;
	
	public PushableObject(double x, double y, double size_x, double size_y, Color color, double density, Double slipperiness) {
		super(x, y, size_x, size_y, color, density);
		this.slipperiness = slipperiness;
		this.type = ObjType.MovableObject;
	}
	
	@Override
	public void move() {
		super.move();
	}
	
	@Override
	public void crush() {
		((GamePanel) ApplicationFrame.current).createShake(10, 10, 2);
		super.crush();
	}
	
	public void playHitSound(LivingObject attacker) {
		SoundHelper.playSound(attacker.hitSound);
	}
	
	public void damage(int damage, GameObject source) {}
	
	public void damage(int damage, GameObject source, String effect) {
		this.damage(damage, source);
		if (effect.equals("Explosion")) {
			if (damage > Math.sqrt(this.getWeight())) this.crush();
		}
	}

}
