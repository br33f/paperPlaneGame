package entities;

import java.awt.Color;
import java.awt.Graphics;

import entities.primitives.Point;
import entities.primitives.Triangle;
import game.World;

/**
 * Klasa Obstacle - przeszkoda.
 * Dziedziczy z Entity.
 */
public class Obstacle extends Entity
{
	//attributes
	public static int colors[][] = {{111,183,214},{187,214,111},{170,116,222},{215,178,113},{255,88,94},{222,117,174}};
	private float speedX, speedY;
	private int colorVariant;
	private int sizeIncrementator = 0;
	
	//methods

	/**
     * @param x punkt początkowy (oś pozioma).
     * @param y punkt początkowy (oś pionowa).
     * @param width szerokość encji.
     * @param height wysokość encji.
	 * @param speedX prędkość poruszania się przeszkody w osi X.
	 * @param speedY prędkość poruszania się przeszkody w osi Y.
	 * @param colorVariant wariant kolorystyczny przeszkody.
	 */
	public Obstacle(float x, float y, int width, int height, float speedX, float speedY, int colorVariant)
	{
		super(x, y, width, height);
		this.speedX = speedX;
		this.speedY = speedY;
		this.colorVariant = colorVariant;
	}

    /**
     * Metoda przemieszcza przeszkodę. Wywoływana w tick().
     */
    public void movement()
	{
        float factor = (World.Accelerated) ? 1.5f : 1.0f;
		this.setX(this.getX() + this.speedX * factor);
		this.setY(this.getY() + this.speedY * factor);
	}

    /**
     * Metoda sprawdza czy zachodzi kolizja między graczem a przeszkodą.
     * @param t trójkąt (punkty odpowiedzialne za położenie gracza)
     * @return wartość true/false (true - kolizja, false - brak kolizji)
     */
    public boolean collision(Triangle t)
	{
		boolean returnedValue = false;
		float collisionPoints[][] =	{
				{this.getX(), this.getY()},
				{this.getX(), this.getY() + this.height/2},
				{this.getX() + this.width/2, this.getY()},
				{this.getX() + this.width, this.getY()},
				{this.getX() + this.width, this.getY() + this.height/2},
				{this.getX(), this.getY() + this.height},
				{this.getX() + this.width/2, this.getY() + this.height},
				{this.getX() + this.width, this.getY() + this.height}
		};
		
		for(int i = 0; i < 8; i++)
		{
			boolean b1, b2, b3;

			b1 = sign(new Point[] {
                    new Point(collisionPoints[i][0], collisionPoints[i][1]),
                    t.getVertex(1),
                    t.getVertex(2)
            }) < 0.0f;
            b2 = sign(new Point[] {
                    new Point(collisionPoints[i][0], collisionPoints[i][1]),
                    t.getVertex(2),
                    t.getVertex(3)
            }) < 0.0f;
            b3 = sign(new Point[] {
                    new Point(collisionPoints[i][0], collisionPoints[i][1]),
                    t.getVertex(3),
                    t.getVertex(1)
            }) < 0.0f;

			returnedValue |= ((b1 == b2) && (b2 == b3));
		}
		
		return returnedValue;
	}

    /**
     * Metoda pomocnicza do sprawdzania kolizji punktu z trójkątem.
     * @param p tablica 3 punktów
     * @return wynik obliczeń
     */
    private float sign(Point[] p)
	{
		return (p[0].getX() - p[2].getX()) * (p[1].getY() - p[2].getY()) - (p[1].getX() - p[2].getX()) * (p[0].getY() - p[2].getY());
	}

	@Override
	public void tick() 
	{
		//rozszerzanie obiektow przy spadaniu
		if(sizeIncrementator == 20){
			this.setX(this.getX() - 1);
			this.setY(this.getY() - 1);
			this.setWidth(this.getWidth() + 1);
			this.setHeight(this.getHeight() + 1);
			sizeIncrementator = 0;
		}
		else
			sizeIncrementator++;

		this.movement();
	}

	@Override
	public void render(Graphics g) 
	{
		int i = this.colorVariant % Obstacle.colors.length;
		g.setColor(new Color((((float)Obstacle.colors[i][0]) / 255.0f), (((float)Obstacle.colors[i][1]) / 255.0f), (((float)Obstacle.colors[i][2]) / 255.0f)));
		g.fillRect((int)this.getX(), (int)this.getY(), this.width, this.height);
	}
}
