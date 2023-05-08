package platformerTest.assets.effects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import com.fasterxml.jackson.databind.ser.std.EnumSerializer;

import platformerTest.game.GameObject;
import platformerTest.game.LivingObject;

public class Effect {
	
	public int lifetime = 0;
	public int delay = 1;
	public int strength = 0;
	public Image image;
	public Color color;
	public LivingObject applier;
	public String name;
	
	public Effect(int lifetime, int delay) {
		this.lifetime = lifetime;
		this.delay = delay;
		this.color = Color.gray;
	}
	
	/**
	 * Called every tick
	 */
	public void update(LivingObject host) {
		this.lifetime--;
		
		//trigger action
		if (this.lifetime % this.delay == 0) this.trigger(host);
	}
	
	public void trigger(LivingObject host) {
		
	}
	
	public static void getEffectColor(Graphics g, String effect, int dmgTime) {
		if (effect.equals("Poison")) g.setColor(new Color(50, 255, 0, dmgTime));
		else if (effect.equals("Fire")) g.setColor(new Color(255, 100, 0, dmgTime));
		else g.setColor(new Color(255, 0, 0, dmgTime));
	}

}
