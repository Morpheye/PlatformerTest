package platformerTest.assets.creature.creatures;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import platformerTest.Main;
import platformerTest.assets.creature.CreatureAi;
import platformerTest.assets.creature.ai.HorizontalMovementAi;
import platformerTest.assets.creature.ai.attack.NormalMovementAttackAi;
import platformerTest.assets.creature.ai.attack.ProjectileAttack;
import platformerTest.assets.creature.ai.horizontal.HorizontalFollowAi;
import platformerTest.assets.creature.ai.vertical.VerticalFollowAi;
import platformerTest.assets.projectiles.ProjectileDart;
import platformerTest.game.GameObject;
import platformerTest.game.Player;
import platformerTest.menu.GamePanel;

public class CreatureDartGoblin extends CreatureGoblin {

	public int projectileRange;
	
	/**
	* Create a dart goblin following an x point with unlimited range and set projectile range
	*/
	public CreatureDartGoblin(double initX, double initY, double size, int projectileRange) {
		this(initX, initY, size, 0, Double.MAX_VALUE, projectileRange);
	}
	
	/**
	* Create a dart goblin following an x point with limited range and set projectile range
	*/
	public CreatureDartGoblin(double initX, double initY, double size, double minRange, double maxRange, int projectileRange) {
		super(initX, initY, size, minRange, maxRange);
		
		this.jumpStrength = 16;
		this.projectileRange = projectileRange;
		this.health = 30;
		this.maxHealth = 30;
		this.rangedAttackDamage = 3;
		
		this.aiList = new ArrayList<CreatureAi>();
		this.aiList.add(new VerticalFollowAi(minRange, maxRange, 50, 100, 50, 100, GamePanel.player)); //jumps to shoot at player
		this.aiList.add(new HorizontalMovementAi(this.x, 40, Double.MAX_VALUE)); //tries to return to original spot
		this.aiList.add(new ProjectileAttack(maxRange, 10, GamePanel.player));
		this.aiList.add(new NormalMovementAttackAi(this.attackRange/2, GamePanel.player));
	}
	
	/**
	* Create a dart goblin following the player within a certain range with a set projectile range
	*/
	public CreatureDartGoblin(double initX, double initY, double size, double minRangeX, double maxRangeX, double minRangeY, double maxRangeY, int projectileRange) {
		super(initX, initY, size, minRangeX, maxRangeX, minRangeY, maxRangeY);
		
		this.jumpStrength = 16;
		this.projectileRange = projectileRange;
		this.health = 30;
		this.maxHealth = 30;
		this.rangedAttackDamage = 3;
		
		this.aiList = new ArrayList<CreatureAi>();
		this.aiList.add(new VerticalFollowAi(minRangeX, maxRangeX, minRangeY, maxRangeY, minRangeY, maxRangeY, GamePanel.player)); //jumps to shoot at player
		this.aiList.add(new HorizontalFollowAi(minRangeX, maxRangeX, minRangeY, maxRangeY, GamePanel.player)); //follow player
		this.aiList.add(new ProjectileAttack(maxRangeX, 10, GamePanel.player));
		this.aiList.add(new NormalMovementAttackAi(this.attackRange/2, GamePanel.player));
	}
	
	@Override
	public void rangedAttack() {
		GamePanel.projectiles.add(new ProjectileDart(this.x, this.y, 15*this.lastDirection, 1, this, this.rangedAttackDamage));
		this.attackCooldown = this.maxAttackCooldown;
	}
	
	@Override
	public void draw(Graphics g, Player player, double x, double y, double size) {
		super.draw(g, player, x, y, size);
		int drawX = (int) ( (this.x - (this.size_x)/2 - (x - size/2)) * (Main.SIZE/size));
		int drawY = (int) ( (size - (this.y + (this.size_y)/2) + (y - size/2)) * (Main.SIZE/size));
		
		//mask
		g.setColor(GameObject.COLOR_WOOD);
		g.fillRoundRect(drawX-(int)(2*Main.SIZE/size), drawY-(int)(2*Main.SIZE/size), (int) (this.size_x * Main.SIZE/size)+(int)(4*Main.SIZE/size),
				(int) (this.size_y*5/6 * Main.SIZE/size), (int)(5*(Main.SIZE/size)), (int)(5*(Main.SIZE/size)));
		
		//redraw eyes and draw mouth
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke((float)(4*(Main.SIZE/size))));
		g2d.setColor(this.eyeColor);
		int x1, x2, x3;
		if (this.lastDirection == 1) {
			x1 = (int) (drawX+(this.size_x*(Main.SIZE/size)*2/5));
			x2 = (int) (drawX+(this.size_x*(Main.SIZE/size)*3/4));
			
			x3 = (int) (drawX+(this.size_x*(Main.SIZE/size)*3/5));
			
		} else {
			x1 = (int) (drawX+(this.size_x*(Main.SIZE/size)*1/4));
			x2 = (int) (drawX+(this.size_x*(Main.SIZE/size)*3/5));
			
			x3 = (int) (drawX+(this.size_x*(Main.SIZE/size)*2/5));
		}
		int y1 = (int) (drawY+(this.size_y*(Main.SIZE/size)*1/4));
		int y2 = (int) (drawY+(this.size_y*(Main.SIZE/size)*2/5));
		int y3 = (int) (drawY+(this.size_y*(Main.SIZE/size)*3/5));
		g2d.drawLine(x1, y1, x1, y2);
		g2d.drawLine(x2, y1, x2, y2);
		g2d.setColor(Color.BLACK);
		g2d.drawLine(x3, y3, x3, y3);
		
	}
	

}
