package ch.bouverat.chessengineclient.chessengine.client;

import ch.bouverat.chessengineclient.chessengine.Main;
import ch.bouverat.chessengineclient.chessengine.network.GamePlayerRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class Frame {
    public Canvas gameCanvas;
    public Canvas hudCanvas;
    public Canvas possibleMoveCanvas;

    public GraphicsContext CreateWindow (Pawn[][] board) {
        Stage gameStage = new Stage();
        gameStage.setTitle("Game");
        gameCanvas = new Canvas(800, 800);
        hudCanvas = new Canvas(800, 800);
        possibleMoveCanvas = new Canvas(800, 800);
        GraphicsContext graphicsContext = gameCanvas.getGraphicsContext2D();
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(gameCanvas,possibleMoveCanvas ,hudCanvas);
        Scene scene = new Scene(stackPane);
        scene.setOnKeyPressed(event -> {
            String mapJson = "";
            if (event.getCode() == javafx.scene.input.KeyCode.W) {
                try {
                    String url = "http://localhost:8080/api/game/update";
                    URL obj = new URL(url);
                    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                    con.setRequestMethod("POST");
                    con.setRequestProperty("Content-Type", "text/plain");
                    con.setDoOutput(true);

                    try (OutputStream os = con.getOutputStream()) {
                        byte[] input = GameClient.gameId.getBytes("utf-8");
                        os.write(input, 0, input.length);
                    }

                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    mapJson = response.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            GamePlayerRequest gamePlayerRequest = new GamePlayerRequest();
            gamePlayerRequest.getMap(board, mapJson);
        });
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
                frameX += 100;
            }
            color = !color;
            frameX = 0;
            frameY += 100;
        }

        DrawPawn(graphicsContext, gameTab);
    }

    private void DrawCell(int frameY, int frameX, GraphicsContext graphicsContext, boolean color) {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
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
                }

                frameX += 100;
            }
            frameX = 18;
            frameY += 100;
        }
    }

    public void DrawOuterLine (int posY, int posX, Color color) {
        GraphicsContext graphicsContext = hudCanvas.getGraphicsContext2D();

        for (int y = 0; y < 100; y++) {
            for (int x = 0; x < 100; x++) {
                if ((x <= 5) || (y <= 5) || (x >= 94) || (y >= 94)) {
                    graphicsContext.setFill(color);
                    graphicsContext.fillRect(x + posX, y + posY, 1, 1);
                }
            }
        }
    }

    public void clearHud () {
        hudCanvas.getGraphicsContext2D().clearRect(0, 0, 800, 800);
    }
    public void clearPossibleMove () {
        possibleMoveCanvas.getGraphicsContext2D().clearRect(0, 0, 800, 800);
    }

}
