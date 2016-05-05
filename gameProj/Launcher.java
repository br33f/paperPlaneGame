package gameProj;

public class Launcher 
{
	public static final int WINDOW_WIDTH = 640;
	public static final int WINDOW_HEIGHT = 640;
	
	public static void main(String[] args)
	{
		Game game = new Game("gameProj", WINDOW_WIDTH, WINDOW_HEIGHT);
		game.start();
	}
}
