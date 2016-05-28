package states;

import java.awt.*;
import java.awt.image.BufferedImage;

import game.Game;
import gfx.ImageLoader;

/**
 * Klasa abstrakcyjna State - Stan.
 * Klasa zawiera podstawowe funkcjonalności , które dziecziczy kazdy stan takie jak: instacja gry, menu, metody tick i render.
 */
public abstract class State
{
	//attributes
	private static State currentState = null;
	protected Game game;

	//buttons
	protected BufferedImage button = ImageLoader.loadImage("/images/btn.jpg");
	protected BufferedImage buttonActive = ImageLoader.loadImage("/images/btn-active.jpg");
    protected BufferedImage cloud = ImageLoader.loadImage("/images/cloud.png");
	protected String[] buttonNames = {"Menu", "Wyjdz"};
	protected int buttonCursor;
	protected int buttonHeight = 50;
    protected int[] menuPosition;

    //methods

    /**
     * Konstruktor parametryczny klasy State - stan.
     * @param game instancja klasy Game - gra.
     */
	public State(Game game)
	{
		this.game = game;
        this.menuPosition = new int[] {0, 0};
	}

    /**
     * Statyczna metoda przestawiająca aktualny stan.
     * @param state stan który ma być ustawiony jako aktualny.
     */
    public static void setState(State state)
	{
		State.currentState = state;
	}

    /**
     * Statyczna metoda zwracająca aktualny stan.
     * @return obiekt State - stan
     */
    public static State getState()
	{
		return State.currentState;
	}

    /**
     * Abstrakcyjna metoda tick() służąca do aktualizacji gry.
     */
    public abstract void tick();

    /**
     * Abstrakcyjna metoda render() służąca do rysowania obiektów poszczególnych stanów.
     * @param g instancja Graphics
     */
    public abstract void render(Graphics g);

    /**
     * Pobiera dane z klawiatury za pomoca KeyManager'a oraz przesuwa kursor w menu.
     */
    protected void getMenuInput()
    {
        if(game.getKeyManager().p_up)
            this.buttonCursor = (this.buttonCursor - 1) % this.buttonNames.length;
        if(game.getKeyManager().p_down)
            this.buttonCursor = (this.buttonCursor + 1) % this.buttonNames.length;
        if(game.getKeyManager().p_enter)
            this.menuSubmitAction();
        this.buttonCursor = (this.buttonCursor < 0) ? this.buttonNames.length - 1 : this.buttonCursor;
    }

    /**
     * Metoda implementuje domyślne akcje na naciśnięte klawisze menu.
     */
    protected void menuSubmitAction()
    {
        switch(this.buttonCursor)
        {
            case 0: //cofnij
                State.setState(game.menuState);
                break;
            case 1: //wyjdz
                System.exit(0);
                break;
        }
    }

    /**
     * Metoda rysuje menu z uwzględnieniem kursora.
     * @param g instacja Graphics
     */
    protected void drawMenu(Graphics g)
    {
        g.setFont(new Font("Segoe Print", Font.PLAIN, 20));
        for(int i = 0; i < this.buttonNames.length; i++)
        {
            BufferedImage buttonImage = (this.buttonCursor == i) ? this.buttonActive : this.button;
            g.drawImage(buttonImage, this.menuPosition[0], this.menuPosition[1] + 50*i, 40, 15, null);
            g.drawString(this.buttonNames[i], this.menuPosition[0] + 50, this.menuPosition[1] + 15 + this.buttonHeight*i);
        }
    }
}
