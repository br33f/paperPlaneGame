package states;

import gameProj.Game;
import gameProj.Launcher;

import java.awt.*;

/**
 * Created by br33 on 07.05.2016.
 */
public class ScoresState extends State {
    //methods
    public ScoresState(Game g)
    {
        super(g);
        this.menuPosition = new int[] {250, 380};
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
        g.drawString("Top 5", 230, 250);

        for(int i = 1 ; i <= 5; i++)
        {
            g.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
            g.drawString(i + ". ", 235, 250 + i * 20);
        }

        this.drawMenu(g);
    }

}
