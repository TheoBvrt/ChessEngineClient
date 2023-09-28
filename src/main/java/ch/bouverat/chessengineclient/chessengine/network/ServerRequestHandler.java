package ch.bouverat.chessengineclient.chessengine.network;

import ch.bouverat.chessengineclient.chessengine.client.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerRequestHandler {

    public Pawn[][] boardUpdateRequest() {
        Pawn[][] newBoard = new Pawn[8][8];
        newBoard[0][0] = new Pawn(0, 0, "BR1", PawnType.ROOK, PawnColor.BLACK);
        newBoard[0][1] = new Pawn(0, 1, "BKT1", PawnType.KNIGHT, PawnColor.BLACK);
        newBoard[0][2] = new Pawn(0, 2, "BB1", PawnType.BISHOP, PawnColor.BLACK);
        newBoard[0][3] = new Pawn(0, 3, "BQ", PawnType.QUEEN, PawnColor.BLACK);
        newBoard[0][4] = new Pawn(0, 4, "BK", PawnType.KING, PawnColor.BLACK);
        newBoard[0][5] = new Pawn(0, 5, "BB2", PawnType.BISHOP, PawnColor.BLACK);
        newBoard[0][6] = new Pawn(0, 6, "BKT2", PawnType.KNIGHT, PawnColor.BLACK);
        newBoard[0][7] = new Pawn(0, 7, "BR2", PawnType.ROOK, PawnColor.BLACK);

        newBoard[7][0] = new Pawn(7, 0, "WR1", PawnType.ROOK, PawnColor.WHITE);
        newBoard[7][1] = new Pawn(7, 1, "WKT1", PawnType.KNIGHT, PawnColor.WHITE);
        newBoard[7][2] = new Pawn(7, 2, "WB1", PawnType.BISHOP, PawnColor.WHITE);
        newBoard[7][3] = new Pawn(7, 3, "WQ", PawnType.QUEEN, PawnColor.WHITE);
        newBoard[7][4] = new Pawn(7, 4, "WK", PawnType.KING, PawnColor.WHITE);
        newBoard[7][5] = new Pawn(7, 5, "WB2", PawnType.BISHOP, PawnColor.WHITE);
        newBoard[7][6] = new Pawn(7, 6, "WKT2", PawnType.KNIGHT, PawnColor.WHITE);
        newBoard[7][7] = new Pawn(7, 7, "WR2", PawnType.ROOK, PawnColor.WHITE);
        for (int i = 0; i < 8; i++) {
            newBoard[1][i] = new Pawn(1, i, "BP" + (i + 1), PawnType.PAWN, PawnColor.BLACK);
        }
        for (int i = 0; i < 8; i++) {
            newBoard[6][i] = new Pawn(6, i, "WP" + (i + 1), PawnType.PAWN, PawnColor.WHITE);
        }
        for (int y = 2; y < 6; y++) {
            for (int x = 0; x < 8; x++) {
                newBoard[y][x] = new Pawn(y, x, "000", PawnType.EMPY, PawnColor.EMPTY);
            }
        }
        return (newBoard);
    }

    public String gameCreationRequest() {
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
            int respCode = con.getResponseCode();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            //System.out.println(respCode);
            gameId = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (gameId);
    }
}
