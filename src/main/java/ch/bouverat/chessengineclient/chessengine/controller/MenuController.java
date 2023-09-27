package ch.bouverat.chessengineclient.chessengine.controller;

import ch.bouverat.chessengineclient.chessengine.client.GameClient;
import ch.bouverat.chessengineclient.chessengine.Main;
import ch.bouverat.chessengineclient.chessengine.network.ServerRequestHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {
    @FXML
    private void joinGame () throws IOException {
        Stage menuStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/join.fxml"));
        MenuController menuController = new MenuController();
        Scene scene = new Scene(fxmlLoader.load());

        fxmlLoader.setController(menuController);
        menuStage.setScene(scene);
        menuStage.show();
    }

    @FXML
    private void createGame () {
        ServerRequestHandler serverRequestHandler = new ServerRequestHandler();
        String gameId = serverRequestHandler.gameCreationRequest();

        if (gameId != null) {
            GameClient gameClient = new GameClient(gameId);
            gameClient.run();
        }
    }
}