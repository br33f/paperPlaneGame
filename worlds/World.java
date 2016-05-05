package worlds;

import java.awt.Graphics;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.LinkedList;

import entities.Entity;
import entities.Obstacle;
import gameProj.Launcher;
import gameProj.Score;
import tiles.FloorTile;
import tiles.Tile;

public class World 
{
	//attributes
	private int[][] tiles;
	private List<Obstacle> obstacles; 
	private Score score;
	public static final int TILE_WIDTH = 20; 
	public static final int TILE_HEIGHT = 20;
    public static boolean Accelerated = false;
	private int obstacleDelay, obstacleCounter, obstacleIncreaseCounter;
	
	//methods
	public World(String path, Score score)
	{
		this.score = score;
		this.tiles = new int[TILE_WIDTH][TILE_HEIGHT];
		this.obstacles = new LinkedList<Obstacle>();

		this.obstacleCounter = this.obstacleDelay = 60;
		this.obstacleIncreaseCounter = 0;
		
		//initializing with -1
		for(int x = 0; x < TILE_WIDTH; x++)
			for(int y = 0; y < TILE_HEIGHT; y++)
				this.tiles[x][y] = -1;
		
		this.loadWorld(path);
	}
	public void loadWorld(String path)
	{
		/*for(int x = 0; x < TILE_WIDTH; x++)
			for(int y = 0; y < TILE_HEIGHT; y++)
			{
				if(y > 15)
					this.tiles[x][y] = 0;
			}
			*/
	}
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
		
		for(int x = 0; x < TILE_WIDTH; x++)
			for(int y = 0; y < TILE_HEIGHT; y++)
			{
				int tileId = this.tiles[x][y];
				if(tileId != -1)
					Tile.tiles[tileId].tick();
			}
	}
	public void render(Graphics g)
	{	
		int size = this.obstacles.size();
		for(int i = 0; i < size; i++)
			this.obstacles.get(i).render(g);
		
		for(int x = 0; x < TILE_WIDTH; x++)
			for(int y = 0; y < TILE_HEIGHT; y++)
			{
				int tileId = this.tiles[x][y];
				if(tileId != -1)
					Tile.tiles[tileId].render(g, x*Launcher.WINDOW_WIDTH/TILE_WIDTH, y*Launcher.WINDOW_HEIGHT/TILE_WIDTH);
			}
	}
	public boolean isCollision(float x, float y)
	{
		x = x / (Launcher.WINDOW_WIDTH/TILE_WIDTH);
		y = y / (Launcher.WINDOW_HEIGHT/TILE_HEIGHT);
		
		boolean returnedValue = false;
		if(x < TILE_WIDTH && x > 0 && y < TILE_HEIGHT && y > 0)
		{
			int tileId = this.tiles[(int)x][(int)y];
			if(tileId != -1)
				returnedValue = Tile.tiles[tileId].isSolid();
		}
		return returnedValue;
	}
	public boolean isHitByObstacle(float x1, float x2, float x3, float y1, float y2, float y3)
	{
		int size = this.obstacles.size();
		boolean returnedValue = false;
		
		for(int i = 0; i < size; i++)
			returnedValue |= this.obstacles.get(i).collision(x1, x2, x3, y1, y2, y3);

		return returnedValue;
	}
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
