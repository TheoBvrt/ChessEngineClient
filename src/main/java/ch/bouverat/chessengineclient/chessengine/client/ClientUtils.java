package ch.bouverat.chessengineclient.chessengine.client;

public class ClientUtils {
    public static int getHundreds(int nb){
        while (nb % 100 != 0) {
            nb --;
        }
        return (nb);
    }
}