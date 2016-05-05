package tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tile 
{
	//static attributes
	public static Tile[] tiles = new Tile[128];
	public static Tile floorTile = new FloorTile(0);
	
	private static final int TILE_WIDTH = 20; 
	private static final int TILE_HEIGHT = 20; 
	
	//attributes
	public final int id;
	
	//methods
	public Tile(int id)
	{
		this.id = id;
		
		Tile.tiles[id] = this;
	}
	
	public boolean isSolid()
	{
		return false;
	}
	
	public void tick()
	{
		
	}
	
	public void render(Graphics g, int x, int y)
	{
		
	}
	
}
