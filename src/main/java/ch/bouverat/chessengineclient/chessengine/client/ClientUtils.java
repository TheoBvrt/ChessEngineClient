package ch.bouverat.chessengineclient.chessengine.client;

public class ClientUtils {
    public static int getHundreds(int nb){
        while (nb % 100 != 0) {
            nb --;
        }
        return (nb);
    }

    public static void reverseTab(Pawn[][] gameBoard) {
        Pawn[] line0 = gameBoard[0];
        Pawn[] line1 = gameBoard[1];
        Pawn[] line2 = gameBoard[2];
        Pawn[] line3 = gameBoard[3];
        Pawn[] line4 = gameBoard[4];
        Pawn[] line5 = gameBoard[5];
        Pawn[] line6 = gameBoard[6];
        Pawn[] line7 = gameBoard[7];

        gameBoard[0] = line7;
        gameBoard[1] = line6;
        gameBoard[2] = line5;
        gameBoard[3] = line4;
        gameBoard[4] = line3;
        gameBoard[5] = line2;
        gameBoard[6] = line1;
        gameBoard[7] = line0;

        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard.length; j++) {
                if (GameClient.playerTeam == PawnColor.BLACK) {
                    gameBoard[i][j].posY = switch (gameBoard[i][j].posY) {
                        case 7 -> 0;
                        case 6 -> 1;
                        case 5 -> 2;
                        case 4 -> 3;
                        case 3 -> 4;
                        case 2 -> 5;
                        case 1 -> 6;
                        case 0 -> 7;
                        default -> gameBoard[i][j].posY;
                    };
                }
            }
        }
    }
}