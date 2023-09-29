package ch.bouverat.chessengineclient.chessengine.controller;

import ch.bouverat.chessengineclient.chessengine.client.GameClient;
import ch.bouverat.chessengineclient.chessengine.Main;
import ch.bouverat.chessengineclient.chessengine.client.PawnColor;
import ch.bouverat.chessengineclient.chessengine.network.ServerRequestHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MenuController {
    @FXML
    private void joinGame () throws IOException {
        Stage menuStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/join.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        menuStage.setScene(scene);
        menuStage.show();
    }

    @FXML
    private void createGame () {
        String gameId = ServerRequestHandler.gameCreationRequest();

        if (gameId != null) {

            GameClient gameClient = new GameClient();
            GameClient.gameId = gameId;
            GameClient.playerTeam = PawnColor.WHITE;
            GameClient.enemiTeam = PawnColor.BLACK;
            GameClient.playerNumber = 0;
            gameClient.run();
        }
    }
}