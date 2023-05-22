package skycubedPlatformer.items.weapons.shopWeaponsT4;

import javax.imageio.ImageIO;

import skycubedPlatformer.game.LivingObject;
import skycubedPlatformer.game.ObjType;
import skycubedPlatformer.game.Player;
import skycubedPlatformer.items.weapons.Weapon;

public class JadedHalberd extends Weapon {
	
	public int attackDamage = 3;
	public int attackRange = 15;
	public int attackCooldown = 15;
	
	public JadedHalberd() {
		super();
		try {
			this.coinCost = 5000;
			this.gemCost = 1;
			this.inShop = 1;
			
			this.size = 40;
			this.name = "Jaded Halberd";
			this.attackSound = "/sounds/attack/axe/attack.wav";
			this.tier = 4;
			
			this.stats = new String[]{"Attack Damage +60%", "Attack Range +75%", "Attack Speed -27.3%",
					"Luck +30%"};
			this.statMap = new int[] {1, 1, -1, 2};
			
			this.lore = "Finding and excavating jade is an extremely uncommon occurance, and the fortune involved "
					+ "in the spelunking is infused into the blade from which the jade is created.";
			
			this.image = ImageIO.read(this.getClass().getResource("/weapons/shopWeaponsT4/JadedHalberd.png"));

		} catch (Exception e) {}
	}
	
	@Override
	public void init(LivingObject l) {
		this.attackSound = "/sounds/attack/axe/attack.wav";
		l.attackDamage += this.attackDamage;
		l.maxAttackCooldown += this.attackCooldown;
		l.attackRange += this.attackRange;
		if (l.type.equals(ObjType.Player)) ((Player) l).luck += 0.3;
	}
}
