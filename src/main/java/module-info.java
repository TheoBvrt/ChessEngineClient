module ch.bouverat.chestengineclient.chestengine {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.java_websocket;


    opens ch.bouverat.chessengineclient.chessengine to javafx.fxml;
    exports ch.bouverat.chessengineclient.chessengine;
    exports ch.bouverat.chessengineclient.chessengine.controller;
    opens ch.bouverat.chessengineclient.chessengine.controller to javafx.fxml;
    exports ch.bouverat.chessengineclient.chessengine.network;
    opens ch.bouverat.chessengineclient.chessengine.network to javafx.fxml;
    exports ch.bouverat.chessengineclient.chessengine.client;
    opens ch.bouverat.chessengineclient.chessengine.client to javafx.fxml;
}