package savetheking.game;

public class PGNTest {
    public static void main(String[] args) {
        PGN pgn = new PGN();

        // Simulate some moves
        pgn.addMove(new Pawn("white", new Point(1, 4), 1), new Point(1, 4), new Point(3, 4), false, false); // e4
        pgn.addMove(new Knight("black", new Point(7, 6)), new Point(7, 6), new Point(5, 5), false, false); // Nf6
        pgn.addMove(new Pawn("white", new Point(6, 3), -1), new Point(6, 3), new Point(4, 3), true, true); // dxc5 e.p.

        // Print moves
        System.out.println("Move History:");
        for (String move : pgn.getMoveHistory()) {
            System.out.print(move + " ");
        }
        System.out.println();

        // Save and load test
        String filePath = "test_game.pgn";
        pgn.saveToFile(filePath);

        PGN loadedPGN = new PGN();
        loadedPGN.loadFromFile(filePath);

        System.out.println("Loaded Move History:");
        for (String move : loadedPGN.getMoveHistory()) {
            System.out.print(move + " ");
        }
    }
}
