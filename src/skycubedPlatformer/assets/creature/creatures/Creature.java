package skycubedPlatformer.assets.creature.creatures;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.sound.sampled.AudioSystem;

import skycubedPlatformer.Main;
import skycubedPlatformer.assets.creature.CreatureAi;
import skycubedPlatformer.assets.decoration.particles.CoinParticle;
import skycubedPlatformer.assets.decoration.particles.GemParticle;
import skycubedPlatformer.assets.effects.Effect;
import skycubedPlatformer.game.GameObject;
import skycubedPlatformer.game.LivingObject;
import skycubedPlatformer.game.ObjType;
import skycubedPlatformer.game.Player;
import skycubedPlatformer.menu.GamePanel;
import skycubedPlatformer.util.SoundHelper;

public class Creature extends LivingObject {

	public int minCoins = 0; //minimum coin drop
	public int maxCoins = 0; //max extra coin drop
	public double coinWeight = 2; //maximum amount a coin can hold
	
	public double gemChance = 0; //chance of one gem
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
			SoundHelper.loadSound(this, this.attackSound, inputAttack);
			SoundHelper.loadSound(this, this.hitSound, inputHit);
			
			} catch (Exception e) {}
		
	}
	
	@Override
	public void move() {
		super.move();

		//CHECK BOUNDS
		if (this.y > GamePanel.getPanel().level.topLimit) GamePanel.getPanel().deletedObjects.add(this);
		if (this.y < GamePanel.getPanel().level.bottomLimit) GamePanel.getPanel().deletedObjects.add(this);
		if (this.timeSinceDeath > 60) GamePanel.getPanel().deletedObjects.add(this);
		
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
	public void damage(int damage, GameObject source, String effect) {
		super.damage(damage, source, effect);
		for (CreatureAi ai: this.aiList) ai.onDamage(this, source); 

	}
	
	@Override
	public void damage(int damage, GameObject source) {
		super.damage(damage, source);
		for (CreatureAi ai: this.aiList) ai.onDamage(this, source); 

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
		Creature.loot(this);
	}
	
	public static void loot(Creature c) {
		if (Math.random() > (1 - c.gemChance * (1 + GamePanel.getPanel().player.luck) )) GemParticle.spawnGem(c.x, c.y, 1); //drop gem
		
		int coinAmount = (int) ((c.minCoins + (int) (Math.random() * (c.maxCoins - c.minCoins + 1))) * (1 + GamePanel.getPanel().player.luck));
		if (coinAmount < 0) coinAmount = 0;
		CoinParticle.spawnCoins(c.x, c.y, (int) Math.ceil(coinAmount / c.coinWeight), coinAmount);
	}
	

	
}
