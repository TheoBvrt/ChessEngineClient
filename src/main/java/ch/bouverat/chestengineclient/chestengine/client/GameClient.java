package ch.bouverat.chestengineclient.chestengine.client;

import ch.bouverat.chestengineclient.chestengine.network.GamePlayerRequest;
import ch.bouverat.chestengineclient.chestengine.network.ServerRequestHandler;

import java.util.Objects;

public class GameClient {
    int gameId;

    public GameClient (int gameId) {
        this.gameId = gameId;
    }

    public void run() {
        ServerRequestHandler serverRequestHandler = new ServerRequestHandler();

        Pawn[][] gameBoard = serverRequestHandler.boardUpdateRequest();
        System.out.println("Game starting...");
        for (Pawn[] pawns : gameBoard) {
            for (int j = 0; j < gameBoard.length; j++) {
                System.out.print(pawns[j].getPawnId() + " ");
            }
            System.out.println();
        }
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
