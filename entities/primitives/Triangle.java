package entities.primitives;

/**
 * Klasa typu Triangle - trójkąt.
 * Created by br33 on 23.05.2016.
 */
public class Triangle {
    /*
     *    3
     *   /\
     *  /  \
     * /----\
     * 1    2
    */

    //attributes
    private Point[] v;

    //methods

    /**
     * Konstruktor parametryczny tworzący obiekt klasy Triangle - trójkąt o trzech podanych wierzchołkach.
     * @param v tablica 3 wierzchołków.
     */
    public Triangle(Point[] v){
        this.v = v;
    }

    /**
     * Metoda zwracająca punkt odpowiadający n temu wierzchołkowi trójkąta.
     * @param vertex numer wierzchołka trójkąta.
     * @return Point - punkt odpowiadający n temu wierzchołkowi.
     */
    public Point getVertex(int vertex) {
        if(vertex < 1 || vertex > 3)
            vertex = 1;
        return this.v[vertex - 1];
    }

    /**
     * Metoda zmieniająca dany wierzchołek.
     * @param vertex numer wierzchołka do zmiany.
     * @param v punkt, na który zmieniamy wierzchołek.
     */
    public void setVertex(int vertex, Point v) {
        if(vertex >= 1 && vertex <= 3)
            this.v[vertex - 1] = v;
    }

    /**
     * Metoda zwracająca punkt obrotu trójkąta
     * @return punktu obrotu trójkąta.
     */
    public Point getRotationPoint(){
        Point p1 = this.v[0].getMiddle(this.v[1]);
        return p1.getMiddle(this.v[2]);
    }

    /**
     * Metoda obraca trójkąt o zadany kąt
     * @param angle kąt obrotu podany w stopniach
     */
    public void rotate(float angle){
        float angleRad = angle * 3.14f / 180.0f;
        Point rotationPoint = this.getRotationPoint();

        for(int i = 0; i < 3; i++){
            float x = Math.round( (this.v[i].getX() - rotationPoint.getX()) * Math.cos(angleRad) - (this.v[i].getY() - rotationPoint.getY()) * Math.sin(angleRad)) + rotationPoint.getX();
            float y = Math.round( (this.v[i].getX() - rotationPoint.getX()) * Math.sin(angleRad) + (this.v[i].getY() - rotationPoint.getY()) * Math.cos(angleRad)) + rotationPoint.getY();
            this.v[i].setX(x);
            this.v[i].setY(y);
        }
    }

    /**
     * Metoda przesuwa trójkąt o zadane wartości
     * @param x przesunięcie w osi poziomej
     * @param y przesunięcie w osi pionowej
     */
    public void translate(float x, float y){
        for(int i = 0; i < 3; i++){
            this.v[i].setX(this.v[i].getX() + x);
            this.v[i].setY(this.v[i].getY() + y);
        }
    }
}
