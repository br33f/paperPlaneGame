package states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gameProj.Game;
import gameProj.Launcher;
import gameProj.Score;
import gfx.ImageLoader;

public class InstructionState extends State
{
    //attributes
    public static BufferedImage background = ImageLoader.loadImage("/images/instructionsmenu.jpg");

    private BufferedImage button = ImageLoader.loadImage("/images/btn.jpg");
    private BufferedImage buttonActive = ImageLoader.loadImage("/images/btn-active.jpg");
    private String[] buttonNames = {"Cofnij", "Wyjdz"};
    private int buttonCursor;
    private final int BUTTON_HEIGHT = 50;

    //methods
    public InstructionState(Game game)
    {
        super(game);
        this.buttonCursor = 0;
    }
    @Override
    public void tick()
    {
        this.getInput();
    }

    @Override
    public void render(Graphics g)
    {
        g.drawImage(InstructionState.background, 0, 0, Launcher.WINDOW_WIDTH, Launcher.WINDOW_HEIGHT, null);

        g.setFont(new Font("Segoe Print", Font.PLAIN, 20));
        for(int i = 0; i < this.buttonNames.length; i++)
        {
            BufferedImage buttonImage = (this.buttonCursor == i) ? this.buttonActive : this.button;
            g.drawImage(buttonImage, 250, 385 + 50*i, 40, 15, null);
            g.drawString(this.buttonNames[i], 300, 400 + BUTTON_HEIGHT*i);
        }
    }

    private void getInput()
    {
        if(game.getKeyManager().p_up)
            this.buttonCursor = (this.buttonCursor - 1) % this.buttonNames.length;
        if(game.getKeyManager().p_down)
            this.buttonCursor = (this.buttonCursor + 1) % this.buttonNames.length;
        if(game.getKeyManager().p_enter)
            this.menuSubmitAction();
        this.buttonCursor = (this.buttonCursor < 0) ? this.buttonNames.length - 1 : this.buttonCursor;
    }

    private void menuSubmitAction()
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
}
