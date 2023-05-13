package platformerTest.assets.creature.creatures;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.sound.sampled.AudioSystem;

import platformerTest.Main;
import platformerTest.appdata.DataManager;
import platformerTest.assets.creature.CreatureAi;
import platformerTest.assets.creature.ai.AttackAi;
import platformerTest.assets.effects.Effect;
import platformerTest.game.GameObject;
import platformerTest.game.LivingObject;
import platformerTest.game.ObjType;
import platformerTest.game.Player;
import platformerTest.menu.GamePanel;
import platformerTest.weapons.weaponsT5.SpiritScythe;

public class Creature extends LivingObject {

	public double gemChance = 0;
	public boolean required;
	
	public Color eyeColor;
	public boolean friendlyFire;

	public ArrayList<CreatureAi> aiList;
	
	public Creature(double initX, double initY, double size, Color color, Color eyeColor, double density,
			int maxHealth, double movementSpeed, double jumpStrength, int AttackDamage,
			int AttackRange, int AttackSpeed, int AttackKnockback) {
		super(initX, initY, size, size, color, 1.0);
		
		this.type = ObjType.Creature;
		this.movementSpeed = 0.1;
		this.jumpStrength = 10;
		
		this.eyeColor = eyeColor;
		
		this.density = density;
		this.movable = true;
		this.x = initX;
		this.y = initY;
		this.size_x = size;
		this.size_y = size;
		this.slipperiness = 1;
		this.isAlive = true;
		this.required = false;
		
		//combat
		this.dmgTime = 0;
		this.timeSinceDeath = 0;
		this.timeSinceDamaged = 0;
		this.fireResistant = false;
		
		this.movementSpeed = movementSpeed;
		this.jumpStrength = jumpStrength;
		
		this.maxHealth = maxHealth;
		this.health = this.maxHealth;
		this.overheal = 0;
		this.maxAttackCooldown = AttackSpeed;
		this.attackCooldown = 0;
		this.attackRange = AttackRange;
		this.attackDamage = AttackDamage;
		this.attackKnockback = AttackKnockback;
		
		this.attack = new CreatureAttack(this.size_x, this.size_y);

		this.aiList = new ArrayList<CreatureAi>();
		this.friendlyFire = true;
		
		String inputAttack;
		String inputHit;
		
		if (this.weapon != null) { //sounds
			this.weapon.init(this);
			if (this.weapon.attackSound != null) inputAttack = this.weapon.attackSound;
			else inputAttack = "/sounds/attack/default/attack.wav";
			
			if (this.weapon.hitSound != null) inputHit = this.weapon.hitSound;
			else inputHit = "/sounds/attack/default/hit.wav";
			
		} else {
			inputAttack = "/sounds/attack/default/attack.wav";
			inputHit = "/sounds/attack/default/hit.wav";
		}
			try {
			
			this.attackSound = AudioSystem.getClip();
			this.hitSound = AudioSystem.getClip();
			DataManager.loadSound(this, this.attackSound, inputAttack);
			DataManager.loadSound(this, this.hitSound, inputHit);
			
			} catch (Exception e) {}
		
	}
	
	@Override
	public void move() {
		super.move();

		//CHECK BOUNDS
		if (this.y > GamePanel.level.topLimit) GamePanel.deletedObjects.add(this);
		if (this.y < GamePanel.level.bottomLimit) GamePanel.deletedObjects.add(this);
		if (this.timeSinceDeath > 60) GamePanel.deletedObjects.add(this);
		
		//AI goes here
		if (this.isAlive) {
			for (CreatureAi ai : this.aiList) {
				ai.run(this);
			}
		}
		
	}
	
	@Override
	public void draw(Graphics g, Player player, double x, double y, double size) {
		int alpha = (this.timeSinceDeath > 25) ? 0 : 255 - (this.timeSinceDeath * 10);
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
	
	@Override
	public void damage(int damage, LivingObject source, String effect) {
		super.damage(damage, source, effect);
		for (CreatureAi ai: this.aiList) ai.onDamage(this, source); 

	}
	
	@Override
	public void damage(int damage, LivingObject source) {
		super.damage(damage, source);
		for (CreatureAi ai: this.aiList) ai.onDamage(this, source); 

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
		for (GameObject obj : GamePanel.objects) { //check for enemies in range
			if (obj.equals(this)) continue;
			if (obj.hasCollided(this.attack) && obj.type.equals(ObjType.Creature)) {
				if (!this.friendlyFire && !((GamePanel.player.weapon != null && GamePanel.player.weapon instanceof SpiritScythe
						&& ((SpiritScythe) GamePanel.player.weapon).spirits.contains(obj)))) continue;
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
				
			} else if (obj.hasCollided(this.attack) && obj.type.equals(ObjType.Player)) {
				if (this.weapon != null) this.weapon.onAttackStart(this, (LivingObject) obj); //WEAPON TRIGGER
				((Player) obj).damage(this.attackDamage, this);
				
				//SOUND
				((LivingObject) obj).playHitSound(this);
				
				double pushStrength = this.attackKnockback;
				ArrayList<GameObject> list = new ArrayList<GameObject>();
				list.add(this.attack);
				((Player) obj).pushx(pushStrength * Math.cos(angle*Math.PI/180), this.attack, list, false, true);
				((Player) obj).pushy(pushStrength * Math.sin(angle*Math.PI/180), this.attack, list, false, true);
				if (this.weapon != null) this.weapon.onAttackEnd(this, (LivingObject) obj); //WEAPON TRIGGER
			}
		}
		
	}
	
	public void rangedAttack() {
		if (!this.isAlive) return;
	}
	
	public class CreatureAttack extends GameObject {
		public CreatureAttack(double size_x, double size_y) {
			super(0, 0, size_x, size_y, new Color(0,0,0,50));
			this.type = ObjType.Null;
			this.density = 1;
		}

	}
	
	@Override
	public void crush() {
		this.health = 0;
		this.die();
	}
	
	@Override
	public void die() {
		if (this.isAlive) {
			this.removeEffects.addAll(this.effects);
			this.isAlive = false;
			this.exists = false;
		}
	}
	
	public void dropLoot() {
		
	}
	

	
}
