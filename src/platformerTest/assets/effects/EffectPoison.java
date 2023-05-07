package platformerTest.assets.effects;

import java.awt.Color;
import java.io.IOException;

import javax.imageio.ImageIO;

import platformerTest.game.GameObject;
import platformerTest.game.LivingObject;

public class EffectPoison extends Effect {
	
	public EffectPoison(int lifetime, int damage, LivingObject applier) {
		super(lifetime, 45);
		this.strength = damage;
		this.applier = applier;
		this.color = Color.red;
		this.name = "Poison";
		try {this.image = ImageIO.read(this.getClass().getResource("/effects/poison.png"));
		} catch (IOException e) {}
	}
	
	@Override
	public void trigger(LivingObject host) {
		host.damage(this.strength, this.applier, this.name);
	}

}
