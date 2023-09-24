package ch.bouverat.chestengineclient.chestengine.network;

import ch.bouverat.chestengineclient.chestengine.client.GameClient;
import ch.bouverat.chestengineclient.chestengine.client.Pawn;
import ch.bouverat.chestengineclient.chestengine.client.PawnColor;
import ch.bouverat.chestengineclient.chestengine.client.PawnType;

import java.util.ArrayList;
import java.util.Arrays;
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
        } else if (pawnToCheck.getPawnType() == PawnType.KNIGHT) {
            return (checkKnight());
        } else if (pawnToCheck.getPawnType() == PawnType.KING) {
            return (checkKing());
        } else if (pawnToCheck.getPawnType() == PawnType.ROOK) {
            return (checkRook());
        }
        return (0);
    }

    public int checkSimplePawn() {
        List<int[]> movePosList = new ArrayList<>();
        int pawnPosY = pawnToCheck.getPawnPos()[0];
        int pawnPosX = pawnToCheck.getPawnPos()[1];

        if (pawnToCheck.getMoveCount() == 0) {
            if (GameClient.playerTeam == PawnColor.WHITE) {
                int[] newPosition = {pawnPosY - 1, pawnPosX};
                int[] newPosition2 = {pawnPosY - 2, pawnPosX};
                movePosList.add(newPosition);
                if (gameBoard[newPosition[0]][newPosition[1]].getPawnType() == PawnType.EMPY) {
                    movePosList.add(newPosition2);
                }
            } else {
                int[] newPosition = {pawnPosY + 1, pawnPosX};
                int[] newPosition2 = {pawnPosY + 2, pawnPosX};
                movePosList.add(newPosition);
                if (gameBoard[newPosition[0]][newPosition[1]].getPawnType() == PawnType.EMPY) {
                    movePosList.add(newPosition2);
                }
            }
        } else {
            if (GameClient.playerTeam == PawnColor.WHITE) {
                int[] newPositon = {pawnPosY - 1, pawnPosX};
                movePosList.add(newPositon);
            } else {
                int[] newPositon = {pawnPosY + 1, pawnPosX};
                movePosList.add(newPositon);
            }
        }
        movePosList.removeIf(pos -> gameBoard[pos[0]][pos[1]].getPawnType() != PawnType.EMPY);

        if (GameClient.playerTeam == PawnColor.WHITE) {
            if (pawnPosX - 1 > 0 && gameBoard[pawnPosY - 1][pawnPosX - 1].getPawnColor() == GameClient.enemiTeam) {
                movePosList.add(new int[]{pawnPosY - 1, pawnPosX - 1});
            }

            if (pawnPosX + 1 < 7 && gameBoard[pawnPosY - 1][pawnPosX + 1].getPawnColor() == GameClient.enemiTeam) {
                movePosList.add(new int[]{pawnPosY - 1, pawnPosX + 1});
            }
        } else {
            if (pawnPosX + 1 < 7 && gameBoard[pawnPosY + 1][pawnPosX + 1].getPawnColor() == GameClient.enemiTeam) {
                movePosList.add(new int[]{pawnPosY + 1, pawnPosX + 1});
            }
            if (pawnPosX - 1 > 0 &&  gameBoard[pawnPosY + 1][pawnPosX - 1].getPawnColor() == GameClient.enemiTeam) {
                movePosList.add(new int[]{pawnPosY + 1, pawnPosX - 1});
            }
        }
        movePosList.removeIf(pos -> pos[0] < 0 || pos[0] > 7 || pos[1] < 0 || pos[1] > 7);
        for (int[] pos : movePosList) {
            if (destY == pos[0] && destX == pos[1]) {
                return (0);
            }
        }
        return (1);
    }

    public int checkKnight() {
        int pawnPosY = pawnToCheck.getPawnPos()[0];
        int pawnPosX = pawnToCheck.getPawnPos()[1];

        int[][] posTab = {
                {pawnPosY - 2, pawnPosX + 1},
                {pawnPosY + 1, pawnPosX + 2},
                {pawnPosY + 2, pawnPosX - 1},
                {pawnPosY - 1, pawnPosX - 2},

                {pawnPosY - 2, pawnPosX - 1},
                {pawnPosY + 1, pawnPosX - 2},
                {pawnPosY + 2, pawnPosX + 1},
                {pawnPosY - 1, pawnPosX + 2},
        };

        List<int[]> movePosList = new ArrayList<>(Arrays.stream(posTab).toList());
        movePosList.removeIf(pos -> pos[0] < 0 || pos[0] > 7 || pos[1] < 0 || pos[1] > 7);
        movePosList.removeIf(pos -> gameBoard[pos[0]][pos[1]].getPawnColor() == GameClient.playerTeam);

        for (int[] pos : movePosList) {
            if (destY == pos[0] && destX == pos[1]) {
                return (0);
            }
        }
        return (1);
    }

    public int checkKing() {
        int pawnPosY = pawnToCheck.getPawnPos()[0];
        int pawnPosX = pawnToCheck.getPawnPos()[1];

        int[][] posTab = {
                {pawnPosY - 1, pawnPosX - 1},
                {pawnPosY - 1, pawnPosX},
                {pawnPosY - 1, pawnPosX + 1},

                {pawnPosY, pawnPosX - 1},
                {pawnPosY, pawnPosX + 1},

                {pawnPosY + 1, pawnPosX - 1},
                {pawnPosY + 1, pawnPosX},
                {pawnPosY + 1, pawnPosX + 1},
        };

        List<int[]> movePosList = new ArrayList<>(Arrays.stream(posTab).toList());
        movePosList.removeIf(pos -> pos[0] < 0 || pos[0] > 7 || pos[1] < 0 || pos[1] > 7);
        movePosList.removeIf(pos -> gameBoard[pos[0]][pos[1]].getPawnColor() == GameClient.playerTeam);

        for (int[] pos : movePosList) {
            if (destY == pos[0] && destX == pos[1]) {
                return (0);
            }
        }
        return (1);
    }

    public int checkRook() {
        List<int[]> movePosList = new ArrayList<>();
        int pawnPosY = pawnToCheck.getPawnPos()[0] - 1;
        int pawnPosX = pawnToCheck.getPawnPos()[1];
        while (pawnPosY >= 0 && gameBoard[pawnPosY][pawnPosX].getPawnColor() != GameClient.playerTeam) {
            if (gameBoard[pawnPosY][pawnPosX].getPawnColor() == GameClient.enemiTeam) {
                movePosList.add(new int[]{pawnPosY, pawnPosX});
                break;
            }
            movePosList.add(new int[] {pawnPosY, pawnPosX});
            pawnPosY --;
        }

        pawnPosY = pawnToCheck.getPawnPos()[0];
        pawnPosX = pawnToCheck.getPawnPos()[1] + 1;
        while (pawnPosX <= 7 && gameBoard[pawnPosY][pawnPosX].getPawnColor() != GameClient.playerTeam) {
            if (gameBoard[pawnPosY][pawnPosX].getPawnColor() == GameClient.enemiTeam) {
                movePosList.add(new int[] {pawnPosY, pawnPosX});
                break;
            }
            movePosList.add(new int[] {pawnPosY, pawnPosX});
            pawnPosX ++;
        }

        pawnPosY = pawnToCheck.getPawnPos()[0];
        pawnPosX = pawnToCheck.getPawnPos()[1] - 1;
        while (pawnPosX >= 0 && gameBoard[pawnPosY][pawnPosX].getPawnColor() != GameClient.playerTeam) {
            if (gameBoard[pawnPosY][pawnPosX].getPawnColor() == GameClient.enemiTeam) {
                movePosList.add(new int[] {pawnPosY, pawnPosX});
                break;
            }
            movePosList.add(new int[] {pawnPosY, pawnPosX});
            pawnPosX --;
        }

        pawnPosY = pawnToCheck.getPawnPos()[0] + 1;
        pawnPosX = pawnToCheck.getPawnPos()[1];
        while (pawnPosY <= 7 && gameBoard[pawnPosY][pawnPosX].getPawnColor() != GameClient.playerTeam) {
            if (gameBoard[pawnPosY][pawnPosX].getPawnColor() == GameClient.enemiTeam) {
                movePosList.add(new int[] {pawnPosY, pawnPosX});
                break;
            }
            movePosList.add(new int[] {pawnPosY, pawnPosX});
            pawnPosY ++;
        }

        movePosList.removeIf(pos -> pos[0] < 0 || pos[0] > 7 || pos[1] < 0 || pos[1] > 7);

        for (int[] pos : movePosList) {
            if (destY == pos[0] && destX == pos[1]) {
                return (0);
            }
        }

        return (1);
    }
}
