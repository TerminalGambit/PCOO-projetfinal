package savetheking.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import java.util.List;

/**
 * Classe abstraite représentant une pièce d’échecs pour le mode Solo Chess.
 * Chaque pièce a une couleur, une position et une image.
 */
public abstract class Piece {
    protected String color; // La couleur de la pièce ("White" ou "Black")
    protected Point position; // La position actuelle de la pièce sur le plateau
    protected String texturePath; // Le chemin vers l'image de la pièce
    private Texture texture; // Texture pour le rendu graphique
    private int moveCount;

    /**
     * Constructeur pour initialiser une pièce avec une couleur, une position et un chemin d'image.
     *
     * @param color    La couleur de la pièce ("White" ou "Black").
     * @param position La position initiale de la pièce sur le plateau.
     * @param texture Le chemin vers l'image de la pièce.
     */
    public Piece(String color, Point position, Texture texture) {
        this.color = color;
        this.position = position;
        this.texture = texture;
        this.moveCount = 0;
    }

    /**
     * Retourne la couleur actuelle de la pièce.
     *
     * @return La couleur de la pièce ("White" ou "Black").
     */
    public String getColor() {
        return color;
    }

    /**
     * Définit une nouvelle couleur pour la pièce.
     *
     * @param color La nouvelle couleur ("White" ou "Black").
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Retourne la position actuelle de la pièce.
     *
     * @return La position actuelle de la pièce sous forme de `Point`.
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Retourne la texture de la pièce pour le rendu graphique.
     *
     * @return La texture de la pièce.
     */
    public Texture getTexture() {
        return texture;
    }

    /**
     * Déplace la pièce vers une nouvelle position.
     *
     * @param newPosition La nouvelle position cible.
     * @param boardSize   La taille du plateau.
     */
    public void move(Point newPosition, int boardSize) {
        if (isWithinBounds(newPosition, boardSize)) {
            this.position = newPosition;
            this.moveCount++;
        } else {
            throw new IllegalArgumentException("Position hors limites : " + newPosition);
        }
    }

    /**
     * Vérifie si une position est dans les limites du plateau.
     *
     * @param point     La position à vérifier.
     * @param boardSize La taille du plateau.
     * @return `true` si la position est dans les limites du plateau, sinon `false`.
     */
    protected boolean isWithinBounds(Point point, int boardSize) {
        return point.x >= 0 && point.x < boardSize && point.y >= 0 && point.y < boardSize;
    }

    /**
     * Méthode abstraite pour déterminer les mouvements possibles pour une pièce.
     *
     * @param board L'état actuel du plateau.
     * @return Une liste des positions possibles où la pièce peut se déplacer.
     */
    public abstract List<Point> getPossibleMoves(Board board);

    /**
     * Retourne une représentation textuelle de la pièce.
     *
     * @return Une chaîne décrivant la pièce.
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " (" + color + ")";
    }

    /**
     * Libère les ressources liées à la texture de la pièce.
     */
    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }



    public int getMoveCount() {
        return this.moveCount;
    }
}
