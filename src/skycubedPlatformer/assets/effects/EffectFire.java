package skycubedPlatformer.assets.effects;

import java.awt.Color;
import java.io.IOException;

import javax.imageio.ImageIO;

import skycubedPlatformer.game.GameObject;
import skycubedPlatformer.game.LivingObject;
import skycubedPlatformer.menu.GamePanel;

public class EffectFire extends Effect {
	
	public EffectFire(int lifetime, int damage, LivingObject applier) {
		super(lifetime, 45);
		this.strength = damage;
		this.applier = applier;
		this.color = Color.red;
		this.name = "Fire";
		try {this.image = ImageIO.read(this.getClass().getResource("/effects/fire.png"));
		} catch (IOException e) {}
	}
	
	@Override
	public void update(LivingObject host) { 
		if (GamePanel.getPanel().level.isRaining) this.lifetime--;
	}
	
	@Override
	public void trigger(LivingObject host) {
		if (!host.fireResistant) { 
			host.damage(this.strength, this.applier, this.name);
		}
	}

}
