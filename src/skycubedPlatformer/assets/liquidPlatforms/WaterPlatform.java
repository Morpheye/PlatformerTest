package skycubedPlatformer.assets.liquidPlatforms;

import skycubedPlatformer.assets.LiquidPlatform;
import skycubedPlatformer.assets.effects.Effect;
import skycubedPlatformer.game.GameObject;
import skycubedPlatformer.game.LivingObject;
import skycubedPlatformer.game.ObjType;

public class WaterPlatform extends LiquidPlatform {

	public WaterPlatform(double x, double y, double size_x, double size_y) {
		super(x, y, size_x, size_y, COLOR_WATER);
		
		this.slipperiness = 0.95;
		this.density = 1;
		this.liquidType = LiquidType.Water;
		
	}
	
	@Override
	public void onTick(GameObject obj) {
		if (obj.type.equals(ObjType.Player) || obj.type.equals(ObjType.Creature)) {
			((LivingObject) obj).removeEffect("Fire");
		}
	}

}
