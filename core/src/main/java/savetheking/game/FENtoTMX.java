package savetheking.game;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

public class FENtoTMX {

    // ----------------------------------------------------------
    // Mapping from FEN character -> tile ID in ChessPieceObjects.tsx
    // (You can tweak or extend if you use a different scheme)
    // ----------------------------------------------------------
    private static int getTileIdForPiece(char fenChar) {
        // Lowercase => black, uppercase => white
        boolean isWhite = Character.isUpperCase(fenChar);
        char piece = Character.toLowerCase(fenChar);

        switch (piece) {
            case 'p': // Pawn
                return isWhite ? 9 : 3;
            case 'r': // Rook
                return isWhite ? 11 : 5;
            case 'n': // Knight
                return isWhite ? 8 : 2;
            case 'b': // Bishop
                return isWhite ? 6 : 0;
            case 'q': // Queen
                return isWhite ? 10 : 4;
            case 'k': // King
                return isWhite ? 7 : 1;
            default:
                return -1; // Not recognized
        }
    }

    /**
     * Creates a new <objectgroup> for the "Piece Layer" by parsing a given FEN.
     *
     * @param fen  a standard FEN string, e.g. "3RR3/N2R4/..."
     * @return     an XML string representing the <objectgroup> with <object> elements.
     */
    public static String parseFENAndCreatePieceLayer(String fen) {
        // We'll assume the FEN is only the "piece placement" field (before side to move, etc.)
        // If you have a full FEN with 'w' or 'b' etc., you might need to split it by spaces.
        String piecePlacement = fen.split(" ")[0];  // E.g. "3RR3/N2R4/..."

        // Split ranks (FEN has 8 parts separated by "/")
        String[] ranks = piecePlacement.split("/");

        // Start building the <objectgroup> ...
        StringBuilder sb = new StringBuilder();
        sb.append(" <objectgroup id=\"2\" name=\"Piece Layer\">\n");

        int objectIdCounter = 1;

        // FEN rank[0] = rank 8, rank[7] = rank 1
        // But we want rankIndex=0 => board[0][0] => x=0, y=64
        // We'll define a new formula so rankIndex=0 => y=64, rankIndex=1 => y=128, ..., rankIndex=7 => y=576
        // e.g. y = (rankIndex + 1) * 64
        // If you prefer something else, adjust as needed.
        for (int rankIndex = 0; rankIndex < 8; rankIndex++) {
            String rank = ranks[rankIndex];
            int fileIndex = 0; // fileIndex goes from 0..7 (columns a..h)
            for (int i = 0; i < rank.length(); i++) {
                char c = rank.charAt(i);
                if (Character.isDigit(c)) {
                    // c is a digit => number of empty squares
                    int emptyCount = c - '0';
                    fileIndex += emptyCount; // just skip those squares
                } else {
                    // It's a piece
                    int tileId = getTileIdForPiece(c);
                    if (tileId >= 0) {
                        // Convert tileId to GID => tileId + 3
                        int gid = tileId + 3;

                        // For the bottom-left corner indexing:
                        // x = fileIndex * 64
                        // y = (rankIndex + 1)*64
                        // If rankIndex=0 => y=64 => bottom row
                        // If rankIndex=7 => y=512 => top row
                        int x = fileIndex * 64;
                        int y = (rankIndex + 1) * 64;

                        // color => uppercase => white, lowercase => black
                        String color = Character.isUpperCase(c) ? "white" : "black";
                        String type = pieceTypeFromFenChar(Character.toLowerCase(c));

                        sb.append("  <object id=\"")
                            .append(objectIdCounter++)
                            .append("\" gid=\"")
                            .append(gid)
                            .append("\" x=\"")
                            .append(x)
                            .append("\" y=\"")
                            .append(y)
                            .append("\" width=\"64\" height=\"64\">\n");

                        sb.append("   <properties>\n");
                        sb.append("    <property name=\"type\" value=\"").append(type).append("\"/>\n");
                        sb.append("    <property name=\"color\" value=\"").append(color).append("\"/>\n");
                        sb.append("   </properties>\n");
                        sb.append("  </object>\n");
                    }
                    fileIndex++;
                }
            }
        }

        sb.append(" </objectgroup>\n");
        return sb.toString();
    }

    /**
     * Convenience method to map a single FEN character ('p','r','n','b','q','k') to type string.
     * Lowercase is black, uppercase is white, but the letter itself just indicates piece type.
     *
     * @param c a single char indicating the piece, in lowercase.
     * @return  e.g. "pawn", "rook", "knight", "bishop", "queen", or "king"
     */
    private static String pieceTypeFromFenChar(char c) {
        switch (c) {
            case 'p': return "pawn";
            case 'r': return "rook";
            case 'n': return "knight";
            case 'b': return "bishop";
            case 'q': return "queen";
            case 'k': return "king";
            default:   return "unknown";
        }
    }

    /**
     * Example main method that:
     *  1) Reads a FEN (hardcoded here)
     *  2) Generates an <objectgroup> snippet
     *  3) Creates a TMX file (with a minimal example Board Layer)
     */
    public static void main(String[] args) {
        String fen = "3RR3/N2R4/1N2N3/1QB1K2Q/R2N1BQ1/N1B1RN2/2BQR3/2NR2B1";
        // If the FEN includes spaces (like "... w - - 0 1"), remove them or parse accordingly

        String pieceLayerXml = parseFENAndCreatePieceLayer(fen);

        // Now let's produce a minimal .tmx file with a hard-coded board layer + the pieceLayerXml
        String tmxOutput = generateTmxFile(pieceLayerXml);

        // Write to disk if desired
        FileWriter fw = null;
        try {
            fw = new FileWriter("assets/HarderDifficulty.tmx");
            fw.write(tmxOutput);
            System.out.println("TMX file created: output.tmx");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Helper method to produce a full TMX string containing:
     *  1) The board layer (DarkGreenTileSet.tsx / LightWhiteTileSet.tsx)
     *  2) The object group for the pieces
     */
    private static String generateTmxFile(String pieceLayerXml) {
        // This is just a sample minimal TMX structure;
        // you can adapt it or read from an existing template .tmx
        // and then insert the <objectgroup> snippet before </map>
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<map version=\"1.10\" tiledversion=\"1.11.0\" orientation=\"orthogonal\" " +
            "renderorder=\"right-down\" width=\"8\" height=\"8\" tilewidth=\"64\" tileheight=\"64\" " +
            "infinite=\"0\" nextlayerid=\"3\" nextobjectid=\"999\">\n" + // nextobjectid is arbitrary
            " <tileset firstgid=\"1\" source=\"DarkGreenTileSet.tsx\"/>\n" +
            " <tileset firstgid=\"2\" source=\"LightWhiteTileSet.tsx\"/>\n" +
            " <tileset firstgid=\"3\" source=\"pieces/ChessPieceObjects.tsx\"/>\n" +
            " <layer id=\"1\" name=\"Board Layer\" width=\"8\" height=\"8\">\n" +
            "  <data encoding=\"csv\">\n" +
            "2,1,2,1,2,1,2,1,\n" +
            "1,2,1,2,1,2,1,2,\n" +
            "2,1,2,1,2,1,2,1,\n" +
            "1,2,1,2,1,2,1,2,\n" +
            "2,1,2,1,2,1,2,1,\n" +
            "1,2,1,2,1,2,1,2,\n" +
            "2,1,2,1,2,1,2,1,\n" +
            "1,2,1,2,1,2,1,2\n" +
            "  </data>\n" +
            " </layer>\n" +
            pieceLayerXml +
            "</map>\n";
    }
}
