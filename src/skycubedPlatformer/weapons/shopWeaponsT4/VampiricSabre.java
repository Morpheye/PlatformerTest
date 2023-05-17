package skycubedPlatformer.weapons.shopWeaponsT4;

import javax.imageio.ImageIO;

import skycubedPlatformer.assets.decoration.particles.CoinParticle;
import skycubedPlatformer.assets.effects.EffectPoison;
import skycubedPlatformer.game.LivingObject;
import skycubedPlatformer.game.ObjType;
import skycubedPlatformer.game.Player;
import skycubedPlatformer.weapons.Weapon;

public class VampiricSabre extends Weapon {
	
	public int attackDamage = 3;
	public int attackRange = 4;
	public int naturalRegenCooldown = 180;
	
	public VampiricSabre() {
		try {
			this.coinCost = 12000;
			this.gemCost = 3;
			this.inShop = 1;
			
			this.size = 30;
			this.name = "Vampiric Sabre";
			this.attackSound = "/sounds/attack/sword/attack.wav";
			this.tier = 4;
			
			this.stats = new String[]{"Attack Damage +60%", "Attack Range +20%", "Natural Regeneration -50%",
					"On kill: Heal 15 HP", "Overheals up to 300%"};
			this.statMap = new int[] {1, 1, -1, 2, 2};
			
			this.lore = "Millenia ago, the spirit of a great vampire was salvaged from destruction. It now lays "
					+ "within this raging blade, more than enthusiastic to drink again.";
			
			this.image = ImageIO.read(this.getClass().getResource("/weapons/shopWeaponsT4/VampiricSabre.png"));

		} catch (Exception e) {}
	}
	
	int hitnum = 0;
	
	@Override
	public void onKill(LivingObject wielder, LivingObject victim) {
		if (wielder.health + 15 > wielder.maxHealth) { //overheal
			if (wielder.overheal > wielder.maxHealth * 2) return; //already above 300%
			else if (wielder.overheal + 15 > wielder.maxHealth * 2) wielder.overheal = wielder.maxHealth * 2;
			else if (wielder.health == wielder.maxHealth) wielder.overheal += 15; //already above or at max
			else { //split the healing
				int overheal = wielder.maxHealth - wielder.health;
				wielder.overheal += overheal;
				wielder.health += 15 - overheal;
			}
		} else {
			wielder.health += 15;
		}
	}
	
	@Override
	public void init(LivingObject l) {
		this.attackSound = "/sounds/attack/sword/attack.wav";
		l.attackDamage += this.attackDamage;
		l.attackRange += this.attackRange;
		if (l.type.equals(ObjType.Player)) ((Player) l).naturalRegenCooldown += this.naturalRegenCooldown;
	}
}
