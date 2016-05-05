package gameProj;

import java.awt.Graphics;

public class Score 
{
	//attributes
	public static int lastScore;
	private int points;
	private int counter;

	//methods
	public Score()
	{
		this.points = 0;
		this.counter = 0;
	}
	
	public int getPoints() 
	{
		return this.points;
	}

	public void setPoints(int points) 
	{
		this.points = points;
	}
	
	public void tick()
	{
		this.counter++;
		/*if(this.counter >= 20)
		{
			this.points++;
			Score.lastScore = this.points;
			this.counter = 0;
		}*/
	}

}
