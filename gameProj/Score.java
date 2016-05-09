package gameProj;

import javax.swing.*;
import java.awt.Graphics;
import java.sql.*;
import java.util.ArrayList;

public class Score 
{
	//attributes
	public static int lastScore;
	private int points;
	private int counter;
	//db config
	protected Connection dbConnection = null;
    //class instance
    private static Score instance = null;

	//methods
	private Score()
	{
		this.resetScore();

		this.dbConnect();
	}

    public static Score getInstance() {
        if(Score.instance == null) {
            Score.instance = new Score();
        }
        return Score.instance;
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
		String host = "46.41.128.103";
		String port = "3306";
		String dbName = "paperJet_game";
		String user = "paperJet_game";
		String password = "glfL696#";
		String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName;
		try {
			this.dbConnection = DriverManager.getConnection(url, user, password);
			System.out.println("Connected to: " + url);
		} catch (Exception e) {
            System.out.println("Connection failed : " + url);
		}
	}

	public boolean isConnected() {
		return (this.dbConnection != null) ? true : false;
	}

    /*
    * Function checks whether players score is in 5 top positions
    * */
    public boolean isPolePosition()
    {
        boolean returnedValue = false;
        ArrayList<ArrayList<String>> scores = null;

        try {
            scores = this.getScores(5);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int size = scores.size();
        if(size < 5) returnedValue = true;
        else
            for(int i = 0; i < size; i++)
                if(Score.lastScore > Integer.parseInt(scores.get(i).get(0))) returnedValue = true;

        return returnedValue;

    }

    public ArrayList<ArrayList<String>> getScores(int limit) throws SQLException {
        ArrayList<ArrayList<String>> returnedArray = new ArrayList<ArrayList<String>>();
        ResultSet result = this.getResultSet("SELECT `score`, `name` FROM `PaperJet_Scores` ORDER BY `score` DESC LIMIT " + limit);

        if(result != null)
            while(result.next())
            {
                ArrayList<String> tmp = new ArrayList<String>();
                for(int i = 1; i <= 2; i++)
                {
                    String column = result.getString(i);
                    if(!column.equals("0"))
                        tmp.add(column);
                }
                returnedArray.add(tmp);
            }

        return returnedArray;
    }

    /**
     * Function returns ResultSet from mysql query string
     * @param str - mysql query string
     * @return ResultSet - result from mysql query
     */
    private ResultSet getResultSet(String str)
    {
        ResultSet returnedValue = null;

        if(this.isConnected()) {
            try {
                Statement s = this.dbConnection.createStatement();
                returnedValue = s.executeQuery(str);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return returnedValue;
    }

    public void resetScore()
    {
        this.points = 0;
        this.counter = 0;
        Score.lastScore = 0;
    }

    public void addBestScore()
    {
        String str = (String) JOptionPane.showInputDialog("Gratulacje! Zająłeś jedno z 5 pierwszych miejsc. Podaj swoje imie/nick, aby zapisać wynik:");

        if(str != null)
        {
            try {
                Statement s = this.dbConnection.createStatement();
                s.execute("INSERT INTO `PaperJet_Scores` (`score`, `name`) VALUES('" + Score.lastScore + "', '" + str + "');");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
