package ch.bouverat.chessengineclient.chessengine.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ClientGameManager {
    public boolean canPlay;

    public void getGameInformation() {
        try {
            getPlayerToPlay();
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }

    private void getPlayerToPlay() throws IOException {
        URL url = new URL("http://localhost:8080/api/game/get-player-to-play");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "text/plain");
        con.setDoOutput(true);

        String body = GameClient.gameId;

        try (OutputStream os = con.getOutputStream()) {
            byte[] input = body.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            if (GameClient.playerNumber == 0) {
                canPlay = response.toString().equals("0");
            }

            if (GameClient.playerNumber == 1) {
                canPlay = response.toString().equals("1");
            }
        }

        con.disconnect();
    }
}
