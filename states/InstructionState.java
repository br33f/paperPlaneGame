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

    //methods
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
}
