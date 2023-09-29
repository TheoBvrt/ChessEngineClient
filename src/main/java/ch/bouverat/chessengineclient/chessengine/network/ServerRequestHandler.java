package ch.bouverat.chessengineclient.chessengine.network;

import ch.bouverat.chessengineclient.chessengine.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerRequestHandler {

    public static String gameCreationRequest() {
        String gameId = "";
        try {
            String url = "http://localhost:8080/api/game/start";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "text/plain");
            con.setDoOutput(true);

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = GameClient.uuid.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            gameId = response.toString();
            con.disconnect();
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return (gameId);
    }

    public static String getMapJson() {
        String mapJson = "";
        try {
            String url = "http://localhost:8080/api/game/update";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "text/plain");
            con.setDoOutput(true);

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = GameClient.gameId.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            mapJson = response.toString();
            con.disconnect();
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return (mapJson);
    }

    public static boolean getPlayerToPlay() throws IOException {
        boolean canPlay = false;
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
        return (canPlay);
    }
}
