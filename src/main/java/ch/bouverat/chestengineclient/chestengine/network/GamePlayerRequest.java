package ch.bouverat.chestengineclient.chestengine.network;

import ch.bouverat.chestengineclient.chestengine.client.Pawn;
import ch.bouverat.chestengineclient.chestengine.client.PawnType;

public class GamePlayerRequest {
    public int movePawRequest(Pawn pawn, int posY, int posX, Pawn[][] board) {
        board[pawn.getPawnPos()[0]][pawn.getPawnPos()[1]] = new Pawn(posY, posX, "000", PawnType.EMPY);
        pawn.setPawnId(posY, posX);
        board[posY][posX] = pawn;
        return (0);
    }
}
