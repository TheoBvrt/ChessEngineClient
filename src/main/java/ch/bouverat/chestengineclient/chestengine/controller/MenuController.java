package ch.bouverat.chestengineclient.chestengine.controller;

import ch.bouverat.chestengineclient.chestengine.client.GameClient;
import ch.bouverat.chestengineclient.chestengine.Main;
import ch.bouverat.chestengineclient.chestengine.network.ServerRequestHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {
    @FXML
    private void joinGame () throws IOException {
        Stage menuStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("join.fxml"));
        MenuController menuController = new MenuController();
        Scene scene = new Scene(fxmlLoader.load());

        fxmlLoader.setController(menuController);
        menuStage.setScene(scene);
        menuStage.show();
    }

    @FXML
    private void createGame () {
        ServerRequestHandler serverRequestHandler = new ServerRequestHandler();
        int gameId = serverRequestHandler.gameCreationRequest();

        if (gameId != -1) {
            GameClient gameClient = new GameClient(gameId);
            gameClient.run();
        }
    }
}