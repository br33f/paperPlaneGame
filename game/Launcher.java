package game;

/**
 * Klasa uruchamiajaca aplikacje.
 */
public class Launcher
{
	public static final int WINDOW_WIDTH = 640;
	public static final int WINDOW_HEIGHT = 640;

	/**
	 * Punkt wejsciowy aplikacji.
	 * @param args parametry wejsciowe
	 */
	public static void main(String[] args)
	{
		Game game = new Game("Paper jet");
		game.start();
	}
}
