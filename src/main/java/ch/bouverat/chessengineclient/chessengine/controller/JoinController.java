package ch.bouverat.chessengineclient.chessengine.controller;

import ch.bouverat.chessengineclient.chessengine.client.ClientUtils;
import ch.bouverat.chessengineclient.chessengine.client.GameClient;
import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;

public class JoinController {

    @FXML
    private TextField inputGameId;

    @FXML
    public void joinGame() throws IOException {
        URL url = new URL("http://localhost:8080/api/game/join");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);

        String gameId = inputGameId.getText();

        String json = String.format("{\"gameId\":\"%s\",\"player2Ip\":\"%s\"}", gameId, ClientUtils.getMacAdress());

        try (OutputStream os = con.getOutputStream()) {
            byte[] input = json.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println("Réponse du serveur : " + response);
        }

        con.disconnect();

        if (gameId != null) {
            GameClient gameClient = new GameClient();
            GameClient.gameId = gameId;
            gameClient.run();
        }
    }
}
