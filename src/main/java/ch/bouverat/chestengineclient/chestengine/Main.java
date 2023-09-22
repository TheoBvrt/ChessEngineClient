package ch.bouverat.chestengineclient.chestengine;

import ch.bouverat.chestengineclient.chestengine.client.Frame;
import ch.bouverat.chestengineclient.chestengine.controller.MenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
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