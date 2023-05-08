package platformerTest.weapons.starterWeapons;

import java.io.BufferedInputStream;

import javax.imageio.ImageIO;

import platformerTest.game.LivingObject;
import platformerTest.weapons.Weapon;

public class SwiftDagger extends Weapon {
	
	public int attackCooldown = -20;
	public int attackRange = -4;
	public double attackKnockback = -0.8;
	
	public SwiftDagger() {
		try {
			this.coinCost = 1000;
			this.inShop = 1;
			
			this.size = 25;
			this.name = "Swift Dagger";
			this.attackSound = "/sounds/attack/knife/attack.wav";
			this.tier = 1;
			
			this.stats = new String[]{"Attack speed +100%", "Attack Range -20%", "Attack Knockback -40%"}; //x2 dmg
			this.statMap = new int[] {1, -1, -1};
			
			this.lore = "Inflicts painful damage on vulnerable enemies within a close distance, but lacks the reach of"
					+ " many other weapons.";
			
			this.image = ImageIO.read(this.getClass().getResource("/weapons/starterWeapons/SwiftDagger.png"));

		} catch (Exception e) {}
	}
	
	@Override
	public void init(LivingObject l) {
		this.attackSound = "/sounds/attack/knife/attack.wav";
		l.maxAttackCooldown += this.attackCooldown;
		l.attackRange += this.attackRange;
		l.attackKnockback += this.attackKnockback;
	}
}
