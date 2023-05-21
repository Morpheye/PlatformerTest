package skycubedPlatformer.items.weapons.starterWeapons;

import javax.imageio.ImageIO;

import skycubedPlatformer.game.LivingObject;
import skycubedPlatformer.items.weapons.Weapon;

public class SharpSword extends Weapon {
	
	public int attackDamage = 3;
	public int attackCooldown = 10;
	public int attackRange = 4;
	
	public SharpSword() {
		try {
			this.coinCost = 1000;
			this.inShop = 1;
			
			this.size = 30;
			this.name = "Sharp Sword";
			this.attackSound = "/sounds/attack/sword/attack.wav";
			this.tier = 1;
			
			this.stats = new String[]{"Attack Damage +60%", "Attack Range +20%", "Attack Speed -20%"}; //1.28x dmg
			this.statMap = new int[] {1, 1, -1};
			
			this.lore = "Reliable melee weapon for any adventure, but a bit slow to swing for the untrained user.";
			
			this.image = ImageIO.read(this.getClass().getResource("/weapons/starterWeapons/SharpSword.png"));

		} catch (Exception e) {}
	}
	
	@Override
	public void init(LivingObject l) {
		this.attackSound = "/sounds/attack/sword/attack.wav";
		l.attackDamage += this.attackDamage;
		l.maxAttackCooldown += this.attackCooldown;
		l.attackRange += this.attackRange;
	}
}
