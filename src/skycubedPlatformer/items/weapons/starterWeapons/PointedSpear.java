package skycubedPlatformer.items.weapons.starterWeapons;

import javax.imageio.ImageIO;

import skycubedPlatformer.game.LivingObject;
import skycubedPlatformer.items.weapons.Weapon;

public class PointedSpear extends Weapon {
	
	public int attackRange = 20;
	public int attackCooldown = 5;
	public double attackKnockback = -0.5;
	
	public PointedSpear() {
		super();
		try {
			this.coinCost = 1000;
			this.inShop = 1;
			
			this.size = 40;
			this.name = "Pointed Spear";
			this.tier = 1;
			
			this.stats = new String[]{"Attack Range +100%", "Attack Speed -11.1%", "Attack Knockback -25%"}; //0.89x dmg
			this.statMap = new int[] {1, -1, -1};
			
			this.lore = "Reliable melee weapon for any adventure, but a bit slow to swing for the untrained user.";
			
			this.image = ImageIO.read(this.getClass().getResource("/weapons/starterWeapons/PointedSpear.png"));

		} catch (Exception e) {}
	}
	
	@Override
	public void init(LivingObject l) {
		l.maxAttackCooldown += this.attackCooldown;
		l.attackRange += this.attackRange;
		l.attackKnockback += this.attackKnockback;
	}
}
