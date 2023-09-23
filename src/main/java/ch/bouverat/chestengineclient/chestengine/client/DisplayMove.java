package ch.bouverat.chestengineclient.chestengine.client;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DisplayMove {
    final GraphicsContext graphicsContext;
    Pawn[][] gameBoard;
    Pawn currentPawn;

    public DisplayMove (GraphicsContext graphicsContext, Pawn[][] gameBoard, Pawn currentPawn) {
        this.graphicsContext = graphicsContext;
        this.gameBoard = gameBoard;
        this.currentPawn = currentPawn;
    }

    public void drawMoves(int posY, int posX) {
        System.out.println("test");

        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect (38 + posX, 38 + posY, 24 ,24);
    }
}
