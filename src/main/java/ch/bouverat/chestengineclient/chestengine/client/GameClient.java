package ch.bouverat.chestengineclient.chestengine.client;

import ch.bouverat.chestengineclient.chestengine.network.GamePlayerRequest;
import ch.bouverat.chestengineclient.chestengine.network.ServerRequestHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.Objects;
import java.util.Scanner;

public class GameClient {
    int gameId;
    boolean posBool = false;
    Pawn pawnToMove;

    PawnColor playerTeam = PawnColor.WHITE;
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

        frame.hudCanvas.setOnMouseClicked((MouseEvent event) -> {
            int mouseX = ClientUtils.getHundreds((int) event.getX());
            int mouseY = ClientUtils.getHundreds((int) event.getY());

            if (!posBool) {
                frame.DrawOuterLine(mouseY, mouseX, Color.RED);
                Pawn currentPawn = gameBoard[mouseY / 100][mouseX / 100];
                if (currentPawn.pawnColor == playerTeam) {
                    pawnToMove = currentPawn;
                }
            } else {
                if (pawnToMove != null && gameBoard[mouseY / 100][mouseX / 100].pawnColor != playerTeam) {
                    gamePlayerRequest.movePawRequest(pawnToMove, mouseY / 100, mouseX / 100, gameBoard);
                    pawnToMove = null;
                }
                frame.clearDisplay(frame.hudCanvas.getGraphicsContext2D());
            }
            posBool = !posBool;
        });

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
