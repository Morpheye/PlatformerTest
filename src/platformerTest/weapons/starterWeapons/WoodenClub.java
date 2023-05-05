package platformerTest.weapons.starterWeapons;

import java.io.BufferedInputStream;

import javax.imageio.ImageIO;

import platformerTest.game.LivingObject;
import platformerTest.weapons.Weapon;

public class WoodenClub extends Weapon {
	
	public int attackKnockback = 3;
	public int attackRange = 5;
	public int attackDamage = -1;

	
	public WoodenClub() {
		try {
			this.coinCost = 1000;
			this.inShop = true;
			
			this.size = 30;
			this.name = "Wooden Club";
			this.hitSound = new BufferedInputStream(this.getClass().getResourceAsStream("/sounds/attack/club/attack.wav"));
			this.tier = 1;
			
			this.stats = new String[]{"Attack Knockback +300%", "Attack Range +25%", "Attack Damage -20%"}; //0.8x dmg
			this.statMap = new int[] {1, 1, -1};
			
			this.lore = "Hits with the push of a beast but the pain of a sunflower. BONK!";
			
			this.image = ImageIO.read(this.getClass().getResource("/weapons/starterWeapons/WoodenClub.png"));

		} catch (Exception e) {}
	}
	
	@Override
	public void init(LivingObject l) {
		this.hitSound = new BufferedInputStream(this.getClass().getResourceAsStream("/sounds/attack/club/attack.wav"));
		l.attackKnockback += this.attackKnockback;
		l.attackRange += this.attackRange;
		l.attackDamage += this.attackDamage;
	}
}
