package platformerTest.weapons.shopWeaponsT2;

import javax.imageio.ImageIO;

import platformerTest.assets.liquidPlatforms.WaterPlatform;
import platformerTest.game.LivingObject;
import platformerTest.weapons.Weapon;

public class AquaforgedTrident extends Weapon {
	
	public int attackCooldown = 5;
	public int attackRange = 5;
	
	public AquaforgedTrident() {
		try {
			this.coinCost = 3000;
			this.inShop = 1;
			
			this.size = 30;
			this.name = "Aquaforged Trident";
			this.tier = 2;
			
			this.stats = new String[]{"Attack Range +25%", "Attack Speed -11.1%", "While in water:", "Attack Damage +300%"}; //1.28x dmg
			this.statMap = new int[] {1, -1, 2, 2};
			
			this.lore = "Forged in an underwater furnace and cooled in that very ocean, the Aquaforged Trident "
					+ "greatly boosts its wielder while they are immersed in water.";
			
			this.image = ImageIO.read(this.getClass().getResource("/weapons/shopWeaponsT2/AquaforgedTrident.png"));

		} catch (Exception e) {}
	}
	
	@Override
	public void onAttackStart(LivingObject wielder, LivingObject target) {
		if (wielder.liquids.contains(WaterPlatform.class)) wielder.attackDamage += 15; 
	}
	
	@Override
	public void onAttackEnd(LivingObject wielder, LivingObject target) {
		if (wielder.liquids.contains(WaterPlatform.class)) wielder.attackDamage -= 15; 
	}
	
	@Override
	public void init(LivingObject l) {
		l.maxAttackCooldown += this.attackCooldown;
		l.attackRange += this.attackRange;
	}
}
