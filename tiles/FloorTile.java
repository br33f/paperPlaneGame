package tiles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gameProj.Launcher;
import worlds.World;

public class FloorTile extends Tile
{
	public FloorTile(int id) 
	{
		super(id);
	}
	
	public void tick()
	{
		
	}
	
	public void render(Graphics g, int x, int y)
	{
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x, y, 32, 32);
	}
	
	public boolean isSolid()
	{
		return true;
	}

}
