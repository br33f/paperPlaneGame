package game;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

/**
 * Klasa Score - Wynik.
 * Klasa jest odpowiedzialna za odczyt/zapis najlepszych wyników, zliczanie punktów itd.
 */
public class Score
{
	//attributes
	public static int lastScore;
	private int points;
	private int counter;
	//db config
	protected Connector connector;
    //class instance
    private static Score instance = null;

	//methods

    /**
     * Konstruktor klasy Score.
     * KONSTRUKTOR PRYWATNY.
     * Tworzy nowy wątek dla Connectora.
     */
    private Score()
	{
        this.resetScore();

        this.connector = new Connector();
        Thread t = new Thread(this.connector);
        t.start();
	}

    /**
     * Zapewnienie właściwości Singleton dla klasy Score.
     * @return instancje klasy Score.
     */
    public static Score getInstance() {
        if(Score.instance == null) {
            Score.instance = new Score();
        }
        return Score.instance;
    }

    /**
     * Getter.
     * @return liczba punktów.
     */
    public int getPoints()
	{
		return this.points;
	}

    /**
     * Setter.
     * @param points liczba punktów na jaką ma być ustawione.
     */
    public void setPoints(int points)
	{
		this.points = points;
	}

    /**
     * Aktualizacja danych.
     */
    public void tick()
	{
		this.counter++;
	}

    /**
     * Metoda sprawdza stan połączenia.
     * @return true/false (true - jest połączenie).
     */
    public boolean isConnected() {
		return (this.connector.getDbAdapter() != null && this.connector.getDbAdapter().getDbConnection() != null) ? true : false;
	}


    /**
     * Metoda sprawdza pozycje gracza w rankingu.
     * @return pozycja gracza (jeśli większa od 5 to zwróci 0)
     */
    public int getPlayerPosition()
    {
        int returnedValue = 0;
        ArrayList<ArrayList<String>> scores = null;

        try {
            scores = this.getScores(5);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int size = 0;
        if(scores != null)
            size = scores.size();
        returnedValue = 1;

        for(int i = 0; i < size; i++)
            if(Score.lastScore <= Integer.parseInt(scores.get(i).get(0))) returnedValue++;

        return returnedValue;
    }

    /**
     * Metoda pobiera kolekcje wyników
     * @param limit limiter
     * @return 2-wymiarowa tablica stringów
     * @throws SQLException
     */
    public ArrayList<ArrayList<String>> getScores(int limit) throws SQLException {
        if(!this.isConnected()) return null;

        ArrayList<ArrayList<String>> returnedArray = new ArrayList<ArrayList<String>>();
        ResultSet result = this.connector.getDbAdapter().getResultSet("SELECT `score`, `name` FROM `PaperJet_Scores` ORDER BY `score` DESC LIMIT " + limit);

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
     * Metoda resetuje wynik.
     */
    public void resetScore()
    {
        this.points = 0;
        this.counter = 0;
        Score.lastScore = 0;
    }

    /**
     * Metoda realizuje dodanie najlepszego wyniku.
     * Pobiera informacje do gracza i dodaje do bazy danych.
     */
    public void addBestScore()
    {
        String response = null;
        String msg = "Gratulacje! Zająłeś " + this.getPlayerPosition() + " miejsce. Podaj swoje imie/nick, aby zapisać wynik (minimum 3 znaki):";

        response = (String) JOptionPane.showInputDialog(null, msg, "Zapis wyniku", JOptionPane.OK_CANCEL_OPTION);

        if (response != null && response.trim().length() > 2) {
            try {
                Statement s = this.connector.getDbAdapter().getDbConnection().createStatement();
                s.execute("INSERT INTO `PaperJet_Scores` (`score`, `name`) VALUES('" + Score.lastScore + "', '" + response + "');");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
