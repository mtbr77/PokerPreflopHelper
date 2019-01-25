import java.util.*;

//TODO pool of  1326 cards
public class HoldemHoleCards extends HoleCards {
    private Boolean isSuited;
    private int hi,lo;
    public int rangeTableRow, rangeTableColumn;// row and column in range table
    //number format like 13h2c
    public HoldemHoleCards(Collection<String> cards) {
        super(cards);
        init();
        //TODO add calculation of strenght of hand
    }

    private void init() {
        isSuited = this.cards.get(0).getSuit() == this.cards.get(1).getSuit();
        hi = this.cards.get(0).getValue();
        lo = this.cards.get(1).getValue();
        rangeTableRow = isSuited ? hi : lo;
        rangeTableColumn = isSuited ? lo : hi;
    }

    public HoldemHoleCards(String c1, String c2) {
        this(Arrays.asList(c1,c2));
    }

    public HoldemHoleCards(Card card1, Card card2) {
        cards.add(card1);
        cards.add(card2);
        super.trimAndSort();
        init();
    }

    public int getHi(){
        return hi;
    }

    public int getLo(){
        return lo;
    }

    public boolean inRange(HoldemRange holdemRange) {
        return holdemRange.contains(this);
    }

    public boolean isAx() {
        return hi == 14;
    }

    public boolean isPair() {
        return hi == lo;
    }

    public Boolean isSuited() {
        return isSuited;
    }

}
