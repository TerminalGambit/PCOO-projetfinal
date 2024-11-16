package savetheking.game;

import java.util.List;

/**
 * The Minimax class implements the Minimax algorithm with alpha-beta pruning
 * to determine the best possible move in a given board state.
 */
public class Minimax {

    private static final int MAX_DEPTH = 3; // Depth limit for the Minimax algorithm

    /**
     * Evaluates the board position for a given player.
     * @param board The current state of the board.
     * @param color The color of the player to evaluate for ("white" or "black").
     * @return An integer representing the evaluation score. Positive is favorable for the player.
     */
    public int evaluatePosition(Board board, String color) {
        int score = 0;

        // Retrieve player and opponent pieces
        List<Piece> playerPieces = board.getPlayerPieces(color);
        List<Piece> opponentPieces = board.getOpponentPieces(color);

        // Material score
        score += getMaterialScore(playerPieces);
        score -= getMaterialScore(opponentPieces);

        // King safety
        King playerKing = null;
        for (Piece piece : playerPieces) {
            if (piece instanceof King) {
                playerKing = (King) piece;
                break;
            }
        }

        King opponentKing = null;
        for (Piece piece : opponentPieces) {
            if (piece instanceof King) {
                opponentKing = (King) piece;
                break;
            }
        }

        if (playerKing != null) score += getKingSafetyScore(playerKing, board);
        if (opponentKing != null) score -= getKingSafetyScore(opponentKing, board);

        // Pawn structure
        score += getPawnStructureScore(playerPieces, board);
        score -= getPawnStructureScore(opponentPieces, board);

        // Mobility
        score += getMobilityScore(playerPieces, board);
        score -= getMobilityScore(opponentPieces, board);

        // Piece activity
        score += getPieceActivityScore(playerPieces);
        score -= getPieceActivityScore(opponentPieces);

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
        if (depth == 0 || isGameOver(board)) {
            // Evaluate the board position when at the max depth or if the game is over
            return evaluatePosition(board, color);
        }

        String opponentColor = color.equals("white") ? "black" : "white";
        List<Piece> pieces = maximizingPlayer ? board.getPlayerPieces(color) : board.getPlayerPieces(opponentColor);

        if (maximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            for (Piece piece : pieces) {
                for (Point move : piece.getPossibleMoves(board)) {
                    Board copy = board.copy();
                    copy.movePiece(piece.getPosition(), move);

                    int eval = minimax(copy, depth - 1, alpha, beta, false, color);
                    maxEval = Math.max(maxEval, eval);
                    alpha = Math.max(alpha, eval);

                    if (beta <= alpha) break; // Alpha-beta pruning
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (Piece piece : pieces) {
                for (Point move : piece.getPossibleMoves(board)) {
                    Board copy = board.copy();
                    copy.movePiece(piece.getPosition(), move);

                    int eval = minimax(copy, depth - 1, alpha, beta, true, color);
                    minEval = Math.min(minEval, eval);
                    beta = Math.min(beta, eval);

                    if (beta <= alpha) break; // Alpha-beta pruning
                }
            }
            return minEval;
        }
    }

    /**
     * Finds the best move for a given player using Minimax.
     * @param board The current state of the board.
     * @param color The color of the player making the move.
     * @return The best Move object for the player.
     */
    public Move getBestMove(Board board, String color) {
        int bestScore = Integer.MIN_VALUE;
        Move bestMove = null;

        List<Piece> playerPieces = board.getPlayerPieces(color);

        for (Piece piece : playerPieces) {
            for (Point move : piece.getPossibleMoves(board)) {
                Board copy = board.copy();
                copy.movePiece(piece.getPosition(), move);

                int moveScore = minimax(copy, MAX_DEPTH - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, false, color);
                if (moveScore > bestScore) {
                    bestScore = moveScore;
                    bestMove = new Move(piece, piece.getPosition(), move, copy.getTileAt(move) instanceof OccupiedTile, false);
                }
            }
        }

        return bestMove;
    }

    /**
     * Checks if the game is over (checkmate or stalemate).
     * @param board The current state of the board.
     * @return True if the game is over, false otherwise.
     */
    private boolean isGameOver(Board board) {
        GameState gameState = GameState.getInstance();

        for (Piece piece : board.getPlayerPieces("white")) {
            if (!piece.getPossibleMoves(board).isEmpty()) return false;
        }

        for (Piece piece : board.getPlayerPieces("black")) {
            if (!piece.getPossibleMoves(board).isEmpty()) return false;
        }

        return gameState.checkGameOver();
    }

    // Material score calculation
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

    // King safety evaluation
    private int getKingSafetyScore(King king, Board board) {
        int score = 0;

        Point kingPosition = king.getPosition();
        List<Point> defendedTiles = board.getDefendedTiles();

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue; // Skip the king's own position
                Point adjacent = new Point(kingPosition.x + dx, kingPosition.y + dy);
                if (board.isWithinBounds(adjacent)) {
                    Tile tile = board.getTileAt(adjacent);
                    if (defendedTiles.contains(adjacent)) score += 5;
                    else if (tile instanceof EmptyTile) score -= 3;
                }
            }
        }

        return score;
    }

    private int getPawnStructureScore(List<Piece> pawns, Board board) {
        int score = 0;
        boolean[] filesWithPawns = new boolean[board.getColumnCount()];

        for (Piece piece : pawns) {
            if (piece instanceof Pawn) {
                Point position = piece.getPosition();
                filesWithPawns[position.y] = true;

                // Reward pawn chains (protected pawns)
                List<Point> defendedTiles = piece.getDefendedTiles(board);
                for (Point defended : defendedTiles) {
                    Tile tile = board.getTileAt(defended);
                    if (tile instanceof OccupiedTile && ((OccupiedTile) tile).getPiece() instanceof Pawn) {
                        score += 5;
                    }
                }

                // Check for passed pawns
                boolean isPassed = true;
                for (int i = position.x + 1; i < board.getRowCount(); i++) {
                    Tile tile = board.getTileAt(new Point(i, position.y));
                    if (tile instanceof OccupiedTile && !((OccupiedTile) tile).getPiece().getColor().equals(piece.getColor())) {
                        isPassed = false;
                        break;
                    }
                }
                if (isPassed) score += 20;
            }
        }

        // Penalize doubled pawns
        for (boolean file : filesWithPawns) {
            if (file) score -= 10;
        }

        return score;
    }

    private int getMobilityScore(List<Piece> pieces, Board board) {
        int score = 0;

        for (Piece piece : pieces) {
            List<Point> possibleMoves = piece.getPossibleMoves(board);
            score += possibleMoves.size(); // Reward pieces with more mobility

            // Extra reward for central control
            for (Point move : possibleMoves) {
                if (move.x >= 2 && move.x <= 5 && move.y >= 2 && move.y <= 5) {
                    score += 2;
                }
            }
        }

        return score;
    }

    private int getPieceActivityScore(List<Piece> pieces) {
        int score = 0;

        for (Piece piece : pieces) {
            Point position = piece.getPosition();

            if (piece instanceof Knight) {
                if (position.x >= 2 && position.x <= 5 && position.y >= 2 && position.y <= 5) {
                    score += 10; // Reward central knight placement
                }
            }

            if (piece instanceof Rook) {
                if (position.x == 6) {
                    score += 10; // Reward rooks on the 7th rank
                }
            }
        }

        return score;
    }

}
