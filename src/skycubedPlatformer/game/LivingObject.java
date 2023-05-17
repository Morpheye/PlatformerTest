package skycubedPlatformer.game;

import java.awt.Color;
import java.util.ArrayList;

import javax.sound.sampled.Clip;

import skycubedPlatformer.assets.LiquidPlatform;
import skycubedPlatformer.assets.creature.creatures.Creature;
import skycubedPlatformer.assets.effects.Effect;
import skycubedPlatformer.menu.GamePanel;
import skycubedPlatformer.weapons.Weapon;
import skycubedPlatformer.weapons.weaponsT5.SpiritScythe;

public class LivingObject extends MovableObject {

	public Clip attackSound;
	public Clip hitSound;
	
	public double movementSpeed;
	public double jumpStrength;
	
	public boolean isAlive;
	
	public boolean movingInLiquid;
	
	public int maxHealth;
	public int health;
	public int overheal;
	public boolean fireResistant;
	
	public int maxAttackCooldown; //MAX ATTACK COOLDOWN
	public int attackRange;
	
	public int attackDamage;
	public int rangedAttackDamage; //Not in use
	
	public double attackKnockback;
	public GameObject attack; //attack hitbox
	public Weapon weapon;
	public ArrayList<Effect> effects;
	
	//lastattackinfo
	public int attackCooldown;
	public int meleeCooldown;
	public int lastAttackRange;
	public int lastAttackAngle;
	
	public int timeSinceDamaged;
	public int timeSinceDeath;
	
	//movement & attack
	
	public boolean movingUp = false;
	public boolean movingDown = false;
	public boolean movingLeft = false;
	public boolean movingRight = false;
	public boolean isAttacking = false;
	public boolean isRangedAttacking = false;
	
	public int lastDirection = 1;
	
	public LivingObject(double x, double y, double size_x, double size_y, Color color, double density) {
		super(x, y, size_x, size_y, color, density);
		this.effects = new ArrayList<Effect>();
		// TODO Auto-generated constructor stub
	}
	
	protected ArrayList<Effect> removeEffects = new ArrayList<Effect>();
	
	@Override
	public void move() {
		this.movingInLiquid = false;
		this.liquidDensity = 1;
		
		if (this.weapon != null) this.weapon.onTick(this);
		
		for (GameObject obj : GamePanel.objects) { //check for water
			if (obj.equals(this)) continue;
			//liquids
			if (this.hasCollided(obj) && obj.type.equals(ObjType.LiquidPlatform) && obj.exists) {
				this.movingInLiquid = true;
				this.liquidDensity = ((LiquidPlatform) obj).density;
			}
		}
		
		if (this.vy > 0) this.inAir = true;
		
		if (movingInLiquid) {
			double slowdown = 1;
			double diff = this.density - liquidDensity;
			if (diff > 1) {
				slowdown = 1/(diff+0.5); //starts at 1, approaches 0
			}
			
			if (this.movingUp) this.vy += Math.pow(1, slowdown)*this.movementSpeed;
			if (this.movingDown)this.vy -= Math.pow(1, slowdown)*this.movementSpeed;
			if (this.movingRight) this.vx += Math.pow(1, slowdown)*this.movementSpeed;
			if (this.movingLeft) this.vx -= Math.pow(1, slowdown)*this.movementSpeed;
			
		} else {
			if (this.movingUp & !this.inAir) { //jump
				this.vy += this.jumpStrength;
				this.inAir = true;
			}
			if (this.movingRight) this.vx += this.movementSpeed;
			if (this.movingLeft) this.vx -= this.movementSpeed;
		}
		
		if (this.health <= 0) this.die();
		if (this.health > this.maxHealth) this.health = this.maxHealth; 
		
		//then attack
		if (this.attackCooldown > 0) this.attackCooldown--;
		if (this.meleeCooldown > 0) this.meleeCooldown--;
		if (this.isAttacking && this.attackCooldown == 0) this.attack();
		if (this.isRangedAttacking && this.attackCooldown == 0) this.rangedAttack();
		
		//if dead
		if (!this.isAlive) this.timeSinceDeath++; 
		
		//Apply effects
		for (Effect e: this.effects) {
			e.update(this);
			if (e.lifetime <= 0) removeEffects.add(e);
		}
		this.effects.removeAll(removeEffects);
		removeEffects.clear();
		
		//then apply movableobject physics
		super.move();
		
		
	}
	
	public void applyEffect(Effect newEffect) {
		for (Effect e: this.effects) {
			if (e.getClass().equals(newEffect.getClass())) { //already has effect
				e.applier = newEffect.applier; //override old applier
				if (e.strength < newEffect.strength) e.strength = newEffect.strength; //if stronger, override old
				if (e.lifetime < newEffect.lifetime) e.lifetime = newEffect.lifetime; //if longer, override old 
				return;
			}
		}
		//no effect found
		this.effects.add(newEffect);
	}
	public Effect hasEffect(String effect) {
		for (Effect e: this.effects) {
			if (e.name.equals(effect)) { //already has effect
				return e;
			}
		}
		return null;
	}
	public Effect hasEffect(Effect effect) {
		return this.hasEffect(effect.name);
	}
	public void removeEffect(String effect) {
		ArrayList<Effect> removed = new ArrayList<Effect>();
		for (Effect e: this.effects) {
			if (e.name.equals(effect)) { //already has effect
				removed.add(e);
			}
		}
		
		this.effects.removeAll(removed);
	}
	public void removeEffect(Effect effect) {
		this.removeEffect(effect.name);
	}
	
	public int dmgTime;
	public String lastDamageEffect;
	public void damage(int damage, LivingObject source) {
		this.timeSinceDamaged = 0;
		
		if (this.overheal != 0) {
			if (this.overheal >= damage) this.overheal -= damage;
			else {
				int newDamage = damage - this.overheal;
				this.overheal = 0;
				this.health -= newDamage;
			}
		} else this.health -= damage;
		
		if (damage > 20 & this.dmgTime < 255) this.dmgTime = 255;
		else if (damage > 5 & this.dmgTime < 175) this.dmgTime = 175;
		else if (this.dmgTime < 100) this.dmgTime = 100;
		this.lastDamageEffect = null;
		
		if (this.health <= 0 && this.isAlive) {
			this.health = 0;
			this.die();
			if (source != null) {
				if ((source.equals(GamePanel.player) || source instanceof SpiritScythe.ScytheSpirit)
						&& this.type.equals(ObjType.Creature)) {
					if (GamePanel.player.weapon != null) GamePanel.player.weapon.onKill(source, this); //WEAPON TRIGGER
					((Creature) this).dropLoot();
			}}
		}
		
		if (this.weapon != null) this.weapon.onUserHit(this, (LivingObject) source); //WEAPON TRIGGER
	}
	
	public void damage(int amount, LivingObject source, String effect) {
		this.damage(amount, source);
		this.lastDamageEffect = effect;
	}
	
	public void attack() {}
	public void rangedAttack() {}
	
	public void playHitSound(LivingObject attacker) {
		if (attacker.hitSound != null) {
			attacker.hitSound.stop();
			attacker.hitSound.setMicrosecondPosition(0);
			attacker.hitSound.start();
		}
	}
	
	public void playAttackSound() {
		if (this.attackSound != null) {
			this.hitSound.stop();
			this.attackSound.setMicrosecondPosition(0);
			this.attackSound.start();
		}
	}

}
