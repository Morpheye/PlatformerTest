package skycubedPlatformer.items.weapons.starterWeapons;

import javax.imageio.ImageIO;

import skycubedPlatformer.game.LivingObject;
import skycubedPlatformer.items.weapons.Weapon;

public class WoodenClub extends Weapon {
	
	public int attackKnockback = 3;
	public int attackRange = 5;
	public int attackDamage = -1;

	
	public WoodenClub() {
		super();
		try {
			this.coinCost = 1000;
			this.inShop = 1;
			
			this.size = 30;
			this.name = "Wooden Club";
			this.hitSound = "/sounds/attack/club/hit.wav";
			this.tier = 1;
			
			this.stats = new String[]{"Attack Knockback +300%", "Attack Range +25%", "Attack Damage -20%"}; //0.8x dmg
			this.statMap = new int[] {1, 1, -1};
			
			this.lore = "Hits with the push of a beast but the pain of a sunflower. BONK!";
			
			this.image = ImageIO.read(this.getClass().getResource("/weapons/starterWeapons/WoodenClub.png"));

		} catch (Exception e) {}
	}
	
	@Override
	public void init(LivingObject l) {
		this.hitSound = "/sounds/attack/club/hit.wav";
		l.attackKnockback += this.attackKnockback;
		l.attackRange += this.attackRange;
		l.attackDamage += this.attackDamage;
	}
}
