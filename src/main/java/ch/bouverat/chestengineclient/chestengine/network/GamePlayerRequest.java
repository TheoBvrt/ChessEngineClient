package ch.bouverat.chestengineclient.chestengine.network;

import ch.bouverat.chestengineclient.chestengine.client.Pawn;
import ch.bouverat.chestengineclient.chestengine.client.PawnColor;
import ch.bouverat.chestengineclient.chestengine.client.PawnType;

public class GamePlayerRequest {
    public int movePawnRequest(Pawn pawn, int posY, int posX, Pawn[][] board) {
        MoveChecker moveChecker = new MoveChecker(pawn, posY, posX, board);
        if (moveChecker.checkMove() == 0) {
            board[pawn.getPawnPos()[0]][pawn.getPawnPos()[1]] = new Pawn(pawn.getPawnPos()[0], pawn.getPawnPos()[1], "000", PawnType.EMPY, PawnColor.EMPTY);
            pawn.movePawn(posY, posX);
            board[posY][posX] = pawn;
            pawn.addMoveCount();
            System.out.println("Pawn has been moved !");
            return (0);
        }
        System.out.println("Illegal move !");
        return (1);
    }


}
