package skycubedPlatformer.weapons.shopWeaponsT3;

import javax.imageio.ImageIO;

import skycubedPlatformer.assets.effects.EffectPoison;
import skycubedPlatformer.game.GameObject;
import skycubedPlatformer.game.LivingObject;
import skycubedPlatformer.game.ObjType;
import skycubedPlatformer.weapons.Weapon;

public class PoisonEdgeKatana extends Weapon {
	
	public int attackDamage = 1;
	public int attackRange = 3;
	public double attackKnockback = -0.5;
	
	public PoisonEdgeKatana() {
		try {
			this.coinCost = 5000;
			this.inShop = 1;
			
			this.size = 30;
			this.name = "Poison-Edge Katana";
			this.tier = 3;
			
			this.stats = new String[]{"Attack Damage +20%", "Attack Range +15%", "Attack Knockback -25%",
					"On hit: Apply poison", "(12 dmgticks, 1 dmg per)"};
			this.statMap = new int[] {1, 1, -1, 2, 2};
			
			this.lore = "The blade contains micropores filled with corrosive poison, released on contact with an enemy. "
					+ "The relatively dull blade is overshadowed by the damage inflicted over time.";
			
			this.image = ImageIO.read(this.getClass().getResource("/weapons/shopWeaponsT3/PoisonEdgeKatana.png"));

		} catch (Exception e) {}
	}
	
	@Override
	public void onAttackStart(LivingObject wielder, GameObject victim) {
		if (!(victim.type.equals(ObjType.Player) || victim.type.equals(ObjType.Creature))) return;
		((LivingObject) victim).applyEffect(new EffectPoison(12*45, 1, wielder));
	}
	
	@Override
	public void init(LivingObject l) {
		l.attackKnockback += this.attackKnockback;
		l.attackDamage += this.attackDamage;
		l.attackRange += this.attackRange;
	}
}
