package states;

import gameProj.Game;
import gameProj.Launcher;
import gameProj.Score;
import gfx.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by br33 on 07.05.2016.
 */
public class GameoverState extends State {
    //attributes
    public static BufferedImage logo = ImageLoader.loadImage("/images/logo.png");

    public int endScore;

    //methods
    public GameoverState(Game game){
        super(game);
        this.buttonCursor = 0;
        this.buttonNames = new String[] {"Menu", "Wyjdz"};
        this.buttonHeight = 50;
        this.menuPosition = new int[] {230, 350};

        this.endScore = 0;
    }

    @Override
    public void tick() {
        this.getMenuInput();
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(GameState.colors[0][0] / 255.0f, GameState.colors[0][1] / 255.0f, GameState.colors[0][2] / 255.0f));
        g.fillRect(0, 0, Launcher.WINDOW_WIDTH, Launcher.WINDOW_HEIGHT);

        g.drawImage(this.cloud, 0, Launcher.WINDOW_HEIGHT - 150, null);

        g.drawImage(GameoverState.logo, 130, 100, null);

        g.setColor(Color.DARK_GRAY);
        g.setFont(new Font("Yu Gothic", Font.PLAIN, 30));
        g.drawString("GAME OVER", 230, 250);

        g.setFont(new Font("Yu Gothic", Font.PLAIN, 20));
        g.drawString("Wynik: " + this.endScore , 230, 280);

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
