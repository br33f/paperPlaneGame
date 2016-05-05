package entities;

import java.awt.*;
import java.awt.geom.Line2D;


import gameProj.Game;
import gameProj.Launcher;
import worlds.World;

public class Player extends Creature 
{
    //static attributes
    public static int colors[][] =  {{101,147,207}};

	//attributes
	private Game game;
    private Float angle, rotX, rotY;
	
	//methods
	public Player(Game game, float x, float y, int width, int height, World world) 
	{
		super(x, y, width, height, world);
		this.game = game;
        this.angle = 0.0f;
        this.rotX = this.x + this.width/2;
        this.rotY = this.y + this.height/2;
	}
	@Override
	public void tick()
	{
		this.getInput();
        this.calculateRotationPoint();
		if(this.world.isHitByObstacle(getPointX(1), getPointX(2), getPointX(3), getPointY(1), getPointY(2), getPointY(3)) || this.y + this.height >= Launcher.WINDOW_HEIGHT)
			this.game.gameOver();
		else
			this.move();
	}

    public void move()
    {
        if(this.xMove > 0)
            angle = 20.0f;
        else if(this.xMove < 0)
            angle = -20.0f;
        else
            angle = 0.0f;
        super.move();
    }

    private void calculateRotationPoint()
    {
        this.rotX = this.x + this.width/2;
        this.rotY = this.y + this.height/2;
    }

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
	@Override
	public void render(Graphics g) 
	{
		int colorIdx = 0;

        float middleX = (getPointX(1) + getPointX(2)) / 2;
        float middleY = (getPointY(1) + getPointY(2)) / 2;
        //fill positions
		int[] xPositions = {(int)getPointX(1), (int)getPointX(2), (int)getPointX(3)};
		int[] yPositions = {(int)getPointY(1), (int)getPointY(2),(int)getPointY(3)};
        //border positions
        int[] xPositions2 = {(int)middleX-1, (int)middleX+1, (int)getPointX(3)};
        int[] yPositions2 = {(int)middleY, (int)middleY,(int)getPointY(3)};

        //trails
        g.setColor(Color.WHITE);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        g2.drawLine((int)getPointX(1), (int)getPointY(1) - 2, (int)getPointX(1) - (int)this.xMove * 20, (int)getPointY(1) - (int)this.yMove * 10); //left trail
        g2.drawLine((int)getPointX(2), (int)getPointY(2) - 2, (int)getPointX(2) - (int)this.xMove * 20, (int)getPointY(2) - (int)this.yMove * 10);  //right trail
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

    /*Gets points X position INCLUDING variable angle.
    *    3
    *   /\
    *  /  \
    * /----\
    * 1    2
    * */
    private float getPointX(int vertex)
    {
        float pX = getVertexXPos(vertex);
        float pY = getVertexYPos(vertex);
        float angleRad = this.angle * 3.14f / 180.0f;

        return Math.round( (pX - this.rotX) * Math.cos(angleRad) - (pY - this.rotY) * Math.sin(angleRad)) + this.rotX;
    }

    /*Gets points Y position INCLUDING variable angle. */
    private float getPointY(int vertex)
    {
        float pX = getVertexXPos(vertex);
        float pY = getVertexYPos(vertex);
        float angleRad = this.angle * 3.14f / 180.0f;

        return Math.round( (pX - this.rotX) * Math.sin(angleRad) + (pY - this.rotY) * Math.cos(angleRad)) + this.rotY;
    }

    private float getVertexXPos(int vertex)
    {
        float pX = 0.0f;
        switch(vertex)
        {
            case 1:
                pX = this.x;
                break;
            case 2:
                pX = this.x + this.width;
                break;
            case 3:
                pX = this.x + this.width / 2;
                break;
        }
        return pX;
    }

    private float getVertexYPos(int vertex)
    {
        float pY = 0.0f;
        switch(vertex)
        {
            case 1:
                pY = this.y + this.height;
                break;
            case 2:
                pY = this.y + this.height;
                break;
            case 3:
                pY = this.y ;
                break;
        }
        return pY;
    }

}
