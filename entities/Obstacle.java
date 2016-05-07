package entities;

import java.awt.Color;
import java.awt.Graphics;

import gameProj.World;

public class Obstacle extends Entity
{
	//attributes
	public static int colors[][] = {{111,183,214},{187,214,111},{170,116,222},{215,178,113},{255,88,94},{222,117,174}};
	private float speedX, speedY;
	private int colorVariant;
	
	//methods
	public Obstacle(float x, float y, int width, int height, float speedX, float speedY, int colorVariant) 
	{
		super(x, y, width, height);
		this.speedX = speedX;
		this.speedY = speedY;
		this.colorVariant = colorVariant;
	}
	
	public void movement()
	{
        float factor = (World.Accelerated) ? 1.5f : 1.0f;
		this.x += this.speedX * factor;
		this.y += this.speedY * factor;
	}
	
	public boolean collision(float x1, float x2, float x3, float y1, float y2, float y3)
	{
		boolean returnedValue = false;
		float center_x = x + width / 2;
		float center_y = y + height / 2;
		float collisionPoints[][] =	{
				{this.x, this.y},
				{this.x, this.y + this.height/2},
				{this.x + this.width/2, this.y},
				{this.x + this.width, this.y},
				{this.x + this.width, this.y + this.height/2},
				{this.x, this.y + this.height},
				{this.x + this.width/2, this.y + this.height},
				{this.x + this.width, this.y + this.height}
		};
		
		for(int i = 0; i < 8; i++)
		{
			boolean b1, b2, b3;

			b1 = sign(collisionPoints[i][0], x1, x2, collisionPoints[i][1], y1, y2) < 0.0f;
			b2 = sign(collisionPoints[i][0], x2, x3, collisionPoints[i][1], y2, y3) < 0.0f;
			b3 = sign(collisionPoints[i][0], x3, x1, collisionPoints[i][1], y3, y1) < 0.0f;

			returnedValue |= ((b1 == b2) && (b2 == b3));
		}
		
		return returnedValue;
	}

	private float sign(float p1x, float p2x, float p3x, float p1y, float p2y, float p3y)
	{
		return (p1x - p3x) * (p2y - p3y) - (p2x - p3x) * (p1y - p3y);
	}

	@Override
	public void tick() 
	{
		this.movement();
	}

	@Override
	public void render(Graphics g) 
	{
		int i = this.colorVariant % Obstacle.colors.length;
		g.setColor(new Color((((float)Obstacle.colors[i][0]) / 255.0f), (((float)Obstacle.colors[i][1]) / 255.0f), (((float)Obstacle.colors[i][2]) / 255.0f)));
		g.fillRect((int)this.x, (int)this.y, this.width, this.height);
	}
}
