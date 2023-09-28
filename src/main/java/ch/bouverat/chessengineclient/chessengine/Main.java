package ch.bouverat.chessengineclient.chessengine;

import ch.bouverat.chessengineclient.chessengine.client.GameClient;
import ch.bouverat.chessengineclient.chessengine.controller.MenuController;
import ch.bouverat.chessengineclient.chessengine.network.GamePlayerRequest;
import ch.bouverat.chessengineclient.chessengine.network.ServerRequestHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.UUID;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        GameClient.uuid = UUID.randomUUID().toString();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/menu.fxml"));
        MenuController menuController = new MenuController();
        Scene scene = new Scene(fxmlLoader.load());
        fxmlLoader.setController(menuController);
        primaryStage.setTitle("ChestEngine");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}