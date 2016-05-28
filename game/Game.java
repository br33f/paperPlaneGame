package game;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.sql.SQLException;

import gfx.Display;
import input.KeyManager;
import states.*;

import javax.swing.*;


/**
 * Główna klasa gry implementująca inteface Runnable
 * Klasa ta tworzy osobny wątek
 */
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

    /**
     * Konstruktor parametryczny klasy Game
     * @param title tytuł gry (wyświetlany w nagłówku okienka)
     * @param width szerokość okna gry
     * @param height wysokość okna gry
     */
    public Game(String title, int width, int height)
	{
		this.height = height;
		this.width = width;
		this.title = title;
		keyManager = new KeyManager();
		this.score =  Score.getInstance();
	}

    /**
     * Metoda inicjalizująca.
     * Wykonuje się na początku przed wejściem do głównej pętli gry.
     */
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

    /**
     * Metoda aktualizacji danych (współrzędnych gracza, przeszkód itp.)
     */
    private void tick()
	{
		keyManager.tick();
		
		if(this.getKeyManager().p_esc)
			this.toogleMenu();
		
		if(State.getState() != null)
			State.getState().tick();
	}

    /**
     * Metoda rysowania obiektów.
     * Wykonywana zawsze po tick().
     */
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

    /**
     * Metoda inicjuje dane oraz uruchamia główną pętle gry.
     */
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
				d = (d > 2) ? 0 : d-1;
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

    /**
     * Getter
     * @return obiekt klasy KeyManager pozwalający na odczyt wciskanych przez gracza klawiszy.
     */
    public KeyManager getKeyManager()
	{
		return this.keyManager;
	}

    /**
     * Metoda utworzenia nowego wątku.
     * Uniemożliwia powołanie kolejnego wątku, jeśli gra jest już uruchomiona.
     */
    public synchronized void start()
	{
		if(!this.running)
		{
			this.running = true;
			thread = new Thread(this);
			thread.start();
		}
	}

    /**
     * Metoda zatrzymująca wątek.
     */
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

    /**
     * Metoda wywoływana jest po kolizji gracza z przeszkodą.
     * Spradzany jest stan połaczenia z bazą danych oraz pozycja gracza w rankingu.
     * Stan ustalany jest na GameoverState
     */
    public void gameOver()
	{
		int playersPosition = this.score.getPlayerPosition();
        if(this.score.isConnected() && playersPosition >= 1 && playersPosition <= 5) {
            this.score.addBestScore();
            this.renewBestScoresTable();
        }

        GameoverState gs = (GameoverState) this.gameoverState;
        gs.endScore = Score.lastScore;
        State.setState(gameoverState);
		this.gameState = new GameState(this);
        this.keyManager.clearKeys();
	}

    /**
     * Metoda przełącza stan między menu a grą.
     */
    private void toogleMenu()
	{
		if(State.getState() == this.menuState)
			State.setState(gameState);
		else if(State.getState() == this.gameState)
			State.setState(menuState);
	}

    /**
     * Metoda odświeża tablice 5 najlepszych wyników.
     */
    public void renewBestScoresTable()
    {
        ScoresState ss = (ScoresState) this.scoresState;
        try {
            ss.setScoresTable(this.score.getScores(5));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
