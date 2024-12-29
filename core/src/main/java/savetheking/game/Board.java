package savetheking.game;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe Board représente une grille de type échiquier contenant des objets Tile.
 * Elle est adaptée au mode Solo Chess.
 */
public class Board implements Observable {
    private final Tile[][] tiles;
    private final int rowCount;
    private final int columnCount;
    private final List<Observer> observers = new ArrayList<Observer>();

    /**
     * Constructeur pour un plateau carré.
     * @param size La taille du plateau (nombre de rangées et colonnes).
     */
    public Board(int size) {
        this(size, size);
    }

    /**
     * Constructeur pour un plateau rectangulaire.
     * @param rowCount Nombre de rangées.
     * @param columnCount Nombre de colonnes.
     */
    public Board(int rowCount, int columnCount) {
        if (rowCount <= 0 || columnCount <= 0) {
            throw new IllegalArgumentException("Les dimensions du plateau doivent être positives.");
        }
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.tiles = new Tile[rowCount][columnCount];
        initializeBoard();
    }

    /**
     * Initialise le plateau en remplissant chaque position avec des objets EmptyTile.
     */
    public void initializeBoard() {
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                tiles[i][j] = new EmptyTile(new Point(i, j));
            }
        }
    }

    /**
     * Place une pièce à une position donnée sur le plateau.
     * @param piece La pièce à placer.
     * @param position La position où placer la pièce.
     */
    public void placePiece(Piece piece, Point position) {
        if (piece == null || position == null) {
            throw new IllegalArgumentException("La pièce et la position ne peuvent pas être null.");
        }
        if (!isWithinBounds(position)) {
            throw new IllegalArgumentException("Position hors des limites du plateau : " + position);
        }
        tiles[position.x][position.y] = new OccupiedTile(position, piece);
    }

    /**
     * Retire une pièce d'une position donnée sur le plateau.
     * @param position La position de la pièce à retirer.
     */
    public void removePiece(Point position) {
        if (!isWithinBounds(position)) {
            throw new IllegalArgumentException("Position hors des limites du plateau : " + position);
        }
        tiles[position.x][position.y] = new EmptyTile(position);
    }

    /**
     * Retourne la tuile à une position donnée.
     * @param position La position à récupérer.
     * @return La tuile à la position donnée ou null si hors limites.
     */
    public Tile getTileAt(Point position) {
        if (!isWithinBounds(position)) {
            return null;
        }
        return tiles[position.x][position.y];
    }

    /**
     * Retourne le nombre de rangées du plateau.
     * @return Le nombre de rangées.
     */
    public int getRowCount() {
        return rowCount;
    }

    /**
     * Vérifie si une position est dans les limites du plateau.
     * @param position La position à vérifier.
     * @return True si la position est dans les limites, sinon false.
     */
    public boolean isWithinBounds(Point position) {
        return position != null &&
            position.x >= 0 && position.x < rowCount &&
            position.y >= 0 && position.y < columnCount;
    }

    /**
     * Déplace une pièce d'une position de départ à une position d'arrivée.
     * @param start La position de départ de la pièce.
     * @param end La position d'arrivée de la pièce.
     */
    public void movePiece(Point start, Point end) {
        if (!isWithinBounds(start) || !isWithinBounds(end)) {
            throw new IllegalArgumentException("Les positions doivent être dans les limites du plateau.");
        }
        Tile startTile = getTileAt(start);
        if (startTile instanceof OccupiedTile) {
            OccupiedTile occupiedTile = (OccupiedTile) startTile;
            Piece piece = occupiedTile.getPiece();
            removePiece(start);
            placePiece(piece, end);
            piece.move(end, this.rowCount);
        }
    }

    /**
     * Affiche une représentation visuelle du plateau dans la console.
     */
    public void printBoard() {
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                Tile tile = tiles[i][j];
                System.out.print(tile instanceof OccupiedTile ? "O " : ". ");
            }
            System.out.println();
        }
    }

    public int getColumnCount() {
        return columnCount;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    /**
     * Retourne une liste de toutes les pièces restantes sur le plateau.
     * @return Une liste contenant les pièces restantes.
     */
    public List<Piece> getRemainingPieces() {
        List<Piece> remainingPieces = new ArrayList<Piece>();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                Tile tile = tiles[i][j];
                if (tile instanceof OccupiedTile) {
                    Piece piece = ((OccupiedTile) tile).getPiece();
                    if (piece != null) {
                        remainingPieces.add(piece);
                    }
                }
            }
        }
        return remainingPieces;
    }
}
