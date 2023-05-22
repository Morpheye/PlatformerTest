package skycubedPlatformer.items.weapons.shopWeaponsT3;

import javax.imageio.ImageIO;

import skycubedPlatformer.assets.creature.creatures.Creature;
import skycubedPlatformer.game.GameObject;
import skycubedPlatformer.game.LivingObject;
import skycubedPlatformer.game.ObjType;
import skycubedPlatformer.items.weapons.Weapon;
import skycubedPlatformer.menu.GamePanel;

public class BejeweledMoonstaff extends Weapon {
	
	public int attackDamage = 3;
	public int attackRange = 15;
	public int attackCooldown = 15;
	
	public BejeweledMoonstaff() {
		super();
		try {
			this.coinCost = 6000;
			this.inShop = 1;
			
			this.size = 40;
			this.name = "Bejeweled Moonstaff";
			this.attackSound = "/sounds/attack/axe/attack.wav";
			this.tier = 3;
			
			this.stats = new String[]{"Attack Damage +60%", "Attack Range +75%", "Attack Speed -27.3%",
					"Gem drop chances tripled"};
			this.statMap = new int[] {1, 1, -1, 2};
			
			this.lore = "The Bejeweled Moonstaff brings a lustrous new look to a powerful ancient weapon. "
					+ "Dealing quite significant damage at a distance, the weapon is nonetheless challenging to wield.";
			
			this.image = ImageIO.read(this.getClass().getResource("/weapons/shopWeaponsT3/BejeweledMoonstaff.png"));

		} catch (Exception e) {}
	}
	
	@Override
	public void onAttackStart(LivingObject wielder, GameObject victim) {
		GamePanel.createShake(3, 40 * (double) wielder.attackDamage / wielder.maxHealth, 2);
	}
	
	@Override
	public void onKill(LivingObject wielder, LivingObject victim) {
		if (victim.type.equals(ObjType.Creature)) {
			((Creature) victim).gemChance *= 3;
		}
	}
	
	@Override
	public void init(LivingObject l) {
		this.attackSound = "/sounds/attack/axe/attack.wav";
		l.attackDamage += this.attackDamage;
		l.maxAttackCooldown += this.attackCooldown;
		l.attackRange += this.attackRange;
	}
}
