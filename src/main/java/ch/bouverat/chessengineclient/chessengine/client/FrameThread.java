package ch.bouverat.chessengineclient.chessengine.client;

import ch.bouverat.chessengineclient.chessengine.network.GamePlayerRequest;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FrameThread extends AnimationTimer {
    final private Frame frame;
    final private GraphicsContext graphicsContext;
    final private Pawn[][] board;
    final private ClientGameManager clientGameManager;

    public FrameThread (Frame frame, GraphicsContext graphicsContext, Pawn[][] board, ClientGameManager clientGameManager) {
        this.frame = frame;
        this.graphicsContext = graphicsContext;
        this.board = board;
        this.clientGameManager = clientGameManager;
    }

    @Override
    public void handle(long now) {
        frame.UpdateFrame(graphicsContext, board);
    }
}


