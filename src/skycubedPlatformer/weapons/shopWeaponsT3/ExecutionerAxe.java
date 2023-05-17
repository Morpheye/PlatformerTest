package skycubedPlatformer.weapons.shopWeaponsT3;

import java.io.BufferedInputStream;

import javax.imageio.ImageIO;

import skycubedPlatformer.game.LivingObject;
import skycubedPlatformer.weapons.Weapon;

public class ExecutionerAxe extends Weapon {
	public int attackDamage = 5;
	public int attackCooldown = 25;
	public int attackRange = 10;
	
	public ExecutionerAxe() {
		try {
			this.coinCost = 5000;
			this.inShop = 1;
			
			this.size = 35;
			this.name = "Executioner Axe";
			this.attackSound = "/sounds/attack/axe/attack.wav";
			this.tier = 3;
			
			this.stats = new String[]{"Attack Damage +100%", "Attack Range +50%", "Attack Speed -38.5%",
					"Per 1% missing enemy HP:", "Deal +3% dmg:" }; //1.23x dmg
			this.statMap = new int[] {1, 1, -1, 2, 2};
			
			this.lore = "Neither of the blade's crimson edges show any restraint, begging to bathe in more blood.";
			
			this.image = ImageIO.read(this.getClass().getResource("/weapons/shopWeaponsT3/ExecutionerAxe.png"));

		} catch (Exception e) {}
	}
	
	int attackBoost = 0;
	
	@Override
	public void onAttackStart(LivingObject wielder, LivingObject victim) {
		attackBoost = (int) (15 * (1 - (double) victim.health / victim.maxHealth));
		wielder.attackDamage += attackBoost;
	}
	
	@Override
	public void onAttackEnd(LivingObject wielder, LivingObject victim) {
		wielder.attackDamage -= attackBoost;
	}
	
	@Override
	public void init(LivingObject l) {
		this.attackSound = "/sounds/attack/axe/attack.wav";
		l.attackDamage += this.attackDamage;
		l.maxAttackCooldown += this.attackCooldown;
		l.attackRange += this.attackRange;
	}
}
