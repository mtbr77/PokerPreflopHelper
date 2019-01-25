import java.util.*;

public class Game {
    private String currency; //USD
    private String modifier; // zoom
    private int numberOfSeats;
    //private List<String> listOfPlayers = new ArrayList<String>(9);
    //private Set<Integer> seats;
    private Map<Byte,String> playerSeats = new TreeMap<Byte, String>();
    private GameTable gameTable;
    //private String id;
    private GameRound round;
    private HoleCards holeCards;
    private int position;
    private int raiserPosition;

    public Game(GameTable gameTable) {
        this.gameTable = gameTable;
    }

    public HoleCards getHoleCards() {
        return holeCards;
    }

    public boolean setHoleCards(Collection<String> cards) {
        if (holeCards != null && holeCards.wasParsed(cards))
            return false;
        holeCards = create(cards);
        return true;
    }

    private HoleCards create(Collection<String> cards) {
        switch (cards.size()) {
            case 2: return new HoldemHoleCards(cards);
            case 4: return new OmahaHoleCards(cards);
        }

        return null;
    }

    public void setRound(GameRound round) {
        this.round = round;
    }
    public GameRound getRound() {
        return round;
    }

    public int getMyPosition() { //TODO sitouts
        if (position == -11)
            position = OCR.getHeroPosition(gameTable.getRectangle());
        return position;
    }

    public boolean isZoom() {
        return gameTable.isZoom();
    }

    public void setMyPosition(int i) {
        position = i;
    }

    public boolean isInPosition() {
        return getMyPosition() < getRaiserPosition();
    }

    public int getRaiserPosition() {
        if (raiserPosition == -11)
            raiserPosition = OCR.getRaiserPosition(gameTable.getRectangle());
        return raiserPosition;
    }

    public void setRaiserPosition(int raiserPosition) {
        this.raiserPosition = raiserPosition;
    }

}
