package states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entities.Player;
import gameProj.Game;
import gameProj.Launcher;
import gameProj.Score;
import gfx.ImageLoader;
import worlds.World;

public class GameState extends State
{
	//attributes
	public static BufferedImage background = ImageLoader.loadImage("");
	public static int colors[][] =  {{233,237,253}};
	private Player player;
	private World world;

	//methods
	public GameState(Game game)
	{
		super(game);
		this.game.score = new Score();
		this.world = new World("", this.game.score);
		this.player = new Player(game, 304, 320, 32, 64, this.world);
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
		String str = "SCORE: " + Integer.toString(this.game.score.getPoints());
		g.drawString(str, 20, 620);
	}
}
