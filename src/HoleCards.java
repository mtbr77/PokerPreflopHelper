import java.util.*;

public abstract class HoleCards implements Comparable<HoleCards> {
    //TODO private?
    protected ArrayList<Card> cards = new ArrayList<>(7);
    protected String rawCards;

    public HoleCards(Collection<String> cards) {
        rawCards = cards.toString();
        cards.stream().map(Card::new).forEach(this.cards::add);
        trimAndSort();
    }

    protected void trimAndSort() {
        this.cards.trimToSize();
        Collections.sort(this.cards, new CardValueComparator());
    }

    protected HoleCards() {
    }

    public int compareTo(HoleCards hCards)
    {
        for (Card card : cards) {
            int i = cards.indexOf(card);
            if (card.less(hCards.cards.get(i))) {
                return -1;
            }

            if (card.greater(hCards.cards.get(i))) {
                return 1;
            }
        }

        return 0;
    }

    public boolean equals(Object o) {
        HoleCards hCards = (HoleCards) o;
        for (int i = 0; i < cards.size() ; i++) {
            if (!cards.get(i).equals(hCards.cards.get(i)))
                return false;
        }

        return true;
    }

    public boolean wasParsed(Collection<String> rawCards) {
        return this.rawCards.equals(rawCards.toString());
    }

    @Override
    public String toString() {
        return cards.toString();
    }
}
