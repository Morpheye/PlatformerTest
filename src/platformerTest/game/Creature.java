package platformerTest.game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import platformerTest.Main;
import platformerTest.assets.LiquidPlatform;
import platformerTest.assets.creature.CreatureAi;
import platformerTest.menu.GamePanel;

public class Creature extends MovableObject {

	public double movementSpeed = 0.1;
	public double jumpStrength = 10;
	
	public boolean isAlive;
	
	boolean movingInLiquid = false;
	
	public int health;
	public int maxHealth;
	public int maxAttackCooldown; //MAX ATTACK COOLDOWN
	public int attackRange;
	public int attackDamage;
	public int attackKnockback;
	public GameObject attack; //attack hitbox
	
	//lastattackinfo
	public int attackCooldown;
	public int lastAttackRange;
	public int lastAttackAngle;
	
	public ArrayList<CreatureAi> aiList;
	
	public Creature(double initX, double initY, double size, Color color, double density,
			int maxHealth, double movementSpeed, double jumpStrength, int AttackDamage,
			int AttackRange, int AttackSpeed, int AttackKnockback) {
		super(initX, initY, size, size, color, 1.0);
		
		this.type = ObjType.Creature;
		
		this.density = density;
		this.movable = true;
		this.x = initX;
		this.y = initY;
		this.size_x = size;
		this.size_y = size;
		this.slipperiness = 0.96;
		this.isAlive = true;
		
		//combat
		this.dmgTime = 0;
		
		this.movementSpeed = movementSpeed;
		this.jumpStrength = jumpStrength;
		
		this.maxHealth = maxHealth;
		this.health = this.maxHealth;
		this.maxAttackCooldown = AttackSpeed;
		this.attackCooldown = 0;
		this.attackRange = AttackRange;
		this.attackDamage = AttackDamage;
		this.attackKnockback = AttackKnockback;
		
		this.attack = new CreatureAttack(this.size_x, this.size_y);

		this.aiList = new ArrayList<CreatureAi>();
		
	}
	
	public boolean movingUp = false;
	public boolean movingDown = false;
	public boolean movingLeft = false;
	public boolean movingRight = false;
	public boolean isAttacking = false;
	
	public int lastDirection = 1;
	
	@Override
	public void move() {
		
		this.movingInLiquid = false;
		this.liquidDensity = 1;
		
		for (GameObject obj : GamePanel.objects) { //check for water
			if (obj.equals(this)) continue;
			//liquids
			if (this.hasCollided(obj) && obj.type.equals(ObjType.LiquidPlatform) && obj.exists) {
				this.movingInLiquid = true;
				this.liquidDensity = ((LiquidPlatform) obj).density;
			}
		}
		
		if (this.inLiquid) {
			double diff = this.density - liquidDensity;
			double lift = GamePanel.gravity * Math.atan(2*diff) / (Math.PI / 2) - GamePanel.gravity;
			
			this.vy += lift;

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
		
		if (this.y > GamePanel.level.topLimit && GamePanel.levelWon==0) GamePanel.restartLevel(GamePanel.level);
		if (this.y < GamePanel.level.bottomLimit && GamePanel.levelWon==0) GamePanel.restartLevel(GamePanel.level);
		
		if (this.health <= 0) this.die();
		
		//then attack
		if (this.attackCooldown > 0) this.attackCooldown--;
		if (this.isAttacking && this.attackCooldown == 0) this.attack();
		
		//then apply movableobject physics
		super.move();
		
		//AI goes here
		for (CreatureAi ai : this.aiList) {
			ai.run(this);
		}
		
	}
	
	@Override
	public void draw(Graphics g, Player player, double x, double y, double size) {
		int drawX = (int) ( (this.x - (this.size_x)/2 - (x - size/2)) * (Main.SIZE/size));
		int drawY = (int) ( (size - (this.y + (this.size_y)/2) + (y - size/2)) * (Main.SIZE/size));
		
		g.setColor(this.color);
		g.fillRoundRect(drawX, drawY, (int) (this.size_x * Main.SIZE/size), (int) (this.size_y * Main.SIZE/size), 
		5*(int)(Main.SIZE/size), 5*(int)(Main.SIZE/size));
		
		if (this.dmgTime != 0) {
			g.setColor(new Color(255, 0, 0, this.dmgTime));
			g.fillRoundRect(drawX, drawY, (int) (this.size_x * Main.SIZE/size), (int) (this.size_y * Main.SIZE/size), 
			5*(int)(Main.SIZE/size), 5*(int)(Main.SIZE/size));
		}
		if (this.dmgTime > 0) this.dmgTime -= 5;
		
		//DRAW HEALTHBAR
		
		drawX = (int) ( (this.x - (this.size_x)/2 - (x - size/2)) * (Main.SIZE/size)); 
		drawY = (int) ( (size - (this.y + (this.size_y)/2) + (y - size/2) - 10) * (Main.SIZE/size));
		
		g.setColor(Color.BLACK);
		g.fillRect(drawX, drawY, (int) (this.size_y * Main.SIZE/size), 5*(int)(Main.SIZE/size));
		g.setColor(Color.RED);
		g.fillRect(drawX, drawY, (int) (this.size_y * ((double) this.health/this.maxHealth) * Main.SIZE/size), 5*(int)(Main.SIZE/size));
		
	}
	
	public int dmgTime;
	
	public void damage(int damage, GameObject source) {
		this.health -= damage;
		
		if (damage > 20 & this.dmgTime < 255) this.dmgTime = 255;
		else if (damage > 5 & this.dmgTime < 150) this.dmgTime = 150;
		else if (this.dmgTime < 50) this.dmgTime = 50;
		
		if (this.health <= 0) this.die();
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
		
		//Apply collisions
		for (GameObject obj : GamePanel.objects) { //check for enemies in range
			if (obj.equals(this)) continue;
			if (obj.hasCollided(this.attack) && obj.type.equals(ObjType.Creature)) {
				((Creature) obj).damage(this.attackDamage, this);
				
				int pushStrength = this.attackKnockback;
				ArrayList<GameObject> list = new ArrayList<GameObject>();
				list.add(this);
				((Creature) obj).pushx(pushStrength * Math.cos(angle*Math.PI/180), this, list, false, true);
				((Creature) obj).pushy(pushStrength * Math.sin(angle*Math.PI/180), this, list, false, true);
				
			} else if (obj.hasCollided(this.attack) && obj.type.equals(ObjType.Player)) {
				((Player) obj).damage(this.attackDamage, this);
				
				int pushStrength = this.attackKnockback;
				ArrayList<GameObject> list = new ArrayList<GameObject>();
				list.add(this);
				((Player) obj).pushx(pushStrength * Math.cos(angle*Math.PI/180), this, list, false, true);
				((Player) obj).pushy(pushStrength * Math.sin(angle*Math.PI/180), this, list, false, true);
			}
		}
		
	}
	
	public class CreatureAttack extends GameObject {
		public CreatureAttack(double size_x, double size_y) {
			super(0, 0, size_x, size_y, new Color(0,0,0,50));
			this.type = ObjType.Null;
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
			this.isAlive = false;
			this.exists = false;
		}
	}
	

	
}
