package platformerTest.weapons.shopWeaponsT2;

import javax.imageio.ImageIO;

import platformerTest.game.LivingObject;
import platformerTest.weapons.Weapon;

public class HeavyMace extends Weapon {
	
	public int attackDamage = 10;
	public int attackCooldown = 25;
	public int attackKnockback = 1;
	
	public HeavyMace() {
		try {
			this.coinCost = 2500;
			this.inShop = true;
			
			this.size = 30;
			this.name = "Heavy Mace";
			this.tier = 2;
			
			this.stats = new String[]{"Attack Damage +200%", "Attack Knockback +50%", "Attack Speed -38.5%"}; //1.28x dmg
			this.statMap = new int[] {1, 1, -1};
			
			this.lore = "Deals a crushing blow on every hit, but the excessive weight makes recharging attacks very difficult.";
			
			this.image = ImageIO.read(this.getClass().getResource("/weapons/shopWeaponsT2/HeavyMace.png"));

		} catch (Exception e) {}
	}
	
	@Override
	public void init(LivingObject l) {
		l.attackDamage += this.attackDamage;
		l.maxAttackCooldown += this.attackCooldown;
		l.attackKnockback += this.attackKnockback;
	}
}
