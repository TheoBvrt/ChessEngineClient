package ch.bouverat.chestengineclient.chestengine.network;

import ch.bouverat.chestengineclient.chestengine.client.GameClient;
import ch.bouverat.chestengineclient.chestengine.client.Pawn;
import ch.bouverat.chestengineclient.chestengine.client.PawnType;
import java.util.ArrayList;
import java.util.List;

public class MoveChecker {

    Pawn pawnToCheck;
    Pawn[][] gameBoard;

    int destY;
    int destX;

    public MoveChecker(Pawn pawn, int destY, int destX, Pawn[][] gameBoard) {
        this.pawnToCheck = pawn;
        this.destY = destY;
        this.destX = destX;
        this.gameBoard = gameBoard;
    }

    public int checkMove() {
        if (pawnToCheck.getPawnType() == PawnType.PAWN) {
            return (checkSimplePawn());
        }

        return (0);
    }

    public int checkSimplePawn() {
        List<int[]> movePosList = new ArrayList<>();
        int pawnPosY = pawnToCheck.getPawnPos()[0];
        int pawnPosX = pawnToCheck.getPawnPos()[1];

        if (pawnToCheck.getMoveCount() == 0) {
            int[] newPosition = {pawnPosY - 1, pawnPosX};
            int[] newPosition2 = {pawnPosY - 2, pawnPosX};
            movePosList.add(newPosition);
            if (gameBoard[newPosition[0]][newPosition[1]].getPawnType() == PawnType.EMPY) {
                movePosList.add(newPosition2);
            }
        } else {
            int[] newPositon = {pawnPosY - 1, pawnPosX};
            movePosList.add(newPositon);
        }

        movePosList.removeIf(pos -> gameBoard[pos[0]][pos[1]].getPawnType() != PawnType.EMPY);

        if (gameBoard[pawnPosY - 1][pawnPosX - 1].getPawnColor() == GameClient.enemiTeam) {
            movePosList.add(new int[] {pawnPosY - 1, pawnPosX - 1});
        }

        if (gameBoard[pawnPosY - 1][pawnPosX + 1].getPawnColor() == GameClient.enemiTeam) {
            movePosList.add(new int[] {pawnPosY - 1, pawnPosX + 1});
        }

        for (int[] pos : movePosList) {
            if (destY == pos[0] && destX == pos[1]) {
                return (0);
            }
        }
        return (1);
    }
}
