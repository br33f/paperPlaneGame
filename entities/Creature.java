package entities;

import game.Launcher;
import game.World;

public abstract class Creature extends Entity
{
	//attributes
	protected int health;
	protected float speed, velocityX;
	protected World world;
	public static final float DEFAULT_SPEED = (float)2;
	protected float xMove, yMove;
	
	//methods
	public Creature(float x, float y, int width, int height, World world) 
	{
		super(x, y, width, height);
		this.speed = DEFAULT_SPEED;
		this.xMove = 0;
		this.yMove = 0;
		this.velocityX = 0.0f;
		this.world = world;
	}
}
