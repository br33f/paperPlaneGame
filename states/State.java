package states;

import java.awt.Graphics;

import gameProj.Game;
import gameProj.Score;

public abstract class State 
{
	//attributes
	private static State currentState = null;
	protected Game game;
	
	//methods
	public State(Game game)
	{
		this.game = game;
	}
	public static void setState(State state)
	{
		State.currentState = state;
	}
	public static State getState()
	{
		return State.currentState;
	}
	public abstract void tick();
	public abstract void render(Graphics g);
}
