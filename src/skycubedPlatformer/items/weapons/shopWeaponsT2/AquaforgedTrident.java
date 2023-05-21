package skycubedPlatformer.items.weapons.shopWeaponsT2;

import java.awt.Color;

import javax.imageio.ImageIO;

import skycubedPlatformer.assets.liquidPlatforms.WaterPlatform;
import skycubedPlatformer.game.GameObject;
import skycubedPlatformer.game.LivingObject;
import skycubedPlatformer.items.weapons.Weapon;
import skycubedPlatformer.menu.GamePanel;

public class AquaforgedTrident extends Weapon {
	
	public int attackCooldown = 5;
	public int attackRange = 10;
	
	public AquaforgedTrident() {
		super();
		try {
			this.coinCost = 3000;
			this.inShop = 1;
			
			this.size = 35;
			this.name = "Aquaforged Trident";
			this.tier = 2;
			
			this.stats = new String[]{"Attack Range +50%", "Attack Speed -11.1%", "While in water:", "Attack Damage +300%"}; //1.28x dmg
			this.statMap = new int[] {1, -1, 2, 2};
			
			this.lore = "Forged in an underwater furnace and cooled in that very ocean, the Aquaforged Trident "
					+ "greatly boosts its wielder while they are immersed in water.";
			
			this.image = ImageIO.read(this.getClass().getResource("/weapons/shopWeaponsT2/AquaforgedTrident.png"));

		} catch (Exception e) {}
	}
	
	@Override
	public void onAttackStart(LivingObject wielder, GameObject target) {
		if (wielder.liquids.contains(WaterPlatform.class)) {
			wielder.attackDamage += 15; 
			GamePanel.createShake(3, 40 * (double) wielder.attackDamage / wielder.maxHealth, 2);
			GamePanel.createFlash(new Color(0, 255, 255), 20);
		}
	}
	
	@Override
	public void onAttackEnd(LivingObject wielder, GameObject target) {
		if (wielder.liquids.contains(WaterPlatform.class)) wielder.attackDamage -= 15; 
	}
	
	@Override
	public void init(LivingObject l) {
		l.maxAttackCooldown += this.attackCooldown;
		l.attackRange += this.attackRange;
	}
}
