package ch.bouverat.chestengineclient.chestengine.client;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;

public class FrameThread extends AnimationTimer {
    final private Frame frame;
    final private GraphicsContext graphicsContext;
    final private Pawn[][] board;

    public FrameThread (Frame frame, GraphicsContext graphicsContext, Pawn[][] board) {
        this.frame = frame;
        this.graphicsContext = graphicsContext;
        this.board = board;
    }

    @Override
    public void handle(long now) {
        frame.UpdateFrame(graphicsContext, board);
    }
}


