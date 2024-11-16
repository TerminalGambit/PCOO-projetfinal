package savetheking.game;

import java.util.Scanner;

public class MinimaxTest {

    public static void main(String[] args) {
        Board board = new Board(8); // Create an 8x8 board
        GameState gameState = GameState.getInstance();
        Minimax minimax = new Minimax();

        // Setup a test position
        setupTestPosition(board);

        Scanner scanner = new Scanner(System.in);
        boolean isPlayerTurn = true;

        while (!gameState.checkGameOver()) {
            board.printBoard(); // Print the board state

            if (isPlayerTurn) {
                // Player's move
                System.out.println("Enter your move (e.g., 'e2 e4'):");
                String input = scanner.nextLine();
                if (!processPlayerMove(input, board)) {
                    System.out.println("Invalid move. Try again.");
                    continue;
                }
            } else {
                // AI's move
                System.out.println("AI is thinking...");
                Move bestMove = minimax.getBestMove(board, "black");
                if (bestMove != null) {
                    board.movePiece(bestMove.getStart(), bestMove.getEnd());
                    gameState.recordMove(bestMove.getPiece(), bestMove.getStart(), bestMove.getEnd(), bestMove.isCapture(), bestMove.isEnPassant());
                    System.out.println("AI moved: " + bestMove.getStart() + " to " + bestMove.getEnd());
                } else {
                    System.out.println("AI has no valid moves. Game over!");
                    break;
                }
            }

            // Switch turns
            isPlayerTurn = !isPlayerTurn;
        }

        System.out.println("Game over!");
        scanner.close();
    }

    private static void setupTestPosition(Board board) {
        // Example position
        board.placePiece(new King("white", new Point(0, 4)), new Point(0, 4));
        board.placePiece(new King("black", new Point(7, 4)), new Point(7, 4));
        board.placePiece(new Rook("white", new Point(0, 0)), new Point(0, 0));
        board.placePiece(new Rook("black", new Point(7, 0)), new Point(7, 0));
    }

    private static boolean processPlayerMove(String input, Board board) {
        // Parse input (e.g., "e2 e4")
        String[] parts = input.split(" ");
        if (parts.length != 2) return false;

        Point start = parsePosition(parts[0]);
        Point end = parsePosition(parts[1]);

        if (start == null || end == null) return false;

        // Validate and execute move
        Tile startTile = board.getTileAt(start);
        if (startTile instanceof OccupiedTile) {
            Piece piece = ((OccupiedTile) startTile).getPiece();
            if (piece.getPossibleMoves(board).contains(end)) {
                board.movePiece(start, end);
                return true;
            }
        }
        return false;
    }

    private static Point parsePosition(String pos) {
        if (pos.length() != 2) return null;
        char col = pos.charAt(0);
        char row = pos.charAt(1);

        int x = 8 - Character.getNumericValue(row); // Convert row to index
        int y = col - 'a'; // Convert column to index
        if (x < 0 || x >= 8 || y < 0 || y >= 8) return null;

        return new Point(x, y);
    }
}
