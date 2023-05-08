package platformerTest.weapons.shopWeaponsT2;

import javax.imageio.ImageIO;

import platformerTest.game.LivingObject;
import platformerTest.weapons.Weapon;

public class AerialStaff extends Weapon {
	
	public int attackDamage = -2;
	public int attackCooldown = -5;
	public int attackRange = 10;
	
	public AerialStaff() {
		try {
			this.coinCost = 3000;
			this.inShop = 1;
			
			this.size = 35;
			this.name = "Aerial Staff";
			this.tier = 2;
			
			this.stats = new String[]{"Attack Range +50%", "Attack Speed +14.3%", "Attack Damage -40%",
					"While midair:", "Attack Damage +260%"};
			this.statMap = new int[] {1, 1, -1, 2, 2};
			
			this.lore = "Used by monks training high in the mountains, the Aerial Staff channels wind energy into "
					+ "crushing strikes.";
			
			this.image = ImageIO.read(this.getClass().getResource("/weapons/shopWeaponsT2/AerialStaff.png"));

		} catch (Exception e) {}
	}
	
	@Override
	public void onAttackStart(LivingObject wielder, LivingObject target) {
		if (wielder.inAir && !wielder.inLiquid) wielder.attackDamage += 13; 
	}
	
	@Override
	public void onAttackEnd(LivingObject wielder, LivingObject target) {
		if (wielder.inAir && !wielder.inLiquid) wielder.attackDamage -= 13; 
	}
	
	@Override
	public void init(LivingObject l) {
		l.attackDamage += this.attackDamage;
		l.maxAttackCooldown += this.attackCooldown;
		l.attackRange += this.attackRange;
	}
}
