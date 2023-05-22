package skycubedPlatformer.items.weapons.shopWeaponsT3;

import javax.imageio.ImageIO;

import skycubedPlatformer.game.GameObject;
import skycubedPlatformer.game.LivingObject;
import skycubedPlatformer.game.ObjType;
import skycubedPlatformer.items.weapons.Weapon;
import skycubedPlatformer.menu.ApplicationFrame;
import skycubedPlatformer.menu.GamePanel;

public class ExecutionerAxe extends Weapon {
	public int attackDamage = 5;
	public int attackCooldown = 25;
	public int attackRange = 10;
	
	public ExecutionerAxe() {
		super();
		try {
			this.coinCost = 5000;
			this.inShop = 1;
			
			this.size = 35;
			this.name = "Executioner Axe";
			this.attackSound = "/sounds/attack/axe/attack.wav";
			this.tier = 3;
			
			this.stats = new String[]{"Attack Damage +100%", "Attack Range +50%", "Attack Speed -38.5%",
					"Per 1% missing enemy HP:", "Deal +3% dmg:" }; //1.23x dmg
			this.statMap = new int[] {1, 1, -1, 2, 2};
			
			this.lore = "Neither of the blade's crimson edges show any restraint, begging to bathe in more blood.";
			
			this.image = ImageIO.read(this.getClass().getResource("/weapons/shopWeaponsT3/ExecutionerAxe.png"));

		} catch (Exception e) {}
	}
	
	int attackBoost = 0;
	
	@Override
	public void onAttackStart(LivingObject wielder, GameObject victim) {
		if (!(victim.type.equals(ObjType.Player) || victim.type.equals(ObjType.Creature))) return;
		attackBoost = (int) (15 * (1 - (double) ((LivingObject) victim).health / ((LivingObject) victim).maxHealth));
		wielder.attackDamage += attackBoost;
		((GamePanel) ApplicationFrame.current).createShake(3, 40 * (double) wielder.attackDamage / wielder.maxHealth, 2);
	}
	
	@Override
	public void onAttackEnd(LivingObject wielder, GameObject victim) {
		if (!(victim.type.equals(ObjType.Player) || victim.type.equals(ObjType.Creature))) return;
		wielder.attackDamage -= attackBoost;
	}
	
	@Override
	public void init(LivingObject l) {
		this.attackSound = "/sounds/attack/axe/attack.wav";
		l.attackDamage += this.attackDamage;
		l.maxAttackCooldown += this.attackCooldown;
		l.attackRange += this.attackRange;
	}
}
