package ch.bouverat.chestengineclient.chestengine.client;

import ch.bouverat.chestengineclient.chestengine.Main;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;

public class Frame {
    final int cellSize = 100;

    public GraphicsContext CreateWindow () {
        Stage gameStage = new Stage();
        gameStage.setTitle("Game");
        Canvas canvas = new Canvas(800, 800);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        Pane root = new Pane(canvas);
        Scene scene = new Scene(root);
        gameStage.setScene(scene);
        gameStage.show();
        return (graphicsContext);
    }

    public void UpdateFrame(GraphicsContext graphicsContext, Pawn[][] gameTab) {
        int frameY = 0;
        int frameX = 0;
        boolean color = true;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                DrawCell(frameY, frameX, graphicsContext, color);
                color = !color;
                frameX += cellSize;
            }
            color = !color;
            frameX = 0;
            frameY += cellSize;
        }
        DrawPawn(graphicsContext, gameTab);
    }

    private void DrawCell(int frameY, int frameX, GraphicsContext graphicsContext, boolean color) {
        for (int i = 0; i < cellSize; i++) {
            for (int j = 0; j < cellSize; j++) {
                if (color)
                    graphicsContext.setFill(Color.BLUEVIOLET);
                else
                    graphicsContext.setFill(Color.PINK);
                graphicsContext.fillRect(i + frameX, j + frameY, 1, 1);
            }
        }
    }

    private void DrawPawn(GraphicsContext graphicsContext, Pawn[][] board) {
        int frameY = 18;
        int frameX = 18;
        URL imageUrl;

        for (int posY = 0; posY < board.length; posY++) {
            for (int posX = 0; posX < board.length; posX++) {
                PawnType currentPawnType = board[posY][posX].getPawnType();
                Pawn currentPawn = board[posY][posX];

                switch (currentPawnType) {
                    case ROOK -> imageUrl = Main.class.getResource(currentPawn.folder + "/rook.png");
                    case KNIGHT -> imageUrl = Main.class.getResource(currentPawn.folder + "/knight.png");
                    case BISHOP -> imageUrl = Main.class.getResource(currentPawn.folder + "/bishop.png");
                    case QUEEN ->  imageUrl = Main.class.getResource(currentPawn.folder + "/queen.png");
                    case KING -> imageUrl = Main.class.getResource(currentPawn.folder + "/king.png");
                    default -> imageUrl = Main.class.getResource(currentPawn.folder + "/pawn.png");
                }

                if (currentPawnType != PawnType.EMPY) {
                    assert imageUrl != null;
                    Image image = new Image(imageUrl.toExternalForm());
                    graphicsContext.drawImage(image, frameX, frameY);
                    frameX += cellSize;
                }
            }
            frameX = 18;
            frameY += cellSize;
        }
    }
}
