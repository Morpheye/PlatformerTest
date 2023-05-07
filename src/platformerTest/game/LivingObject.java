package platformerTest.game;

import java.awt.Color;
import java.util.ArrayList;

import javax.sound.sampled.Clip;

import platformerTest.assets.effects.Effect;
import platformerTest.weapons.Weapon;

public class LivingObject extends MovableObject {

	public Clip attackSound;
	public Clip hitSound;
	
	public double movementSpeed;
	public double jumpStrength;
	
	public boolean isAlive;
	
	public boolean movingInLiquid;
	
	public int health; //PLAYER HP, MAX=100
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
	
	public void damage(int amount, GameObject source) {
		
	}
	
	public void playHitSound(LivingObject attacker) {
		if (attacker.hitSound != null) {
			attacker.hitSound.setMicrosecondPosition(0);
			attacker.hitSound.start();
		}
	}
	
	public void playAttackSound() {
		if (this.attackSound != null) {
			this.attackSound.setMicrosecondPosition(0);
			this.attackSound.start();
		}
	}

}
