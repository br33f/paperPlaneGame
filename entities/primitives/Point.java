package entities.primitives;

/**
 * Klasa Point - Punkt
 * Created by br33 on 23.05.2016.
 */
public class Point {
    //attributes
    private float x;
    private float y;

    //methods

    /**
     * Konstruktor parametryczny klasy Point
     * @param x współrzędna położenia punktu w osi poziomej
     * @param y współrzędna położenia punktu w osi pionowej
     */
    public Point(float x, float y){
        this.x = x;
        this.y = y;
    }

    /**
     * Getter
     * @return współrzędna y
     */
    public float getY() {
        return y;
    }

    /**
     * Setter
     * @param y współrzędna y
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Getter
     * @return współrzędna x
     */
    public float getX() {
        return x;
    }

    /**
     * Setter
     * @param x współrzędna x
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Metoda zwraca punkt położony pomiędzy sobą, a przekazanym punktem.
     * @param p obiekt klasy Point - punkt
     * @return punkt pomiędzy sobą, a przekazanym jako parametr punktem
     */
    public Point getMiddle(Point p){
        if(p != null)
            return new Point((this.x + p.x) / 2, (this.y + p.y) / 2);
        else
            return this;
    }

}
