package states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import game.Game;
import game.Launcher;
import game.Score;
import gfx.ImageLoader;

/**
 * Klasa MenuState odpowiadająca za obsługe głownego menu i jego wyświetlanie.
 * Rozszerza State.
 */
public class MenuState extends State
{
	//attributes
	public static BufferedImage background = ImageLoader.loadImage("/images/menubg.jpg");

	//methods

    /**
     * Konstruktor parametryczny klasy MenuState.
     * @param game obiekt klasy Game.
     */
    public MenuState(Game game)
	{
		super(game);
        this.buttonCursor = 0;
        this.buttonNames = new String[] {"Zagraj", "Instrukcje", "Wyniki", "Wyjdz"};
        this.buttonHeight = 50;
        this.menuPosition = new int[] {250, 285};
	}
	@Override
	public void tick()
    {
        this.getMenuInput();
	}

	@Override
	public void render(Graphics g) 
	{
		g.setColor(Color.gray);
		g.fillRect(0, 0, Launcher.WINDOW_WIDTH, Launcher.WINDOW_HEIGHT);
		g.drawImage(MenuState.background, 0, 0, Launcher.WINDOW_WIDTH, Launcher.WINDOW_HEIGHT, null);

        g.drawImage(this.cloud, 0, Launcher.WINDOW_HEIGHT - 150, null);

        this.drawMenu(g);

		g.setColor(Color.BLACK);
		String str = "WYNIK: " + Integer.toString(Score.lastScore);
		g.drawString(str, 250, 600);
	}

    /**
     * Reakcja na nacisniecie przycisku ENTER w menu.
     */
    protected void menuSubmitAction()
    {
        switch(this.buttonCursor)
        {
            case 0: //zagraj
                State.setState(game.gameState);
                break;
            case 1: //instrukcje
                State.setState(game.instructionState);
                break;
            case 2: //wyniki
                State.setState(game.scoresState);
                this.game.renewBestScoresTable();
                break;
            case 3: //wyjdz
                System.exit(0);
                break;
        }
    }
}
