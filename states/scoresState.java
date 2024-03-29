package states;

import game.Game;
import game.Launcher;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Klasa ScoresState ( opcja menu - wyniki ).
 * Rozszerza State.
 */
public class ScoresState extends State {
    //attributes
    private ArrayList<ArrayList<String>> scoresTable = null;

    //methods

    /**
     * Konstruktor parametryczny klasy ScoresState.
     * @param g obiekt Game
     */
    public ScoresState(Game g)
    {
        super(g);
        this.menuPosition = new int[] {250, 380};
        try {
            this.setScoresTable(g.score.getScores(5));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void tick() {
        this.getMenuInput();
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(GameState.colors[0][0] / 255.0f, GameState.colors[0][1] / 255.0f, GameState.colors[0][2] / 255.0f));
        g.fillRect(0, 0, Launcher.WINDOW_WIDTH, Launcher.WINDOW_HEIGHT);

        g.drawImage(this.cloud, 0, Launcher.WINDOW_HEIGHT - 150, null);

        g.drawImage(GameoverState.logo, 130, 100, null);

        g.setColor(Color.DARK_GRAY);
        g.setFont(new Font("Yu Gothic", Font.PLAIN, 30));
        g.drawString("Top 5", 230, 250);

        g.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
        if(this.scoresTable == null || this.scoresTable.size() == 0)
            g.drawString("Brak połączenia z bazą danych.", 235, 270);
        else {
            for (int i = 1; i <= 5; i++) {
                ArrayList<String> score = this.getScore(i);
                if (score != null)
                    g.drawString(i + ". " + score.get(1) + " (" + score.get(0) + "pkt.)", 235, 250 + i * 20);
            }
        }

        this.drawMenu(g);
    }

    /**
     * Metoda aktualizuje tablice wyników, która znajduje się wewnątrz klasy.
     * @param scoresTable tablica wyników, na która ma być zaktualizowana lokalna.
     */
    public void setScoresTable(ArrayList<ArrayList<String>> scoresTable)
    {
        this.scoresTable = scoresTable;
    }

    /**
     * Metoda pobiera wynik o zadanym indeksie z tablicy wyników.
     * @param position  indeks pozycji (zaczynajac od 1 - pierwszy wynik)
     * @return tablica String'ów (wynik, nazwa)
     */
    public ArrayList<String> getScore(int position)
    {
        if(position > 0 && position <= this.scoresTable.size())
            return this.scoresTable.get(position - 1);
        else return null;
    }

}
