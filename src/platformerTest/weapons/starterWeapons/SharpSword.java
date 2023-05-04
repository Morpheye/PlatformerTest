package platformerTest.weapons.starterWeapons;

import javax.imageio.ImageIO;

import platformerTest.game.LivingObject;
import platformerTest.weapons.Weapon;

public class SharpSword extends Weapon {
	
	public int attackDamage = 3;
	public int attackCooldown = 10;
	public int attackRange = 4;
	
	public SharpSword() {
		try {
			this.coinCost = 1000;
			this.inShop = true;
			
			this.size = 30;
			this.name = "Sharp Sword";
			
			this.stats = new String[]{"Attack Damage +60%", "Attack Range +20%", "Attack Speed -20%"}; //1.28x dmg
			this.statMap = new int[] {1, 1, -1};
			
			this.lore = "Reliable melee weapon for any adventure, but a bit slow to swing for the untrained user.";
			
			this.image = ImageIO.read(this.getClass().getResource("/weapons/SharpSword.png"));

		} catch (Exception e) {}
	}
	
	public void init(LivingObject l) {
		l.attackDamage += this.attackDamage;
		l.maxAttackCooldown += this.attackCooldown;
		l.attackRange += this.attackRange;
	}
}
