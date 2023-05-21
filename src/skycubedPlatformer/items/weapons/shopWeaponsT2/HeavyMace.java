package skycubedPlatformer.items.weapons.shopWeaponsT2;

import javax.imageio.ImageIO;

import skycubedPlatformer.game.GameObject;
import skycubedPlatformer.game.LivingObject;
import skycubedPlatformer.items.weapons.Weapon;
import skycubedPlatformer.menu.GamePanel;

public class HeavyMace extends Weapon {
	
	public int attackDamage = 10;
	public int attackCooldown = 25;
	public int attackKnockback = 1;
	
	public HeavyMace() {
		super();
		try {
			this.coinCost = 2500;
			this.inShop = 1;
			
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
	public void onAttackStart(LivingObject wielder, GameObject target) {
		GamePanel.createShake(3, 40 * (double) wielder.attackDamage / wielder.maxHealth, 2);
	}
	
	@Override
	public void init(LivingObject l) {
		l.attackDamage += this.attackDamage;
		l.maxAttackCooldown += this.attackCooldown;
		l.attackKnockback += this.attackKnockback;
	}
}
