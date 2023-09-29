package ch.bouverat.chessengineclient.chessengine.client;

import ch.bouverat.chessengineclient.chessengine.network.ServerRequestHandler;

import java.io.IOException;
public class ClientGameManager {
    public boolean canPlay;

    public void getGameInformation() {
        try {
            canPlay =  ServerRequestHandler.getPlayerToPlay();
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }
}
