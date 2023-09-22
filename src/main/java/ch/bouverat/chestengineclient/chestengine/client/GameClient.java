package ch.bouverat.chestengineclient.chestengine.client;

import ch.bouverat.chestengineclient.chestengine.Main;
import ch.bouverat.chestengineclient.chestengine.network.GamePlayerRequest;
import ch.bouverat.chestengineclient.chestengine.network.ServerRequestHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.net.URL;
import java.util.Objects;
import java.util.Scanner;

public class GameClient {
    int gameId;

    public GameClient (int gameId) {
        this.gameId = gameId;
    }

    public void run() {
        System.out.println("Game starting...");

        ServerRequestHandler serverRequestHandler = new ServerRequestHandler();
        GamePlayerRequest gamePlayerRequest = new GamePlayerRequest();
        Frame frame = new Frame();
        GraphicsContext graphicsContext = frame.CreateWindow();
        Pawn[][] gameBoard = serverRequestHandler.boardUpdateRequest();
        FrameThread frameThread = new FrameThread(frame, graphicsContext, gameBoard);
        frameThread.start();

        Thread thread = new Thread(() -> {
            while (true) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Entrer l'id du pion");
                Pawn pawn = getPawnById(scanner.nextLine(), gameBoard);

                System.out.println("Entrer la position Y :");
                int Y = scanner.nextInt();
                System.out.println("Entrer la position X :");
                int X = scanner.nextInt();

                gamePlayerRequest.movePawRequest(pawn, Y, X, gameBoard);
            }
        });
        thread.start();
    }

    private Pawn getPawnById(String pawnId, Pawn[][] board) {
        int y = 0;
        int x = 0;

        while (y < 8) {
            while (x < 8) {
                if (Objects.equals(board[y][x].getPawnId(), pawnId)) {
                    return (board[y][x]);
                }
                x ++;
            }
            x = 0;
            y ++;
        }
        return (null);
    }
}
