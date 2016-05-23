package states;

import java.awt.*;
import java.awt.image.BufferedImage;

import entities.Obstacle;
import entities.Player;
import game.Game;
import game.Launcher;
import game.Score;
import gfx.ImageLoader;
import game.World;

public class GameState extends State
{
	//attributes
	public static BufferedImage background = ImageLoader.loadImage("");
	public static int colors[][] =  {{233,237,253}};
	public static int currentFps = 0;
	private Player player;
	private World world;

	//methods
	public GameState(Game game)
	{
		super(game);
		this.game.score = Score.getInstance();
		this.world = new World(this.game.score);
		this.player = new Player(game, 304, 320, 32, 64, this.world);
		this.game.score.resetScore();
	}

	public void tick() 
	{
		this.world.tick();
		this.player.tick();
		this.game.score.tick();
	}

	public void render(Graphics g) 
	{
		g.setColor(new Color(GameState.colors[0][0] / 255.0f, GameState.colors[0][1] / 255.0f, GameState.colors[0][2] / 255.0f));
		g.fillRect(0, 0, Launcher.WINDOW_WIDTH, Launcher.WINDOW_HEIGHT);
		g.drawImage(GameState.background, 0, 0, 640, 640, null);

		this.world.render(g);
		this.player.render(g);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.BOLD, 12));
		String str = "WYNIK: " + Integer.toString(this.game.score.getPoints());
		g.drawString(str, 20, 620);

        str = "FPS: " + Integer.toString(GameState.currentFps);
        g.drawString(str, 580, 620);

        g.setColor(new Color(Obstacle.colors[4][0] / 255.0f, Obstacle.colors[4][1] / 255.0f, Obstacle.colors[4][2] / 255.0f));
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));
        g.drawLine(0, Launcher.WINDOW_HEIGHT - 2, Launcher.WINDOW_WIDTH, Launcher.WINDOW_HEIGHT - 2);
	}
}
