package ch.bouverat.chessengineclient.chessengine.network;

import ch.bouverat.chessengineclient.chessengine.client.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class GamePlayerRequest {
    public void movePawnRequest(Pawn pawn, int posY, int posX, Pawn[][] board) {
        MoveChecker moveChecker = new MoveChecker(pawn, posY, posX, board);
        if (moveChecker.checkMove() == 0) {
            board[pawn.getPawnPos()[0]][pawn.getPawnPos()[1]] = new Pawn(pawn.getPawnPos()[0], pawn.getPawnPos()[1], "000", PawnType.EMPY, PawnColor.EMPTY);
            pawn.movePawn(posY, posX);
            board[posY][posX] = pawn;
            pawn.addMoveCount();
            System.out.println("Pawn has been moved !");
        }
        System.out.println("Illegal move !");
    }

    public void sendMap(Pawn[][] board) throws IOException {
        String[][] tabToSend = new String[8][8];
        convertBoardToArray(tabToSend, board);
        Gson gson = new GsonBuilder().create();
        String jsonArray = gson.toJson(tabToSend);

        URL url = new URL("http://localhost:8080/api/game/send-map");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("gameId", GameClient.gameId);
        jsonObject.addProperty("jsonMap", jsonArray);

        String json = jsonObject.toString();

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
    }

    public void getMap (Pawn[][] board, String jsonArray) {
        Gson gson = new GsonBuilder().create();
        String[][] newBoard = gson.fromJson(jsonArray, String[][].class);
        Pawn[][] boardToCopy = convertArrayToBoard(newBoard);
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(boardToCopy[i], 0, board[i], 0, board.length);
        }
    }

    public void convertBoardToArray(String[][] tabToConvert, Pawn[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                tabToConvert[i][j] = board[i][j].getPawnId();
            }
        }
    }

    private Pawn[][] convertArrayToBoard(String[][] tabToConvert){
        Pawn[][] board = new Pawn[8][8];
        for (int i = 0; i < tabToConvert.length; i++) {
            for (int j = 0; j < tabToConvert.length; j++) {
                board[i][j] = pawnToSet(tabToConvert[i][j], i, j);
            }
        }
        return board;
    }

    private Pawn pawnToSet(String pawnId, int Y, int X) {
        PawnColor newColorPawn = PawnColor.EMPTY;
        PawnType newPawnType = PawnType.EMPY;

        if (pawnId.charAt(0) == 'B')
            newColorPawn = PawnColor.BLACK;
        if (pawnId.charAt(0) == 'W')
            newColorPawn = PawnColor.WHITE;

        if (pawnId.indexOf("R", 1) != -1)
            newPawnType = PawnType.ROOK;
        else if (pawnId.indexOf("KT", 1) != -1)
            newPawnType = PawnType.KNIGHT;
        else if (pawnId.indexOf("B", 1) != -1)
            newPawnType = PawnType.BISHOP;
        else if (pawnId.indexOf("Q", 1) != -1)
            newPawnType = PawnType.QUEEN;
        else if (pawnId.indexOf("K", 1) != -1 && !pawnId.contains("KY"))
            newPawnType = PawnType.KING;
        else if (pawnId.indexOf("P", 1) != -1)
            newPawnType = PawnType.PAWN;

        return (new Pawn(Y, X, pawnId, newPawnType, newColorPawn));
    }
}
