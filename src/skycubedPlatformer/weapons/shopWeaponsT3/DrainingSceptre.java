package skycubedPlatformer.weapons.shopWeaponsT3;

import java.io.BufferedInputStream;

import javax.imageio.ImageIO;

import skycubedPlatformer.game.LivingObject;
import skycubedPlatformer.weapons.Weapon;

public class DrainingSceptre extends Weapon {
	public int attackCooldown = 5;
	public int attackRange = 5;
	
	public DrainingSceptre() {
		try {
			this.coinCost = 6000;
			this.inShop = 1;
			
			this.size = 35;
			this.name = "Draining Sceptre";
			this.hitSound = "/sounds/attack/sceptre/hit.wav";
			this.tier = 3;
			
			this.stats = new String[]{"Attack Range +25%", "Attack Speed -11.1%",
					"On hit: Heal 1 HP"}; //1.23x dmg
			this.statMap = new int[] {1, -1, 2};
			
			this.lore = "The stem of this staff was once intended to create a powerful cadecuceus, but was "
					+ "repurposed for a weapon of war capable of revitalizing its wielder.";
			
			this.image = ImageIO.read(this.getClass().getResource("/weapons/shopWeaponsT3/DrainingSceptre.png"));

		} catch (Exception e) {}
	}
	
	int attackBoost = 0;
	
	@Override
	public void onAttackStart(LivingObject wielder, LivingObject victim) {
		if (wielder.health < wielder.maxHealth) wielder.health += 1;
	}
	
	@Override
	public void init(LivingObject l) {
		this.hitSound = "/sounds/attack/sceptre/hit.wav";
		l.maxAttackCooldown += this.attackCooldown;
		l.attackRange += this.attackRange;
	}
}
