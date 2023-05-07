package platformerTest.weapons.shopWeaponsT3;

import javax.imageio.ImageIO;

import platformerTest.assets.effects.EffectPoison;
import platformerTest.game.LivingObject;
import platformerTest.weapons.Weapon;

public class PoisonEdgeKatana extends Weapon {
	
	public int attackDamage = -1;
	public int attackRange = 3;
	
	public PoisonEdgeKatana() {
		try {
			this.coinCost = 5000;
			this.inShop = true;
			
			this.size = 30;
			this.name = "Poison-Edge Katana";
			this.tier = 3;
			
			this.stats = new String[]{"Attack Range +15%", "Attack Damage -20%",
					"On hit: Apply poison", "(6 dmgticks, 1 dmg per)"};
			this.statMap = new int[] {1, -1, 2, 2};
			
			this.lore = "The blade contains micropores filled with corrosive poison, released on contact with an enemy. "
					+ "The relatively dull blade is overshadowed by the damage inflicted over time.";
			
			this.image = ImageIO.read(this.getClass().getResource("/weapons/shopWeaponsT3/PoisonEdgeKatana.png"));

		} catch (Exception e) {}
	}
	
	@Override
	public void onAttackStart(LivingObject wielder, LivingObject victim) {
		victim.applyEffect(new EffectPoison(6*45, 1, wielder));
	}
	
	@Override
	public void init(LivingObject l) {
		l.attackDamage += this.attackDamage;
		l.attackRange += this.attackRange;
	}
}
