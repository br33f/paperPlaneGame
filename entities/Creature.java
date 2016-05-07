package entities;

import gameProj.Launcher;
import gameProj.World;

public abstract class Creature extends Entity
{
	//attributes
	protected int health;
	protected float speed, velocityX;
	protected World world;
	public static final int DEFAULT_HEALTH = 10;
	public static final float DEFAULT_SPEED = (float)2;
	protected float xMove, yMove;
	
	//methods
	public Creature(float x, float y, int width, int height, World world) 
	{
		super(x, y, width, height);
		this.health = DEFAULT_HEALTH;
		this.speed = DEFAULT_SPEED;
		this.xMove = 0;
		this.yMove = 0;
		this.velocityX = 0.0f;
		this.world = world;
	}
	public void move()
	{
		if(this.xMove * this.yMove != 0)
		{
			this.xMove /= Math.sqrt(2);
			this.yMove /= Math.sqrt(2);
		}
		this.moveX();
		this.moveY();
	}
	private void moveX()
	{
		if((this.xMove > 0 && this.x < Launcher.WINDOW_WIDTH - this.width) || (this.xMove < 0 && this.x >0))
		{
			float saved = this.x;
			this.x += this.xMove;
			float checkedX = this.x;
			if(this.xMove > 0)
				checkedX += 32;
		}
	}
	private void moveY()
	{
		if((this.yMove > 0 && this.y < Launcher.WINDOW_HEIGHT - this.height) || (this.yMove < 0 && this.y > 0))
		{
            float saved = this.y;
            this.y += this.yMove;
            float checkedY = this.y;
            if (this.yMove > 0)
                checkedY += 32;
            World.Accelerated = false;
		}
        else if(this.y <= 0)
            World.Accelerated = true;
		else
			this.yMove = 0;
	}
	public int getHealth() 
	{
		return health;
	}
	public void setHealth(int health) 
	{
		this.health = health;
	}
	public float getSpeed() 
	{
		return speed;
	}
	public void setSpeed(float speed) 
	{
		this.speed = speed;
	}
}
