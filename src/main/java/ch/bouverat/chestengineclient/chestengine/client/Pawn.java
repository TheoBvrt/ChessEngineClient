package ch.bouverat.chestengineclient.chestengine.client;

public class Pawn {
    int posY;
    int posX;

    int moveCount = 0;
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
        } else if (color == PawnColor.WHITE){
            pawnColor = PawnColor.WHITE;
            folder = "/white-pawns";
        } else {
            pawnColor = PawnColor.EMPTY;
            folder = "empty";
        }
    }

    public String getPawnId () {
        return pawnId;
    }
    public PawnType getPawnType () {
        return pawnType;
    }
    public PawnColor getPawnColor () {
        return pawnColor;
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

    public void addMoveCount () {
        moveCount ++;
    }

    public int getMoveCount() {
        return (moveCount);
    }
}
