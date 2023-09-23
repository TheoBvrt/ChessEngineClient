package ch.bouverat.chestengineclient.chestengine.network;

import ch.bouverat.chestengineclient.chestengine.client.Pawn;
import ch.bouverat.chestengineclient.chestengine.client.PawnColor;
import ch.bouverat.chestengineclient.chestengine.client.PawnType;
import javafx.geometry.Pos;

public class GamePlayerRequest {
    public int movePawRequest(Pawn pawn, int posY, int posX, Pawn[][] board) {
        board[pawn.getPawnPos()[0]][pawn.getPawnPos()[1]] = new Pawn(pawn.getPawnPos()[0], pawn.getPawnPos()[1], "000", PawnType.EMPY, PawnColor.EMPTY);
        pawn.movePawn(posY, posX);
        board[posY][posX] = pawn;
        return (0);
    }
}
