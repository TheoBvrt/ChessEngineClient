package ch.bouverat.chessengineclient.chessengine.client;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DisplayMove {
    final GraphicsContext graphicsContext;
    Pawn[][] gameBoard;
    final PawnColor teamColor;

    public DisplayMove (GraphicsContext graphicsContext, Pawn[][] gameBoard, PawnColor teamColor) {
        this.graphicsContext = graphicsContext;
        this.gameBoard = gameBoard;
        this.teamColor = teamColor;
    }

    public void drawMoves(Pawn currentPawn) {
        List<int[]> posList = new ArrayList<>();

        if (currentPawn.getPawnType() == PawnType.PAWN)
            posList = simplePawn(currentPawn);
        if (currentPawn.getPawnType() == PawnType.KNIGHT)
            posList = knight(currentPawn);
        if (currentPawn.getPawnType() == PawnType.KING)
            posList = king(currentPawn);
        if (currentPawn.getPawnType() == PawnType.ROOK)
            posList = rook(currentPawn);
        if (currentPawn.getPawnType() == PawnType.BISHOP)
            posList = bishop(currentPawn);
        if (currentPawn.getPawnType() == PawnType.QUEEN)
            posList = queen(currentPawn);

        for (int[] pos : posList) {
            graphicsContext.setFill(Color.GREEN);
            graphicsContext.fillRect(pos[1] * 100 + 38, pos[0] * 100 + 38, 24, 24);
        }
    }

    private List<int[]> simplePawn(Pawn pawn) {
        List<int[]> movePosList = new ArrayList<>();

        int pawnPosY = pawn.getPawnPos()[0];
        int pawnPosX = pawn.getPawnPos()[1];

        if (pawn.getMoveCount() == 0) {
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
        if (pawn.posX - 1 > 0 && gameBoard[pawn.posY - 1][pawn.posX - 1].getPawnColor() == GameClient.enemiTeam) {
            movePosList.add(new int[] {pawn.posY - 1, pawn.posX - 1});
        }
        if (pawn.posX + 1 < 7 && gameBoard[pawn.posY  - 1][pawn.posX + 1].getPawnColor() == GameClient.enemiTeam) {
            movePosList.add(new int[] {pawn.posY - 1, pawn.posX + 1});
        }

        return (movePosList);
    }

    public List<int[]> knight(Pawn pawn) {
        int pawnPosY = pawn.getPawnPos()[0];
        int pawnPosX = pawn.getPawnPos()[1];
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
        List<int[]> movePosList = new ArrayList<>(Arrays.asList(posTab));
        movePosList.removeIf(pos -> pos[0] < 0 || pos[0] > 7 || pos[1] < 0 || pos[1] > 7);
        movePosList.removeIf(pos -> gameBoard[pos[0]][pos[1]].getPawnColor() == teamColor);

        return (movePosList);
    }

    public List<int[]> king(Pawn pawn) {
        int pawnPosY = pawn.getPawnPos()[0];
        int pawnPosX = pawn.getPawnPos()[1];
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
        List<int[]> movePosList = new ArrayList<>(Arrays.asList(posTab));
        movePosList.removeIf(pos -> pos[0] < 0 || pos[0] > 7 || pos[1] < 0 || pos[1] > 7);
        movePosList.removeIf(pos -> gameBoard[pos[0]][pos[1]].getPawnColor() == teamColor);


        return (movePosList);
    }

    public List<int[]> rook(Pawn pawn) {
        List<int[]> movePosList = new ArrayList<>();
        int[] dRows = {-1, 0, 1, 0};
        int[] dCols = {0, -1, 0, 1};

        for (int i = 0; i < 4; i++) {
            int currentY = pawn.getPawnPos()[0];
            int currentX = pawn.getPawnPos()[1];

            while (gameBoard[currentY][currentX].getPawnColor() != GameClient.enemiTeam) {
                currentY += dRows[i];
                currentX += dCols[i];
                boolean isInvalid = currentY < 0 || currentY > 7 || currentX < 0 || currentX > 7;
                if (isInvalid || gameBoard[currentY][currentX].getPawnColor() == GameClient.playerTeam) {
                    break;
                }
                movePosList.add(new int[] {currentY, currentX});
            }
        }
        return (movePosList);
    }

    public List<int[]> bishop(Pawn pawn) {
        List<int[]> movePosList = new ArrayList<>();
        int[] dRows = {-1, -1, 1, 1};
        int[] dCols = {1, -1, 1, -1};

        for (int i = 0; i < 4; i++) {
            int currentY = pawn.getPawnPos()[0];
            int currentX = pawn.getPawnPos()[1];

            while (gameBoard[currentY][currentX].getPawnColor() != GameClient.enemiTeam) {
                currentY += dRows[i];
                currentX += dCols[i];
                boolean isInvalid = currentY < 0 || currentY > 7 || currentX < 0 || currentX > 7;
                if (isInvalid || gameBoard[currentY][currentX].getPawnColor() == GameClient.playerTeam) {
                    break;
                }
                movePosList.add(new int[] {currentY, currentX});
            }
        }
        return (movePosList);
    }

    public List<int[]> queen(Pawn pawn) {
        List<int[]> movePosList = new ArrayList<>();
        int[] dRows = {-1, -1, 1, 1, -1, 0, 1, 0};
        int[] dCols = {1, -1, 1, -1, 0, -1, 0, 1};

        for (int i = 0; i < 8; i++) {
            int currentY = pawn.getPawnPos()[0];
            int currentX = pawn.getPawnPos()[1];

            while (gameBoard[currentY][currentX].getPawnColor() != GameClient.enemiTeam) {
                currentY += dRows[i];
                currentX += dCols[i];
                boolean isInvalid = currentY < 0 || currentY > 7 || currentX < 0 || currentX > 7;
                if (isInvalid || gameBoard[currentY][currentX].getPawnColor() == GameClient.playerTeam) {
                    break;
                }
                movePosList.add(new int[] {currentY, currentX});
            }
        }
        return (movePosList);
    }
}
