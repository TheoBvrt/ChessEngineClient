package ch.bouverat.chessengineclient.chessengine.network;

import ch.bouverat.chessengineclient.chessengine.client.Pawn;
import ch.bouverat.chessengineclient.chessengine.client.PawnColor;
import ch.bouverat.chessengineclient.chessengine.client.PawnType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GamePlayerRequest {
    public int movePawnRequest(Pawn pawn, int posY, int posX, Pawn[][] board) {
        MoveChecker moveChecker = new MoveChecker(pawn, posY, posX, board);
        if (moveChecker.checkMove() == 0) {
            board[pawn.getPawnPos()[0]][pawn.getPawnPos()[1]] = new Pawn(pawn.getPawnPos()[0], pawn.getPawnPos()[1], "000", PawnType.EMPY, PawnColor.EMPTY);
            pawn.movePawn(posY, posX);
            board[posY][posX] = pawn;
            pawn.addMoveCount();
            System.out.println("Pawn has been moved !");
            return (0);
        }
        System.out.println("Illegal move !");
        return (1);
    }

    public void updateTab(Pawn[][] board) {
        String[][] tabToSend = new String[8][8];

        convertBoardToArray(tabToSend, board);

        Gson gson = new GsonBuilder().create();
        String jsonArray = gson.toJson(tabToSend);

        String[][] newBoard = gson.fromJson(jsonArray, String[][].class);

        Pawn[][] boardToCopy = convertArrayToBoard(newBoard);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = boardToCopy[i][j];
            }
        }
    }

    private void convertBoardToArray(String[][] tabToConvert, Pawn[][] board) {
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
