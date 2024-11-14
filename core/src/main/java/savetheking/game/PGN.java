package savetheking.game;

import java.io.*;
// import java.nio.file.Files;
// import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PGN {
    private final List<String> moves;
    private int moveNumber;

    public PGN() {
        this.moves = new ArrayList<String>(); // Remove diamond operator for Java 6 compatibility
        this.moveNumber = 1;
    }

    public void addMove(Piece piece, Point start, Point end, boolean capture, boolean enPassant) {
        StringBuilder moveNotation = new StringBuilder();

        // Determine if the piece is a pawn and build the move notation
        if (piece instanceof Pawn) {
            if (capture) {
                moveNotation.append((char) ('a' + start.y)).append("x");
            }
            moveNotation.append((char) ('a' + end.y)).append(end.x + 1);
            if (enPassant) moveNotation.append(" e.p.");
        } else {
            moveNotation.append(piece.toString().charAt(0)); // Use first letter for non-pawn pieces, e.g., 'R' for Rook
            if (capture) moveNotation.append("x");
            moveNotation.append((char) ('a' + end.y)).append(end.x + 1);
        }

        // Insert the move number at the beginning for white moves
        if (moveNumber % 2 != 0) {
            moveNotation.insert(0, (moveNumber / 2 + 1) + ". ");
        }

        // Add the formatted move to the list
        moves.add(moveNotation.toString());

        // Increment the move number after each move
        moveNumber++;
    }

    public void saveToFile(String filePath) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(filePath));
            for (String move : moves) {
                writer.write(move + " ");
                if (move.endsWith(".")) writer.newLine(); // New line after each pair of moves
            }
        } catch (IOException e) {
            System.err.println("Error saving PGN file: " + e.getMessage());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.err.println("Error closing writer: " + e.getMessage());
                }
            }
        }
    }

    public void loadFromFile(String filePath) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line;
            moves.clear();
            while ((line = reader.readLine()) != null) {
                for (String move : line.split(" ")) {
                    moves.add(move);
                    moveNumber++;
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading PGN file: " + e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.err.println("Error closing reader: " + e.getMessage());
                }
            }
        }
    }

    public List<String> getMoveHistory() {
        return moves;
    }
}
