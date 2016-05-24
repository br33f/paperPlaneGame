package entities;

import game.Launcher;
import game.World;

/**
 * Klasa abstrakcyjna Creature - stworzenie
 * Dziedziczy po Entity, poza elementami dziedicznymi przechowuje:
 * szybkość, prędkość w osi X, a także przesunięcia w poszczególnych osiach.
 */
public abstract class Creature extends Entity
{
	//attributes
	protected float speed, velocityX;
	protected World world;
	public static final float DEFAULT_SPEED = (float)2;
	protected float xMove, yMove;
	
	//methods

	/**
	 * Konstruktor parametryczny klasy Creature
     * @param x punkt początkowy (oś pozioma)
     * @param y punkt początkowy (oś pionowa)
     * @param width szerokość encji
     * @param height wysokość encji
	 * @param world obiekt świata (World)
	 */
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
