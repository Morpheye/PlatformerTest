package platformerTest.assets.effects;

import platformerTest.game.GameObject;
import platformerTest.game.LivingObject;

public class EffectPoison extends Effect {

	public int damage;
	public GameObject applier;
	
	public EffectPoison(int lifetime, int damage, GameObject applier) {
		super(lifetime, 45);
		this.damage = damage;
		this.applier = applier;
	}
	
	@Override
	public void trigger(LivingObject host) {
		host.damage(this.damage, this.applier);
	}

}
