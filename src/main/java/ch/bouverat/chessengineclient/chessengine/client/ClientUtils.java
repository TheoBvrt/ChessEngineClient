package ch.bouverat.chessengineclient.chessengine.client;

public class ClientUtils {
    public static int getHundreds(int nb){
        while (nb % 100 != 0) {
            nb --;
        }
        return (nb);
    }

    public static void reverseTab(Pawn[][] gameBoard) {
        if (GameClient.playerTeam != PawnColor.BLACK) {
            return;
        }

        for (int i = 0; i < gameBoard.length / 2; i++) {
            Pawn[] temp = gameBoard[i];
            gameBoard[i] = gameBoard[gameBoard.length - 1 - i];
            gameBoard[gameBoard.length - 1 - i] = temp;
        }

        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length / 2; j++) {
                Pawn temp = gameBoard[i][j];
                gameBoard[i][j] = gameBoard[i][gameBoard[i].length - 1 - j];
                gameBoard[i][gameBoard[i].length - 1 - j] = temp;
            }
        }

        for (Pawn[] pawns : gameBoard) {
            for (Pawn pawn : pawns) {
                pawn.posY = 7 - pawn.posY;
                pawn.posX = 7 - pawn.posX;
            }
        }
    }
}