package platformerTest.assets.creature.ai.attack;

import java.util.ArrayList;

import platformerTest.Projectile;
import platformerTest.assets.creature.ai.AttackAi;
import platformerTest.game.Creature;
import platformerTest.game.GameObject;

public class ProjectileAttack extends AttackAi {

	double xBoundLeft;
	double yBoundLeft;
	double xBoundRight;
	double yBoundRight;
	Projectile projectile;
	
	public ProjectileAttack(double xBound, double yBound, ArrayList<GameObject> targets, Projectile projectile) {
		this(xBound, xBound, yBound, yBound, targets, projectile);
	}
	
	public ProjectileAttack(double xBound, double yBound, GameObject target, Projectile projectile) {
		this(xBound, xBound, yBound, yBound, target, projectile);
	}
	
	public ProjectileAttack(double xBoundLeft, double xBoundRight, double yBoundLeft, double yBoundRight, GameObject target, Projectile projectile) {
		super(0, target);
		
		this.xBoundLeft = xBoundLeft;
		this.xBoundRight = xBoundRight;
		this.yBoundLeft = yBoundLeft;
		this.yBoundRight = yBoundRight;
		
		this.projectile = projectile;
	}
	
	public ProjectileAttack(double xBoundLeft, double xBoundRight, double yBoundLeft, double yBoundRight, ArrayList<GameObject> targets, Projectile projectile) {
		super(0, targets);
		
		this.xBoundLeft = xBoundLeft;
		this.xBoundRight = xBoundRight;
		this.yBoundLeft = yBoundLeft;
		this.yBoundRight = yBoundRight;
		
		this.projectile = projectile;
	}
	

	
	@Override
	public void run(Creature c) {
		
	}

}
