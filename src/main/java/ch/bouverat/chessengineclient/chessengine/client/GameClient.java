package ch.bouverat.chessengineclient.chessengine.client;

import ch.bouverat.chessengineclient.chessengine.network.GamePlayerRequest;
import ch.bouverat.chessengineclient.chessengine.network.ServerRequestHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class GameClient {
    static public PawnColor playerTeam;
    static public PawnColor enemiTeam;
    static public String uuid;
    static public String gameId;
    static public int playerNumber;

    private Pawn pawnToMove;
    boolean posBool = false;


    public void run() {
        System.out.println("Game starting...");
        GamePlayerRequest gamePlayerRequest = new GamePlayerRequest();
        Frame frame = new Frame();
        Pawn[][] gameBoard = new Pawn[8][8];

        String startingMap = ServerRequestHandler.getMapJson();
        System.out.println(startingMap);
        gamePlayerRequest.getMap(gameBoard, startingMap);

        GraphicsContext graphicsContext = frame.CreateWindow();

        Thread updateThread = new Thread(() -> {
            String mapJson = "";
            while (true) {
                ClientGameManager newClient = new ClientGameManager();
                newClient.getGameInformation();
                mapJson = ServerRequestHandler.getMapJson();
                gamePlayerRequest.getMap(gameBoard, mapJson);
                if (playerTeam == PawnColor.BLACK) {
                    ClientUtils.reverseTab(gameBoard);
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.fillInStackTrace();
                }
            }
        });

        updateThread.start();
        FrameThread frameThread = new FrameThread(frame, graphicsContext, gameBoard);
        frameThread.start();
        DisplayMove displayMove = new DisplayMove(frame.possibleMoveCanvas.getGraphicsContext2D(), gameBoard, playerTeam);
        mouseClickController(frame, gameBoard, gamePlayerRequest, displayMove);
    }


    private void mouseClickController(Frame frame, Pawn[][] gameBoard, GamePlayerRequest gamePlayerRequest, DisplayMove displayMove) {
        frame.hudCanvas.setOnMouseClicked((MouseEvent event) -> {
            ClientGameManager clientGameManager = new ClientGameManager();
            clientGameManager.getGameInformation();
            if (event.getButton() == MouseButton.PRIMARY && clientGameManager.canPlay) {
                int mouseX = ClientUtils.getHundreds((int) event.getX());
                int mouseY = ClientUtils.getHundreds((int) event.getY());

                if (!posBool) {
                    frame.DrawOuterLine(mouseY, mouseX, Color.RED);
                    Pawn pawnSelected = gameBoard[mouseY / 100][mouseX / 100];
                    if (pawnSelected.getPawnType() != PawnType.EMPY && pawnSelected.getPawnColor() == playerTeam) {
                        Pawn currentPawn = gameBoard[mouseY / 100][mouseX / 100];
                        if (currentPawn.pawnColor == playerTeam && currentPawn.getPawnType() != PawnType.EMPY) {
                            pawnToMove = currentPawn;
                        }
                        displayMove.drawMoves(currentPawn);
                    }
                } else {
                    if (pawnToMove != null && gameBoard[mouseY / 100][mouseX / 100].pawnColor != playerTeam) {
                        gamePlayerRequest.movePawnRequest(pawnToMove, mouseY / 100, mouseX / 100, gameBoard);
                        try {
                            if (playerTeam == PawnColor.BLACK)
                                ClientUtils.reverseTab(gameBoard);
                            gamePlayerRequest.sendMap(gameBoard);
                        } catch (IOException e) {
                            e.fillInStackTrace();
                        }
                        pawnToMove = null;
                    }
                    frame.clearHud();
                    frame.clearPossibleMove();
                }
            } else {
                pawnToMove = null;
                frame.clearHud();
                frame.clearPossibleMove();
            }
            posBool = !posBool;
        });
    }
}
