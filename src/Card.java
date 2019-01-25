import java.util.Comparator;

public class Card implements Comparable<Card> {
    private int value;
    private char suit;

    //any format Ah, 14h
    public Card(String card) {
        int indexOfSuite = card.length() - 1 ;
        String value = card.substring(0, indexOfSuite);
        try {
            this.value = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            this.value = valueToInt(value);
        }
        suit = card.charAt(indexOfSuite);
    }

    public static int valueToInt(String value) {
        switch (value) {
            case "A": return 14;
            case "K": return 13;
            case "Q": return 12;
            case "J": return 11;
            case "T": return 10;
            case "x": return 0;
            default : return Integer.parseInt(value);
        }
    }

    public static int valueToInt(char value) {
        switch (value) {
            case 'A': return 14;
            case 'K': return 13;
            case 'Q': return 12;
            case 'J': return 11;
            case 'T': return 10;
            case 'x': return 0;
            default : return Character.getNumericValue(value);
        }
    }

    public boolean greater(Card card) {
        return value > card.value;
    }

    public boolean less(Card card) {
        return value < card.value;
    }

    public boolean greaterOrEqual(Card card) {
        return value >= card.value;
    }

    public boolean lessOrEqual(Card card) {
        return value <= card.value;
    }

    public boolean weakEqual(Card card) {
        return value == card.value;
    }

    @Override
    public int compareTo(Card card) {
        return value - card.value;
    }

    @Override
    public boolean equals(Object obj) {
        Card oCard = (Card) obj;
        return value == oCard.value && suit == oCard.suit;
    }

    public int getValue() {
        return value;
    }

    public char getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        return String.valueOf(value) + suit;
    }
}

class CardValueComparator implements Comparator<Card> {
    @Override
    public int compare(Card c1, Card c2) {
        return c2.getValue() - c1.getValue();
    }
}

class CardSuitComparator implements Comparator<Card> {
    @Override
    public int compare(Card c1, Card c2) {
        return c2.getSuit() - c1.getSuit();
    }
}