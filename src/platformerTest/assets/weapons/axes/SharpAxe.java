package platformerTest.assets.weapons.axes;

import javax.imageio.ImageIO;

import platformerTest.assets.creature.creatures.Creature;
import platformerTest.assets.weapons.Weapon;
import platformerTest.game.Player;

public class SharpAxe extends Weapon {
	public int attackDamage = 6;
	public int attackCooldown = 25;
	public int attackRange = 10;
	
	public SharpAxe() {
		try {
			this.coinCost = 1000;
			this.inShop = true;
			
			this.size = 35;
			this.name = "Sharp Axe";
			this.stats = new String[]{"Attack Damage + 120%", "Attack Range +50%", "Attack speed -38.5%"};
			this.lore = "Longer ranged melee weapon.";
			
			this.image = ImageIO.read(this.getClass().getResource("/weapons/SharpAxe.png"));

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
