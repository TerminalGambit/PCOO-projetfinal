package savetheking.game;

import java.util.Objects;

/**
 * La classe Point représente une position sur l'échiquier avec des coordonnées x et y.
 */
public class Point {
    public int x; // Coordonnée de la ligne (0-indexée)
    public int y; // Coordonnée de la colonne (0-indexée)

    /**
     * Constructeur de la classe Point.
     *
     * @param x La coordonnée de la ligne.
     * @param y La coordonnée de la colonne.
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Compare ce point avec un autre objet pour vérifier l'égalité.
     *
     * @param obj L'objet à comparer.
     * @return true si les deux points ont les mêmes coordonnées, sinon false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Point point = (Point) obj;
        return x == point.x && y == point.y;
    }

    /**
     * Génère un hashcode pour ce point.
     *
     * @return Le hashcode basé sur x et y.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Représentation textuelle du point pour le débogage.
     *
     * @return Une chaîne de caractères représentant le point sous forme (x, y).
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * Vérifie si ce point est dans les limites d'un échiquier carré.
     *
     * @param boardSize La taille de l'échiquier (par exemple, 8 pour un échiquier standard).
     * @return true si le point est dans les limites, sinon false.
     */
    public boolean isWithinBounds(int boardSize) {
        return x >= 0 && x < boardSize && y >= 0 && y < boardSize;
    }

    /**
     * Convertit les coordonnées (x, y) en notation échiquéenne (par exemple, a1, h8).
     *
     * @param boardSize La taille de l'échiquier (par exemple, 8 pour un échiquier standard).
     * @return La notation échiquéenne correspondant à ce point.
     * @throws IllegalArgumentException si les coordonnées sont hors des limites.
     */
    public String toChessNotation(int boardSize) {
        if (isWithinBounds(boardSize)) {
            throw new IllegalArgumentException("Les coordonnées (" + x + ", " + y + ") sont hors des limites de l'échiquier.");
        }
        char file = (char) ('a' + y); // Convertit y (colonne) en lettre (a-h)
        int rank = boardSize - x;    // Convertit x (ligne) en rangée (1-8)
        return "" + file + rank;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }
}
