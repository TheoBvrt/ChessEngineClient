package ch.bouverat.chestengineclient.chestengine.client;

public class Pawn {
    int posY;
    int posX;
    final private String pawnId;
    final private PawnType pawnType;

    public Pawn(int posY, int posX, String pawnId, PawnType pawnType) {
        this.posY = posY;
        this.posX = posX;
        this.pawnId = pawnId;
        this.pawnType = pawnType;
    }

    public String getPawnId () {
        return pawnId;
    }
    public void setPawnId(int newPosY, int newPosX) {
        posY = newPosY;
        posX = newPosX;
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
