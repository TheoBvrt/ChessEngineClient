module ch.bouverat.chestengineclient.chestengine {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens ch.bouverat.chestengineclient.chestengine to javafx.fxml;
    exports ch.bouverat.chestengineclient.chestengine;
    exports ch.bouverat.chestengineclient.chestengine.controller;
    opens ch.bouverat.chestengineclient.chestengine.controller to javafx.fxml;
    exports ch.bouverat.chestengineclient.chestengine.network;
    opens ch.bouverat.chestengineclient.chestengine.network to javafx.fxml;
    exports ch.bouverat.chestengineclient.chestengine.client;
    opens ch.bouverat.chestengineclient.chestengine.client to javafx.fxml;
}