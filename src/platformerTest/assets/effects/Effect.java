package platformerTest.assets.effects;

import java.awt.Image;

import platformerTest.game.LivingObject;

public class Effect {
	
	public int lifetime = 0;
	public int delay = 1;
	public Image image;
	
	public Effect(int lifetime, int delay) {
		this.lifetime = lifetime;
		this.delay = delay;
	}
	
	/**
	 * Called every tick
	 */
	public void update(LivingObject host) {
		this.lifetime--;
		if (this.lifetime <= 0) return;
		
		//trigger action
		if (this.lifetime % this.delay == 0) this.trigger(host);
	}
	
	public void trigger(LivingObject host) {
		
	}
	
}
