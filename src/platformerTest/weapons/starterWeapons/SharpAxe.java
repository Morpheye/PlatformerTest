package platformerTest.weapons.starterWeapons;

import javax.imageio.ImageIO;

import platformerTest.game.LivingObject;
import platformerTest.weapons.Weapon;

public class SharpAxe extends Weapon {
	public int attackDamage = 5;
	public int attackCooldown = 25;
	public int attackRange = 12;
	
	public SharpAxe() {
		try {
			this.coinCost = 1000;
			this.inShop = true;
			
			this.size = 35;
			this.name = "Sharp Axe";
			
			this.stats = new String[]{"Attack Damage +120%", "Attack Range +60%", "Attack Speed -38.5%"}; //1.23x dmg
			this.statMap = new int[] {1, 1, -1};
			
			this.lore = "Longer ranged melee weapon, reliable for heavier damage at a greater distance.";
			
			this.image = ImageIO.read(this.getClass().getResource("/weapons/SharpAxe.png"));

		} catch (Exception e) {}
	}
	
	public void init(LivingObject l) {
		l.attackDamage += this.attackDamage;
		l.maxAttackCooldown += this.attackCooldown;
		l.attackRange += this.attackRange;
	}
}
