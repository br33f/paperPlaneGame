package game;

import adapters.MysqlAdapter;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class Score 
{
	//attributes
	public static int lastScore;
	private int points;
	private int counter;
	//db config
	protected MysqlAdapter adapter;
    //class instance
    private static Score instance = null;

	//methods
	private Score()
	{
        this.resetScore();

        this.adapter = new MysqlAdapter("mysqlconfig.properties", false);
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

	public boolean isConnected() {
		return (this.adapter.getDbConnection() != null) ? true : false;
	}

    /*
    * Function checks whether players score is in 5 top positions
    * */
    public int getPlayerPosition()
    {
        int returnedValue = 0;
        ArrayList<ArrayList<String>> scores = null;

        try {
            scores = this.getScores(5);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int size = scores.size();
        returnedValue = 1;

        for(int i = 0; i < size; i++)
            if(Score.lastScore <= Integer.parseInt(scores.get(i).get(0))) returnedValue++;

        return returnedValue;
    }

    public ArrayList<ArrayList<String>> getScores(int limit) throws SQLException {
        ArrayList<ArrayList<String>> returnedArray = new ArrayList<ArrayList<String>>();
        ResultSet result = this.adapter.getResultSet("SELECT `score`, `name` FROM `PaperJet_Scores` ORDER BY `score` DESC LIMIT " + limit);

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

    public void resetScore()
    {
        this.points = 0;
        this.counter = 0;
        Score.lastScore = 0;
    }

    public void addBestScore()
    {
        String response = null;
        String msg = "Gratulacje! Zająłeś" + this.getPlayerPosition() + " miejsce. Podaj swoje imie/nick, aby zapisać wynik:";
        String notCorrectMsg = "Nie poprawna nazwa użytkownika (minimum 3 znaki).\n";
        boolean invalid = false;

        do {
            String str = (invalid) ? notCorrectMsg + msg : msg;
            response = (String) JOptionPane.showInputDialog(str);

            if (response != null && response.trim().length() > 2) {
                try {
                    Statement s = this.adapter.getDbConnection().createStatement();
                    s.execute("INSERT INTO `PaperJet_Scores` (`score`, `name`) VALUES('" + Score.lastScore + "', '" + response + "');");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                invalid = false;
            }
            else
                invalid = true;
        }while(invalid);
    }


}
