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
    boolean posBool = false;
    Pawn pawnToMove;

    static public PawnColor playerTeam;
    static public PawnColor enemiTeam;
    static public String uuid;
    static public String gameId;
    static public int playerNumber;

    ClientGameManager clientGameManager;


    public void run() {
        System.out.println("Game starting...");
        ServerRequestHandler serverRequestHandler = new ServerRequestHandler();
        GamePlayerRequest gamePlayerRequest = new GamePlayerRequest();
        Frame frame = new Frame();
        Pawn[][] gameBoard = serverRequestHandler.boardUpdateRequest();

        if (playerNumber == 0) {
            try {
                gamePlayerRequest.sendMap(gameBoard);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            String mapJson = "";
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
            gamePlayerRequest.getMap(gameBoard, mapJson);
        }

        GraphicsContext graphicsContext = frame.CreateWindow(gameBoard);
        clientGameManager = new ClientGameManager();

        Thread updateThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String mapJson = "";
                while (true) {
                    if (clientGameManager.canPlay) {
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
                        GamePlayerRequest gamePlayerRequest = new GamePlayerRequest();
                        gamePlayerRequest.getMap(gameBoard, mapJson);
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        updateThread.start();

        FrameThread frameThread = new FrameThread(frame, graphicsContext, gameBoard, clientGameManager);
        frameThread.start();
        DisplayMove displayMove = new DisplayMove(frame.possibleMoveCanvas.getGraphicsContext2D(), gameBoard, playerTeam);
        mouseClickController(frame, gameBoard, gamePlayerRequest,displayMove);
    }


    private void mouseClickController (Frame frame, Pawn[][] gameBoard, GamePlayerRequest gamePlayerRequest, DisplayMove displayMove) {
        frame.hudCanvas.setOnMouseClicked((MouseEvent event) -> {
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
                            gamePlayerRequest.sendMap(gameBoard);
                        } catch (IOException e) {
                            e.printStackTrace();
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
