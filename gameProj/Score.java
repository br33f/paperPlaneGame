package gameProj;

import java.awt.Graphics;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Score 
{
	//attributes
	public static int lastScore;
	private int points;
	private int counter;

	//db config
	protected Connection dbConnection = null;

	//methods
	public Score()
	{
		this.points = 0;
		this.counter = 0;

		this.dbConnect();
	}
	
	public int getPoints() 
	{
		return this.points;
	}

	public void setPoints(int points) 
	{
		this.points = points;
	}
	
	public void tick()
	{
		this.counter++;
	}

	private void dbConnect()
	{
//		String url = "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.dbName;
//		try {
//			this.dbConnection = DriverManager.getConnection(url, this.user, this.password);
//			System.out.println("Connected to: " + url);
//		} catch (SQLException e) {
//            System.out.println("Connection fail: " + url);
//		}
	}

	public boolean isConnected() {
		return (this.dbConnection != null) ? true : false;
	}


}
