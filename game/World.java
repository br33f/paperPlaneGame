package game;

import java.awt.Graphics;
import java.util.List;
import java.util.Random;
import java.util.LinkedList;

import entities.Obstacle;
import entities.primitives.Triangle;

/**
 * Klasa zawiera atrybuty i metody służące przechowywaniu, aktualizowaniu i rysowaniu obiektów świata gry takich jak przeszkody.
 */
public class World
{
	//attributes
	private List<Obstacle> obstacles; 
	private Score score;
    public static boolean Accelerated = false;
	private int obstacleDelay, obstacleCounter, obstacleIncreaseCounter;

    //methods

	/**
	 * Konstruktor parametryczny klasy World
	 * @param score obiekt typu Score
	 */
	public World(Score score)
	{
		this.score = score;
		this.obstacles = new LinkedList<Obstacle>();

		this.obstacleCounter = this.obstacleDelay = 60;
		this.obstacleIncreaseCounter = 0;
	}

    /**
     * Metoda aktualizacji danych.
     * Przesuwa przeszkody, generuje je w zdanym okresie czasu.
     */
    public void tick()
	{
		this.obstacleIncreaseCounter++;
		if(this.obstacleIncreaseCounter >= 150)
		{
			this.obstacleIncreaseCounter = 0;
			this.obstacleDelay = (this.obstacleDelay > 20) ? this.obstacleDelay - 1 : 20;
		}
		this.obstacleCounter++;
		if(this.obstacleCounter >= this.obstacleDelay)
		{
			this.obstacleCounter = 0;
			this.createObstacle();
		}
		int last = this.obstacles.size() - 1;
		
		while(last >= 0)
		{
			this.obstacles.get(last).tick();
			if(this.obstacles.get(last).getY() > Launcher.WINDOW_HEIGHT)
			{
				this.obstacles.remove(last);
				int currentScore = this.score.getPoints() + 1;
				this.score.setPoints(currentScore);
				Score.lastScore = currentScore;
			}
			last --;
		}
	}

    /**
     * Metoda wywołująca rysowanie przeszkód.
     * Wywoływana zawsze po tick().
     * @param g obiekt klasy Graphics pozwalający na rysowanie
     */
    public void render(Graphics g)
	{	
		int size = this.obstacles.size();
		for(int i = 0; i < size; i++)
			this.obstacles.get(i).render(g);
	}

	/**
	 * Metoda sprawdza czy gracz został uderzony przez którąkolwiek z przeszkód spadających.
	 * @param t trójkąt odpowiadajacy położeniu gracza
	 * @return true/false (true - kolizja)
	 */
	public boolean isHitByObstacle(Triangle t)
	{
		int size = this.obstacles.size();
		boolean returnedValue = false;
		
		for(int i = 0; i < size; i++)
			returnedValue |= this.obstacles.get(i).collision(t);

		return returnedValue;
	}

	/**
	 * Metoda generująca przeszkody.
	 * Wywoływana z różną częstotliwością z funkcji tick().
	 * Parametry spadających obiektów takie jak prędkość, kolor itd. dobierane są w głównej mierze losowo.
	 */
	private void createObstacle()
	{
		int randX, randWidth, randHeight, sign, special, color;
		float randSpeedX, randSpeedY;
		Random rand = new Random();
		
		special = (rand.nextFloat() > 0.91f) ? 1 : 0;
		sign = (rand.nextBoolean()) ? 1 : -1;
		randX = rand.nextInt(640);
		randWidth = 10 + (rand.nextInt(30) * ((special + 1) % 2));
		randHeight = 20 + rand.nextInt(30) + 80 * special;
		randSpeedX = (0.5f * rand.nextFloat() * sign) * ((special + 1) % 2);
		randSpeedY = 1.0f + rand.nextFloat() + (3.0f * special);
		color = (special == 1) ? Obstacle.colors.length - 1 : rand.nextInt(Obstacle.colors.length - 1);
		
		this.obstacles.add(new Obstacle(randX, -100, randWidth, randHeight, randSpeedX, randSpeedY, color));
	}
}
