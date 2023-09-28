package ch.bouverat.chessengineclient.chessengine.client;

import ch.bouverat.chessengineclient.chessengine.network.GamePlayerRequest;
import ch.bouverat.chessengineclient.chessengine.network.ServerRequestHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.UUID;

public class GameClient {
    boolean posBool = false;
    Pawn pawnToMove;

    static public PawnColor playerTeam = PawnColor.WHITE;
    static public PawnColor enemiTeam = PawnColor.BLACK;
    static public String uuid;
    static public String gameId;


    public void run() {
        System.out.println("Game starting...");
        ServerRequestHandler serverRequestHandler = new ServerRequestHandler();
        GamePlayerRequest gamePlayerRequest = new GamePlayerRequest();
        Frame frame = new Frame();
        Pawn[][] gameBoard = serverRequestHandler.boardUpdateRequest();
        GraphicsContext graphicsContext = frame.CreateWindow(gameBoard);

        FrameThread frameThread = new FrameThread(frame, graphicsContext, gameBoard);
        frameThread.start();
        DisplayMove displayMove = new DisplayMove(frame.possibleMoveCanvas.getGraphicsContext2D(), gameBoard, playerTeam);
        mouseClickController(frame, gameBoard, gamePlayerRequest,displayMove);
    }


    private void mouseClickController (Frame frame, Pawn[][] gameBoard, GamePlayerRequest gamePlayerRequest, DisplayMove displayMove) {
        frame.hudCanvas.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton() == MouseButton.PRIMARY) {
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
