package savetheking.game;

public class PointTest {
    public static void main(String[] args) {
        Point point = new Point(0, 0); // Coin inférieur gauche
        System.out.println(point.toChessNotation(8)); // Affiche "a8"

        Point point2 = new Point(7, 7); // Coin supérieur droit
        System.out.println(point2.toChessNotation(8)); // Affiche "h1"

        Point point3 = new Point(4, 3); // Milieu de l'échiquier
        System.out.println(point3.toChessNotation(8)); // Affiche "d4"
    }
}
