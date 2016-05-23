package entities;

import java.awt.Graphics;
import entities.primitives.Point;

public abstract class Entity 
{
	//attributes
	protected Point p;
	protected int width, height;
	
	//methods
	public Entity(float x, float y, int width, int height)
	{
		this.p = new Point(x, y);
		this.width = width;
		this.height = height;
	}
	public float getX() 
	{
		return p.getX();
	}
	public void setX(float x) 
	{
		p.setX(x);
	}
	public float getY() 
	{
		return p.getY();
	}
	public void setY(float y) 
	{
		p.setY(y);
	}
	public int getWidth() 
	{
		return width;
	}
	public void setWidth(int width) 
	{
		this.width = width;
	}
	public int getHeight() 
	{
		return height;
	}
	public void setHeight(int height) 
	{
		this.height = height;
	}
	public abstract void tick();
	public abstract void render(Graphics g);
}
