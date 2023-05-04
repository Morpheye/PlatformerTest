package platformerTest.assets.weapons.swords;

import javax.imageio.ImageIO;

import platformerTest.assets.creature.creatures.Creature;
import platformerTest.assets.weapons.Weapon;
import platformerTest.game.Player;

public class SharpSword extends Weapon {
	
	public int attackDamage = 3;
	public int attackCooldown = 10;
	public int attackRange = 5;
	
	public SharpSword() {
		try {
			this.coinCost = 1000;
			this.inShop = true;
			
			this.size = 30;
			this.name = "Sharp Sword";
			this.stats = new String[]{"Attack Damage + 60%", "Attack Range +25%", "Attack speed -20%"};
			this.lore = "Reliable melee weapon.";
			
			this.image = ImageIO.read(this.getClass().getResource("/weapons/SharpSword.png"));

		} catch (Exception e) {}
	}
	
	public void init(Creature creature) {
		creature.attackDamage += this.attackDamage;
		creature.maxAttackCooldown += this.attackCooldown;
		creature.attackRange += this.attackRange;
	}
	
	public void init(Player player) {
		player.attackDamage += this.attackDamage;
		player.maxAttackCooldown += this.attackCooldown;
		player.attackRange += this.attackRange;
	}
	
}
