package skycubedPlatformer.items.weapons.shopWeaponsT2;

import javax.imageio.ImageIO;

import skycubedPlatformer.assets.decoration.particles.CoinParticle;
import skycubedPlatformer.game.LivingObject;
import skycubedPlatformer.items.weapons.Weapon;

public class GoldenKnife extends Weapon {
	
	public int attackCooldown = -10;
	public int attackRange = -2;
	public double attackKnockback = -0.6;
	
	public GoldenKnife() {
		super();
		try {
			this.coinCost = 3500;
			this.inShop = 1;
			
			this.size = 25;
			this.name = "Golden Knife";
			this.attackSound = "/sounds/attack/knife/attack.wav";
			this.tier = 2;
			
			this.stats = new String[]{"Attack Speed +33.3%", "Attack Range -10%", "Attack Knockback -30%",
					"On kill: extra 1-3 coins"};
			this.statMap = new int[] {1, -1, -1, 2};
			
			this.lore = "Gold is normally an incredibly weak material, but even after looting many enemies, this blade seems"
					+ " to never dull.";
			
			this.image = ImageIO.read(this.getClass().getResource("/weapons/shopWeaponsT2/GoldenKnife.png"));

		} catch (Exception e) {}
	}
	
	@Override
	public void onKill(LivingObject wielder, LivingObject victim) {
		int amount = 1 + (int) (Math.random() * 3);
		CoinParticle.spawnCoins(victim.x, victim.y, amount, amount);
	}
	@Override
	public void init(LivingObject l) {
		this.attackSound = "/sounds/attack/knife/attack.wav";
		l.maxAttackCooldown += this.attackCooldown;
		l.attackKnockback += this.attackKnockback;
		l.attackRange += this.attackRange;
	}
}
