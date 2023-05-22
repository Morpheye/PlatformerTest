package skycubedPlatformer.items.weapons.weaponsT5;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import skycubedPlatformer.Main;
import skycubedPlatformer.assets.creature.CreatureAi;
import skycubedPlatformer.assets.creature.creatures.Creature;
import skycubedPlatformer.assets.effects.Effect;
import skycubedPlatformer.game.GameObject;
import skycubedPlatformer.game.LivingObject;
import skycubedPlatformer.game.ObjType;
import skycubedPlatformer.game.Player;
import skycubedPlatformer.items.weapons.Weapon;
import skycubedPlatformer.menu.ApplicationFrame;
import skycubedPlatformer.menu.GamePanel;

public class SpiritScythe extends Weapon {
	
	public int attackRange = 10;
	public int naturalRegenCooldown = 180;
	
	public SpiritScythe() {
		super();
		try {
			this.coinCost = 120000;
			this.gemCost = 120;
			this.inShop = 0;
			
			this.size = 35;
			this.name = "Spirit Scythe";
			this.tier = 5;
			
			this.stats = new String[]{"Attack Range +50%", "Natural Regeneration -50%", 
					"On kill: Create spirit", "Max 10 spirits"};
			this.statMap = new int[] {1, -1, 2, 2};
			
			this.lore = "Random description goes here";
			
			this.image = ImageIO.read(this.getClass().getResource("/weapons/weaponsT5/SpiritScythe.png"));

		} catch (Exception e) {}
	}
	
	@Override
	public void init(LivingObject l) {
		for (ScytheSpirit c : this.spirits) {
			c.health = 0;
			c.overheal = 0;
		}
		this.spirits.clear();
		this.rotation = 0;
		
		l.attackRange += this.attackRange;
		if (l.type.equals(ObjType.Player)) ((Player) l).naturalRegenCooldown += this.naturalRegenCooldown;
	}

	public ArrayList<ScytheSpirit> spirits = new ArrayList<ScytheSpirit>();
	int rotation = 0;
	
	@Override
	public void onTick(LivingObject wielder) {
		rotation++;
		if (this.rotation == 360) this.rotation = 0;
	}
	
	@Override
	public void onKill(LivingObject wielder, LivingObject victim) {
		if (victim instanceof ScytheSpirit) return;
		
		for (ScytheSpirit c : spirits) { //Begin hp regeneration
			if (c.health + 10 > c.maxHealth) { //would overheal
				if (c.overheal > c.maxHealth * 2) return; //already above 300%
				else if (c.overheal + 10 > c.maxHealth * 2) c.overheal = c.maxHealth * 2;
				else if (c.health == c.maxHealth) c.overheal += 10; //already above or at max
				else { //split the healing
					int overheal = c.maxHealth - c.health;
					c.overheal += overheal;
					c.health += 10 - overheal;
				}
			} else {
				c.health += 10;
			}
		}
		
		if (this.spirits.contains(wielder)) wielder.health = wielder.maxHealth; //if spirit, heal it to max health
		
		//spawn new spirit
		LivingObject realWielder = (this.spirits.contains(wielder)) ? ((ScytheSpirit) wielder).wielder : wielder;
		if (this.spirits.size() < 10) spirits.add(new ScytheSpirit(realWielder, victim, 30));
		
	}
	
	@Override
	public void onUserHit(LivingObject wielder, GameObject attacker) {
		if (attacker != null && (attacker.type.equals(ObjType.Creature) || attacker.type.equals(ObjType.Player))) {
			for (ScytheSpirit c : spirits) c.targets.add((LivingObject) attacker);
		}
	}
	
	@Override
	public void onAttackStart(LivingObject wielder, GameObject victim) {
		if (victim instanceof ScytheSpirit) return;
		if (!(victim.type.equals(ObjType.Player) || victim.type.equals(ObjType.Creature))) return;
		for (ScytheSpirit c : spirits) c.targets.add((LivingObject) victim);
	}
	
	public class ScytheSpirit extends Creature {
		LivingObject wielder;
		LivingObject target;
		ArrayList<LivingObject> targets;
		
		public ScytheSpirit(LivingObject wielder, LivingObject victim, int size) {
			super(victim.x, victim.y, size, new Color(255, 255, 255, 100),
					Color.black, 1, size, wielder.movementSpeed, 0, 5, 20, 40, 2);
			this.solid = false;
			this.wielder = wielder;
			this.targets = new ArrayList<LivingObject>();
			this.drawLayer = 1;
			
			GamePanel.getPanel().addedObjects.add(this);
			
			
		}
		
		@Override
		public void move() {
			if (this.health <= 0 || !spirits.contains(this)) this.die();
			if (this.health > this.maxHealth) this.health = this.maxHealth; 
			
			//then attack
			if (this.attackCooldown > 0) this.attackCooldown--;
			if (this.meleeCooldown > 0) this.meleeCooldown--;
			if (this.isAttacking && this.attackCooldown == 0) this.attack();
			if (this.isRangedAttacking && this.attackCooldown == 0) this.rangedAttack();
			
			//if dead
			if (!this.isAlive) this.timeSinceDeath++; 
			
			for (CreatureAi ai : this.aiList) ai.run(this); 
			
			if (this.movingRight) this.vx += this.movementSpeed;
			if (this.movingLeft) this.vx -= this.movementSpeed;
			if (this.movingUp) this.vy += this.movementSpeed;
			if (this.movingDown) this.vy -= this.movementSpeed;
			
			this.attemptMoveY(this.vy, true);
			this.attemptMoveX(this.vx, true);
			

			this.vy *= GamePanel.getPanel().airDrag;
			this.vx *= GamePanel.getPanel().airDrag;

			//CHECK BOUNDS
			if (this.y > GamePanel.getPanel().level.topLimit) GamePanel.getPanel().deletedObjects.add(this);
			if (this.y < GamePanel.getPanel().level.bottomLimit) GamePanel.getPanel().deletedObjects.add(this);
			//INERTIA THRESHOLD
			if (Math.abs(vx) < 0.01) vx = 0;
			if (Math.abs(vy) < 0.01) vy = 0;
			this.liquidDensity = 1;
			
			//if dead
			if (!this.isAlive) this.timeSinceDeath++; 
			
			//Apply effects
			for (Effect e: this.effects) {
				e.update(this);
				if (e.lifetime <= 0) removeEffects.add(e);
			}
			this.effects.removeAll(removeEffects);
			removeEffects.clear();
			
			//change targets
			if (this.target != null && (!this.target.exists || !this.target.isAlive || this.target.equals(this.wielder)
					|| !GamePanel.getPanel().objects.contains(target) 
					|| !this.target.hasCollided(GamePanel.getPanel().MainFrameObj))) {
				this.targets.remove(this.target);
				this.target = null;
			}
			
			if (this.target == null) {
				if (this.targets.size() > 0) this.target = this.targets.get(0);
			}
			
			this.chaseTarget();	
			
		}
		
		private void chaseTarget() {
			//calculate target
			double xDist, yDist;
			int targetX, targetY;
			if (this.target != null && this.target.hasCollided(GamePanel.getPanel().MainFrameObj)) {
				xDist = Math.abs(this.x - target.x) - (0.5 * Math.abs(this.size_x + target.size_x));
				yDist = Math.abs(this.y - target.y) - (0.5 * Math.abs(this.size_y + target.size_y));
				targetX = (int) target.x;
				targetY = (int) target.y;
			} else {
				double angle = Math.toRadians(rotation) + (Math.PI * 2 * spirits.indexOf(this) / spirits.size());
				targetX = (int) (this.wielder.x + Math.cos(angle) * 150);
				targetY = (int) (this.wielder.y + Math.sin(angle) * 150);
				xDist = Math.abs(this.x - targetX);
				yDist = Math.abs(this.y - targetY);
			}
			
			if (xDist < 0) xDist = 0;
			if (yDist < 0) yDist = 0;
			
			double distance = Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2));
			
			//chase target
			if (this.target != null && this.target.hasCollided(GamePanel.getPanel().MainFrameObj)) {
				if (targetX > this.x && distance >= 0) {
					this.movingRight = true;
					this.movingLeft = false;
					this.lastDirection = 1;
				} else if (targetX < this.x && distance >= 0) {
					this.movingRight = false;
					this.movingLeft = true;
					this.lastDirection = -1;
				}
				
				if (targetY > this.y && distance >= 0) {
					this.movingUp = true;
					this.movingDown = false;
				} else if (targetY < this.y && distance >= 0) {
					this.movingUp = false;
					this.movingDown = true;
				}
					
				if (distance <= this.attackRange) this.isAttacking = true;
				else this.isAttacking = false;
			} else { //return to guard pos
				this.isAttacking = false;
				int minDistance = 10;
				if (targetX > this.x && distance >= minDistance) {
					this.movingRight = true;
					this.movingLeft = false;
					this.lastDirection = 1;
				} else if (targetX < this.x && distance >= minDistance) {
					this.movingRight = false;
					this.movingLeft = true;
					this.lastDirection = -1;
				}
				
				if (targetY > this.y && distance >= minDistance) {
					this.movingUp = true;
					this.movingDown = false;
				} else if (targetY < this.y && distance >= minDistance) {
					this.movingUp = false;
					this.movingDown = true;
				}
			}
		}
		
		@Override
		public void attack() {
			if (!this.isAlive) return;
			
			//move attackbox
			this.attack.x = this.x + 0;
			this.attack.y = this.y + 0;
			
			//check direction
			int calcX = 0, calcY = 0, angle = 0;
			if (this.movingUp) calcY++;
			if (this.movingDown) calcY--;
			if (this.movingRight) calcX++;
			if (this.movingLeft) calcX--; 
			if (calcX == 0 && calcY == 0) calcX += this.lastDirection; //no input
			if (calcX == 0) angle = 90 * calcY;
			
			if (calcX == 0) angle = 90 * calcY;
			else if (calcY == 0) angle = 90 - (90*calcX);
			else if (calcY == 1) angle = 90 - (45*calcX);
			else if (calcY == -1) angle = -90 + (45*calcX);
			
			//now move
			this.attack.x += this.attackRange * Math.cos(angle * Math.PI/180);
			this.attack.y += this.attackRange * Math.sin(angle * Math.PI/180);
			
			this.lastAttackAngle = angle;
			this.lastAttackRange = this.attackRange;
			this.attackCooldown = this.maxAttackCooldown;
			this.meleeCooldown = this.maxAttackCooldown;
			
			//play sound
			this.playAttackSound();
			
			//Apply collisions
			for (GameObject obj : GamePanel.getPanel().objects) { //check for enemies in range
				if (obj.equals(this)) continue;
				if (obj.hasCollided(this.attack) && obj.type.equals(ObjType.Creature)) {
					if (spirits.contains(obj)) continue;
					if (this.weapon != null) this.weapon.onAttackStart(this, (LivingObject) obj); //WEAPON TRIGGER
					((Creature) obj).damage(this.attackDamage, this);
					
					//SOUND
					((LivingObject) obj).playHitSound(this);
					
					double pushStrength = this.attackKnockback;
					ArrayList<GameObject> list = new ArrayList<GameObject>();
					list.add(this.attack);
					((Creature) obj).pushx(pushStrength * Math.cos(angle*Math.PI/180), this.attack, list, false, true);
					((Creature) obj).pushy(pushStrength * Math.sin(angle*Math.PI/180), this.attack, list, false, true);
					if (this.weapon != null) this.weapon.onAttackEnd(this, (LivingObject) obj); //WEAPON TRIGGER
					
				}
			}
		}
		
		@Override
		public void die() {
			spirits.remove(this);
			super.die();
		}
		
		public void damage(int damage, LivingObject source) {
			this.timeSinceDamaged = 0;
			if (!source.equals(wielder)) this.targets.add(source);
			super.damage(damage, source);
		}
		
		public void damage(int amount, LivingObject source, String effect) {
			this.damage(amount, source);
			this.lastDamageEffect = effect;
		}
		
		@Override
		public void draw(Graphics g, Player player, double x, double y, double size) {
			int alpha = (this.timeSinceDeath > 25) ? 0 : 155 - (this.timeSinceDeath * 5);
			int drawX = (int) ( (this.x - (this.size_x)/2 - (x - size/2)) * (Main.SIZE/size));
			int drawY = (int) ( (size - (this.y + (this.size_y)/2) + (y - size/2)) * (Main.SIZE/size));
			
			if (this.required) {
				g.setColor(new Color(255, 255, 255, alpha));
				g.fillRoundRect(drawX-1, drawY-1, (int) (this.size_x * Main.SIZE/size)+2, (int) (this.size_y * Main.SIZE/size)+2, 
						(int)(5*(Main.SIZE/size)), (int)(5*(Main.SIZE/size)));
			}
			
			
			g.setColor(new Color(this.color.getRed(),this.color.getGreen(),this.color.getBlue(), alpha));
			g.fillRoundRect(drawX, drawY, (int) (this.size_x * Main.SIZE/size), (int) (this.size_y * Main.SIZE/size), 
					(int)(5*(Main.SIZE/size)), (int)(5*(Main.SIZE/size)));
			
			//damage
			if (this.dmgTime != 0) {
				if (this.overheal > 100) g.setColor(new Color(0, 255, 255, this.dmgTime));
				else if (this.overheal > 0) g.setColor(new Color(255, 215, 0, this.dmgTime));
				else if (this.lastDamageEffect != null) Effect.getEffectColor(g, lastDamageEffect, this.dmgTime);
				else g.setColor(new Color(255, 0, 0, this.dmgTime));
						
				g.fillRoundRect(drawX, drawY, (int) (this.size_x * Main.SIZE/size), (int) (this.size_y * Main.SIZE/size), 
				(int)(5*(Main.SIZE/size)), (int)(5*(Main.SIZE/size)));
			}
					
			if (this.dmgTime > 0) this.dmgTime -= 5;
			
			//eyes
			Graphics2D g2d = (Graphics2D) g;
			g2d.setStroke(new BasicStroke((float)(4*(Main.SIZE/size))));
			g2d.setColor(new Color(this.eyeColor.getRed(),this.eyeColor.getGreen(),this.eyeColor.getBlue(), alpha));
			int x1, x2;
			if (this.lastDirection == 1) {
				x1 = (int) (drawX+(this.size_x*(Main.SIZE/size)*2/5));
				x2 = (int) (drawX+(this.size_x*(Main.SIZE/size)*3/4));
			} else {
				x1 = (int) (drawX+(this.size_x*(Main.SIZE/size)*1/4));
				x2 = (int) (drawX+(this.size_x*(Main.SIZE/size)*3/5));
			}
			int y1 = (int) (drawY+(this.size_y*(Main.SIZE/size)*1/4));
			int y2 = (int) (drawY+(this.size_y*(Main.SIZE/size)*2/4));
			g2d.drawLine(x1, y1, x1, y2);
			g2d.drawLine(x2, y1, x2, y2);
					
			//DRAW HEALTHBAR
			
			drawX = (int) ( (this.x - (this.size_x)/2 - (x - size/2)) * (Main.SIZE/size)); 
			drawY = (int) ( (size - (this.y + (this.size_y)/2) + (y - size/2) - 10) * (Main.SIZE/size));
			
			g.setColor(new Color(0,0,0,alpha));
			g.fillRect(drawX, drawY, (int) (this.size_y * Main.SIZE/size), (int)(5*(Main.SIZE/size)));
			g.setColor(new Color(255,0,0,alpha));
			g.fillRect(drawX, drawY, (int) (this.size_y * ((double) this.health/this.maxHealth) * Main.SIZE/size), (int)(5*(Main.SIZE/size)));
			
			if (this.overheal != 0) {
				int overHeal = this.overheal;
				if (this.overheal > this.maxHealth) overHeal = this.maxHealth;
				
				g.setColor(GameObject.COLOR_GOLD);
				g.fillRect(drawX, drawY, (int) (this.size_y * ((double) overHeal/this.maxHealth) * Main.SIZE/size), (int)(5*(Main.SIZE/size)));
				
			}
			
			if (this.overheal > this.maxHealth) {
				int gigaHeal = this.overheal-this.maxHealth;
				if (gigaHeal > this.maxHealth) gigaHeal = this.maxHealth;
				
				g.setColor(GameObject.COLOR_DIAMOND);
				g.fillRect(drawX, drawY, (int) (this.size_y * ((double) gigaHeal/this.maxHealth) * Main.SIZE/size), (int)(5*(Main.SIZE/size)));
				
			}
			
		}
		
		
	}
	

}
