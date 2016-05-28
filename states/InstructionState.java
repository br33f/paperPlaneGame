package states;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import game.Game;
import game.Launcher;
import gfx.ImageLoader;

/**
 * Klasa InstructionState ( opcja menu - instrukcje ).
 * Rozszerza State.
 */
public class InstructionState extends State
{
    //attributes
    public static BufferedImage background = ImageLoader.loadImage("/images/instructionsmenu.jpg");

    //methods

    /**
     * Konstruktor parametryczny klasy InstructionState.
     * @param game obiekt Game
     */
    public InstructionState(Game game)
    {
        super(game);
        this.buttonCursor = 0;
        this.buttonNames = new String[] {"Cofnij", "Wyjdz"};
        this.buttonHeight = 50;
        this.menuPosition = new int[] {250, 385};
    }
    @Override
    public void tick()
    {
        this.getMenuInput();
    }

    @Override
    public void render(Graphics g)
    {
        g.drawImage(InstructionState.background, 0, 0, Launcher.WINDOW_WIDTH, Launcher.WINDOW_HEIGHT, null);
        g.drawImage(this.cloud, 0, Launcher.WINDOW_HEIGHT - 150, null);

        this.drawMenu(g);
    }

}
