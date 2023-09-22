package ch.bouverat.chestengineclient.chestengine.client;

public class Pawn {
    int posY;
    int posX;
    final PawnColor pawnColor;
    final String folder;
    final private String pawnId;
    final private PawnType pawnType;

    public Pawn(int posY, int posX, String pawnId, PawnType pawnType, PawnColor color) {
        this.posY = posY;
        this.posX = posX;
        this.pawnId = pawnId;
        this.pawnType = pawnType;

        if (color == PawnColor.BLACK) {
            pawnColor = PawnColor.BLACK;
            folder = "/black-pawns";
        } else {
            pawnColor = PawnColor.WHITE;
            folder = "/white-pawns";
        }
    }

    public String getPawnId () {
        return pawnId;
    }
    public PawnType getPawnType () {
        return pawnType;
    }

    public int[] getPawnPos() {
        int[] pos = new int[2];

        pos[0] = posY;
        pos[1] = posX;
        return (pos);
    }

    public void movePawn(int newPosY, int newPosX) {
        posY = newPosY;
        posX = newPosX;
    }
}
