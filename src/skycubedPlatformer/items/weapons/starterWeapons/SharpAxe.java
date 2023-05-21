package skycubedPlatformer.items.weapons.starterWeapons;

import javax.imageio.ImageIO;

import skycubedPlatformer.game.GameObject;
import skycubedPlatformer.game.LivingObject;
import skycubedPlatformer.items.weapons.Weapon;
import skycubedPlatformer.menu.GamePanel;

public class SharpAxe extends Weapon {
	public int attackDamage = 6;
	public int attackCooldown = 25;
	public int attackRange = 12;
	
	public SharpAxe() {
		try {
			this.coinCost = 1000;
			this.inShop = 1;
			
			this.size = 35;
			this.name = "Sharp Axe";
			this.attackSound = "/sounds/attack/axe/attack.wav";
			this.tier = 1;
			
			this.stats = new String[]{"Attack Damage +120%", "Attack Range +60%", "Attack Speed -38.5%"};
			this.statMap = new int[] {1, 1, -1};
			
			this.lore = "Longer ranged melee weapon, reliable for heavier damage at a greater distance.";
			
			this.image = ImageIO.read(this.getClass().getResource("/weapons/starterWeapons/SharpAxe.png"));

		} catch (Exception e) {}
	}
	
	@Override
	public void onAttackStart(LivingObject wielder, GameObject target) {
		GamePanel.createShake(3, 40 * (double) wielder.attackDamage / wielder.maxHealth, 2);
	}
	
	@Override
	public void init(LivingObject l) {
		this.attackSound = "/sounds/attack/axe/attack.wav";
		l.attackDamage += this.attackDamage;
		l.maxAttackCooldown += this.attackCooldown;
		l.attackRange += this.attackRange;
	}
}
