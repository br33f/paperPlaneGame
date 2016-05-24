package entities;

import java.awt.Graphics;
import entities.primitives.Point;

/**
 * Klasa Entity - obiekt
 * Jest to klasa abstrakcyjna, każda dziedzicząca klasa chrakeryzować się będzie
 * punktem kontrolnym p oraz szerokościa i wysokością (używane np. w przypadku przeszkód)
 */
public abstract class Entity
{
	//attributes
	protected Point p;
	protected int width, height;

	//methods

	/**
	 * Konstruktor parametryczny klasy Entity
	 * @param x punkt początkowy (oś pozioma)
	 * @param y punkt początkowy (oś pionowa)
	 * @param width szerokość encji
	 * @param height wysokość encji
	 */
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
