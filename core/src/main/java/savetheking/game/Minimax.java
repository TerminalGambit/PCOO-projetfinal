package savetheking.game;

import java.util.List;

/**
 * The Minimax class implements the Minimax algorithm with alpha-beta pruning
 * to determine the best possible move in a given board state.
 */
public class Minimax {

    /**
     * Evaluates the board position for a given player.
     * @param board The current state of the board.
     * @param color The color of the player to evaluate for ("white" or "black").
     * @return An integer representing the evaluation score. Positive is favorable for the player.
     */
    public int evaluatePosition(Board board, String color) {
        int score = 0;

        // Retrieve all player and opponent pieces
        List<Piece> playerPieces = board.getPlayerPieces(color);
        List<Piece> opponentPieces = board.getOpponentPieces(color);

        // Material score: Add value of player pieces and subtract opponent pieces
        score += getMaterialScore(playerPieces);
        score -= getMaterialScore(opponentPieces);

        // Positional score: Favor positions with control and safety
        score += getPositionalScore(playerPieces, board);
        score -= getPositionalScore(opponentPieces, board);

        return score;
    }

    /**
     * Minimax algorithm with alpha-beta pruning to find the best move.
     * @param board The current state of the board.
     * @param depth The depth to search in the game tree.
     * @param alpha The alpha value for alpha-beta pruning.
     * @param beta The beta value for alpha-beta pruning.
     * @param maximizingPlayer True if it's the maximizing player's turn.
     * @param color The color of the player to evaluate.
     * @return The best score for the given player.
     */
    public int minimax(Board board, int depth, int alpha, int beta, boolean maximizingPlayer, String color) {
        // Placeholder for now
        return 0;
    }

    /**
     * Finds the best move for a given player using Minimax.
     * @param board The current state of the board.
     * @param color The color of the player making the move.
     * @return The best Move object for the player.
     */
    public Move getBestMove(Board board, String color) {
        // Placeholder for now
        return null;
    }

    /**
     * Calculates the total material score for a list of pieces.
     * @param pieces The list of pieces to evaluate.
     * @return The total material score.
     */
    private int getMaterialScore(List<Piece> pieces) {
        int score = 0;

        for (Piece piece : pieces) {
            if (piece instanceof King) score += 1000;
            else if (piece instanceof Queen) score += 9;
            else if (piece instanceof Rook) score += 5;
            else if (piece instanceof Bishop || piece instanceof Knight) score += 3;
            else if (piece instanceof Pawn) score += 1;
        }

        return score;
    }

    /**
     * Calculates the positional score for a list of pieces on the board.
     * @param pieces The list of pieces to evaluate.
     * @param board The current state of the board.
     * @return The total positional score.
     */
    private int getPositionalScore(List<Piece> pieces, Board board) {
        int score = 0;

        for (Piece piece : pieces) {
            List<Point> defendedTiles = piece.getDefendedTiles(board);
            score += defendedTiles.size(); // Favor pieces that control more tiles
        }

        return score;
    }
}
