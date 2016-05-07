package gameProj;

import java.awt.Color;
import java.awt.DefaultFocusTraversalPolicy;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import input.KeyManager;
import states.*;

import javax.swing.*;

public class Game implements Runnable
{
	//attributes
	private Display display; //display instance
	//settings
	public int width, height;
	public String title;
	//game loop variables
	private Thread thread; //thread
	private boolean running = false;
	//graphics
	private BufferStrategy bs;
	private Graphics g;
	//states
	public State gameState, menuState, instructionState, gameoverState, scoresState;
	//input
	private KeyManager keyManager;
	//score
	public Score score;
	
	//methods
	public Game(String title, int width, int height)
	{
		this.height = height;
		this.width = width;
		this.title = title;
		keyManager = new KeyManager();
		this.score = new Score();
	}
	private void init()
	{		
		display = new Display(this.title, this.width, this.height);
		display.getFrame().addKeyListener(keyManager);
		ImageIcon icon=new ImageIcon("res/ikona.png");
		display.setIconImage(icon.getImage());
		
		gameState = new GameState(this);
		menuState = new MenuState(this);
        instructionState = new InstructionState(this);
		gameoverState = new GameoverState(this);
        scoresState = new ScoresState(this);
		State.setState(menuState);
		
	}
	private void tick()
	{
		keyManager.tick();
		
		if(this.getKeyManager().p_esc)
			this.toogleMenu();
		
		if(State.getState() != null)
			State.getState().tick();
	}
	private void render()
	{
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null)
		{
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		g.clearRect(0, 0, this.width, this.height);
		
		if(State.getState() != null)
			State.getState().render(g);
		
		bs.show();
		g.dispose();
	}
	public void run()
	{
		this.init();
		//fps controll variables
		int fps = 99;
		double tPT = 1000000000 / fps;
		double d = 0;
		long now;
		long recent = System.nanoTime();
		double timer = 0.0;
		int ticks = 0;
		//main game loop
		while(this.running)
		{
			now = System.nanoTime();
			d += (now - recent) / tPT;
			timer += (now - recent);
			recent = now;
			if(d >= 1)
			{
				this.tick();
				this.render();
				d--;
				ticks++;
			}
			if(timer >= 1000000000)
			{
				System.out.println("FPS: " + ticks);
                GameState.currentFps = ticks;
				ticks = 0;
				timer = 0.0;
			}
		}
		this.stop();
	}
	public KeyManager getKeyManager()
	{
		return this.keyManager;
	}
	public synchronized void start()
	{
		if(!this.running)
		{
			this.running = true;
			thread = new Thread(this);
			thread.start();
		}
	}
	public synchronized void stop()
	{
		if(this.running)
		{
			this.running = false;
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public void gameOver()
	{
		State.setState(gameoverState);
		this.gameState = new GameState(this);
	}
	private void toogleMenu()
	{
		if(State.getState() == this.menuState)
			State.setState(gameState);
		else if(State.getState() == this.gameState)
			State.setState(menuState);
	}
}
