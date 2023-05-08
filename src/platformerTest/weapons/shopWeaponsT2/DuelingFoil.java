package platformerTest.weapons.shopWeaponsT2;

import javax.imageio.ImageIO;

import platformerTest.game.LivingObject;
import platformerTest.weapons.Weapon;

public class DuelingFoil extends Weapon {
	
	public int attackDamage = -1;
	public int attackCooldown = -10;
	public int attackRange = 5;
	
	public DuelingFoil() {
		try {
			this.coinCost = 2500;
			this.inShop = 1;
			
			this.size = 30;
			this.name = "Dueling Foil";
			this.tier = 2;
			
			this.stats = new String[]{"Attack Range +25%", "Attack Speed +33.3%", "Attack Damage -20%"}; //1.28x dmg
			this.statMap = new int[] {1, 1, -1};
			
			this.lore = "The thin blade deals reduced damage, but offers fast stabs and slightly increased reach.";
			
			this.image = ImageIO.read(this.getClass().getResource("/weapons/shopWeaponsT2/DuelingFoil.png"));

		} catch (Exception e) {}
	}
	
	@Override
	public void init(LivingObject l) {
		l.attackDamage += this.attackDamage;
		l.maxAttackCooldown += this.attackCooldown;
		l.attackRange += this.attackRange;
	}
}
