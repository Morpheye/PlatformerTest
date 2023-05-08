package platformerTest.weapons.shopWeaponsT3;

import java.io.BufferedInputStream;

import javax.imageio.ImageIO;

import platformerTest.assets.creature.creatures.Creature;
import platformerTest.game.LivingObject;
import platformerTest.game.ObjType;
import platformerTest.weapons.Weapon;

public class BejeweledMoonstaff extends Weapon {
	
	public int attackDamage = 3;
	public int attackRange = 15;
	public int attackCooldown = 10;
	
	public BejeweledMoonstaff() {
		try {
			this.coinCost = 6000;
			this.inShop = true;
			
			this.size = 40;
			this.name = "Bejeweled Moonstaff";
			this.attackSound = new BufferedInputStream(this.getClass().getResourceAsStream("/sounds/attack/axe/attack.wav"));
			this.tier = 3;
			
			this.stats = new String[]{"Attack Damage +60%", "Attack Range +75%", "Attack Speed -20%",
					"Gem drop chances tripled"}; //0.89x dmg
			this.statMap = new int[] {1, 1, -1, 2};
			
			this.lore = "The Bejeweled Moonstaff brings a lustrous new look to a powerful ancient weapon. "
					+ "Dealing quite significant damage at a distance, the weapon is nonetheless challenging to wield.";
			
			this.image = ImageIO.read(this.getClass().getResource("/weapons/shopWeaponsT3/BejeweledMoonstaff.png"));

		} catch (Exception e) {}
	}
	
	@Override
	public void onKill(LivingObject wielder, LivingObject victim) {
		if (victim.type.equals(ObjType.Creature)) {
			((Creature) victim).gemChance *= 3;
		}
	}
	
	@Override
	public void init(LivingObject l) {
		this.attackSound = new BufferedInputStream(this.getClass().getResourceAsStream("/sounds/attack/axe/attack.wav"));
		l.attackDamage += this.attackDamage;
		l.maxAttackCooldown += this.attackCooldown;
		l.attackRange += this.attackRange;
	}
}
