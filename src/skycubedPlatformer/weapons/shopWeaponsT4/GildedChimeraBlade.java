package skycubedPlatformer.weapons.shopWeaponsT4;

import javax.imageio.ImageIO;

import skycubedPlatformer.assets.decoration.particles.CoinParticle;
import skycubedPlatformer.game.GameObject;
import skycubedPlatformer.game.LivingObject;
import skycubedPlatformer.game.ObjType;
import skycubedPlatformer.weapons.Weapon;

public class GildedChimeraBlade extends Weapon {
	
	public int attackCooldown = -4;
	public int attackRange = 2;
	public double attackKnockback = 0.2;
	
	public int attackDamage = -1;
	
	public GildedChimeraBlade() {
		try {
			this.coinCost = 2000;
			this.gemCost = 1;
			this.inShop = 1;
			
			this.size = 30;
			this.name = "Gilded Chimera Blade";
			this.attackSound = "/sounds/attack/sword/attack.wav";
			this.tier = 4;
			
			this.stats = new String[]{"Attack Speed +11.1%", "Attack Range +10%", "Attack Knockback +10%", "Attack Damage -20%",
					"Every 2nd hit: drop 1 coin"};
			this.statMap = new int[] {1, 1, 1, -1, 2};
			
			this.lore = "Only the energy of a gem can provide the extreme force required to fuse the millions of unique"
					+ " elements into a weapon so full of lustre and wealth.";
			
			this.image = ImageIO.read(this.getClass().getResource("/weapons/shopWeaponsT4/GildedChimeraBlade.png"));

		} catch (Exception e) {}
	}
	
	int hitnum = 0;
	
	@Override
	public void onAttackStart(LivingObject wielder, GameObject victim) {
		if (!(victim.type.equals(ObjType.Player) || victim.type.equals(ObjType.Creature))) return;
		hitnum++;
		if (hitnum >= 2) {
			if (wielder.type.equals(ObjType.Player)) CoinParticle.spawnCoins(victim.x, victim.y, 1, 1);
			hitnum = 0;
		}
	}
	
	@Override
	public void init(LivingObject l) {
		this.attackSound = "/sounds/attack/sword/attack.wav";
		l.attackDamage += this.attackDamage;
		l.attackRange += this.attackRange;
		l.attackKnockback += this.attackKnockback;
		l.maxAttackCooldown += this.attackCooldown;
	}
}
