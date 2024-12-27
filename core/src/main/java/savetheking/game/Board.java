package savetheking.game;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe Board représente une grille de type échiquier contenant des objets Tile.
 * Elle peut supporter différentes tailles de plateau et permet de placer et de retirer des pièces.
 */
public class Board {
    private Tile[][] tiles; // Grille 2D représentant le plateau avec des objets Tile
    private int rowCount;   // Nombre de rangées du plateau
    private int columnCount; // Nombre de colonnes du plateau

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
        tiles = new Tile[rowCount][columnCount];
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
        Tile tile = tiles[position.x][position.y];
        if (tile instanceof EmptyTile) {
            tiles[position.x][position.y] = new OccupiedTile(position, piece);
        } else if (tile instanceof OccupiedTile) {
            ((OccupiedTile) tile).setPiece(piece);
        }
    }

    /**
     * Retire une pièce d'une position donnée sur le plateau.
     * @param position La position de la pièce à retirer.
     */
    public void removePiece(Point position) {
        if (!isWithinBounds(position)) {
            throw new IllegalArgumentException("Position hors des limites du plateau : " + position);
        }
        Tile tile = tiles[position.x][position.y];
        if (tile instanceof OccupiedTile) {
            tiles[position.x][position.y] = new EmptyTile(position);
        }
    }

    /**
     * Vérifie si une position est occupée par une pièce d'une couleur donnée.
     * @param position La position à vérifier.
     * @param color La couleur à vérifier.
     * @return True si la position est occupée par une pièce de la couleur donnée, sinon false.
     */
    public boolean isOccupiedByColor(Point position, String color) {
        if (color == null || position == null) {
            throw new IllegalArgumentException("La couleur et la position ne peuvent pas être null.");
        }
        Tile tile = getTileAt(position);
        if (tile instanceof OccupiedTile) {
            Piece piece = ((OccupiedTile) tile).getPiece();
            return piece.getColor().equals(color);
        }
        return false;
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
     * Retourne le nombre de colonnes du plateau.
     * @return Le nombre de colonnes.
     */
    public int getColumnCount() {
        return columnCount;
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
     * Réinitialise le plateau en supprimant toutes les pièces.
     */
    public void resetBoard() {
        initializeBoard();
    }

    /**
     * Met à jour le plateau en recalculant les tuiles défendues.
     */
    public void updateBoard() {
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                tiles[i][j].setDefended(false);
            }
        }
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                Tile tile = tiles[i][j];
                if (tile instanceof OccupiedTile) {
                    Piece piece = ((OccupiedTile) tile).getPiece();
                    List<Point> defendedTiles = piece.getDefendedTiles(this);
                    for (Point point : defendedTiles) {
                        Tile defendedTile = getTileAt(point);
                        if (defendedTile != null) {
                            defendedTile.setDefended(true);
                        }
                    }
                }
            }
        }
    }

    /**
     * Crée une copie profonde du plateau.
     * @return Une nouvelle instance de Board contenant les mêmes pièces.
     */
    public Board copy() {
        Board copy = new Board(rowCount, columnCount);
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                Tile tile = tiles[i][j];
                if (tile instanceof OccupiedTile) {
                    Piece piece = ((OccupiedTile) tile).getPiece();
                    copy.placePiece(piece, new Point(i, j));
                } else {
                    copy.tiles[i][j] = new EmptyTile(new Point(i, j));
                }
            }
        }
        return copy;
    }

    /**
     * Déplace une pièce d'une position de départ à une position d'arrivée.
     * @param start La position de départ de la pièce.
     * @param end La position d'arrivée de la pièce.
     */
    public void movePiece(Point start, Point end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Les positions de départ et d'arrivée ne peuvent pas être null.");
        }
        if (!isWithinBounds(start) || !isWithinBounds(end)) {
            throw new IllegalArgumentException("Les positions doivent être dans les limites du plateau.");
        }

        Tile startTile = getTileAt(start);
        if (startTile instanceof OccupiedTile) {
            Piece piece = ((OccupiedTile) startTile).getPiece();
            removePiece(start); // Supprime la pièce de la position de départ
            placePiece(piece, end); // Place la pièce à la position d'arrivée
            piece.move(end, this.rowCount); // Met à jour la position de la pièce
        }
    }

    /**
     * Récupère une pièce à une position donnée sur le plateau.
     * @param position La position de la pièce à récupérer.
     * @return La pièce présente à la position donnée, ou null si aucune pièce n'est présente.
     */
    public Piece getPieceAt(Point position) {
        if (position == null) {
            throw new IllegalArgumentException("La position ne peut pas être null.");
        }
        if (!isWithinBounds(position)) {
            throw new IllegalArgumentException("Position hors des limites du plateau : " + position);
        }

        Tile tile = getTileAt(position);
        return (tile instanceof OccupiedTile) ? ((OccupiedTile) tile).getPiece() : null;
    }

    /**
     * Vérifie si une position donnée est attaquée par une pièce adverse.
     * @param position La position à vérifier.
     * @param color La couleur du joueur souhaitant vérifier si la position est attaquée.
     * @return True si la position est attaquée, sinon false.
     */
    public boolean isPositionUnderAttack(Point position, String color) {
        if (position == null || color == null) {
            throw new IllegalArgumentException("La position et la couleur ne peuvent pas être null.");
        }
        if (!isWithinBounds(position)) {
            throw new IllegalArgumentException("Position hors des limites du plateau : " + position);
        }

        List<Piece> opponentPieces = getOpponentPieces(color);
        for (Piece piece : opponentPieces) {
            List<Point> possibleMoves = piece.getPossibleMoves(this);
            for (Point move : possibleMoves) {
                if (move.equals(position)) {
                    return true; // La position est attaquée
                }
            }
        }
        return false; // La position n'est pas attaquée
    }

    /**
     * Affiche une représentation visuelle du plateau dans la console.
     * Les cases vides sont représentées par '.', et les pièces par leur couleur et leur type.
     */
    public void printBoard() {
        final String EMPTY_TILE_REPRESENTATION = "."; // Représentation pour les cases vides
        final String ROW_HEADER = "   "; // Espacement pour les en-têtes de colonnes

        // Affiche les en-têtes de colonnes
        StringBuilder columnHeaders = new StringBuilder(ROW_HEADER);
        for (int col = 0; col < columnCount; col++) {
            columnHeaders.append((char) ('a' + col)).append(" ");
        }
        System.out.println(columnHeaders.toString());

        // Affiche les lignes avec les cases
        for (int i = 0; i < rowCount; i++) {
            // Numéro de ligne sur la gauche
            System.out.print((rowCount - i) + " ");

            for (int j = 0; j < columnCount; j++) {
                Tile tile = tiles[i][j];

                // Représente les cases vides et les pièces
                if (tile instanceof EmptyTile) {
                    System.out.print(" " + EMPTY_TILE_REPRESENTATION);
                } else if (tile instanceof OccupiedTile) {
                    Piece piece = ((OccupiedTile) tile).getPiece();
                    char pieceChar = piece.toString().charAt(0); // Première lettre du type de la pièce
                    char colorChar = piece.getColor().charAt(0);  // Première lettre de la couleur
                    System.out.print(" " + colorChar + pieceChar);
                }
            }

            // Numéro de ligne sur la droite
            System.out.println(" " + (rowCount - i));
        }

        // Affiche les en-têtes de colonnes à nouveau
        System.out.println(columnHeaders.toString());
    }

    /**
     * Récupère toutes les cases défendues par les pièces du plateau.
     * Une case défendue est une case atteinte par le mouvement de n'importe quelle pièce d'une couleur donnée.
     * @return Une liste des positions des cases défendues sur le plateau.
     */
    public List<Point> getDefendedTiles() {
        List<Point> defendedTiles = new ArrayList<Point>();

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                Tile tile = tiles[i][j];
                if (tile instanceof OccupiedTile) {
                    Piece piece = ((OccupiedTile) tile).getPiece();
                    defendedTiles.addAll(piece.getDefendedTiles(this));
                }
            }
        }

        return defendedTiles;
    }

    /**
     * Récupère toutes les pièces du joueur actuel ayant une couleur donnée.
     *
     * @param color La couleur du joueur actuel (ex. "Blanc" ou "Noir").
     * @return Une liste contenant toutes les pièces du joueur sur le plateau.
     */
    public List<Piece> getPlayerPieces(String color) {
        List<Piece> playerPieces = new ArrayList<Piece>();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                Tile tile = tiles[i][j];
                if (tile instanceof OccupiedTile) {
                    Piece piece = ((OccupiedTile) tile).getPiece();
                    if (piece.getColor().equals(color)) {
                        playerPieces.add(piece);
                    }
                }
            }
        }
        return playerPieces;
    }

    /**
     * Récupère toutes les pièces de l'adversaire ayant une couleur opposée à celle spécifiée.
     *
     * @param color La couleur du joueur actuel (ex. "Blanc" ou "Noir").
     * @return Une liste contenant toutes les pièces de l'adversaire sur le plateau.
     */
    public List<Piece> getOpponentPieces(String color) {
        List<Piece> opponentPieces = new ArrayList<Piece>();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                Tile tile = tiles[i][j];
                if (tile instanceof OccupiedTile) {
                    Piece piece = ((OccupiedTile) tile).getPiece();
                    if (!piece.getColor().equals(color)) {
                        opponentPieces.add(piece);
                    }
                }
            }
        }
        return opponentPieces;
    }
}
