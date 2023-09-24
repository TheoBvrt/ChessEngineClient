package ch.bouverat.chestengineclient.chestengine.client;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
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
        List<int[]> posList;

        if (currentPawn.getPawnType() == PawnType.PAWN) {
            posList = simplePawn(currentPawn);
            for (int[] pos : posList) {
                graphicsContext.setFill(Color.RED);
                graphicsContext.fillRect(pos[1] * 100, pos[0] * 100, 24, 24);
            }
        }
    }

    private List<int[]> simplePawn(Pawn pawn) {
        List<int[]> movePosList = new ArrayList<>();

        if (pawn.moveCount == 0) {
            int[] newPosition = {pawn.posY - 1, pawn.posX};
            int[] newPosition2 = {pawn.posY - 2, pawn.posX};
            movePosList.add(newPosition);
            if (gameBoard[newPosition[0]][newPosition[1]].getPawnType() == PawnType.EMPY) {
                movePosList.add(newPosition2);
            }
        } else{
            int[] newPositon = {pawn.posY - 1, pawn.posX};
            movePosList.add(newPositon);
        }

        movePosList.removeIf(pos -> gameBoard[pos[0]][pos[1]].getPawnType() != PawnType.EMPY);

        if (gameBoard[pawn.posY  - 1][pawn.posX - 1].getPawnColor() == GameClient.enemiTeam) {
            movePosList.add(new int[] {pawn.posY - 1, pawn.posX - 1});
        }

        if (gameBoard[pawn.posY  - 1][pawn.posX + 1].getPawnColor() == GameClient.enemiTeam) {
            movePosList.add(new int[] {pawn.posY - 1, pawn.posX + 1});
        }

        return (movePosList);
    }
}
