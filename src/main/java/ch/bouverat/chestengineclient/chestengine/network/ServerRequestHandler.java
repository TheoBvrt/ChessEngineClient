package ch.bouverat.chestengineclient.chestengine.network;

import ch.bouverat.chestengineclient.chestengine.client.Pawn;
import ch.bouverat.chestengineclient.chestengine.client.PawnColor;
import ch.bouverat.chestengineclient.chestengine.client.PawnType;

public class ServerRequestHandler {
    public int gameCreationRequest() {
        System.out.println("Sending request to server...");
        return (5);
    }

    public Pawn[][] boardUpdateRequest() {
        Pawn[][] newBoard = new Pawn[8][8];
        newBoard[0][0] = new Pawn(0, 0, "BR1", PawnType.ROOK, PawnColor.BLACK);
        newBoard[0][1] = new Pawn(0, 1, "BKT1", PawnType.KNIGHT, PawnColor.BLACK);
        newBoard[0][2] = new Pawn(0, 2, "BB1", PawnType.BISHOP, PawnColor.BLACK);
        newBoard[0][3] = new Pawn(0, 3, "BQ", PawnType.QUEEN, PawnColor.BLACK);
        newBoard[0][4] = new Pawn(0, 4, "BK", PawnType.KING, PawnColor.BLACK);
        newBoard[0][5] = new Pawn(0, 5, "BB2", PawnType.BISHOP, PawnColor.BLACK);
        newBoard[0][6] = new Pawn(0, 6, "BKT2", PawnType.KNIGHT, PawnColor.BLACK);
        newBoard[0][7] = new Pawn(0, 7, "BR2", PawnType.ROOK, PawnColor.BLACK);

        newBoard[7][0] = new Pawn(7, 0, "WR1", PawnType.ROOK, PawnColor.WHITE);
        newBoard[7][1] = new Pawn(7, 1, "WKT1", PawnType.KNIGHT, PawnColor.WHITE);
        newBoard[7][2] = new Pawn(7, 2, "WB1", PawnType.BISHOP, PawnColor.WHITE);
        newBoard[7][3] = new Pawn(7, 3, "WQ", PawnType.QUEEN, PawnColor.WHITE);
        newBoard[7][4] = new Pawn(7, 4, "WK", PawnType.KING, PawnColor.WHITE);
        newBoard[7][5] = new Pawn(7, 5, "WB2", PawnType.BISHOP, PawnColor.WHITE);
        newBoard[7][6] = new Pawn(7, 6, "WKT2", PawnType.KNIGHT, PawnColor.WHITE);
        newBoard[7][7] = new Pawn(7, 7, "WR2", PawnType.ROOK, PawnColor.WHITE);
        for (int i = 0; i < 8; i++) {
            newBoard[1][i] = new Pawn(1, i, "BP" + i, PawnType.PAWN, PawnColor.BLACK);
        }
        for (int i = 0; i < 8; i++) {
            newBoard[6][i] = new Pawn(6, i, "WP" + i, PawnType.PAWN, PawnColor.WHITE);
        }
        for (int y = 2; y < 6; y++) {
            for (int x = 0; x < 8; x++) {
                newBoard[y][x] = new Pawn(y, x, "000", PawnType.EMPY, PawnColor.EMPTY);
            }
        }
        return (newBoard);
    }
}
