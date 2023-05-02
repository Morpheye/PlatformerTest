package platformerTest.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import platformerTest.Main;
import platformerTest.assets.LiquidPlatform;
import platformerTest.assets.Trigger;
import platformerTest.assets.triggers.Powerup;
import platformerTest.menu.GamePanel;

public class Player extends MovableObject {

	public double movementSpeed = 0.25;
	public double jumpStrength = 16;
	
	public boolean isAlive;
	
	boolean movingInLiquid = false;
	
	public int health; //PLAYER HP, MAX=100
	public int overheal;
	public boolean fireResistant;
	
	public int maxAttackCooldown = 40; //MAX ATTACK COOLDOWN
	public int attackRange = 20;
	
	public int attackDamage = 5;
	public int rangedAttackDamage = 5; //Not in use
	
	public double attackKnockback = 2;
	public GameObject attack; //attack hitbox
	
	//lastattackinfo
	public int attackCooldown;
	public int meleeCooldown;
	public int lastAttackRange;
	public int lastAttackAngle;
	
	public int timeSinceDamaged;
	public int timeSinceDeath;
	
	public Player(double initX, double initY, double size) {
		super(initX, initY, size, size, Color.WHITE, 1.0);
		
		this.type = ObjType.Player;
		
		this.movable = true;
		this.x = initX;
		this.y = initY;
		this.size_x = size;
		this.size_y = size;
		this.slipperiness = 1;
		this.isAlive = true;
		this.density = 1;
		
		//combat
		this.dmgTime = 0;
		
		this.health = 100;
		this.overheal = 0;
		this.timeSinceDamaged = 0;
		this.timeSinceDeath = 0;
		this.fireResistant = false;
		
		this.maxAttackCooldown = 40;
		this.attackCooldown = 0;
		this.attackRange = 20;
		this.attackDamage = 5;
		this.rangedAttackDamage = 5;
		
		this.attackKnockback = 2;
		
		this.attack = new PlayerAttack(this.size_x, this.size_y);


		
		
	}
	
	public boolean movingUp = false;
	public boolean movingDown = false;
	public boolean movingLeft = false;
	public boolean movingRight = false;
	public boolean isAttacking = false;
	public boolean isRangedAttacking = false;
	
	public int lastDirection = 1;
	
	@Override
	public void move() {
		
		this.movingInLiquid = false;
		this.liquidDensity = 1;
		
		//Check interactables and water
		for (GameObject obj : GamePanel.objects) { //check for water
			if (obj.equals(this)) continue;
			//finish flag
			if (obj.hasCollided(this) && obj.type.equals(ObjType.FinishFlag) && GamePanel.levelWon==0 && obj.exists) {
				GamePanel.levelWon=1;
				GamePanel.targetCoins += GamePanel.level.reward;
			}
			//powerup
			if (obj.hasCollided(this) && obj.type.equals(ObjType.Powerup) && GamePanel.levelWon==0 && obj.exists) {
				((Powerup) obj).run();
				obj.exists = false;
			}
			//trigger
			if (obj.hasCollided(this) && obj.type.equals(ObjType.Trigger) && GamePanel.levelWon==0 && obj.exists) {
				((Trigger) obj).run();
				obj.exists = false;
			}
			//liquids
			if (this.hasCollided(obj) && obj.type.equals(ObjType.LiquidPlatform) && obj.exists) {
				this.movingInLiquid = true;
				this.liquidDensity = ((LiquidPlatform) obj).density;
			}
		}
		
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
		
		//CHECK BOUNDS
		if (this.y > GamePanel.level.topLimit && GamePanel.levelWon==0) GamePanel.restartLevel(GamePanel.level);
		if (this.y < GamePanel.level.bottomLimit && GamePanel.levelWon==0) GamePanel.restartLevel(GamePanel.level);
		if (this.timeSinceDeath > 120 && GamePanel.levelWon==0) GamePanel.restartLevel(GamePanel.level);
		
		//hp shenanigans
		if (timeSinceDamaged >= 1320) {
			this.timeSinceDamaged -= 120;
			if (this.health < 100) this.health++;
		}
		this.timeSinceDamaged++;
		
		if (this.health <= 0) this.die();
		
		//then attack
		
		if (this.attackCooldown > 0) this.attackCooldown--;
		if (this.meleeCooldown > 0) this.meleeCooldown--;
		if (this.isAttacking && this.attackCooldown == 0) this.attack();
		if (this.isRangedAttacking && this.attackCooldown == 0) this.rangedAttack();
		
		//if dead
		if (!this.exists) this.timeSinceDeath++; 
		
		//then apply movableobject physics
		super.move();
		
		
	}
	
	@Override
	public void draw(Graphics g, Player player, double x, double y, double size) {
		int drawX = (int) ( (this.x - (this.size_x)/2 - (x - size/2)) * (Main.SIZE/size));
		int drawY = (int) ( (size - (this.y + (this.size_y)/2) + (y - size/2)) * (Main.SIZE/size));
		//WHITE and modify opacity for deathtime
		g.setColor(new Color(this.color.getRed(),this.color.getGreen(),this.color.getBlue(),255-(2*this.timeSinceDeath)));
		g.fillRoundRect(drawX, drawY, (int) (this.size_x * Main.SIZE/size), (int) (this.size_y * Main.SIZE/size), 
		(int)(5*(Main.SIZE/size)), (int)(5*(Main.SIZE/size)));
		
		//eyes
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke((float)(4*(Main.SIZE/size))));
		g2d.setColor(new Color(0,0,0,255-(2*this.timeSinceDeath))); //BLACK and modify opacity for deathtime
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
		
		//damage
		if (this.dmgTime != 0) {
			if (this.overheal > 100) g.setColor(new Color(0, 255, 255, this.dmgTime));
			else if (this.overheal > 0) g.setColor(new Color(255, 215, 0, this.dmgTime));
			else g.setColor(new Color(255, 0, 0, this.dmgTime));
			
			g.fillRoundRect(drawX, drawY, (int) (this.size_x * Main.SIZE/size), (int) (this.size_y * Main.SIZE/size), 
			(int)(5*(Main.SIZE/size)), (int)(5*(Main.SIZE/size)));
		}
		
		if (this.dmgTime > 0) this.dmgTime -= 5;
		
	}
	
	public int dmgTime;
	
	public void damage(int damage, GameObject source) {
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
		
		if (this.health <= 0) {
			this.health = 0;
			this.die();
		}
	}
	
	public void attack() {
		if (!this.isAlive) return;
		
		//move attackbox
		this.attack.x = this.x + 0;
		this.attack.y = this.y + 0;
		
		//check direction
		int calcX = 0, calcY = 0, angle = 0;
		if (this.movingUp) calcY++;
		if (this.movingDown) calcY--;
		if (this.movingRight || this.movingLeft) calcX += this.lastDirection;
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
		
		//Apply collisions
		for (GameObject obj : GamePanel.objects) { //check for enemies in range
			if (obj.equals(this)) continue;
			if (obj.hasCollided(this.attack) && obj.type.equals(ObjType.Creature)) {
				((Creature) obj).damage(this.attackDamage, this);
				
				double pushStrength = this.attackKnockback;
				ArrayList<GameObject> list = new ArrayList<GameObject>();
				list.add(this.attack);
				
				((Creature) obj).pushx(pushStrength * Math.cos(angle*Math.PI/180), this.attack, list, false, true);
				((Creature) obj).pushy(pushStrength * Math.sin(angle*Math.PI/180), this.attack, list, false, true);
				
		}}
		
	}
	
	public void rangedAttack() {
		if (!this.isAlive) return;
	}
	
	public class PlayerAttack extends GameObject {
		public PlayerAttack(double size_x, double size_y) {
			super(0, 0, size_x, size_y, new Color(0,0,0,50));
			this.type = ObjType.Null;
			this.density = 1;
		}
		
		@Override
		public void draw(Graphics g, Player player, double cam_x, double cam_y, double size) {
			int drawX = (int) ( (this.x - (this.size_x)/2 - (cam_x - size/2)) * (Main.SIZE/size));
			int drawY = (int) ( (size - (this.y + (this.size_y)/2) + (cam_y - size/2)) * (Main.SIZE/size));
			
			g.setColor(this.color);
			g.fillRoundRect(drawX, drawY, (int) (this.size_x * Main.SIZE/size), (int) (this.size_y * Main.SIZE/size), 
			5*(int)(Main.SIZE/size), 5*(int)(Main.SIZE/size));
			
		}

	}
	
	@Override
	public void crush() {
		if (GamePanel.levelWon == 0) {
			this.health = 0;
			this.die();
		}
	}
	
	@Override
	public void die() {
		if (GamePanel.levelWon == 0 && this.isAlive) {
			GamePanel.createFlash(Color.BLACK, 150);
			this.isAlive = false;
			this.exists = false;
		}
	}
	

	
}
