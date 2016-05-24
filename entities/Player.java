package entities;

import entities.primitives.Point;
import entities.primitives.Triangle;
import game.Game;
import game.Launcher;
import game.World;

import java.awt.*;

/**
 * Klasa Player - gracz.
 * Dziedziczy po Creature - stworzenie.
 * Klasa odpowiada za ruch gracza po ekranie w osiach X, Y a także jego rotację.
 */
public class Player extends Creature
{
    //static attributes
    public static int colors[][] =  {{101,147,207}};

	//attributes
	private Game game;
    private Float angle;
    private Triangle t;
	
	//methods

    /**
     * Konstruktor parametryczny klasy Player
     * @param game obiekt gry (Game)
     * @param x punkt początkowy gracza (oś pozioma)
     * @param y punkt początkowy gracza (oś pionowa)
     * @param width szerokość podstawy trójkąta
     * @param height wysokość podstawy trójkąta
     * @param world obiekt świata (World)
     */
    public Player(Game game, float x, float y, int width, int height, World world)
	{
		super(x, y, width, height, world);
		this.game = game;
        this.angle = 0.0f;

        Point p1 = new Point(x, y + height);
        Point p2 = new Point(x + width, y + height);
        Point p3 = new Point(x + width / 2, y);
        this.t = new Triangle(new Point[] {p1, p2, p3});
	}

	@Override
	public void tick()
	{
		this.getInput();
        Point[] v = {this.t.getVertex(1), this.t.getVertex(2), this.t.getVertex(3)};
		if(this.world.isHitByObstacle(this.t))
			this.game.gameOver();
		else
			this.move();
	}

    @Override
    public void render(Graphics g)
    {
        int colorIdx = 0;

        Point[] v = {this.t.getVertex(1), this.t.getVertex(2), this.t.getVertex(3)};
        Point middlePoint = v[0].getMiddle(v[1]);

        //fill positions
        int[] xPositions = {(int)v[0].getX(), (int)v[1].getX(), (int)v[2].getX()};
        int[] yPositions = {(int)v[0].getY(), (int)v[1].getY(), (int)v[2].getY()};

        //border positions
        int[] xPositions2 = {(int)middlePoint.getX()-1, (int)middlePoint.getX()+1, (int)v[2].getX()};
        int[] yPositions2 = {(int)middlePoint.getY(), (int)middlePoint.getY(), (int)v[2].getY()};

        //trails
        g.setColor(Color.WHITE);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        g2.drawLine((int)v[0].getX(), (int)v[0].getY() - 2, (int)v[0].getX() - (int)this.xMove * 20, (int)v[0].getY() - (int)this.yMove * 10); //left trail
        g2.drawLine((int)v[1].getX(), (int)v[1].getY() - 2, (int)v[1].getX() - (int)this.xMove * 20, (int)v[1].getY() - (int)this.yMove * 10);  //right trail
        g2.setStroke(new BasicStroke(1));

        //body
        g.setColor(new Color((((float)Player.colors[colorIdx][0]) / 255.0f), (((float)Player.colors[colorIdx][1]) / 255.0f), (((float)Player.colors[colorIdx][2]) / 255.0f)));
        g.fillPolygon(xPositions, yPositions, 3);

        //borders
        g.setColor(Color.GRAY);
        g.drawPolygon(xPositions, yPositions, 3);
        g.setColor(Color.DARK_GRAY);
        g.drawPolygon(xPositions2, yPositions2, 3);
    }

    /**
     * Metoda odpowiadająca za ruch gracza po ekranie.
     * Metoda wywołuje rotację, przelicza prędkość w przypadku ruchu gracza w 2 osiach jednocześnie.
     */
    public void move()
    {
        this.makeRotation();

        if(this.xMove * this.yMove != 0)
        {
            this.xMove /= Math.sqrt(2);
            this.yMove /= Math.sqrt(2);
        }
        this.moveX();
        this.moveY();
    }

    /**
     * Metoda kontroluje rotację gracza.
     * Gracz porusza się w prawo - 20 stopni
     * Gracz porusza się w lewo - -20 stopni
     */
    private void makeRotation(){
        if(this.xMove > 0){
            this.t.rotate(20.0f - this.angle);
            this.angle = 20.0f;
        }
        else if(this.xMove < 0){
            this.t.rotate(-20.0f - this.angle);
            this.angle = -20.0f;
        }
        else if(this.angle != 0.0f){
            this.t.rotate(-this.angle);
            this.angle = 0.0f;
        }
    }

    /**
     * Metoda przesuwa gracza w osi X - poziomej.
     * Zabezpiecza ruch w przypadku wyjścia poza granice ekranu.
     */
    private void moveX(){
        if((this.xMove > 0 && this.t.getVertex(3).getX() < Launcher.WINDOW_WIDTH) || (this.xMove < 0 && this.t.getVertex(3).getX() > 0))
        {
            this.t.translate(this.xMove, 0);
        }
        else
            this.xMove = 0;
    }

    /**
     * Metoda przesuwa gracza w osi Y - pionowej.
     * Zabezpiecza ruch w przypadku wyjścia poza granice ekranu:
     * - gdy gracz ucieka w góre przyspiesza grę.
     * - gdy gracz dotyka dolnej krawędzi następuje przerwanie gry i GameOver.
     */
    private void moveY(){
        int bottomCheckPoint = (this.xMove >= 0 ) ? 2 : 1;
        if((this.yMove > 0 && this.t.getVertex(bottomCheckPoint).getY() < Launcher.WINDOW_HEIGHT) || (this.yMove < 0 && this.t.getVertex(3).getY() > 0))
        {
            this.t.translate(0, this.yMove);
            World.Accelerated = false;
        }
        else if(this.yMove > 0 && this.t.getVertex(bottomCheckPoint).getY() >= Launcher.WINDOW_HEIGHT){
            this.game.gameOver();
        }
        else if(this.t.getVertex(3).getY() <= 0){
            World.Accelerated = true;
            this.yMove = 0;
        }
        else
            this.yMove = 0;
    }

    /**
     * Metoda odpowiada za sterowanie.
     * Odczytuje stany klawiszy z keyManager'a i ustala odpowiednie przesunięcia.
     * Powoduje spadanie gracza w przypadku nie naciśniętego przycisku w góre.
     */
    private void getInput()
	{
		this.xMove = 0;
        this.yMove = this.speed/2;
		
		//this.yMove -= this.speed;
		if(game.getKeyManager().up)
			this.yMove = -this.speed;
		if(game.getKeyManager().left)
			this.xMove -= this.speed;
		if(game.getKeyManager().right)
			this.xMove += this.speed;
		
	}

}
