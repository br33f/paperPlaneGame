package states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import gameProj.Game;
import gameProj.Launcher;
import gameProj.Score;

public class MenuState extends State
{
	public MenuState(Game game)
	{
		super(game);
	}
	@Override
	public void tick() 
	{
		
	}
	@Override
	public void render(Graphics g) 
	{
		g.setColor(Color.gray);
		g.fillRect(0, 0, Launcher.WINDOW_WIDTH, Launcher.WINDOW_HEIGHT);
		g.setColor(Color.white);
		g.drawString("Menu", 300, 300);
		
		g.setColor(Color.YELLOW);
		g.setFont(new Font("Courier", Font.BOLD, 16));
		String str = "SCORE: " + Integer.toString(Score.lastScore);
		g.drawString(str, 250, 600);
	}
}
