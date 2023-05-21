package skycubedPlatformer.items.weapons.shopWeaponsT2;

import javax.imageio.ImageIO;

import skycubedPlatformer.game.GameObject;
import skycubedPlatformer.game.LivingObject;
import skycubedPlatformer.items.weapons.Weapon;
import skycubedPlatformer.menu.GamePanel;

public class AerialStaff extends Weapon {
	
	public int attackDamage = -2;
	public int attackCooldown = -5;
	public int attackRange = 10;
	
	public AerialStaff() {
		super();
		try {
			this.coinCost = 3000;
			this.inShop = 1;
			
			this.size = 35;
			this.name = "Aerial Staff";
			this.tier = 2;
			
			this.stats = new String[]{"Attack Range +50%", "Attack Speed +14.3%", "Attack Damage -40%",
					"While midair:", "Attack Damage +260%"};
			this.statMap = new int[] {1, 1, -1, 2, 2};
			
			this.lore = "Used by monks training high in the mountains, the Aerial Staff channels wind energy into "
					+ "crushing strikes.";
			
			this.image = ImageIO.read(this.getClass().getResource("/weapons/shopWeaponsT2/AerialStaff.png"));

		} catch (Exception e) {}
	}
	
	@Override
	public void onAttackStart(LivingObject wielder, GameObject target) {
		if (this.airtime == 20 && !wielder.inLiquid) {
			wielder.attackDamage += 13; 
			GamePanel.createShake(3, 40 * (double) wielder.attackDamage / wielder.maxHealth, 2);
		}
	}
	
	@Override
	public void onAttackEnd(LivingObject wielder, GameObject target) {
		if (this.airtime == 20 && !wielder.inLiquid) wielder.attackDamage -= 13; 
	}
	
	int airtime = 0;
	
	@Override
	public void onTick(LivingObject wielder) {
		if (wielder.inAir && airtime < 20) this.airtime++;
		else if (!wielder.inAir) this.airtime = 0;
	}
	
	@Override
	public void init(LivingObject l) {
		l.attackDamage += this.attackDamage;
		l.maxAttackCooldown += this.attackCooldown;
		l.attackRange += this.attackRange;
	}
}
